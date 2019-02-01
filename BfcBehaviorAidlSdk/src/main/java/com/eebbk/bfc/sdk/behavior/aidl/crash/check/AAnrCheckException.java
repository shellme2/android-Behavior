package com.eebbk.bfc.sdk.behavior.aidl.crash.check;

import android.content.Context;
import android.text.TextUtils;

import com.eebbk.bfc.sdk.behavior.aidl.utils.FileUtils;
import com.eebbk.bfc.sdk.behavior.aidl.utils.LogUtils;
import com.eebbk.bfc.sdk.behavior.aidl.utils.SharedPreferenceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author hesn
 * @function
 * @date 17-6-16
 * @company 步步高教育电子有限公司
 */

public abstract class AAnrCheckException extends ACheckException implements IReportException{
    private static final String TAG = "AAnrCheckException";
    private static final String ANR_FILE_NAME = "traces";// system_app_anr 和 data_app_anr
    private static final String ANR_FILE_PREFIX = "anr--";// 保存的文件前缀
    private static final String ANR_FILE_SUFFIX = ".txt";// 保存的文件后缀
    private static final String SHAREDPREF_ANR_TIME = "anr-time";
    private boolean isAutoFilter = true;

    @Override
    public String getExceptionType() {
        return CrashType.TYPE_ANR;
    }

    @Override
    protected String getSaveFilePrefix() {
        return ANR_FILE_PREFIX;
    }

    @Override
    protected String getSaveFileSuffix() {
        return ANR_FILE_SUFFIX;
    }

    @Override
    protected boolean findDropBoxPackage(String type, String lineContent) {
        return !TextUtils.isEmpty(lineContent) && lineContent.startsWith("Package:");
    }

    @Override
    protected String[] getDropboxTypes() {
        return new String[]{CrashType.DROP_BOX_TYPE_ANR_SYSTEM, CrashType.DROP_BOX_TYPE_ANR_DATA};
    }

    @Override
    public synchronized boolean check(Context context) {
        LogUtils.v(TAG, "start check anr");

        if(!checkParameter(context)){
            return false;
        }

        if (TextUtils.isEmpty(savePath)) {
            savePath = getDefaultSaveLogFilePath(context);
        }

        // dropbox中触发anr时间集合,用于和traces文件合并
        List<DropBoxInfo> dropBoxList = checkDropBox(context, savePath, true);

        // 检查data/anr/ 中有没有anr文件
        List<DropBoxInfo> mergedList = checkTraces(context, savePath, dropBoxList);

        // 过滤信息不全的anr
        List<DropBoxInfo> filteredList = filter(context, savePath, mergedList);

        // 上报
        report(filteredList, isReport);

        SharedPreferenceUtils.getInstance(context, SHAREDPREF_NAME).put(SHAREDPREF_ANR_TIME, System.currentTimeMillis());
        checkendEnd(context);
        return true;
    }

    @Override
    protected void checkendEnd(Context context) {
        //删除空文件夹。因为anr异常获取有分析traces文件，分析时是先缓存一份，
        // 如果不能保存到data/data/报名/ 下，则保存sd卡里，所以anr异常检查时，有可能在.crash文件夹中生成空的app包名文件夹
        File savePath = new File(this.savePath);
        if(FileUtils.isDir(savePath)){
            File[] files = savePath.listFiles();
            if(files == null || files.length <= 0){
                FileUtils.deleteDir(savePath);
                FileUtils.scanFile(context, this.savePath);
            }
        }
        //检查完删除缓存文件夹
        FileUtils.deleteDir(new File(getCachePath(context, this.savePath)));
        super.checkendEnd(context);
    }

    /**
     * 设置是否自动过滤anr信息
     * <p>
     *   1.无trace的anr,不上报
     *   2.trace中无app包名的,不上报
     * <p/>
     * @param isAutoFilter
     */
    public AAnrCheckException setAutoFilter(boolean isAutoFilter){
        this.isAutoFilter = isAutoFilter;
        return this;
    }

    /**
     * 要求没有堆栈信息的都自动排除掉
     * @param list
     * @return
     */
    private List<DropBoxInfo> filter(Context context, String savePath, List<DropBoxInfo> list){
        if(!isAutoFilter){
            return list;
        }
        if(list == null || list.isEmpty()){
            return list;
        }
        List<DropBoxInfo> filteredList = new ArrayList<>();
        for (DropBoxInfo dropBoxInfo : list) {
            if(dropBoxInfo == null){
                continue;
            }
            if(!containsTrace(dropBoxInfo.content, context.getPackageName())){
                String path = savePath + resolveLogFileName(null, dropBoxInfo.time);
                LogUtils.w(TAG, "do not contains my cmd, delete file:" + path);
                FileUtils.deleteFile(new File(path));
                FileUtils.scanFile(context, path);
                continue;
            }
            filteredList.add(dropBoxInfo);
        }
        return filteredList;
    }

