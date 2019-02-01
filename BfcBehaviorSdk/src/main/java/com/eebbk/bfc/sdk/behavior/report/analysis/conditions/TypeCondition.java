package com.eebbk.bfc.sdk.behavior.report.analysis.conditions;

/**
 * 文  件：TypeCondition.java
 * 公  司：步步高教育电子
 * 日  期：2016/8/15  10:00
 * 作  者：HeChangPeng
 */

public class TypeCondition extends BaseCondition {

    private int[] dataType;

    public TypeCondition setEventType(int... eventType) {
        this.dataType = eventType;
        return this;
    }

    public int[] getEventType() {
        return dataType == null ? null : dataType.clone();
    }

    @Override
    public int getType() {
        if (dataType == null || dataType.length == 0) {
            return ALL_DATA;
        }
        return 0;
    }
}
