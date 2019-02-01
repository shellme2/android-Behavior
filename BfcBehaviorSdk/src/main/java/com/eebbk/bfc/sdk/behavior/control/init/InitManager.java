package com.eebbk.bfc.sdk.behavior.control.init;

import android.annotation.TargetApi;
import android.os.Build;

import com.eebbk.bfc.common.app.AppUtils;
import com.eebbk.bfc.common.file.StorageUtils;
import com.eebbk.bfc.sdk.behavior.BehaviorCollector;
import com.eebbk.bfc.sdk.behavior.aidl.crash.check.CheckExceptionAgent;
import com.eebbk.bfc.sdk.behavior.cache.CacheManager;
import com.eebbk.bfc.sdk.behavior.db.DBManager;
import com.eebbk.bfc.sdk.behavior.db.constant.BCConstants;
import com.eebbk.bfc.sdk.behavior.db.entity.Record;
import com.eebbk.bfc.sdk.behavior.exception.AnrCheckExceptionImpl;
import com.eebbk.bfc.sdk.behavior.exception.CrashHandler;
import com.eebbk.bfc.sdk.behavior.exception.NativeCheckExceptionImpl;
import com.eebbk.bfc.sdk.behavior.exception.StrictModeCheckExceptionImpl;
import com.eebbk.bfc.sdk.behavior.report.control.ReportAgent;
import com.eebbk.bfc.sdk.behavior.utils.ConfigAgent;
import com.eebbk.bfc.sdk.behavior.utils.ContextUtils;
import com.eebbk.bfc.sdk.behavior.utils.ExecutorsUtils;
import com.eebbk.bfc.sdk.behavior.utils.ListUtils;
import com.eebbk.bfc.sdk.behavior.utils.NetUtils;
import com.eebbk.bfc.sdk.behavior.utils.StoreUtils;
import com.eebbk.bfc.sdk.behavior.utils.UploadUtils;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

import java.util.List;

/**
 * @author hesn
 * @function
 * @date 16-8-16
 * @company 步步高教育电子有限公司
 */

public class InitManager {

    private static final String TAG = "InitManager";

    /**
     * 必要初始化
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static void init() {
        if (ContextUtils.isEmpty()) {
            return;
        }
        ReportAgent.initReport();
        int sdkVersion = Integer.parseInt(android.os.Build.VERSION.SDK);
        if (sdkVersion >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            ConfigAgent.getBehaviorConfig().bcLifeCycleCallback.cleanList();
            ContextUtils.getContext().registerActivityLifecycleCallbacks(ConfigAgent.getBehaviorConfig().bcLifeCycleCallback);
        }
        ExecutorsUtils.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                LogUtils.i(TAG, " init " + BehaviorCollector.getInstance().getBehaviorVersion());
                //初始化数据保存策略信息
                CacheManager.getInstance().init();
                //初始化异常捕获
                initCrashHandler();
                checkExceptionFile();
                UploadUtils.getInstance().uploadTaskRecheck();
            }
        });
    }

    private static void checkExceptionFile(){
        CheckExceptionAgent.clear();
        initANRCheck();
        initStrictModeCheck();
        initNativeCheck();
        CheckExceptionAgent.check(ContextUtils.getContext());
    }

    private static void initANRCheck(){
        if (!ConfigAgent.getBehaviorConfig().crashConfig.crashAnrEnable) {
            LogUtils.w(TAG, "当前没有打开ANR异常采集功能!");
            return;
        }
        CheckExceptionAgent.add(new AnrCheckExceptionImpl()
                .setAutoFilter(ConfigAgent.getBehaviorConfig().crashConfig.autoFilterAnr)
                .setReport(true)
                .ignoreBefore(ContextUtils.getContext(),ConfigAgent.getBehaviorConfig().crashConfig.ignoreBeforeAnr));
    }

    private static void initStrictModeCheck(){
        if (!ConfigAgent.getBehaviorConfig().crashConfig.crashStrictModeEnable) {
            LogUtils.w(TAG, "当前没有打开严苛采集功能!");
            return;
        }
        CheckExceptionAgent.add(new StrictModeCheckExceptionImpl()
                .setReport(true)
                .ignoreBefore(ContextUtils.getContext(),ConfigAgent.getBehaviorConfig().crashConfig.ignoreBeforeStrictMode));
    }

    private static void initNativeCheck(){
        if (!ConfigAgent.getBehaviorConfig().crashConfig.crashNativeEnable) {
            LogUtils.w(TAG, "当前没有打开native异常采集功能!");
            return;
        }
        CheckExceptionAgent.add(new NativeCheckExceptionImpl()
                .setReport(true)
                .ignoreBefore(ContextUtils.getContext(),ConfigAgent.getBehaviorConfig().crashConfig.ignoreBeforeNative));
    }

    /**
     * 初始化异常捕获
     */
    private static void initCrashHandler() {
        if (!ConfigAgent.getBehaviorConfig().crashConfig.usable) {
            LogUtils.w(TAG, "当前没有打开异常捕获行为采集功能!");
            return;
        }

        if (!AppUtils.isSystemApp(ContextUtils.getContext(), ContextUtils.getContext().getPackageName())
                && (StorageUtils.getExternalStorageAvailableSize() / (1024 * 1024) < 10
                || StorageUtils.getDataAvailableSize() / (1024 * 1024) < 10)) {
            LogUtils.w(TAG, "当前内部或者外部存储控件少于10M,无法开启异常捕获功能!");
            return;
        }

        // set the crash handler.
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init();
        if (crashHandler.isReStartTooMany()) {
            // 如果大于10秒,可以重新尝试
            if (StoreUtils.compare2Sec(crashHandler.readRestartTime(),
                    System.currentTimeMillis(), 10) && ConfigAgent.getBehaviorConfig().crashConfig.usable) {
                crashHandler.registerCrashHandler();
            }
            crashHandler.cleanReStartCount();
        } else {
            if (ConfigAgent.getBehaviorConfig().crashConfig.usable) {
                crashHandler.registerCrashHandler();
            }
        }
    }

    private InitManager() {
        //prevent the instance
    }
}
