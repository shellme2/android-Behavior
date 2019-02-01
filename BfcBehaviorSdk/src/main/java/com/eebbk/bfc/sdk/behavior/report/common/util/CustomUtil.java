package com.eebbk.bfc.sdk.behavior.report.common.util;

import android.content.Context;

import com.eebbk.bfc.sdk.behavior.db.entity.Record;
import com.eebbk.bfc.sdk.behavior.error.ErrorCode;
import com.eebbk.bfc.sdk.behavior.report.analysis.interfaces.ISort;
import com.eebbk.bfc.sdk.behavior.report.common.constants.ConstData;
import com.eebbk.bfc.sdk.behavior.utils.ConfigAgent;
import com.eebbk.bfc.sdk.behavior.utils.ListUtils;
import com.eebbk.bfc.sdk.behavior.utils.NetUtils;
import com.eebbk.bfc.sdk.behavior.utils.StoreUtils;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 文  件：CustomUtil.java
 * 公  司：步步高教育电子
 * 日  期：2016/8/9  14:54
 * 作  者：HeChangPeng
 */

public class CustomUtil {

    private static final String TAG = "CustomUtil";

    /**
     * 过滤数据
     */
    public static List<Record> sortData(List<Record> dataList, ISort sortAlgorithm) {
        if (sortAlgorithm == null) {
            return dataList;
        }
        return (List<Record>) sortAlgorithm.doSort(dataList);
    }

//    /**
//     * 过滤数据
//     */
//    public static List<Record> sortData(List<Record> dataList, ISort...sortAlgorithms) {
//        if (sortAlgorithms == null || sortAlgorithms.length == 0) {
//            return dataList;
//        }
//        List<Record> temp = new ArrayList<>(dataList);
//        for (ISort sortAlgorithm : sortAlgorithms) {
//            if(sortAlgorithm == null){
//                continue;
//            }
//            temp = (List<Record>) sortAlgorithm.doSort(temp);
//        }
//        return temp;
//    }

    /**
     * 切分任务，每个上报任务必须不大于 maxSize 条记录
     *
     * @param dataList
     * @param maxSize
     * @return
     */
    public static List<List<Record>> divideTask(List<Record> dataList, int maxSize) {
        List<List<Record>> taskList = new ArrayList<List<Record>>();
        if (ListUtils.isEmpty(dataList)) {
            LogUtils.w(TAG, "原始dataList为空");
            return taskList;
        }
        doIterate(taskList, dataList, maxSize);
        LogUtils.i(TAG, "总上报任务个数=" + taskList.size());
        return taskList;
    }

    /**
     * 遍历
     */
    private static void doIterate(List<List<Record>> taskList, List<Record> dataList, int maxSize) {
        if (dataList.size() <= maxSize) {
            taskList.add(dataList);
        } else {
            taskList.add(dataList.subList(0, maxSize));
            doIterate(taskList, dataList.subList(maxSize, dataList.size()), maxSize);
        }
    }

    /**
     * 获取上报可以设置的线程数
     */
    public static int getAvailableThread() {
        int totalAvailable = Runtime.getRuntime().availableProcessors();
        LogUtils.v(TAG, "可用线程数=" + totalAvailable);
        if (totalAvailable > ConstData.MAX_UPLOAD_THREAD) {
            return ConstData.MAX_UPLOAD_THREAD;
        }
        return totalAvailable <= 0 ? ConstData.MAX_UPLOAD_THREAD : totalAvailable;
    }

    /**
     * 执行上报前的环境判断
     */
    public static boolean judgeParamsNull(Context context, boolean mIsOpen
            , String modeName, int modeType, int... triggerMode) {
        if (!mIsOpen) {
            LogUtils.e(TAG, modeName + " 模式尚未开启");
            LogUtils.bfcExceptionLog(TAG, ErrorCode.REPORT_THE_MODE_ALREADY_CLOSED);
            return true;
        }

        if (context == null) {
            LogUtils.bfcExceptionLog(TAG, ErrorCode.CONTROL_INIT_DOREPORT);
            return true;
        }

        Arrays.sort(triggerMode);
        if (Arrays.binarySearch(triggerMode, modeType) < 0) {
            String triggerModeStr = Arrays.toString(triggerMode);
            LogUtils.d(TAG, "无效上报触发,当前上报模式类型为:" + modeType + " 触发上报类型为:" + triggerModeStr);
            return true;
        }

        if (StoreUtils.getExternalStoreAvailableSize() < ConfigAgent.getBehaviorConfig().minStoreAvailableSize) {
            LogUtils.bfcExceptionLog(TAG, ErrorCode.REPORT_INSUFFICIENT_REMAINING_SPACE);
            return true;
        }

        return !NetUtils.isAllowUpload(context);
    }

    private CustomUtil() {
        //prevent the instance
    }
}
