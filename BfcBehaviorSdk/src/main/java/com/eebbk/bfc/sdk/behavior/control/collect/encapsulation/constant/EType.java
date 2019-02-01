package com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.constant;

/**
 * @author hesn
 * @function
 * @date 16-8-9
 * @company 步步高教育电子有限公司
 */

public class EType {
    public static final int TYPE_ACTIVITY_IN = 1;
    public static final int TYPE_ACTIVITY_OUT = 2;
    public static final int TYPE_APP_IN = 3;
    public static final int TYPE_APP_OUT = 4;
    public static final int TYPE_CLICK = 5;
    public static final int TYPE_SEARCH = 6;
    public static final int TYPE_CUSTOM = 7;
    public static final int TYPE_COUNT = 8;
    public static final int TYPE_EXCEPTION = 9;
    public static final int TYPE_FUNC_BEGIN = 10;
    public static final int TYPE_FUNC_END = 11;
    public static final int TYPE_DURATION = 12;
    public static final int TYPE_MONITOR_URL = 14;

    public static final String NAME_APP_LAUNCH = "APP调出前台事件";
    public static final String NAME_PAGE = "页面切出事件";
    public static final String NAME_MONITOR_URL = "URL监控事件";
    public static final String NAME_EXCEPTION = "App异常事件";
    public static final String NAME_CLICK = "计次事件";
    public static final String NAME_COUNT = "计数事件";
    public static final String NAME_CUSTOM = "自定义事件";
    public static final String NAME_SEARCH = "搜索事件";
    public static final String NAME_FUNC_BEGIN = "功能点开始事件";
    public static final String NAME_FUNC_END = "功能点结束事件";
    public static final String NAME_DURATION = "使用时长事件";
    public static final String FUNCTION_MONITOR_URL = "URL监控";
    public static final String NAME_SYS_EXCEPTION = "异常事件";
}
