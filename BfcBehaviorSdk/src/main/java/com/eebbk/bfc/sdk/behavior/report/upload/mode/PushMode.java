/**
 * 文  件：PushMode.java
 * 公  司：步步高教育电子
 * 日  期：2016/8/9  9:48
 * 作  者：HeChangPeng
 */
package com.eebbk.bfc.sdk.behavior.report.upload.mode;

import android.content.Context;

import com.eebbk.bfc.sdk.behavior.report.analysis.interfaces.ISort;
import com.eebbk.bfc.sdk.behavior.report.upload.interfaces.IReportModeConfig;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

/**
 * 推送上报模式
 */
public class PushMode extends ABaseUploadMode {

    private static final String TAG = "PushMode";

    @Override
    public int modeType() {
//        return ReportMode.MODE_PUSH;
        return 0;
    }

    @Override
    public void initMode(Context context, IReportModeConfig config) {
        LogUtils.d(TAG, "当前上报模式切换为:推送上报");
    }

    @Override
    void doReport(Context context) {
        dealUpload(context);
    }

    @Override
    String getModeName() {
        return "推送上传";
    }
}
