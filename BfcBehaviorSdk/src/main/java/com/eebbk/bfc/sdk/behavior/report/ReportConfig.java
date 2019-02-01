package com.eebbk.bfc.sdk.behavior.report;

import com.eebbk.bfc.sdk.behavior.report.analysis.interfaces.ISort;
import com.eebbk.bfc.sdk.behavior.report.common.constants.ReportMode;
import com.eebbk.bfc.sdk.behavior.report.upload.mode.config.QuantifyModeConfig;
import com.eebbk.bfc.uploadsdk.upload.net.NetworkType;

/**
 * @author hesn
 * @function 上报模块的配置信息
 * @date 16-9-22
 * @company 步步高教育电子有限公司
 */

public class ReportConfig {
    /**
     * 上报模块开关
     */
    public boolean usable = true;
    public static final int[] DEFAULT_REPORT_MODE_TYPE = new int[]{ReportMode.MODE_QUANTITY, ReportMode.MODE_LAUNCH};
    /**
     * 上报模式
     */
    public int[] reportModeType = DEFAULT_REPORT_MODE_TYPE;
    /**
     * 上报时的数据过滤策略即数据的上报优先级 若null则不过滤
     */
    public ISort sort;

    public ReportModeConfig reportModeConfig = new ReportModeConfig();

    /**
     * 数据上报网络类型
     */
    public int mNetworkTypes = NetworkType.NETWORK_WIFI;

    public static class ReportModeConfig {

        //定时上报设置
        public int fixedTimeHour;
        public int fixedTimeMinute;
        public int fixedTimeSeconds;

        //周期性上报设置
        public long periodSeconds = 60 * 5;

        //定量
        public int quantity = QuantifyModeConfig.DEFAULT_QUANTITY;
    }
}
