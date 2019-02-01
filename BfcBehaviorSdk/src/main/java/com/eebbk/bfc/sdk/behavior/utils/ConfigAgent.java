package com.eebbk.bfc.sdk.behavior.utils;

import com.eebbk.bfc.sdk.behavior.BehaviorConfig;
import com.eebbk.bfc.sdk.behavior.error.ErrorCode;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

/**
 * @author hesn
 * @function
 * @date 16-9-22
 * @company 步步高教育电子有限公司
 */

public class ConfigAgent {

    private static BehaviorConfig mConfig;
    private static final String TAG = "ConfigAgent";

    /**
     * 设置整体采集配置信息
     * @param behaviorConfig
     */
    public static void setBehaviorConfig(BehaviorConfig behaviorConfig){
        if(behaviorConfig == null){
            LogUtils.bfcExceptionLog(TAG, ErrorCode.CONTROL_NULL_POINTER_BEHAVIOR_CONFIG);
            return;
        }
        mConfig = behaviorConfig;
    }

    /**
     * 获取整体采集的配置信息
     * @return
     */
    public static BehaviorConfig getBehaviorConfig(){
        if(mConfig == null){
            mConfig = new BehaviorConfig();
        }
        return mConfig;
    }

    private ConfigAgent(){
        //prevent the instance
    }
}
