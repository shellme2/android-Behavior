package com.eebbk.bfc.sdk.behavior.exception;

import android.text.TextUtils;
import android.util.Log;

import com.eebbk.bfc.common.app.AppUtils;
import com.eebbk.bfc.common.file.FileUtils;
import com.eebbk.bfc.common.file.StorageUtils;
import com.eebbk.bfc.sdk.behavior.BehaviorCollector;
import com.eebbk.bfc.sdk.behavior.aidl.crash.filter.CrashFilter;
import com.eebbk.bfc.sdk.behavior.aidl.crash.filter.ModuleCrashInfo;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.AidlCustomAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.ExceptionEvent;
import com.eebbk.bfc.sdk.behavior.db.constant.BFCColumns;
import com.eebbk.bfc.sdk.behavior.error.ErrorCode;
import com.eebbk.bfc.sdk.behavior.exception.ui.ActivityCrashReportUiImpl;
import com.eebbk.bfc.sdk.behavior.exception.ui.ICrashReportUI;
import com.eebbk.bfc.sdk.behavior.exception.ui.ToastCrashReportUiImpl;
import com.eebbk.bfc.sdk.behavior.utils.ConfigAgent;
import com.eebbk.bfc.sdk.behavior.utils.ContextUtils;
import com.eebbk.bfc.sdk.behavior.utils.ExecutorsUtils;
import com.eebbk.bfc.sdk.behavior.utils.StoreUtils;
import com.eebbk.bfc.sdk.behavior.utils.Utils;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Uncaught crash handler.
 *
 * @author humingming <hmm@dw.gdbbk.com>
 */
public final class CrashHandler implements UncaughtExceptionHandler {

    private static final String TAG = "CrashHandler";
    private static final boolean DEBUG = true;

    private static final String SDCARD_PATH = "/mnt/sdcard";
    private static final String DEFAULT_CRASH_LOG_PATH = SDCARD_PATH
            + File.separator + ".crash" + File.separator;
    private static final long DEFAULT_CRASH_CACHE_SIZE = 1024 * 1024; // 1M

    private static final String VERSION_NAME = "versionName";
    private static final String VERSION_CODE = "versionCode";
    private static final String PRODUCT = "PRODUCT";
    private static final String STACK_TRACE = "STACK_TRACE";
    private static final String SDK_NAME = "SDK_NAME";
    private static final String SDK_PACKAGE_NAME = "SDK_PACKAGE_NAME";
    private static final String SDK_VERSION_NAME = "SDK_VERSION_NAME";
    private static final int RESTART_COUNT = 3;

    private static final String CRASH_REPORTER_EXTENSION = ".cr";

    private String mLogPath = DEFAULT_CRASH_LOG_PATH;
    private String mCountFilePath = null;
    private long mCrashCacheSize = DEFAULT_CRASH_CACHE_SIZE;

    @SuppressWarnings("unused")
    private int mVersionCode = 0;

    private UncaughtExceptionHandler mDefaultHandler;

    private static class InstanceHolder {
        private static final CrashHandler mInstance = new CrashHandler();
    }

    public static CrashHandler getInstance() {
        return CrashHandler.InstanceHolder.mInstance;
    }

    public interface CrashReportCallBack {
        void reports(File f);
    }

    public void init() {
        if (ContextUtils.isEmpty()) {
            LogUtils.bfcExceptionLog(TAG, ErrorCode.CONTROL_INIT_CRASH);
            return;
        }
        String defLogPath = DEFAULT_CRASH_LOG_PATH + ContextUtils.getContext().getPackageName()
                + File.separator;
        init(defLogPath);
    }

    private void init(String logPath) {
        mLogPath = logPath;
        mCrashCacheSize = CrashHandler.DEFAULT_CRASH_CACHE_SIZE;
        // we use app self cache path as re-start count file save path
        try {
            // getCacheDir()会返回空,呵呵
            mCountFilePath = ContextUtils.getContext().getCacheDir().toString() + File.separator
                    + "CrashLaunchCount.sav";
        } catch (Exception e) {
            e.printStackTrace();
            mCountFilePath = mLogPath + "CrashLaunchCount.sav";
        }
    }

