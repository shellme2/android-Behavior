package com.eebbk.bfc.sdk.behavior.report.upload.mode.config;

import com.eebbk.bfc.sdk.behavior.report.common.constants.ReportMode;
import com.eebbk.bfc.sdk.behavior.report.upload.interfaces.IReportModeConfig;

/**
 * @author hesn
 * @function 定时上传模式配置
 * @date 16-9-26
 * @company 步步高教育电子有限公司
 */

public class FixedTimeModeConfig implements IReportModeConfig {

    /**
     * 要固定的时间：hour 24小时制
     */
    private int hour;
    /**
     * 要固定的时间：minute 60分钟制
     */
    private int minute;
    /**
     * 要固定的时间：second 60秒制
     */
    private int second;
    private static final int MAXVALUE = 59;
    private static final int MINVALUE = 0;

    @Override
    public int modeType() {
        return ReportMode.MODE_FIXTIME;
    }

    @Override
    public Object[] getConfig() {
        return new Object[]{hour, minute, second};
    }

    /**
     * 固定的时间：hour 24小时制
     * @param hour
     * @return
     */
    public FixedTimeModeConfig setHour(int hour) {
        this.hour = getFixedValue(hour, 23);
        return this;
    }

    /**
     * 固定的时间：minute 60分钟制
     * @param minute
     * @return
     */
    public FixedTimeModeConfig setMinute(int minute) {
        this.minute = getFixedValue(minute);
        return this;
    }

    /**
     * 固定的时间：second 60秒制
     * @param second
     * @return
     */
    public FixedTimeModeConfig setSecond(int second) {
        this.second = getFixedValue(second);
        return this;
    }

    /**
     * 获取范围以内的值
     * @param initValue
     * @return
     */
    private int getFixedValue(int initValue) {
        return getFixedValue(initValue, MAXVALUE);
    }

    /**
     * 获取范围以内的值
     * @param initValue
     * @param maxValue
     * @return
     */
    private int getFixedValue(int initValue, int maxValue) {
        return initValue < MINVALUE ? getValue(MINVALUE, maxValue) : (initValue > maxValue ? getValue(MINVALUE, maxValue) : initValue);
    }

    /**
     * 获取minValue到maxValue之间的随机数
     * @param minValue
     * @param maxValue
     * @return
     */
    private int getValue(int minValue, int maxValue) {
        return (int) (minValue + Math.random() * (maxValue - minValue + 1));
    }
}
