package com.eebbk.bfc.sdk.behavior.exception.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.eebbk.bfc.sdk.behavior.exception.CrashReportActivity;
import com.eebbk.bfc.sdk.behavior.utils.ContextUtils;

/**
 * @author hesn
 * @function
 * @date 17-3-2
 * @company 步步高教育电子有限公司
 */

public class ActivityCrashReportUiImpl implements ICrashReportUI {

    @Override
    public void show(String errMsg) {
        Intent intent = new Intent(ContextUtils.getContext(), CrashReportActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        PendingIntent contentIntent = PendingIntent.getActivity(ContextUtils.getContext(), 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager mgr = (AlarmManager) ContextUtils.getContext()
                .getSystemService(Context.ALARM_SERVICE);
//		if (isReStartTooMany()) {
//			mgr.cancel(contentIntent);
//			return;
//		}
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 300,
                contentIntent);
//		if (DEBUG) {
//			LogUtils.d(TAG, "we restart the app");
//		}
    }
}
