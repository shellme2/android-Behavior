/**
 * 文  件：FixedTimeMode.java
 * 公  司：步步高教育电子
 * 日  期：2016/8/9  9:42
 * 作  者：HeChangPeng
 */
package com.eebbk.bfc.sdk.behavior.report.upload.mode;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.eebbk.bfc.sdk.behavior.report.analysis.interfaces.ISort;
import com.eebbk.bfc.sdk.behavior.report.common.constants.ConstData;
import com.eebbk.bfc.sdk.behavior.report.common.constants.ReportMode;
import com.eebbk.bfc.sdk.behavior.report.upload.interfaces.IReportModeConfig;
import com.eebbk.bfc.sdk.behavior.utils.ConfigAgent;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

import java.util.Calendar;

/**
 * 定时上传模式
 */
public class FixedTimeMode extends ABaseUploadMode {

    private static final int REQUEST_CODE = 122;
    private static final String TAG = "FixedTimeMode";

    @Override
    public int modeType() {
        return ReportMode.MODE_FIXTIME;
    }

    @Override
    public void initMode(Context context, IReportModeConfig config) {
        setFixTime(context,
                getInteger(config.getConfig()[0]),
                getInteger(config.getConfig()[1]),
                getInteger(config.getConfig()[2]));
        LogUtils.d(TAG, "当前上报模式切换为:定时上报");
    }

    @Override
    void doReport(Context context) {
        dealUpload(context);
    }

    /**
     * 设置固定时间
     */
    private synchronized void setFixTime(Context context, int hour, int minute, int seconds) {
        Intent intent = new Intent(ConstData.FIXTIME_TRIGER_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, seconds);
        am.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), 12 * 60 * 60 * 1000, pendingIntent);
        save(hour, minute, seconds);

        LogUtils.v(TAG, "设置好定时任务");
    }

    private void save(int hour, int minute, int seconds){
        ConfigAgent.getBehaviorConfig().reportConfig.reportModeConfig.fixedTimeHour = hour;
        ConfigAgent.getBehaviorConfig().reportConfig.reportModeConfig.fixedTimeMinute = minute;
        ConfigAgent.getBehaviorConfig().reportConfig.reportModeConfig.fixedTimeSeconds = seconds;
    }

    private int getInteger(Object o) {
        try {
            return Integer.parseInt((o == null ? "0" : (o + "")));
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    String getModeName() {
        return "定时上传";
    }

}
