/**
 * 文  件：FixOrPushReceiver.java
 * 公  司：步步高教育电子
 * 日  期：2016/8/19  15:37
 * 作  者：HeChangPeng
 */
package com.eebbk.bfc.sdk.behavior.report.upload.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.eebbk.bfc.sdk.behavior.error.ErrorCode;
import com.eebbk.bfc.sdk.behavior.report.common.constants.ConstData;
import com.eebbk.bfc.sdk.behavior.report.common.constants.ReportMode;
import com.eebbk.bfc.sdk.behavior.report.control.ReportAgent;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

import java.util.Arrays;

/**
 * 此广播接收用于收到定时消息或推送消息时触发上报
 */
public class FixOrPushReceiver extends BroadcastReceiver {

    private static final String TAG = "FixOrPushReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && ConstData.FIXTIME_TRIGER_ACTION.equals(intent.getAction())) {
            int[] types = ReportAgent.getReportMode();
            Arrays.sort(types);
            if (Arrays.binarySearch(types, ReportMode.MODE_FIXTIME) < 0) {
                LogUtils.bfcExceptionLog(TAG, ErrorCode.REPORT_ILLEGAL_MODE_START_FIXED_TIME);
                return;
            }
            LogUtils.i(TAG, "收到定时消息，进行上报");
            // 此处需要加intent内的参数签名验证
            ReportAgent.doReport(ReportMode.MODE_FIXTIME);
        }
    }
}
