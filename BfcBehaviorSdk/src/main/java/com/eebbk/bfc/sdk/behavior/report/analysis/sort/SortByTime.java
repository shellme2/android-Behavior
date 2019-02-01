/**
 * 文  件：SortByTime.java
 * 公  司：步步高教育电子
 * 日  期：2016/8/10  14:46
 * 作  者：HeChangPeng
 */
package com.eebbk.bfc.sdk.behavior.report.analysis.sort;

import com.eebbk.bfc.sdk.behavior.control.report.NotifyAgent;
import com.eebbk.bfc.sdk.behavior.db.entity.Record;
import com.eebbk.bfc.sdk.behavior.report.analysis.conditions.BaseCondition;
import com.eebbk.bfc.sdk.behavior.report.analysis.conditions.TimeCondition;
import com.eebbk.bfc.sdk.behavior.report.analysis.filter.FilterFactory;
import com.eebbk.bfc.sdk.behavior.report.analysis.interfaces.ISort;
import com.eebbk.bfc.sdk.behavior.report.analysis.interfaces.ISortTimeFilter;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * 通过数据时间过滤
 */
public class SortByTime implements ISort<List<Record>> {

    private final TimeCondition timeCondition;
    private static final String TAG = "SortByTime";

    public SortByTime(TimeCondition condition) {
        timeCondition = condition;
    }

    @Override
    public List<Record> doSort(List<Record> list) {
        if (timeCondition == null) {
            LogUtils.bfcWLog(TAG, "时间过滤器条件信息 TimeCondition 没有初始化，请查看数据初始配置是否正确");
            return list;
        }
        int type = timeCondition.getType();
        if(type == BaseCondition.ALL_DATA || type == 0){
            return list;
        }
        ISortTimeFilter filter = FilterFactory.createTimeFilter(type);
        if(filter == null){
            LogUtils.bfcWLog(TAG, "时间过滤器初始化失败，请查看数据初始配置是否正确");
            return list;
        }
        LogUtils.d(TAG, "按时间过滤数据");
        return dataFilter(list, filter);
    }

    /**
     * 数据过滤
     * @param dataList 原数据
     * @param filter 过滤器
     * @return 过滤后需要上报的数据
     */
    private List<Record> dataFilter(List<Record> dataList, ISortTimeFilter filter) {
        if(filter == null){
            return dataList;
        }
        List<Record> filterList = new ArrayList<>();
        List<Record> remainList = new ArrayList<>();
        for (Record record : dataList) {
            if(record == null){
                continue;
            }
            try {
                if (filter.eligible(record, timeCondition)) {
                    filterList.add(record);
                } else {
                    remainList.add(record);
                }
            } catch (ParseException e) {
                e.printStackTrace();
                filterList.add(record);
            }
        }
        if(filterList.size() <= 0 && remainList.size() > 0){
            //已经没有要优先上报数据，就把剩余数据上报
            filterList.addAll(remainList);
            remainList.clear();
        }
        LogUtils.d(TAG, "过滤出来的数据size=" + filterList.size());
        LogUtils.d(TAG, "过滤剩下的数据size=" + remainList.size());
        //过滤后不需要上报的数据插回数据库
        NotifyAgent.saveRecords(remainList);
        return filterList;
    }

}
