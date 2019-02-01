package com.eebbk.bfc.sdk.behavior.report.upload.mode.config;

import com.eebbk.bfc.sdk.behavior.report.ReportConfig;
import com.eebbk.bfc.sdk.behavior.report.common.constants.ReportMode;
import com.eebbk.bfc.sdk.behavior.report.upload.interfaces.IReportModeConfig;

/**
 * @author hesn
 * @function 上报模式配置信息工厂
 * @date 16-9-26
 * @company 步步高教育电子有限公司
 */

public class ReportModeConfigFactory {

    public static IReportModeConfig[] create(int...types){
        if(types == null || types.length == 0){
            types = ReportConfig.DEFAULT_REPORT_MODE_TYPE;
        }
        IReportModeConfig[] configs = new IReportModeConfig[types.length];
        for (int i = 0; i < types.length; i++) {
            final int type = types[i];
            switch (type){
                case ReportMode.MODE_FIXTIME:
                    //定时上传模式配置
                    configs[i] = new FixedTimeModeConfig();
                    break;
                case ReportMode.MODE_QUANTITY:
                    //定量上传模式配置
                    configs[i] = new QuantifyModeConfig();
                    break;
//            case ReportMode.MODE_PUSH:
//                //推送上报模式
//                return new PushModeConfig();
                case ReportMode.MODE_REAL_TIME:
                    //即时上报模式
                    configs[i] = new RealTimeModeConfig();
                    break;
                case ReportMode.MODE_PERIOD:
                    //周期性上报模式配置
                    configs[i] =  new PeriodicityModeConfig();
                    break;
                case ReportMode.MODE_LAUNCH:
                    //app启动上报模式配置
                    configs[i] =  new LaunchModeConfig();
                    break;
                case ReportMode.MODE_EXIT:
                    //app退出上报模式配置
                    configs[i] =  new ExitModeConfig();
                    break;
                default:
                    configs[i] =  new LaunchModeConfig();
                    break;
            }
        }
        return configs;
    }
}
