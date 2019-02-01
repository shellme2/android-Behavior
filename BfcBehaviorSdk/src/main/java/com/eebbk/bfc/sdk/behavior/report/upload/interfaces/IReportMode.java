/**
 * 文  件：IReportMode.java
 * 公  司：步步高教育电子
 * 日  期：2016/8/9  9:52
 * 作  者：HeChangPeng
 */
package com.eebbk.bfc.sdk.behavior.report.upload.interfaces;

import android.content.Context;

import com.eebbk.bfc.sdk.behavior.report.analysis.interfaces.ISort;

public interface IReportMode {

    int modeType();

    void initMode(Context context, IReportModeConfig config);

    void openMode(boolean flag);

    boolean isModeOpen();

    void doReport(Context context, int...triggerMode);
}
