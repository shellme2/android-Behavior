package com.eebbk.bfc.sdk.behavior.report.analysis.conditions;

/**
 * 文  件：PriorityCondition.java
 * 公  司：步步高教育电子
 * 日  期：2016/8/15  9:57
 * 作  者：HeChangPeng
 */

public class PriorityCondition extends BaseCondition {

    private int priority = -1;

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int getType() {
        if (priority == -1) {
            return ALL_DATA;
        }
        return 0;
    }
}