    /**
     * 内容中是否有对应包名的trace信息
     * @param content
     * @param packageName
     * @return
     */
    private boolean containsTrace(String content, String packageName){
        return !TextUtils.isEmpty(content) && content.contains("Cmd line: " + packageName);
    }

    /**
     * 检查 data/anr/ 下有没有anr文件
     *
     * @param context
     * @param savePath
     * @return
     */
    private List<DropBoxInfo> checkTraces(Context context, String savePath, List<DropBoxInfo> dropBoxList) {
        LogUtils.v(TAG, "checkTraces()");
        if(dropBoxList == null || dropBoxList.size() <= 0){
            // 有traces文件不一定是anr,所以当有产生dropbox文件才检查traces文件
            LogUtils.v(TAG, "dropbox没有产生anr异常,不需要匹配traces文件.");
            return dropBoxList;
        }

        boolean hasTrace = true;
        for (DropBoxInfo dropBoxInfo : dropBoxList) {
            hasTrace = containsTrace(dropBoxInfo.content, context.getPackageName());
            if(!hasTrace){
                break;
            }
        }
        if(hasTrace){
            LogUtils.i(TAG, "all anr have trace.");
            return dropBoxList;
        }

        if (!isLogDirExists()) {
            LogUtils.i(TAG, "anr异常日志路径不存在.");
            return dropBoxList;
        }

        List<File> files = listFiles(context, new ANRFilenameFilter());
        if (files == null || files.size() <= 0) {
            LogUtils.i(TAG, "没有anr异常日志.");
            return dropBoxList;
        }

        // copy to cache
        String cachePath = getCachePath(context, savePath);
        FileUtils.createDirOrExists(cachePath);
        copy2CachePath(files, cachePath);

        // 缓存文件夹里的都从新获取一次,避免有上次提交失败的
        List<File> cacheFiles = FileUtils.listFilesInDirWithFilter(new File(cachePath), new ANRCacheFilenameFilter());
        if (cacheFiles == null || cacheFiles.isEmpty()) {
            LogUtils.i(TAG, "CacheFilePaths is empty.");
            return dropBoxList;
        }

        // check is my anr
        return findMyTraceFile(context, cacheFiles, dropBoxList, savePath);
    }

