package com.eebbk.bfc.sdk.behavior.aidl.crash.check;

import android.content.Context;
import android.os.DropBoxManager;
import android.text.TextUtils;

import com.eebbk.bfc.sdk.behavior.aidl.listener.InnerListener;
import com.eebbk.bfc.sdk.behavior.aidl.utils.AppUtils;
import com.eebbk.bfc.sdk.behavior.aidl.utils.FileUtils;
import com.eebbk.bfc.sdk.behavior.aidl.utils.LogUtils;
import com.eebbk.bfc.sdk.behavior.aidl.utils.SharedPreferenceUtils;
import com.eebbk.bfc.sdk.behavior.aidl.utils.StorageUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * @author hesn
 * @function
 * @date 17-6-16
 * @company 步步高教育电子有限公司
 */

public abstract class ACheckException implements ICheckException {
    private static final String TAG = "ACheckException";
    private static final String SRC_DROPBOX_LOG_PATH = "/data/anr/";
    protected static final String SHAREDPREF_NAME = "bfc-behavior-crash";
    protected static final String SHAREDPREF_NAME_FILE_SUFFIX = "-file";
    private static final String SHAREDPREF_NAME_DROP_BOX_TYPE = "drop_box_";
    private static final String SDCARD_PATH = "/mnt/sdcard";
    protected static final String DEFAULT_SAVE_LOG_PATH = SDCARD_PATH
            + File.separator + ".crash" + File.separator;
    protected String savePath;
    protected boolean isReport = true;
    protected InnerListener mInnerListener;

    private static final String TRACES_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    //匹配进程名
    private static final Pattern TRACE_CMD_LINE = Pattern.compile("Cmd\\sline:\\s(\\S+)");
    //匹配进程触发时间
    private static final Pattern TRACE_PID_LINE = Pattern.compile("-{5}\\spid\\s\\d+\\sat\\s\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}\\s-{5}");
    //匹配main进程信息
    private static final Pattern TRACE_MAIN_LINE = Pattern.compile("^\"main\"");

    /**
     * 保持文件名前缀
     *
     * @return
     */
    protected abstract String getSaveFilePrefix();

    /**
     * 保持文件名后缀
     *
     * @return
     */
    protected abstract String getSaveFileSuffix();

    /**
     * 要检查的异常类型
     *
     * @return
     */
    protected abstract String[] getDropboxTypes();

    /**
     * 匹配找有应用报名的规则
     *
     * @param type
     * @param lineContent
     * @return
     */
    protected abstract boolean findDropBoxPackage(String type, String lineContent);

    /**
     * 不采集设置的这个时间戳之前的异常信息
     *
     * @param context
     * @param timeMillis
     */
    @Override
    public ICheckException ignoreBefore(Context context, long timeMillis) {
        String[] types = getDropboxTypes();
        if (types == null || types.length <= 0) {
            LogUtils.i(TAG, "ignoreBefore() types == null");
            return this;
        }
        for (String type : types) {
            // dorpbox
            ignoreBefore(context, SHAREDPREF_NAME_DROP_BOX_TYPE + type, timeMillis);
        }
        // other file, 目前只有anr用到这个
        ignoreBefore(context, getExceptionType() + SHAREDPREF_NAME_FILE_SUFFIX, timeMillis);
        return this;
    }

    @Override
    public ICheckException setListener(InnerListener innerListener) {
        mInnerListener = innerListener;
        return this;
    }

    /**
     * 查找异常日志的路径 (可以重写修改)
     *
     * @return
     */
    public String getLogPath() {
        return SRC_DROPBOX_LOG_PATH;
    }

    public boolean isLogDirExists() {
        return !TextUtils.isEmpty(getLogPath()) && FileUtils.isDir(getLogPath());
    }

    public List<File> listFiles(Context context, FilenameFilter filter) {
        List<File> files = FileUtils.listFilesInDirWithFilter(new File(getLogPath()), filter);

        if (files == null || files.size() <= 0) {
            LogUtils.i(getExceptionType(), "没有异常日志.");
            return Collections.emptyList();
        }

        return fileExpiredFilter(context, files);
    }

