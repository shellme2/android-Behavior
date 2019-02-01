package com.eebbk.bfc.sdk.behavior;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;

import com.eebbk.bfc.sdk.behavior.control.collect.CollectionManager;
import com.eebbk.bfc.sdk.behavior.report.common.constants.ReportMode;
import com.eebbk.bfc.sdk.behavior.report.common.util.PressHomeKey;
import com.eebbk.bfc.sdk.behavior.report.control.ReportAgent;
import com.eebbk.bfc.sdk.behavior.utils.ConfigAgent;
import com.eebbk.bfc.sdk.behavior.utils.ListUtils;
import com.eebbk.bfc.sdk.behavior.utils.SessionAgent;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

import java.util.ArrayList;
import java.util.List;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class BCLifeCycleCallback implements Application.ActivityLifecycleCallbacks {

    private static final String TAG = "BCLifeCycleCallback";
    private final List<Activity> mActivityList = new ArrayList<Activity>();

    public BCLifeCycleCallback() {
        cleanList();
    }

    public void cleanList() {
        mActivityList.clear();
    }

    public void exit() {
        if (ListUtils.isEmpty(mActivityList)) {
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
        if (ListUtils.isEmpty(mActivityList)) {
            if(ConfigAgent.getBehaviorConfig().enableCollectLaunch){
                BehaviorCollector.getInstance().appLaunch();
            }
            SessionAgent.clean();
        }
        mActivityList.add(activity);
        LogUtils.v(TAG, "==================onActivityCreated=====================");
    }

    @Override
    public synchronized void onActivityResumed(Activity activity) {
        String activityName = activity.getClass().getName();
        if (BehaviorCollector.getInstance().getActivityDurationTrack()) {
            BehaviorCollector.getInstance().pageBegin(activityName);
        }
        if (SessionAgent.onResume()) {
            //新启动
            ReportAgent.doReport(ReportMode.MODE_LAUNCH);
        }
        LogUtils.v(TAG, "==================onActivityResumed=====================");
    }

    @Override
    public synchronized void onActivityPaused(Activity activity) {
        String activityName = activity.getClass().getName();
        if (BehaviorCollector.getInstance().getActivityDurationTrack()) {
            BehaviorCollector.getInstance().pageEnd(activityName);
        }
        SessionAgent.onPause();
        CollectionManager.getInstance().saveData2DB();
        LogUtils.v(TAG, "==================onActivityPaused=====================");
    }

    @Override
    public synchronized void onActivityDestroyed(Activity activity) {
        mActivityList.remove(activity);
        if (mActivityList.size() <= 0) {
            ReportAgent.doReport(ReportMode.MODE_EXIT);
        }
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
        PressHomeKey.getInstance().registerHomePress();
    }

    @Override
    public void onActivityStopped(Activity arg0) {
        LogUtils.v(TAG, "==================onActivityStopped=====================");
        PressHomeKey.getInstance().unRegisterHomePress();
    }

}
