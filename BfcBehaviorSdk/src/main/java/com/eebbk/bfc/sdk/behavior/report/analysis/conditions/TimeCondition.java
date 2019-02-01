package com.eebbk.bfc.sdk.behavior.report.analysis.conditions;

/**
 * 文  件：TimeCondition.java
 * 公  司：步步高教育电子
 * 日  期：2016/8/15  9:36
 * 作  者：HeChangPeng
 */

public class TimeCondition extends BaseCondition {

    /**
     * 某个时间区间
     */
    public static final int DURATION_TIME = 1;

    /**
     * 某个时间之前一段时间
     */
    public static final int BEFORE_SOMETIME = 2;

    /**
     * 某个时间之后一段时间
     */
    public static final int SINCE_SOMETIME = 3;

    /**
     * 某个时间之后的所有数据
     */
    public static final int SINCE_THETIME = 4;

    /**
     * 某个时间之前的所有数据
     */
    public static final int BEFORE_THETIME = 5;

    /**
     * 开始日期时间戳
     */
    private long fromMillis = DEFAULT_VALUE;
    /**
     * 截止日期时间戳
     */
    private long toMillis = DEFAULT_VALUE;
    /**
     * 时长毫秒
     */
    private long timeInMillis = DEFAULT_VALUE;

    /**
     * 设置截止日期时间戳
     */
    public TimeCondition setToMillis(long toMillis) {
        this.toMillis = toMillis;
        return this;
    }

    /**
     * 设置时长
     */
    public TimeCondition setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
        return this;
    }

    /**
     * 设置开始日期时间戳
     */
    public TimeCondition setFromMillis(long fromMillis) {
        this.fromMillis = fromMillis;
        return this;
    }

    public long getFromMillis() {
        return fromMillis;
    }

    public long getToMillis() {
        return toMillis;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    @Override
    public int getType() {
        if (fromMillis == DEFAULT_VALUE && toMillis == DEFAULT_VALUE && timeInMillis == DEFAULT_VALUE) {
            return ALL_DATA;
        }
        if (fromMillis != DEFAULT_VALUE && toMillis != DEFAULT_VALUE && toMillis > fromMillis) {
            return DURATION_TIME;
        }
        if (toMillis != DEFAULT_VALUE && timeInMillis != DEFAULT_VALUE) {
            return BEFORE_SOMETIME;
        }
        if (toMillis != DEFAULT_VALUE && timeInMillis == DEFAULT_VALUE) {
            return BEFORE_THETIME;
        }
        if (fromMillis != DEFAULT_VALUE && timeInMillis != DEFAULT_VALUE) {
            return SINCE_SOMETIME;
        }
        if (fromMillis != DEFAULT_VALUE && timeInMillis == DEFAULT_VALUE) {
            return SINCE_THETIME;
        }
        return 0;
    }
}
