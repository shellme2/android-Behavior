package com.eebbk.bfc.sdk.behavior.aidl.utils;

import com.eebbk.bfc.sdk.behavior.aidl.Settings;
import com.eebbk.bfc.sdk.behavior.aidl.error.ErrorCode;

/**
 * @author hesn
 * @function
 * @date 16-9-22
 * @company 步步高教育电子有限公司
 */

public class ConfigAgent {

    private static Settings mConfig;
    private static final String TAG = "ConfigAgent";

    /**
     * 设置整体采集配置信息
     * @param settings
     */
    public static void setBehaviorConfig(Settings settings){
        if(settings == null){
            LogUtils.bfcExceptionLog(TAG, ErrorCode.CONTROL_NULL_POINTER_BEHAVIOR_CONFIG);
            return;
        }
        mConfig = settings;
    }

    /**
     * 获取整体采集的配置信息
     * @return
     */
    public static Settings getBehaviorConfig(){
        if(mConfig == null){
            mConfig = new Settings();
        }
        return mConfig;
    }

    private ConfigAgent(){
        //prevent the instance
    }
}
