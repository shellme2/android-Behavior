package com.eebbk.bfc.sdk.behavior.aidl.crash;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.eebbk.bfc.sdk.behavior.aidl.BFCColumns;
import com.eebbk.bfc.sdk.behavior.aidl.EType;
import com.eebbk.bfc.sdk.behavior.aidl.crash.filter.CrashFilter;
import com.eebbk.bfc.sdk.behavior.aidl.crash.filter.ModuleCrashInfo;
import com.eebbk.bfc.sdk.behavior.aidl.crash.ui.ICrashReportUI;
import com.eebbk.bfc.sdk.behavior.aidl.crash.ui.ToastCrashReportUiImpl;
import com.eebbk.bfc.sdk.behavior.aidl.error.ErrorCode;
import com.eebbk.bfc.sdk.behavior.aidl.listener.InnerListener;
import com.eebbk.bfc.sdk.behavior.aidl.utils.AppUtils;
import com.eebbk.bfc.sdk.behavior.aidl.utils.ConfigAgent;
import com.eebbk.bfc.sdk.behavior.aidl.utils.ExecutorsUtils;
import com.eebbk.bfc.sdk.behavior.aidl.utils.FileUtils;
import com.eebbk.bfc.sdk.behavior.aidl.utils.LogUtils;
import com.eebbk.bfc.sdk.behavior.aidl.utils.StorageUtils;

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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

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

    private Context mContext;
    private InnerListener mInnerListener;

    private static class InstanceHolder {
        private static final CrashHandler mInstance = new CrashHandler();
    }

    public static CrashHandler getInstance() {
        return CrashHandler.InstanceHolder.mInstance;
    }

    public interface CrashReportCallBack {
        void reports(File f);
    }

    public void init(Context context, InnerListener l) {
        if (context == null) {
            LogUtils.bfcExceptionLog(TAG, ErrorCode.CONTROL_INIT_CRASH);
            return;
        }
        mContext = context.getApplicationContext();
        mInnerListener = l;
        String defLogPath = DEFAULT_CRASH_LOG_PATH + context.getPackageName()
                + File.separator;
        init(defLogPath);
    }

    private void init(String logPath) {
        mLogPath = logPath;
        mCrashCacheSize = CrashHandler.DEFAULT_CRASH_CACHE_SIZE;
        // we use app self cache path as re-start count file save path
        try {
            // getCacheDir()会返回空,呵呵
            mCountFilePath = mContext.getCacheDir().toString() + File.separator
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
            if (mInnerListener != null) {
                mInnerListener.onExitApp();
            }
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
        long start = SystemClock.elapsedRealtime();
        int count = readReStartCount();
        LogUtils.i(TAG, "isReStartTooMany() count:" + count);
        LogUtils.i(TAG, "isReStartTooMany() useTime:"+(SystemClock.elapsedRealtime() - start));
        return count >= RESTART_COUNT;
    }

    /**
     * Send crash report to our server. the upload server need both string and
     * file -_-||.
     */
    private void saveCrashReportToBC(final String log, final ModuleCrashInfo moduleCrashInfo) {
        LogUtils.d(TAG, "log: " + log);
        // TODO: configure the service in here
        long disktotal = StoreUtils.getInternalStoreTotalSize();
        long diskAvailable = StoreUtils.getInternalStoreAvailableSize();

        long sdTotal = StoreUtils.getExternalStoreTotalSize();
        long sdAvailable = StoreUtils.getExternalStoreAvailableSize();

        long memTotal = StoreUtils.getMemoryTotalSize();
        long memAvailable = StoreUtils.getMemoryAvailable(mContext);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("stack", log);
            jsonObject.put("sysLog", LogUtils.getSysErrLog());
            jsonObject.put("diskTotal", StoreUtils.convertSizeUnit(disktotal));
            jsonObject.put("diskUsage", StoreUtils.availablepercent(diskAvailable, disktotal));
            jsonObject.put("sdTotal", sdTotal);
            jsonObject.put("sdUsage", StoreUtils.availablepercent(sdAvailable, sdTotal));
            jsonObject.put("memTotal", memTotal);
            jsonObject.put("memUsage", StoreUtils.availablepercent(memAvailable, memTotal));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Map<String, String> map = new HashMap<>();
        map.put(BFCColumns.COLUMN_EA_TVALUE, jsonObject.toString());
        if (mInnerListener != null) {
            mInnerListener.onEvent(EType.TYPE_EXCEPTION, map);
        }

        if(moduleCrashInfo.isModuleCrash()){
            saveModuleCrashToBC(map, moduleCrashInfo);
        }
    }

    private void saveModuleCrashToBC(Map<String, String> map, ModuleCrashInfo moduleCrashInfo){
        Map<String, String> mcMap = new HashMap<>(map);
        mcMap.put(BFCColumns.COLUMN_AA_MODULENAME, moduleCrashInfo.getModuleName());
        mcMap.put(BFCColumns.COLUMN_AA_PACKAGENAME, moduleCrashInfo.getModulePackageName());
        mcMap.put(BFCColumns.COLUMN_AA_APPVER, moduleCrashInfo.getVersionName());
        JSONObject appJsonObject = new JSONObject();
        try {
            appJsonObject.put("appName", AppUtils.getModuleName(mContext));
            appJsonObject.put("appPackageName", mContext.getPackageName());
            appJsonObject.put("appVersionName", AppUtils.getVersionName(mContext));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mcMap.put(BFCColumns.COLUMN_OA_EXTEND, appJsonObject.toString());
        if (mInnerListener != null) {
            mInnerListener.onEvent(EType.TYPE_EXCEPTION, mcMap);
        }
    }

    public long readRestartTime() {
        LogUtils.i(TAG, "readRestartTime() start");
        long start = SystemClock.elapsedRealtime();
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
        LogUtils.i(TAG, "readRestartTime() time:" + time);
        LogUtils.i(TAG, "readRestartTime() useTime:"+(SystemClock.elapsedRealtime() - start));
        return time;
    }

    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        Log.e(TAG, "", ex);
        String stack = getCrashInfo(ex);
        final ModuleCrashInfo moduleCrashInfo = new CrashFilter().checkIsOtherModuleException(stack);
        final String log = formatStackTrace(stack, moduleCrashInfo);
        showToast();
        ExecutorsUtils.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                saveCrashInfoToFile(log, resolveLogPath());
                // if need upload we send the log file to server.
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
        if (mContext == null || !ConfigAgent.getBehaviorConfig().isToastCrash) {
            return;
        }
        ICrashReportUI toastUI = new ToastCrashReportUiImpl();
        toastUI.show(null, mContext);
    }

    private void saveReStartCount(int count) {
        long start = SystemClock.elapsedRealtime();
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
        LogUtils.i(TAG, "saveReStartCount() useTime:"+(SystemClock.elapsedRealtime() - start));
    }

    private int readReStartCount() {
        if (null == mCountFilePath) {
            return 0;
        }

        if (!canSave2File()) {
            return 0;
        }

        File file = new File(mCountFilePath);
        if (!file.exists()) {
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
            FileUtils.scanFile(mContext, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean canSave2File() {
        if (!AppUtils.isSystemApp(mContext, mContext.getPackageName())
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
                "#", PRODUCT, "=", AppUtils.getAppName(mContext), "\n",
                "#", VERSION_NAME, "=", AppUtils.getVersionName(mContext), "\n",
                "#", VERSION_CODE, "=", String.valueOf(AppUtils.getVersionCode(mContext))).toString();
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
            return mContext.getCacheDir().toString();
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
            FileUtils.scanFile(mContext, cachePath);
            LogUtils.d(TAG, "the cache size is rearch max size, we will clear it, clear: " + ret);
        }
    }

    private void printProperties(Properties properties, PrintWriter writer) {
        Enumeration<?> keys = properties.propertyNames();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            writer.print(key);
            writer.print('=');
            String property = (String) properties.get(key);
            if (null == property) {
                property = "unknow.";
            }
            writer.println(property);
        }
    }

    private void autoRestart(String log) {
//        if (!ConfigAgent.getBehaviorConfig().isDebugMode && !ConfigAgent.getBehaviorConfig().crashConfig.isUIReport) {
//            return;
//        }
//        ICrashReportUI crashReportUI = new ActivityCrashReportUiImpl();
//        crashReportUI.show(log);
    }

    private CrashHandler() {
        //prevent the instance
    }
}
