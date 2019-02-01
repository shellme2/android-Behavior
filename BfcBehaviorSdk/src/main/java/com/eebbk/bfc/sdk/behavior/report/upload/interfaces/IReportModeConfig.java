package com.eebbk.bfc.sdk.behavior.report.upload.interfaces;

/**
 * @author hesn
 * @function 上报模式配置信息
 * @date 16-9-26
 * @company 步步高教育电子有限公司
 */

public interface IReportModeConfig {
    /**
     * 上报模式类型
     * @return
     */
    int modeType();
    /**
     * 配置参数
     * @return
     */
    Object[] getConfig();
}
