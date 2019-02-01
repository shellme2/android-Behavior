/**
 * 文  件：PressHomeKey.java
 * 公  司：步步高教育电子
 * 日  期：2016/8/24  9:32
 * 作  者：HeChangPeng
 */
package com.eebbk.bfc.sdk.behavior.aidl.utils;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.eebbk.bfc.sdk.behavior.aidl.error.ErrorCode;
import com.eebbk.bfc.sdk.behavior.aidl.receiver.HomePressReceiver;

/**
 * 处理Home键按下
 */
public class PressHomeKey {

    /**
     * Home键监听是否已经注册
     */
    private boolean isRegister = false;
    private static final String TAG = "PressHomeKey";

    private static class InstanceHolder {
        private static final PressHomeKey mInstance = new PressHomeKey();
    }

    public static PressHomeKey getInstance() {
        return InstanceHolder.mInstance;
    }

    public void registerHomePress(Context context) {
        if(context == null){
            LogUtils.bfcExceptionLog(TAG, ErrorCode.REPORT_HOME_KEY_REG);
            return;
        }
        try {
            if (!isRegister) {
                context.getApplicationContext().registerReceiver(homePressReceiver, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
                isRegister = true;
            }
        } catch (Exception e) {
            LogUtils.w(TAG, e);
            LogUtils.bfcExceptionLog(TAG, ErrorCode.REPORT_HOME_KEY_REG);
        }
    }

    public void unRegisterHomePress(Context context) {
        if(context == null){
            LogUtils.bfcExceptionLog(TAG, ErrorCode.REPORT_HOME_KEY_UNREG);
            return;
        }
        try {
            if (isRegister) {
                context.getApplicationContext().unregisterReceiver(homePressReceiver);
                isRegister = false;
            }
        } catch (Exception e) {
            LogUtils.w(TAG, e);
            LogUtils.bfcExceptionLog(TAG, ErrorCode.REPORT_HOME_KEY_UNREG);
        }
    }

    private final HomePressReceiver homePressReceiver = new HomePressReceiver(new HomePressReceiver.HomePressListener() {
        @Override
        public void onHomePressed(Context context) {
            LogUtils.v(TAG, "按下了Home键");
//            ReportAgent.doReport(ReportMode.MODE_EXIT
//                    , ReportMode.MODE_QUANTITY
//                    , ReportMode.MODE_PERIOD);
            unRegisterHomePress(context);
        }
    });

    private PressHomeKey() {
        //prevent the instance
    }
}
