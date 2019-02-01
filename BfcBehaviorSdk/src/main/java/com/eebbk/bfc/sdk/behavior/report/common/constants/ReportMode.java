package com.eebbk.bfc.sdk.behavior.report.common.constants;

/**
 * @author hesn
 * @function
 * @date 16-9-12
 * @company 步步高教育电子有限公司
 */

public class ReportMode {
    /**
     * 固定某个时间点上报
     */
    public static final int MODE_FIXTIME = 0;
    /**
     * 周期性的上报
     */
    public static final int MODE_PERIOD = 1;
//    /**
//     * 接收消息推送上报
//     */
//    public static final int MODE_PUSH = 2;
    /**
     * 达到某个量值上报
     */
    public static final int MODE_QUANTITY = 2;
    /**
     * 实时上报
     */
    public static final int MODE_REAL_TIME = 3;
    /**
     * 退出上报
     */
    public static final int MODE_EXIT = 4;
    /**
     * 启动上报
     */
    public static final int MODE_LAUNCH = 5;

    private ReportMode(){
        //prevent the instance
    }
}
