package com.eebbk.bfc.sdk.behavior.userplan;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.eebbk.bfc.common.app.SystemStoreUtils;

/**
 * 用户体验计划
 */
public class UserPlan {
    private static final String TAG = "UserPlan";
    private static final String KEY_UESER_PLAN_ENABLED = "user_plan_enabled";
    //		user_plan_enabled 为null或者true是打开计划任务
    //		Settings.System.UESER_PLAN_ENABLED  上层使用，用"user_plan_enabled"代替
    private static final String NOT_GET = "not_get";
    /**
     * 从系统中获取到的用户体验计划原始值
     */
    private String mUserPlan = NOT_GET;
    /**
     * 是否开启用户体验计划
     */
    private boolean isEnabled = false;

    /**
     * 是否打开用户体验计划
     *
     * @param context
     * @return true:打开
     */
    public boolean isEnabled(Context context) {
        if (TextUtils.equals(NOT_GET, mUserPlan)) {
            onChange(context);
        }
        return isEnabled;
    }

    /**
     * 参数值发生改变,从新获取
     *
     * @param context
     */
    public void onChange(Context context) {
        mUserPlan = getUserPlanEnable(context);
        Log.v(TAG, "User plan enable values is change to " + mUserPlan);
        isEnabled = TextUtils.equals("1", mUserPlan);
    }

    /**
     * 清除缓存
     */
    public void clean() {
        mUserPlan = NOT_GET;
        isEnabled = false;
    }

    /**
     * 获取用户体验计划值
     *
     * @param context
     * @return
     */
    private String getUserPlanEnable(@NonNull Context context) {
        if (context == null) {
            Log.w(TAG, "Get user plan fail, context is null!");
            return NOT_GET;
        }
        return SystemStoreUtils.get(context.getApplicationContext(), KEY_UESER_PLAN_ENABLED);
    }
}



