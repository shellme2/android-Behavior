package com.eebbk.bfc.sdk.behavior.aidl;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;

import com.eebbk.bfc.sdk.behavior.aidl.listener.InnerListener;
import com.eebbk.bfc.sdk.behavior.aidl.utils.ConfigAgent;
import com.eebbk.bfc.sdk.behavior.aidl.utils.LogUtils;
import com.eebbk.bfc.sdk.behavior.aidl.utils.PressHomeKey;
import com.eebbk.bfc.sdk.behavior.aidl.utils.SessionAgent;

import java.util.ArrayList;
import java.util.List;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
class BCLifeCycleCallback implements Application.ActivityLifecycleCallbacks {

    private static final String TAG = "BCLifeCycleCallback";
    private final List<Activity> mActivityList = new ArrayList<Activity>();
    private InnerListener mInnerListener;

    public BCLifeCycleCallback(InnerListener innerListener) {
        mInnerListener = innerListener;
        cleanList();
    }

    public void setInnerListener(InnerListener l) {
        this.mInnerListener = l;
    }

    public void cleanList() {
        mActivityList.clear();
    }

    public void exit() {
        if (mActivityList.isEmpty()) {
            return;
        }
        LogUtils.i(TAG, "exit() mActivityList.size():" + mActivityList.size());
        List<Activity> activityList = new ArrayList<>(mActivityList);
        for (Activity activity : activityList) {
            if (activity == null) {
                continue;
            }
            LogUtils.i(TAG, "finish:" + activity.getClass().getName());
            activity.finish();
        }
        cleanList();
    }

    @Override
    public synchronized void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (mActivityList.isEmpty()) {
            if (ConfigAgent.getBehaviorConfig().enableCollectLaunch && mInnerListener != null) {
                mInnerListener.onAppLaunch();
            }
            SessionAgent.clean();
        }
        mActivityList.add(activity);
        LogUtils.v(TAG, "==================onActivityCreated=====================");
    }

    @Override
    public synchronized void onActivityResumed(Activity activity) {
        String activityName = activity.getClass().getName();
        if (ConfigAgent.getBehaviorConfig().openActivityDurationTrack && mInnerListener != null) {
            mInnerListener.onPageBegin(activityName);
        }
        if (SessionAgent.onResume() && mInnerListener != null) {
            //新启动
            mInnerListener.onRealTime2Upload();
        }
        LogUtils.v(TAG, "==================onActivityResumed=====================");
    }

    @Override
    public synchronized void onActivityPaused(Activity activity) {
        String activityName = activity.getClass().getName();
        if (ConfigAgent.getBehaviorConfig().openActivityDurationTrack && mInnerListener != null) {
            mInnerListener.onPageEnd(activityName);
        }
        SessionAgent.onPause();
        LogUtils.v(TAG, "==================onActivityPaused=====================");
    }

    @Override
    public synchronized void onActivityDestroyed(Activity activity) {
        mActivityList.remove(activity);
        LogUtils.v(TAG, "==================onActivityDestroyed=====================");
    }

    @Override
    public void onActivitySaveInstanceState(Activity arg0, Bundle arg1) {
        LogUtils.v(TAG, "==================onActivitySaveInstanceState=====================");
    }

    @Override
    public void onActivityStarted(Activity arg0) {
        LogUtils.v(TAG, "==================onActivityStarted=====================");
        //mark 太频繁，需要改善
        PressHomeKey.getInstance().registerHomePress(arg0);
    }

    @Override
    public void onActivityStopped(Activity arg0) {
        LogUtils.v(TAG, "==================onActivityStopped=====================");
        PressHomeKey.getInstance().unRegisterHomePress(arg0);
    }

}
