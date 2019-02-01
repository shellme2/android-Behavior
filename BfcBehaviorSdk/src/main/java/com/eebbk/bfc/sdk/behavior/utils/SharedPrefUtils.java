package com.eebbk.bfc.sdk.behavior.utils;

import com.eebbk.bfc.common.app.SharedPreferenceUtils;

/**
 * @author hesn
 * @function
 * @date 16-9-19
 * @company 步步高教育电子有限公司
 */

public class SharedPrefUtils {

    private static final String PREF_NAME = "Bfcbehavior";

    public static void saveKeyStringValue(String key, String value) {
        SharedPreferenceUtils.getInstance(ContextUtils.getContext(), PREF_NAME).put(key, value);
    }

    public static void saveKeyLongValue(String key, long value) {
        SharedPreferenceUtils.getInstance(ContextUtils.getContext(), PREF_NAME).put(key, value);
    }

    public static String getKeyStringValue(String key, String defValue) {
        return SharedPreferenceUtils.getInstance(ContextUtils.getContext(), PREF_NAME).get(key, defValue);
    }

    public static long getKeyLongValue(String key, long defValue) {
        return SharedPreferenceUtils.getInstance(ContextUtils.getContext(), PREF_NAME).get(key, defValue);
    }

    /**
     * 删掉一条数据
     */
    public static void remove(String key) {
        SharedPreferenceUtils.getInstance(ContextUtils.getContext(), PREF_NAME).remove(key);
    }
}
