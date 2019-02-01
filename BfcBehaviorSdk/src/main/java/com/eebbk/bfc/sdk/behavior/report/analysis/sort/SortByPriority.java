/**
 * 文  件：SortByPriority.java
 * 公  司：步步高教育电子
 * 日  期：2016/8/10  14:49
 * 作  者：HeChangPeng
 */
package com.eebbk.bfc.sdk.behavior.report.analysis.sort;

import com.eebbk.bfc.sdk.behavior.db.entity.Record;
import com.eebbk.bfc.sdk.behavior.report.analysis.conditions.BaseCondition;
import com.eebbk.bfc.sdk.behavior.report.analysis.conditions.PriorityCondition;
import com.eebbk.bfc.sdk.behavior.report.analysis.interfaces.ISort;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

import java.util.List;

/**
 * 通过数据优先级过滤
 */
public class SortByPriority implements ISort<List<Record>> {

    private PriorityCondition priorityCondition;

    private SortByPriority() {

    }

    public SortByPriority(PriorityCondition condition) {
        priorityCondition = condition;
    }

    @Override
    public List<Record> doSort(List<Record> list) {
        if (priorityCondition == null) {
            return list;
        }
        switch (priorityCondition.getType()) {
            case BaseCondition.ALL_DATA:
            case 0:
                return list;
            default:
                break;
        }
        LogUtils.v("SortByPriority", "按优先级进行排序");
        return list;
    }
}
