package com.eebbk.bfc.sdk.behavior.report.upload.mode.config;

import com.eebbk.bfc.sdk.behavior.report.common.constants.ReportMode;
import com.eebbk.bfc.sdk.behavior.report.upload.interfaces.IReportModeConfig;

/**
 * @author hesn
 * @function 即时上报模式
 * @date 16-9-26
 * @company 步步高教育电子有限公司
 */

public class RealTimeModeConfig implements IReportModeConfig {

    @Override
    public int modeType() {
        return ReportMode.MODE_REAL_TIME;
    }

    @Override
    public Object[] getConfig() {
        return new Object[0];
    }
}
