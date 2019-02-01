/**
 * 文  件：PeriodicityMode.java
 * 公  司：步步高教育电子
 * 日  期：2016/8/9  9:44
 * 作  者：HeChangPeng
 */
package com.eebbk.bfc.sdk.behavior.report.upload.mode;

import android.content.Context;

import com.eebbk.bfc.sdk.behavior.report.analysis.interfaces.ISort;
import com.eebbk.bfc.sdk.behavior.report.common.constants.ReportMode;
import com.eebbk.bfc.sdk.behavior.report.common.util.ModeConfig;
import com.eebbk.bfc.sdk.behavior.report.upload.interfaces.IReportModeConfig;
import com.eebbk.bfc.sdk.behavior.utils.ConfigAgent;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

/**
 * 周期性上报模式
 */
public class PeriodicityMode extends ABaseUploadMode {

    /**
     * 上报周期 单位:秒
     */
    private long modeValue;
    private static final String TAG = "PeriodicityMode";

    @Override
    public int modeType() {
        return ReportMode.MODE_PERIOD;
    }

    @Override
    public void initMode(Context context, IReportModeConfig config) {
        LogUtils.v(TAG, "初始化周期性上报模式");
        init(getLong(config.getConfig()[0]));
        LogUtils.d(TAG, "当前上报模式切换为:周期性上报");
    }

    @Override
    void doReport(Context context) {
        long lastReportTime = ModeConfig.getKeyLongValue(ModeConfig.PreferencesKey.REPORT_LAST_TIME, 0);
        long periodTime = ConfigAgent.getBehaviorConfig().reportConfig.reportModeConfig.periodSeconds;
        if (Math.abs(System.currentTimeMillis() - lastReportTime) > (periodTime * 1000)) {
            dealUpload(context);
        }
    }

    private void init(long periodSeconds) {
        modeValue = periodSeconds;
        saveConfig();
    }

    private long getLong(Object o) {
        try {
            return Long.parseLong(o == null ? "0" : (o + ""));
        } catch (Exception e) {
            return 0;
        }
    }

    private void saveConfig() {
//        ModeConfig.getInstance().saveKeyLongValue(ModeConfig.PreferencesKey.REPORT_PERIOD, modeValue);
        ConfigAgent.getBehaviorConfig().reportConfig.reportModeConfig.periodSeconds = modeValue;
    }

    @Override
    String getModeName() {
        return "周期性上传";
    }
}
