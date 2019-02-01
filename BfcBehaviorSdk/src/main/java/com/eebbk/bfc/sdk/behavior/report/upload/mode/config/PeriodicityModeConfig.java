package com.eebbk.bfc.sdk.behavior.report.upload.mode.config;

import com.eebbk.bfc.sdk.behavior.report.common.constants.ReportMode;
import com.eebbk.bfc.sdk.behavior.report.upload.interfaces.IReportModeConfig;

/**
 * @author hesn
 * @function 周期性上报模式配置
 * @date 16-9-26
 * @company 步步高教育电子有限公司
 */

public class PeriodicityModeConfig implements IReportModeConfig {

    /**
     * 最大值半天
     */
    public static final long MAXVALUE = 12 * 60 * 60;
    /**
     * 最小值5分钟
     */
    public static final long MINVALUE = 5 * 60;

    public static final long DEFAULT_PERIOD = 60 * 5;
    /**
     * 上报周期 单位:秒
     */
    private long modeValue = DEFAULT_PERIOD;

    @Override
    public int modeType() {
        return ReportMode.MODE_PERIOD;
    }

    @Override
    public Object[] getConfig() {
        return new Object[]{modeValue};
    }

    /**
     * 上报周期
     * @param modeValue 单位:秒
     */
    public PeriodicityModeConfig setModeValue(long modeValue) {
        this.modeValue = modeValue > MAXVALUE ?
                DEFAULT_PERIOD : (modeValue < MINVALUE ? DEFAULT_PERIOD : modeValue);
        return this;
    }
}
