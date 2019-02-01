package com.eebbk.behavior.demo.utils;

import android.text.TextUtils;

import com.eebbk.bfc.json.JsonSyntaxException;
import com.eebbk.bfc.sdk.behavior.BehaviorConfig;
import com.eebbk.bfc.sdk.behavior.utils.ConfigAgent;
import com.eebbk.bfc.sdk.behavior.utils.JsonUtils;
import com.eebbk.bfc.sdk.behavior.utils.SharedPrefUtils;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

/**
 * @author hesn
 * @function
 * @date 16-11-4
 * @company 步步高教育电子有限公司
 */

public class ConfigUtils {

    private static final String TAG = "ConfigUtils";
    private static final String KEY_CONFIG = "allConfig";
    //部分测试模块不能跑monkey
    public static final boolean isMonkeyTest = false;

    public static BehaviorConfig getConfigFromDB() {
        String configJson = SharedPrefUtils.getKeyStringValue(KEY_CONFIG, null);
        LogUtils.i(TAG, "getConfigFromDB() configJson:" + configJson);
        if (TextUtils.isEmpty(configJson)) {
            LogUtils.i(TAG, "getConfigFromDB() configJson null");
            saveConfig();
            return ConfigAgent.getBehaviorConfig();
        }
        SDKConfig sdkConfig;
        try {
            sdkConfig = JsonUtils.fromJson(configJson, SDKConfig.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            LogUtils.i("getConfigFromDB() json to class fail!!");
            sdkConfig = new SDKConfig(ConfigAgent.getBehaviorConfig());
            saveConfig();
        }
        LogUtils.i(TAG, "getConfigFromDB() configJson:" + configJson);
        return sdkConfig.getBehaviorConfig(ConfigAgent.getBehaviorConfig());
    }

    public static void saveConfig() {
        ExecutorsUtils.execute(new Runnable() {
            @Override
            public void run() {
                String configJson = JsonUtils.toJson(new SDKConfig(ConfigAgent.getBehaviorConfig()));
                LogUtils.i(TAG, "save() configJson:" + configJson);
                SharedPrefUtils.saveKeyStringValue(KEY_CONFIG, configJson);
            }
        });
    }
}
