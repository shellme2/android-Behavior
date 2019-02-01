/**
 * 文  件：SortByType.java
 * 公  司：步步高教育电子
 * 日  期：2016/8/10  14:47
 * 作  者：HeChangPeng
 */
package com.eebbk.bfc.sdk.behavior.report.analysis.sort;

import com.eebbk.bfc.sdk.behavior.control.report.NotifyAgent;
import com.eebbk.bfc.sdk.behavior.db.entity.Record;
import com.eebbk.bfc.sdk.behavior.report.analysis.conditions.BaseCondition;
import com.eebbk.bfc.sdk.behavior.report.analysis.conditions.TypeCondition;
import com.eebbk.bfc.sdk.behavior.report.analysis.interfaces.ISort;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 通过数据类型过滤
 */
public class SortByType implements ISort<List<Record>> {

    private TypeCondition typeCondition;
    private static final String TAG = "SortByType";

    @Override
    public List<Record> doSort(List<Record> list) {
        if (typeCondition == null) {
            return list;
        }
        switch (typeCondition.getType()) {
            case BaseCondition.ALL_DATA:
                return list;
            case 0:
                return filterDataByType(list);
            default:
                break;
        }
        LogUtils.d(TAG, "按类型过滤数据");
        return list;
    }

    public SortByType(TypeCondition condition) {
        typeCondition = condition;
    }

    private SortByType() {

    }

    private List<Record> filterDataByType(List<Record> dataList) {
        int[] conditionType = typeCondition.getEventType();
        if(conditionType == null || conditionType.length == 0){
            return dataList;
        }
        List<Record> filterList = new ArrayList<>();
        List<Record> remainList = new ArrayList<>();
        int typeSize = conditionType.length;
        for (Record record : dataList) {
            for (int i = 0; i < typeSize; i++) {
                if (record.getEventType() == conditionType[i]) {
                    filterList.add(record);
                    break;
                }
                if (i == typeSize - 1) {
                    remainList.add(record);
                }
            }
        }
        if(filterList.size() <= 0 && remainList.size() > 0){
            //已经没有要优先上报数据，就把剩余数据上报
            filterList.addAll(remainList);
            remainList.clear();
        }
        LogUtils.d(TAG, "过滤出来的数据size=" + filterList.size());
        LogUtils.d(TAG, "过滤剩下的数据size=" + remainList.size());
        NotifyAgent.saveRecords(remainList);
        return filterList;
    }
}
