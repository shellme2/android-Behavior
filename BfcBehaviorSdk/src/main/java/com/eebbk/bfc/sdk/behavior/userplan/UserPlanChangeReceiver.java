package com.eebbk.bfc.sdk.behavior.userplan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.eebbk.bfc.sdk.behavior.report.control.ReportController;
import com.eebbk.bfc.sdk.behavior.utils.ConfigAgent;

/**
 * 广播接收器<br>
 * 主要处理以下业务：<br>
 * 监听用户体验计划开关 启动采集服务；<br>
 */
public class UserPlanChangeReceiver extends BroadcastReceiver {

    private static final String ACTION_USER_PLAN_CHANGE = "user_plan_change_action";
    private static final String TAG = "UserPlanChangeReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        String action = intent.getAction();
        Log.v(TAG, "action:" + action);
        if (TextUtils.equals(ACTION_USER_PLAN_CHANGE, action) && !ConfigAgent.getBehaviorConfig().isIgnoreUserPlan) {
            ReportController.getInstance().userPlanSettingChange();
        }
    }

}
