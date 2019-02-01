/**
 * 文  件：QuantifyMode.java
 * 公  司：步步高教育电子
 * 日  期：2016/8/9  9:41
 * 作  者：HeChangPeng
 */
package com.eebbk.bfc.sdk.behavior.report.upload.mode;

import android.content.Context;

import com.eebbk.bfc.sdk.behavior.control.report.NotifyAgent;
import com.eebbk.bfc.sdk.behavior.report.analysis.interfaces.ISort;
import com.eebbk.bfc.sdk.behavior.report.common.constants.ReportMode;
import com.eebbk.bfc.sdk.behavior.report.upload.interfaces.IReportModeConfig;
import com.eebbk.bfc.sdk.behavior.report.upload.mode.config.QuantifyModeConfig;
import com.eebbk.bfc.sdk.behavior.utils.ConfigAgent;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

/**
 * 定量上传模式
 */
public class QuantifyMode extends ABaseUploadMode {

    /**
     * 设定的阈值
     */
    private int modeValue;
    private static final String TAG = "QuantifyMode";

    @Override
    public int modeType() {
        return ReportMode.MODE_QUANTITY;
    }

    @Override
    public void initMode(Context context, IReportModeConfig config) {
        initMode(getQuantify(config.getConfig()[0]));
        LogUtils.d(TAG, "当前上报模式切换为:定量上报");
    }

    @Override
    void doReport(Context context) {
        int quantity = ConfigAgent.getBehaviorConfig().reportConfig.reportModeConfig.quantity;
        // 记录数与quantity对比
        int count = NotifyAgent.getRecordcount();
        if (count < quantity) {
            LogUtils.d(TAG, "无需上报,但前记录条数:"+count+",定量上报阀值为:" + quantity);
            return;
        }
        dealUpload(context);
    }

    private void initMode(int quantity) {
        modeValue = quantity;
        saveConfig();
    }

    /**
     * 获取外层设置的容量值
     */
    private int getQuantify(Object o) {
        try {
            return o == null ? QuantifyModeConfig.DEFAULT_QUANTITY : Integer.valueOf(o + "");
        } catch (Exception e) {
            return QuantifyModeConfig.DEFAULT_QUANTITY;
        }
    }

    private void saveConfig() {
//        ModeConfig.getInstance().saveKeyLongValue(ModeConfig.PreferencesKey.REPORT_QUANTITY, modeValue);
        ConfigAgent.getBehaviorConfig().reportConfig.reportModeConfig.quantity = modeValue;
    }

    @Override
    String getModeName() {
        return "定量上传";
    }
}