    public void registerCrashHandler() {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(final Thread thread, final Throwable ex) {
        boolean hadDeal = handleException(ex);
        if (!hadDeal && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }
            LogUtils.i(TAG, "bcLifeCycleCallback.exit() start");
            // 退出程序
            ConfigAgent.getBehaviorConfig().bcLifeCycleCallback.exit();
            LogUtils.i(TAG, "bcLifeCycleCallback.exit() end");
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    /**
     * Set crash log save path.
     *
     * @param path
     */
    public void setCrashLogPath(String path) {
        mLogPath = path;
    }

    /**
     * Set crash cache size, when crash log size is large than the limit size,
     * it will clear the crash log files.
     *
     * @param cacheSize
     */
    public void setCrashCacheSize(long cacheSize) {
        if (cacheSize <= 0) {
            return;
        }
        mCrashCacheSize = cacheSize;
    }

    /**
     * Load last crash file.
     *
     * @return the {@link String} of crash content.
     */
    public String loadLastCrashReport() {
        String crFile = getLastCrashReportFile();
        if (TextUtils.isEmpty(crFile) || !FileUtils.isFileExists(crFile)) {
            return null;
        }

        try {
            return new String(FileUtils.readFile2Bytes(crFile), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get last crash file path.
     *
     * @return full path name of last crash file.
     */
    private String getLastCrashReportFile() {
        String logPath = resolveLogPath();
        if (null == logPath) {
            return null;
        }

        File file = new File(logPath);
        if (file.isFile()) {
            return file.getAbsolutePath();
        }

        File[] files = file.listFiles();
        if (files == null) {
            return null;
        }

        String lastFile = null;
        long lastFileModifed = 0;
        for (File file1 : files) {
            if (file1.isDirectory()) {
                continue;
            }

            if (file1.lastModified() >= lastFileModifed) {
                lastFile = file1.getAbsolutePath();
                lastFileModifed = file1.lastModified();
            }
        }

        return lastFile;
    }

    private void recordReStartCount() {
        // don't use SharedPerference, it can't write to disk immediately.
        // we use the IO file directly.
        int count = readReStartCount();
        if (DEBUG) {
            LogUtils.d(TAG, "read re-start count: " + count);
        }
        count += 1;
        saveReStartCount(count);
    }

    /**
     * Clean re-start count.
     */
    public void cleanReStartCount() {
        if (DEBUG) {
            LogUtils.d(TAG, "clean re-start count !!");
        }
        saveReStartCount(0);
    }

    /**
     * Query re-start count whether over the limit times.
     *
     * @return True is means over the limit count.
     */
    public boolean isReStartTooMany() {
        int count = readReStartCount();
        return count >= RESTART_COUNT;
    }

    /**
     * Send crash report to our server. the upload server need both string and
     * file -_-||.
     */
    private void saveCrashReportToBC(final String log, final ModuleCrashInfo moduleCrashInfo) {
        LogUtils.d(TAG, "log: " + log);
        // TODO: configure the service in here
        final ExceptionEvent exceptionInfo = new ExceptionEvent();
        long disktotal = StoreUtils.getInternalStoreTotalSize();
        long diskAvailable = StoreUtils.getInternalStoreAvailableSize();
        exceptionInfo.diskTotal = StoreUtils.convertSizeUnit(disktotal);
        exceptionInfo.diskUsage = StoreUtils.availablepercent(diskAvailable,
                disktotal);
        long sdTotal = StoreUtils.getExternalStoreTotalSize();
        long sdAvailable = Math.round(StoreUtils.getExternalStoreAvailableSize());
        exceptionInfo.sdTotal = StoreUtils.convertSizeUnit(sdTotal);
        exceptionInfo.sdUsage = StoreUtils.availablepercent(sdAvailable,
                sdTotal);
        long memTotal = StoreUtils.getMemoryTotalSize();
        long memAvailable = StoreUtils.getMemoryAvailable(ContextUtils.getContext());
        exceptionInfo.memTotal = StoreUtils.convertSizeUnit(memTotal);
        exceptionInfo.memUsage = StoreUtils.availablepercent(memAvailable,
                memTotal);
        exceptionInfo.sysLog = LogUtils.getSysErrLog();
        exceptionInfo.stack = log;
        BehaviorCollector.getInstance().exceptionEvent(exceptionInfo);
        if(moduleCrashInfo.isModuleCrash()){
            saveModuleCrashToBC(exceptionInfo, moduleCrashInfo);
        }
    }

    private void saveModuleCrashToBC(ExceptionEvent exceptionInfo, ModuleCrashInfo moduleCrashInfo){
        ExceptionEvent mcExceptionInfo = new ExceptionEvent();
        mcExceptionInfo.diskTotal = exceptionInfo.diskTotal;
        mcExceptionInfo.diskUsage = exceptionInfo.diskUsage;
        mcExceptionInfo.sdTotal = exceptionInfo.sdTotal;
        mcExceptionInfo.sdUsage = exceptionInfo.sdUsage;
        mcExceptionInfo.memTotal = exceptionInfo.memTotal;
        mcExceptionInfo.memUsage = exceptionInfo.memUsage;
        mcExceptionInfo.sysLog = exceptionInfo.sysLog;
        mcExceptionInfo.stack = exceptionInfo.stack;

        AidlCustomAttr attr = new AidlCustomAttr();
        Map<String, String> attrMap = new HashMap<>();
        attrMap.put(BFCColumns.COLUMN_AA_MODULENAME, moduleCrashInfo.getModuleName());
        attrMap.put(BFCColumns.COLUMN_AA_PACKAGENAME, moduleCrashInfo.getModulePackageName());
        attrMap.put(BFCColumns.COLUMN_AA_APPVER, moduleCrashInfo.getVersionName());
        JSONObject appJsonObject = new JSONObject();
        try {
            appJsonObject.put("appName", AppUtils.getAppName(ContextUtils.getContext()));
            appJsonObject.put("appPackageName", ContextUtils.getContext().getPackageName());
            appJsonObject.put("appVersionName", AppUtils.getVersionName(ContextUtils.getContext()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        attrMap.put(com.eebbk.bfc.sdk.behavior.aidl.BFCColumns.COLUMN_OA_EXTEND, appJsonObject.toString());
        attr.setMap(attrMap);
        mcExceptionInfo.addAttr(attr);
        BehaviorCollector.getInstance().exceptionEvent(mcExceptionInfo);
    }

    public long readRestartTime() {
        if (null == mCountFilePath) {
            return 0;
        }
        File file = new File(mCountFilePath);
        if (!file.exists()) {
            return 0;
        }
        long time = 0;
        FileInputStream is = null;
        ObjectInputStream oin = null;
        try {
            is = new FileInputStream(file);
            oin = new ObjectInputStream(is);
            oin.readInt();
            time = oin.readLong();
        } catch (Exception e) {
            e.printStackTrace();
            if (DEBUG) {
                LogUtils.e(TAG, "an error occured while writing count file ...", e);
            }
        } finally {
            FileUtils.closeIO(oin);
            FileUtils.closeIO(is);
        }
        return time;
    }

    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        Log.e(TAG, "", ex);
        String stack = getCrashInfo(ex);
        final ModuleCrashInfo moduleCrashInfo = new CrashFilter().checkIsOtherModuleException(stack);
        showToast();
        final String log = formatStackTrace(stack, moduleCrashInfo);
        ExecutorsUtils.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                saveCrashInfoToFile(log, resolveLogPath());
                saveCrashReportToBC(log, moduleCrashInfo);
                if(moduleCrashInfo.isModuleCrash()){
                    saveCrashInfoToFile(log, DEFAULT_CRASH_LOG_PATH + moduleCrashInfo.getModuleName());
                }
                // record re-start count
                recordReStartCount();
            }
        });
        autoRestart(log);
        return true;
    }

    private void showToast() {
        if (ContextUtils.isEmpty() || !ConfigAgent.getBehaviorConfig().crashConfig.isToast) {
            return;
        }
        LogUtils.i(TAG, "show toast");
        ICrashReportUI toastUI = new ToastCrashReportUiImpl();
        toastUI.show(null);
    }

    private void saveReStartCount(int count) {
        if (null == mCountFilePath) {
            return;
        }

        if (!canSave2File()) {
            return;
        }

        if (!StoreUtils.checkFileDirExisted(mCountFilePath)) {
            LogUtils.e(TAG, "save count file: create count file dir error !");
            return;
        }
        FileOutputStream os = null;
        ObjectOutputStream oout = null;
        try {
            os = new FileOutputStream(mCountFilePath);
            oout = new ObjectOutputStream(os);
            oout.writeInt(count);
            oout.writeLong(System.currentTimeMillis());
            oout.flush();
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
            if (DEBUG) {
                LogUtils.e(TAG, "an error occured while writing count file ...", e);
            }
        } finally {
            FileUtils.closeIO(oout);
            FileUtils.closeIO(os);
        }
    }

    private int readReStartCount() {
        if (null == mCountFilePath) {
            return 0;
        }

        if (!canSave2File()) {
            return 0;
        }

        File file = new File(mCountFilePath);
        if (file == null || !file.exists()) {
            return 0;
        }
        int count = 0;
        FileInputStream is = null;
        ObjectInputStream oin = null;
        try {
            is = new FileInputStream(file);
            oin = new ObjectInputStream(is);
            count = oin.readInt();
        } catch (Exception e) {
            e.printStackTrace();
            if (DEBUG) {
                LogUtils.e(TAG, "an error occured while writing count file ...", e);
            }
        } finally {
            FileUtils.closeIO(oin);
            FileUtils.closeIO(is);
        }
        return count;
    }

    private String getCrashInfo(Throwable ex){
        Writer info = new StringWriter();
        PrintWriter printWriter = new PrintWriter(info);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (null != cause) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }

        printWriter.close();
        return info.toString();
    }

    private void saveCrashInfoToFile(String log, String logPath) {
        if (null == logPath) {
            LogUtils.e(TAG, "log path invalid, can't save crash log file !!");
            return;
        }
        String fileName = resolveLogFileName();
        if (null == fileName) {
            LogUtils.e(TAG, "log file name invalid, can't save crash log file !!");
            return;
        }

        // check save directory whether existed.
        String filePath = logPath + File.separator + fileName;
        if (!StoreUtils.checkFileDirExisted(filePath)) {
            LogUtils.e(TAG, "save crash info: create crash file dir error !");
            return;
        }
        // check the cache size.
        checkCacheSize();
        if (!canSave2File()) {
            return;
        }
        try {
            FileUtils.writeFile(new File(filePath), log, true);
            //通知媒体库更新
            Utils.scanFile(ContextUtils.getContext(), filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean canSave2File() {
        if (!AppUtils.isSystemApp(ContextUtils.getContext(), ContextUtils.getContext().getPackageName())
                && (StorageUtils.getExternalStorageAvailableSize() / (1024 * 1024) < 10
                || StorageUtils.getDataAvailableSize() / (1024 * 1024) < 10)) {
            LogUtils.w(TAG, "当前内部或者外部存储控件少于10M,无法保存异常捕获到文件!");
            return false;
        }
        return true;
    }

    /**
     * 异常信息格式化
     * @param stack
     * @param moduleCrashInfo
     * @return
     */
    private String formatStackTrace(String stack, ModuleCrashInfo moduleCrashInfo) {
        String appInfo = TextUtils.concat("#", "\n",
                "#", new Date().toString(), "\n",
                "#", PRODUCT, "=", AppUtils.getAppName(ContextUtils.getContext()), "\n",
                "#", VERSION_NAME, "=", AppUtils.getVersionName(ContextUtils.getContext()), "\n",
                "#", VERSION_CODE, "=", String.valueOf(AppUtils.getVersionCode(ContextUtils.getContext()))).toString();
        String moduleInfo = "";
        if(moduleCrashInfo.isModuleCrash()){
            moduleInfo = TextUtils.concat("\n",
                    "#", SDK_NAME, "=", moduleCrashInfo.getModuleName(), "\n",
                    "#", SDK_PACKAGE_NAME, "=", moduleCrashInfo.getModulePackageName(), "\n",
                    "#", SDK_VERSION_NAME, "=", moduleCrashInfo.getVersionName()).toString();
        }
        return TextUtils.concat( "\n",
                appInfo, moduleInfo, "\n",
                STACK_TRACE, "=", stack).toString();
    }

    private String resolveLogPath() {
        if (mLogPath != null) {
            return mLogPath;
        }
        try {
            return ContextUtils.getContext().getCacheDir().toString();
        } catch (Exception e) {
            return null;
        }
    }

    private String resolveLogFileName() {
        try {
            Calendar now = Calendar.getInstance();
            // Calendar return month is start from 0.
            return String.format(Locale.CHINA, "crash--%d-%d-%d-%d.%d.%d%s",
                    now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1,
                    now.get(Calendar.DATE), now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE), now.get(Calendar.SECOND),
                    CRASH_REPORTER_EXTENSION);
        } catch (Exception e) {
            return null;
        }
    }

    private void checkCacheSize() {
        String cachePath = resolveLogPath();
        if (null == cachePath) {
            return;
        }
        long cacheSize = StoreUtils.getFileSize(cachePath);
        // if the cache size is large than limit, clear it(delete the directory)
        if (cacheSize >= mCrashCacheSize) {
            boolean ret = StoreUtils.deleteFolder(cachePath);
            Utils.scanFile(ContextUtils.getContext(), cachePath);
            LogUtils.d(TAG, "the cache size is rearch max size, we will clear it, clear: " + ret);
        }
    }

    private void autoRestart(String log) {
        if (!ConfigAgent.getBehaviorConfig().isDebugMode && !ConfigAgent.getBehaviorConfig().crashConfig.isUIReport) {
            return;
        }
        ICrashReportUI crashReportUI = new ActivityCrashReportUiImpl();
        crashReportUI.show(log);
    }

    private CrashHandler() {
        //prevent the instance
    }
}
