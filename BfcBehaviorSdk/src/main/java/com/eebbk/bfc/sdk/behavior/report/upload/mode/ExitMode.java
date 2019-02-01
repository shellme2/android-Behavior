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
 * app退出上报模式
 */
public class ExitMode extends ABaseUploadMode {

    /**
     * 是否开启此模式
     */
    private static final String TAG = "ExitMode";

    @Override
    public int modeType() {
        return ReportMode.MODE_EXIT;
    }

    @Override
    public void initMode(Context context, IReportModeConfig config) {
        LogUtils.d(TAG, "当前上报模式切换为:app退出上报");
    }

    @Override
    String getModeName() {
        return "退出上传";
    }

    @Override
    void doReport(Context context) {
        dealUpload(context);
    }
}
