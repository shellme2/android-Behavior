package com.eebbk.bfc.sdk.behavior.control.report;

import android.util.SparseIntArray;

import com.eebbk.bfc.sdk.behavior.control.report.entity.NotifyReportInfo;

/**
 * @author hesn
 * @function 数据统计管理
 * @date 16-8-18
 * @company 步步高教育电子有限公司
 */

public class CountManager {
    /**
     * 以事件类型 EType 为key，事件数量为 value
     */
    private final SparseIntArray counts = new SparseIntArray();
    private static CountManager mInstance;

    public static CountManager getInstance() {
        if(mInstance != null){
            return mInstance;
        }
        synchronized (CountManager.class) {
            if (null == mInstance) {
                mInstance = new CountManager();
            }
        }
        return mInstance;
    }

    /**
     * 累加总数
     * @param num 数量
     */
    public void addTotal(int num){
        counts.put(NotifyReportInfo.TOTAL_INDEX , getTotal() + num);
    }

    /**
     * 根据事件类型 EType 累加统计数量
     * @param eventType　事件类型
     */
    public void addByEventType(int eventType){
        try {
            // SparseIntArray.put会数组越界
            counts.put(eventType , getCountByEventType(eventType) + 1);
            addTotal(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清空
     */
    public void clean(){
        counts.clear();
    }

    /**
     * 获取统计数据
     * @return
     */
    public SparseIntArray getCounts(){
        return counts;
    }

    /**
     * 获取数据库和缓存的数据总数
     * @return
     */
    private int getTotal(){
        return getCountByEventType(NotifyReportInfo.TOTAL_INDEX);
    }

    /**
     * 获取数据库和缓存的数据总数
     * @return
     */
    private int getCountByEventType(int eventType){
        return counts.get(eventType , 0);
    }

    private CountManager(){
        //prevent the instance
    }
}
