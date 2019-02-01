package com.eebbk.bfc.sdk.behavior.utils;

import android.os.SystemClock;
import android.text.TextUtils;

import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

/**
 * @author hesn
 * @function Session代理类
 * @date 16-11-1
 * @company 步步高教育电子有限公司
 */

public class SessionAgent {

    private static String mSessionid;
    /**
     * 进入后台时间
     */
    private static long mBackstageTime = 0;

    /**
     * 界面前台调用
     * <br> BCLifeCycleCallback.java会自动调用
     * <br> 如有特殊需求,如dialog请自行调用onResume()和onPause()
     *
     * @return 是否刷新了 Session
     */
    public static boolean onResume() {
        if (TextUtils.isEmpty(mSessionid) || needRefresh()) {
            //首次进入
            refreshSessionId();
            return true;
        }

        return false;
    }

    /**
     * 界面进入后台调用
     * <br> BCLifeCycleCallback.java会自动调用
     * <br> 如有特殊需求,如dialog请自行调用onResume()和onPause()
     */
    public static void onPause() {
        mBackstageTime = SystemClock.elapsedRealtime();
    }

    public static void clean() {
        mSessionid = null;
        mBackstageTime = 0;
    }

    /**
     * 获取 Session Id
     *
     * @return
     */
    public static String getSessionId() {
        if(TextUtils.isEmpty(mSessionid)){
            refreshSessionId();
        }
        return mSessionid;
    }

    /**
     * 是否需要刷新 Session
     *
     * @return
     */
    private static boolean needRefresh() {
        return SystemClock.elapsedRealtime() - mBackstageTime > ConfigAgent.getBehaviorConfig().sessionTimeout;
    }

    /**
     * 刷新 Session
     */
    private static void refreshSessionId() {
        LogUtils.v("refresh session Id");
        ExecutorsUtils.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                // 有app反馈UUID.randomUUID()耗时300多毫秒，影响启动速度
                mSessionid = System.currentTimeMillis() + "_" + SystemUtils.getUUID();
            }
        });
    }

    private SessionAgent() {

    }
}
