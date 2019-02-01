package com.eebbk.bfc.sdk.behavior.report.common.util;

import com.eebbk.bfc.sdk.behavior.utils.SharedPrefUtils;

/**
 * 文  件：ModeConfig.java
 * 公  司：步步高教育电子
 * 日  期：2016/8/9  14:19
 * 作  者：HeChangPeng
 */

public class ModeConfig extends SharedPrefUtils{

    /**
     * 对SharedPreferences的key的存储
     */
    public static final class PreferencesKey {
        /**
         * 上报周期
         */
        public static final String REPORT_PERIOD = "report_period";
        /**
         * 上报定量
         */
        public static final String REPORT_QUANTITY = "report_quantity";
        /**
         * 固定时间戳
         */
        public static final String REPORT_FIX_TIME = "report_fix_time";
        /**
         * 上次上报的时间
         */
        public static final String REPORT_LAST_TIME = "report_last_time";
        /**
         * 推送是否上报成功
         */
        public static final String PUSH_REPORT = "push_is_reported";

        /**
         * 上报是否已经完成
         */
        public static final String SET_REPORT = "set_report";

        /**
         * 别名是否设置成功
         */
        public static final String SET_ALIAS = "setalias";

        /**
         * 频道是否订阅成功
         */
        public static final String SET_SUBSCRIBE = "setsubscribe";

        /**
         * 失败
         */
        public static final String FAIL = "fail";

        /**
         * 成功
         */
        public static final String SUCCESS = "success";
    }

}
