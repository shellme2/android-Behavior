/**
 * 文  件：QuantifyMode.java
 * 公  司：步步高教育电子
 * 日  期：2016/8/9  9:41
 * 作  者：HeChangPeng
 */
package com.eebbk.bfc.sdk.behavior.report.upload.mode;

import android.content.Context;

import com.eebbk.bfc.sdk.behavior.report.analysis.interfaces.ISort;
import com.eebbk.bfc.sdk.behavior.report.common.constants.ReportMode;
import com.eebbk.bfc.sdk.behavior.report.upload.interfaces.IReportModeConfig;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

/**
 * 即时上报模式
 * <p> 无其他判断条件上传 <p/>
 */
public class RealTimeMode extends ABaseUploadMode {

    private static final String TAG = "RealTimeMode";

    @Override
    public int modeType() {
        return ReportMode.MODE_REAL_TIME;
    }

    @Override
    public void initMode(Context context, IReportModeConfig config) {
        LogUtils.d(TAG, "当前上报模式切换为:即时上报");
    }

    @Override
    public void openMode(boolean flag) {
        // 无论设置什么上报模式，都可以触发即时上传
        super.openMode(true);
    }

    @Override
    void doReport(Context context) {
        dealUpload(context);
    }

    @Override
    String getModeName() {
        return "即时上传";
    }
}