    /**
     * 过滤已经上报过的异常日志
     *
     * @param context
     * @param srcFiles
     * @return
     */
    public List<File> fileExpiredFilter(Context context, List<File> srcFiles) {
        List<File> files = new ArrayList<>();
        // 有的机器会生成多个traces文件,旧的会将文件名改为"-N",所以只判断文件修改时间还不够,只能过滤部分情况
        long lastDealTime = SharedPreferenceUtils.getInstance(context, SHAREDPREF_NAME).get(getExceptionType() + SHAREDPREF_NAME_FILE_SUFFIX, 0L);
        for (File file : srcFiles) {
            if (lastDealTime < file.lastModified()) {
                files.add(file);
            }
        }
        return files;
    }

    /**
     * 检查 data/system/dropbox/ 下有没有anr文件
     *
     * @param context
     * @param savePath
     * @return
     */
    protected List<DropBoxInfo> checkDropBox(Context context, String savePath) {
        return checkDropBox(context, savePath, false);
    }

    /**
     * 检查 data/system/dropbox/ 下有没有anr文件
     *
     * @param context
     * @param savePath
     * @param isGetDateFromContent 是否需要从内容中获取时间
     * @return
     */
    protected List<DropBoxInfo> checkDropBox(Context context, String savePath, boolean isGetDateFromContent) {
        List<DropBoxInfo> infos = new ArrayList<>();
        LogUtils.v(TAG, "checkDropBox()");
        String[] types = getDropboxTypes();
        if (types == null || types.length <= 0) {
            LogUtils.i(TAG, "types == null");
            return infos;
        }
        DropBoxManager dropBoxManager = (DropBoxManager) context.getSystemService(Context.DROPBOX_SERVICE);
        if (dropBoxManager == null) {
            LogUtils.w(TAG, "dropBoxManager == null");
            return infos;
        }
        StringBuilder content = new StringBuilder(100);
        String lineSeparator = "\n";
        String readline;
        for (String type : types) {
            LogUtils.v(TAG, "type:" + type);
            long lastCheckTime = SharedPreferenceUtils.getInstance(context, SHAREDPREF_NAME).get(SHAREDPREF_NAME_DROP_BOX_TYPE + type, 0L);
            while (true) {
                // 获取 lastAnrTime 的下一个 DROP_BOX_ANR_TYPE 类型异常的文件
                DropBoxManager.Entry entry = dropBoxManager.getNextEntry(type, lastCheckTime);
                if (entry == null) {
                    // 没有下一个anr了
                    break;
                }
                content.setLength(0);
                boolean isMyProcess = false;
                boolean hasGetExactTime = false;
                // entry.getTimeMillis()是dropbox的保存时间，不是异常触发的时间
                long time = entry.getTimeMillis();
                lastCheckTime = time;
                content.append("***** ").append(type).append(" *****").append("\n")
                        .append("# dropbox save time: ").append(getDate(time)).append("\n")
                        .append("# ").append(type)
                        .append("\n\n");
                InputStream inputStream = null;
                BufferedReader bufferReader = null;
                DropBoxInfo dropBoxInfo = new DropBoxInfo();
                try {
                    inputStream = entry.getInputStream();
                    bufferReader = new BufferedReader(new InputStreamReader(inputStream));
                    while ((readline = bufferReader.readLine()) != null) {
                        content.append(readline).append(lineSeparator);
                        if (isMyProcess) {
                            if(!isGetDateFromContent || hasGetExactTime){
                                continue;
                            }
                            // 需要获取准确时间，但是还没有获取到
                            long exactTime = getDataFromDropContent(readline);
                            if(exactTime != -1){
                                hasGetExactTime = true;
                                time = exactTime;
                            }
                            // 已经验证是我的锅,那就只拷贝内容准备上报
                            continue;
                        }
                        if (findDropBoxPackage(type, readline)) {
                            if (!TextUtils.isEmpty(readline) && readline.contains(" " + context.getApplicationContext().getPackageName() + " ")) {
                                dropBoxInfo.versionName = getVersionName(readline);
                                isMyProcess = true;
                            } else {
                                // 找到了包名,但是不是我的锅
                                break;
                            }
                        }
                    }
                    if (isMyProcess) {
                        dropBoxInfo.time = time;
                        // 这个 >>> <<< 貌似影响json序列化 尤其是 <
                        dropBoxInfo.content = content.toString().replaceAll(">", "*").replaceAll("<", "*");
                        save2File(context, type, savePath, dropBoxInfo);
                        infos.add(dropBoxInfo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    FileUtils.closeIO(bufferReader);
                    FileUtils.closeIO(inputStream);
                    FileUtils.closeIO(entry);
                }
                SharedPreferenceUtils.getInstance(context, SHAREDPREF_NAME).put(SHAREDPREF_NAME_DROP_BOX_TYPE + type, lastCheckTime);
            }
        }
        return infos;
    }

    /**
     * 从内容中获取准确的触发时间（不一定有）
     * @param readline
     * @return
     */
    private long getDataFromDropContent(String readline){
        if (findTracesPidTime(readline)) {
            // 记录下发生时间,如果是自己的anr,可以直接拿做文件名
            return getTimeStamp(cutTime(readline));
        }
        return -1;
    }

    private String getVersionName(String content){
        // Package: com.eebbk.bfc.app.bfcbehavior v29 (BFCBehaviorV4.00_20170717_0940)
        // Package: com.eebbk.behavior.demo
        if(TextUtils.isEmpty(content)){
            return null;
        }
        String versionName = null;
        String[] strs = content.split(" ");
        if(strs != null && strs.length >= 4){
            String str = strs[3];
            versionName = str.replaceAll("\\(","").replaceAll("\\)","");
        }
        return versionName;
    }

    private void save2File(Context context, String type, String savePath, DropBoxInfo dropBoxInfo) throws IOException {
        String fileName = savePath + resolveLogFileName(null, dropBoxInfo.time);
        FileUtils.createFileOrExists(fileName);
        FileUtils.writeFile(new File(fileName), dropBoxInfo.content, false);
        LogUtils.w(TAG, "dropbox " + type + " save to:" + fileName);
        FileUtils.scanFile(context, fileName);
    }

    /**
     * 获取保持的文件名
     *
     * @param timeStr
     * @param time
     * @return
     */
    protected String resolveLogFileName(String timeStr, long time) {
        if (TextUtils.isEmpty(timeStr)) {
            try {
                Calendar now = Calendar.getInstance();
                if (time > 0) {
                    now.setTimeInMillis(time);
                }
                // Calendar return month is start from 0.
                return String.format(Locale.CHINA, getSaveFilePrefix() + "%d-%d-%d-%d.%d.%d%s",
                        now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1,
                        now.get(Calendar.DATE), now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE), now.get(Calendar.SECOND), getSaveFileSuffix());
            } catch (Exception e) {
                return null;
            }
        } else {
            String newName = timeStr.replaceAll(" ", "-");
            return TextUtils.concat(getSaveFilePrefix(), newName, getSaveFileSuffix()).toString();
        }
    }

    protected boolean checkParameter(Context context) {
        if (context == null) {
            LogUtils.w(TAG, "context == null, 无法检查ANR异常!");
            recovery();
            return false;
        }

        if (!AppUtils.isSystemApp(context, context.getPackageName())
                && (StorageUtils.getExternalStorageAvailableSize() / (1024 * 1024) < 30
                || StorageUtils.getDataAvailableSize() / (1024 * 1024) < 30)) {
            LogUtils.w(TAG, "当前内部或者外部存储控件少于30M,无法检查anr异常!");
            recovery();
            return false;
        }

        return true;
    }

    protected String getDefaultSaveLogFilePath(Context context) {
        return DEFAULT_SAVE_LOG_PATH + (context == null ? "" : (context.getPackageName() + File.separator));
    }

    /**
     * 检查完毕
     */
    protected void checkendEnd(Context context) {
        SharedPreferenceUtils.getInstance(context, SHAREDPREF_NAME).put(getExceptionType() + SHAREDPREF_NAME_FILE_SUFFIX, System.currentTimeMillis());
        recovery();
    }

    protected void recovery(){
        // 回调接口每次调用完都销毁,下次检查再从新设置,避免泄漏
        mInnerListener = null;
    }

    public String getSavePath() {
        return savePath;
    }

    public ACheckException setSavePath(String savePath) {
        this.savePath = savePath;
        return this;
    }

    public boolean isReport() {
        return isReport;
    }

    public ACheckException setReport(boolean report) {
        isReport = report;
        return this;
    }

    protected boolean canReport(List list, boolean isReport) {
        if (!isReport) {
            LogUtils.w(TAG, "没有开启 " + getExceptionType() + " 上报");
            return false;
        }
        if (list == null || list.isEmpty()) {
            LogUtils.d(TAG, "无" + getExceptionType() + "异常记录");
            return false;
        }
        return true;
    }

    /**
     * 找进程号和触发时间
     * <p>
     * eg:----- pid 15878 at 2017-06-15 16:47:24 -----
     * </p>
     *
     * @param readline
     * @return
     */
    protected boolean findTracesPidTime(String readline) {
        return TRACE_PID_LINE.matcher(readline).find();
    }

    /**
     * 找cmd包名
     * <p>
     * eg:Cmd line: com.eebbk.behavior.demo
     * </p>
     *
     * @param readline
     * @return
     */
    protected boolean findTracesCmdPackage(Context context, String readline) {
        if (TRACE_CMD_LINE.matcher(readline).find()) {
            return readline.contains(context.getApplicationContext().getPackageName());
        }
        return false;
    }

    /**
     * 找Main
     * <p>
     * eg:DALVIK THREADS (94):
     * </p>
     *
     * @param readline
     * @return
     */
    protected boolean findTracesMainThreads(String readline) {
        return TRACE_MAIN_LINE.matcher(readline).find();
    }

    /**
     * 找Main 结束
     * <p>
     * eg:DALVIK THREADS (94):
     * </p>
     *
     * @param readline
     * @return
     */
    protected boolean findMainThreadsEnd(String readline) {
        return TextUtils.isEmpty(readline);
    }

    /**
     * 截取时间
     *
     * @param text ----- pid 15878 at 2017-06-15 16:47:24 -----
     * @return 2017-06-15 16:47:24
     */
    protected static String cutTime(String text) {
        if (TextUtils.isEmpty(text)) {
            return "";
        }
        int lastPoi = text.lastIndexOf("at ");
        int lastSep = text.lastIndexOf(" -----");
        if (lastPoi == -1 || lastSep == -1 || lastPoi >= lastSep) {
            return "";
        }
        return text.substring(lastPoi + 3, lastSep);
    }

    /*将字符串转为时间戳*/
    protected static long getTimeStamp(String time) {
        if (TextUtils.isEmpty(time)) {
            return -1;
        }
        Date date = new Date();
        try {
            date = new SimpleDateFormat(TRACES_TIME_FORMAT).parse(time);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    private String getDate(long time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
                .format(new Date(time));
    }

    /**
     * 不采集设置的这个时间戳之前的异常信息
     *
     * @param context
     * @param key
     * @param timeMillis
     */
    private void ignoreBefore(Context context, String key, long timeMillis) {
        if (SharedPreferenceUtils.getInstance(context, SHAREDPREF_NAME).get(key, 0L) < timeMillis) {
            SharedPreferenceUtils.getInstance(context, SHAREDPREF_NAME).put(key, timeMillis);
        }
    }
}
