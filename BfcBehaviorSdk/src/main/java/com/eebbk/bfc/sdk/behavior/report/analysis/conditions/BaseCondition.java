package com.eebbk.bfc.sdk.behavior.report.analysis.conditions;

/**
 * 文  件：BaseCondition.java
 * 公  司：步步高教育电子
 * 日  期：2016/8/15  9:35
 * 作  者：HeChangPeng
 */

public abstract class BaseCondition {

    /**
     * 不过滤，提取全部数据出来
     */
    public static final int ALL_DATA = 999;

    /**
     * 默认值
     */
    public static final int DEFAULT_VALUE = -1;

    public abstract int getType();
}
