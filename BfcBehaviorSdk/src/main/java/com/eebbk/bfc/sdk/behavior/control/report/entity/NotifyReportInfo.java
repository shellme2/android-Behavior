package com.eebbk.bfc.sdk.behavior.control.report.entity;

import android.util.SparseIntArray;

/**
 * @author hesn
 * @function 数据统计
 * @date 16-8-18
 * @company 步步高教育电子有限公司
 */
public class NotifyReportInfo {
    /** 总数在数组中的索引 */
    public static final int TOTAL_INDEX = 0;
    /** 以事件类型 EType 为key，事件数量为 value */
    private SparseIntArray counts = new SparseIntArray();

    public NotifyReportInfo(SparseIntArray counts){
        this.counts = counts;
    }

    /**
     * 获取数据库和缓存池所有数据的总数
     * @return
     */
    public int getTotal(){
        return getCountByEventType(TOTAL_INDEX);
    }

    /**
     * 获取这类事件数据在缓存区的数量
     * @param eventType 事件类型 EType
     * @return
     */
    public int getCountByEventType(int eventType){
        return counts.get(eventType , 0);
    }
}
