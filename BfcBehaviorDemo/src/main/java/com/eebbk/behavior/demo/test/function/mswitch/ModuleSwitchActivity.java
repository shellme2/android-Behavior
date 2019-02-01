package com.eebbk.behavior.demo.test.function.mswitch;

import android.widget.Switch;

import com.eebbk.behavior.demo.R;
import com.eebbk.behavior.demo.common.ABaseActivity;

/**
 * @author hesn
 * @function
 * @date 16-10-6
 * @company 步步高教育电子有限公司
 */

public class ModuleSwitchActivity extends ABaseActivity {

    private Switch mBehaviorSwitch;
    private Switch mAutoSwitch;
    private Switch mCacheSwitch;
    private Switch mReportSwitch;
    private Switch mDebugModeSwitch;
    private Switch mGetLogSwitch;
    private Switch mCrashSwitch;
    private Switch mCrashToastSwitch;
    private Switch mCrashUISwitch;
    private Switch mNetworkWifiSwitch;
    private Switch mNetworkMobileSwitch;
//    private Switch mNetwork2GSwitch;
//    private Switch mNetwork3GSwitch;
//    private Switch mNetwork4GSwitch;
    private ModuleSwitchPresenter mPresenter;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_module_switch_layout;
    }

    @Override
    protected void initView() {
        mBehaviorSwitch = (Switch) findViewById(R.id.behaviorSwitch);
        mAutoSwitch = (Switch) findViewById(R.id.autoSwitch);
        mCacheSwitch = (Switch) findViewById(R.id.cacheSwitch);
        mReportSwitch = (Switch) findViewById(R.id.reportSwitch);
        mDebugModeSwitch = (Switch) findViewById(R.id.debugModeSwitch);
        mGetLogSwitch = (Switch) findViewById(R.id.getLogSwitch);
        mCrashSwitch = (Switch) findViewById(R.id.crash);
        mCrashToastSwitch = (Switch) findViewById(R.id.crash_toast);
        mCrashUISwitch = (Switch) findViewById(R.id.crash_ui);
        mNetworkWifiSwitch = (Switch) findViewById(R.id.network_wifi);
        mNetworkMobileSwitch = (Switch) findViewById(R.id.network_mobile);
//        mNetwork2GSwitch = (Switch) findViewById(R.id.network_mobile_2G);
//        mNetwork3GSwitch = (Switch) findViewById(R.id.network_mobile_3G);
//        mNetwork4GSwitch = (Switch) findViewById(R.id.network_mobile_4G);
    }

    @Override
    protected void initData() {
        mPresenter = new ModuleSwitchPresenter();
        mBehaviorSwitch.setChecked(mPresenter.enable());
        mAutoSwitch.setChecked(mPresenter.openActivityDurationTrack());
        mCacheSwitch.setChecked(mPresenter.enableCache());
        mReportSwitch.setChecked(mPresenter.enableReport());
        mDebugModeSwitch.setChecked(mPresenter.enableDebugMode());
        mGetLogSwitch.setChecked(mPresenter.enableCacheLog());
        mCrashSwitch.setChecked(mPresenter.enableCrash());
        mCrashToastSwitch.setChecked(mPresenter.enableCrashToast());
        mCrashUISwitch.setChecked(mPresenter.enableCrashUI());
        mNetworkWifiSwitch.setChecked(mPresenter.enableNetworkWifi());
        mNetworkMobileSwitch.setChecked(mPresenter.enableNetworkMobile());
//        mNetwork2GSwitch.setChecked(mPresenter.enableNetwork2G());
//        mNetwork3GSwitch.setChecked(mPresenter.enableNetwork3G());
//        mNetwork4GSwitch.setChecked(mPresenter.enableNetwork4G());
    }

    @Override
    protected boolean isShowLog() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.save(mBehaviorSwitch.isChecked()
                , mCacheSwitch.isChecked()
                , mReportSwitch.isChecked()
                , mDebugModeSwitch.isChecked()
                , mAutoSwitch.isChecked()
                , mGetLogSwitch.isChecked()
                , mCrashSwitch.isChecked()
                , mCrashToastSwitch.isChecked()
                , mCrashUISwitch.isChecked()
                , mNetworkWifiSwitch.isChecked()
                , mNetworkMobileSwitch.isChecked()
//                , mNetwork2GSwitch.isChecked()
//                , mNetwork3GSwitch.isChecked()
//                , mNetwork4GSwitch.isChecked()
        );
    }
}
