package com.eebbk.bfc.sdk.behavior.cache.policy;

import android.os.SystemClock;

import com.eebbk.bfc.sdk.behavior.cache.constant.PolicyType;
import com.eebbk.bfc.sdk.behavior.cache.entity.PolicyInfo;

/**
 * @author hesn
 * @function 时间缓冲策略
 * 在设置的时间段内，无论来多少数据，统一触发一次保存数据库操作
 * @date 16-8-13
 * @company 步步高教育电子有限公司
 */

public class TimeCachePolicy extends APolicy {

    //上次入库的时间
    private long mLastTimeStamp = 0;

    @Override
    public int policyType() {
        return PolicyType.TIME;
    }

    @Override
    public boolean save2DBAble(PolicyInfo info) {
        return info != null && info.timeStamp - mLastTimeStamp > getCacheConfig().getCacheTime();
    }

    @Override
    public void syncData() {
        mLastTimeStamp = getTimeStamp();
    }

    /**
     * 获取时间戳
     * @return
     */
    public static long getTimeStamp(){
        return SystemClock.elapsedRealtime();
    }
}