    private List<DropBoxInfo> findMyTraceFile(Context context, List<File> cacheFiles, List<DropBoxInfo> dropBoxList, String savePath) {
        List<DropBoxInfo> deleteList = new ArrayList<>();
        List<DropBoxInfo> mergedList = new ArrayList<>();
        long mergeOffset = 5000;
        long lastDealAnrTime = SharedPreferenceUtils.getInstance(context, SHAREDPREF_NAME).get(SHAREDPREF_ANR_TIME, 0L);
        for (File cacheFile : cacheFiles) {
            if (cacheFile == null) {
                continue;
            }
            TracesInfo tracesInfo = isMyAnrTraces(context, cacheFile, lastDealAnrTime);
            if (!tracesInfo.isMyAnr) {
                // 不是我的anr删掉
//                FileUtils.deleteFile(cacheFile);
                continue;
            }
            try {
                if (dropBoxList != null && dropBoxList.size() > 0) {
                    long tracesTime = getTimeStamp(tracesInfo.anrTime);
                    for (DropBoxInfo dropBoxInfo : dropBoxList) {
                        if(Math.abs(tracesTime - dropBoxInfo.time) <= mergeOffset){
                            LogUtils.d(TAG, "合并anr文件");
                            // 如果这两个文件触发时间在差不多的时间内,则合并成一个anr异常事件
                            String dropBoxFileName = savePath + resolveLogFileName(null, dropBoxInfo.time);
                            String stack = "***** traces *****\n" + tracesInfo.stack + dropBoxInfo.content;
                            FileUtils.writeFile(new File(dropBoxFileName), stack, false);
                            LogUtils.w(TAG, "Anr traces.txt append to:" + dropBoxFileName);
                            dropBoxInfo.content = stack;
                            deleteList.add(dropBoxInfo);
                            mergedList.add(dropBoxInfo);
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 拷贝完删掉
//            FileUtils.deleteFile(cacheFile);
        }

        dropBoxList.removeAll(deleteList);
        if (dropBoxList.size() <= 0) {
            return mergedList;
        }

        mergedList.addAll(dropBoxList);
        return mergedList;
    }

    /**
     * 回调上报大数据
     *
     * @param anrContentList
     */
    private void report(List<DropBoxInfo> anrContentList, boolean isReport) {
        if (!canReport(anrContentList, isReport)) {
            return;
        }
        for (DropBoxInfo info : anrContentList) {
            if (info == null || TextUtils.isEmpty(info.content)) {
                continue;
            }
            reportException(info.content, info.versionName);
            LogUtils.w(TAG, "anr:\n" + info.content);
        }
    }

    /**
     * 源文件拷贝到缓存路径
     * <p>
     * 这里面的不一定都是我的anr,需要后面分析
     * <p/>
     *
     * @param files
     * @param cachePath
     */
    private void copy2CachePath(List<File> files, String cachePath) {
        for (File file : files) {
            if (file == null) {
                continue;
            }
            LogUtils.d(TAG, "file.getName():" + file.getName());
            String cacheFilePath = cachePath + resolveLogFileName("" + System.currentTimeMillis(), 0);
            LogUtils.d(TAG, "cacheFileName:" + cacheFilePath);
            FileUtils.copyOrMoveFile(file, new File(cacheFilePath), false);
        }
    }

    private TracesInfo isMyAnrTraces(Context context, File file, long lastDealAnrTime) {
        BufferedReader bufferReader = null;
        TracesInfo info = new TracesInfo();
        StringBuilder cacheBuilder = new StringBuilder(100);
        StringBuilder stackBuilder = new StringBuilder(100);
        try {
            bufferReader = new BufferedReader(new FileReader(file));
            String readline;
            String pidAndTime = "";
            String lineSeparator = "\n";
            boolean findCmdPackage = false;
            boolean findMainThreads = false;
            while ((readline = bufferReader.readLine()) != null) {
                if (findTracesPidTime(readline)) {
                    // 记录下发生时间,如果是自己的anr,可以直接拿做文件名
                    findCmdPackage = false;
                    findMainThreads = false;
                    cacheBuilder.setLength(0);
                    pidAndTime = readline;
                    LogUtils.d(TAG, "pidAndTime:" + pidAndTime);
                    continue;
                }
                if (findTracesCmdPackage(context, readline)) {
                    LogUtils.d(TAG, "findCmdPackage() :" + readline);
                    long anrTime = getTimeStamp(cutTime(pidAndTime));
                    if (anrTime == -1) {
                        //没与获取到时间,不科学!只能拿文件最后修改时间
                        anrTime = file.lastModified();
                    }
                    if (anrTime <= lastDealAnrTime) {
                        // 已经采集过了
                        findCmdPackage = false;
                    } else {
                        findCmdPackage = true;
                        cacheBuilder.append(pidAndTime).append(lineSeparator);
                        cacheBuilder.append(readline).append(lineSeparator);
                    }
                    continue;
                }
                if (!findCmdPackage) {
                    // 不是我的锅,跳过
                    continue;
                }
                if (findTracesMainThreads(readline)) {
                    findMainThreads = true;
                    LogUtils.d(TAG, "findMainThreads:" + readline);
                }
                if (!findMainThreads) {
                    // 不是main堆栈的不记录
                    continue;
                }
                if (findMainThreadsEnd(readline)) {
                    // main堆栈记录结束
                    cacheBuilder.append(readline).append(lineSeparator).append(lineSeparator);
                    stackBuilder.append(cacheBuilder);
                    info.anrTime = cutTime(pidAndTime);
                    findCmdPackage = false;
                    findMainThreads = false;
                    cacheBuilder.setLength(0);
                    pidAndTime = "";
                    LogUtils.d(TAG, "findMainThreadsEnd:" + readline);
                    continue;
                }
                if (!TextUtils.isEmpty(readline) && readline.contains("android.os.MessageQueue.next")) {
                    // 在main中出现android.os.MessageQueue.next
                    // 说明主线程在等待下条消息进入消息队列,一般不是他自己导致的
                    findCmdPackage = false;
                    findMainThreads = false;
                    cacheBuilder.setLength(0);
                    pidAndTime = "";
                    LogUtils.d(TAG, "has \"android.os.MessageQueue.next\"");
                    continue;
                }
                cacheBuilder.append(readline).append(lineSeparator);
            }

            if (stackBuilder.length() > 0) {
                info.isMyAnr = true;
                info.stack = stackBuilder.toString();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            FileUtils.closeIO(bufferReader);
        }

        LogUtils.i(TAG, "isMyAnr:" + info.isMyAnr);
        return info;
    }

    private String getCachePath(Context context, String savePath) {
        try {
            // getCacheDir()有试过返回空,系统同事让自己判断,呵呵
            // /data/data/包名/cache/behavior/
            return context.getCacheDir().getAbsolutePath() + File.separator + "behavior" + File.separator;
        } catch (Exception e) {
            e.printStackTrace();
        }
        String cacheDirName = "cache";
        String separator = "";
        if (!savePath.endsWith(File.separator)) {
            separator = File.separator;
        }
        FileUtils.createDirOrExists(savePath);
        // sd卡/.crash/包名/cache/
        return savePath + separator + cacheDirName + File.separator;
    }

    /**
     * 源anr文件匹配过滤
     */
    static class ANRFilenameFilter implements FilenameFilter {

        @Override
        public boolean accept(File dir, String filename) {
            return !TextUtils.isEmpty(filename) && filename.contains(ANR_FILE_NAME);
        }
    }

    /**
     * 自己缓存的anr文件匹配过滤
     */
    static class ANRCacheFilenameFilter implements FilenameFilter {

        @Override
        public boolean accept(File dir, String filename) {
            return !TextUtils.isEmpty(filename) && filename.startsWith(ANR_FILE_PREFIX) && filename.endsWith(ANR_FILE_SUFFIX);
        }
    }
}
