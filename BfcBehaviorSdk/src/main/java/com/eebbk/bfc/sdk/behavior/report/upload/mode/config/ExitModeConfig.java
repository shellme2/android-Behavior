package com.eebbk.bfc.sdk.behavior.report.upload.mode.config;

import com.eebbk.bfc.sdk.behavior.report.common.constants.ReportMode;
import com.eebbk.bfc.sdk.behavior.report.upload.interfaces.IReportModeConfig;

/**
 * @author hesn
 * @function
 * @date 16-10-13
 * @company 步步高教育电子有限公司
 */

public class ExitModeConfig implements IReportModeConfig{
    @Override
    public int modeType() {
        return ReportMode.MODE_EXIT;
    }

    @Override
    public Object[] getConfig() {
        return new Object[0];
    }
}
