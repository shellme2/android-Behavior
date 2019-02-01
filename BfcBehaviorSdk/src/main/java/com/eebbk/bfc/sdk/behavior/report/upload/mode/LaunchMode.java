/**
 * 文  件：PushMode.java
 * 公  司：步步高教育电子
 * 日  期：2016/8/9  9:48
 * 作  者：HeChangPeng
 */
package com.eebbk.bfc.sdk.behavior.report.upload.mode;

import android.content.Context;

import com.eebbk.bfc.sdk.behavior.report.analysis.interfaces.ISort;
import com.eebbk.bfc.sdk.behavior.report.common.constants.ReportMode;
import com.eebbk.bfc.sdk.behavior.report.upload.interfaces.IReportModeConfig;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

/**
 * app启动上报模式
 */
public class LaunchMode extends ABaseUploadMode {

    private static final String TAG = "LaunchMode";

    @Override
    public int modeType() {
        return ReportMode.MODE_LAUNCH;
    }

    @Override
    public void initMode(Context context, IReportModeConfig config) {
        LogUtils.d(TAG, "当前上报模式切换为:app启动上报");
    }

    @Override
    void doReport(Context context) {
        try {
            //provider可能没有初始化好,需要延时
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dealUpload(context);
    }

    @Override
    String getModeName() {
        return "启动上传";
    }
}
