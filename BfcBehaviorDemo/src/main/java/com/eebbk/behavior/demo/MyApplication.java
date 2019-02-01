package com.eebbk.behavior.demo;

import android.app.Application;
import android.os.StrictMode;
import android.util.Log;

import com.eebbk.behavior.demo.utils.ConfigUtils;
import com.eebbk.behavior.demo.utils.DADemoUtils;
import com.eebbk.bfc.sdk.behavior.BehaviorCollector;
import com.eebbk.bfc.sdk.behavior.BehaviorConfig;
import com.eebbk.bfc.sdk.behavior.report.ReportConfig;
import com.eebbk.bfc.sdk.behavior.utils.ExecutorsUtils;
import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.Settings;

/**
 * @author hesn
 * @function
 * @date 16-8-19
 * @company 步步高教育电子有限公司
 */

public class MyApplication extends Application {

    private static MyApplication mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("MyApplication",">>>>>>>>>>>>>>>> onCreate() <<<<<<<<<<<<<<<<<<");
        setContext(this);
        initStrictMode();
        initDA();
        LeakCanary.install(this, new Settings().setMonkeyTest(ConfigUtils.isMonkeyTest));
        BlockCanary.install(this, new AppContext());

    }

    private static void setContext(MyApplication mContext) {
        MyApplication.mContext = mContext;
    }

    public static MyApplication getContext() {
        return mContext;
    }

    private void initDA() {
        BehaviorCollector.getInstance().init(this);
        ExecutorsUtils.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                BehaviorConfig behaviorConfig = ConfigUtils.getConfigFromDB();
                behaviorConfig.crashConfig.crashStrictModeEnable = true;
                BehaviorCollector.getInstance().setConfig(behaviorConfig);
            }
        });
//        BehaviorCollector.getInstance().init(new BehaviorCollector.Builder(this)
//                .enableCrash(false)
//                .setCrashUIReport(false)
//                .setCrashToast(false)
//                .build());
//        DADemoUtils.init(this);
    }

    private void initStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .penaltyDialog()
                .penaltyDropBox()
                .build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDropBox()
                .build());

    }


}
