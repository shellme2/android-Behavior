package com.eebbk.bfc.sdk.behavior.report.upload.mode.config;

import com.eebbk.bfc.sdk.behavior.report.common.constants.ReportMode;
import com.eebbk.bfc.sdk.behavior.report.upload.interfaces.IReportModeConfig;

/**
 * @author hesn
 * @function 定量上传模式配置
 * @date 16-9-26
 * @company 步步高教育电子有限公司
 */

public class QuantifyModeConfig implements IReportModeConfig {
    public static final int DEFAULT_QUANTITY = 10;
    public static final int MAXVALUE = 300;
    public static final int MINVALUE = 5;
    private long quantity = DEFAULT_QUANTITY;

    @Override
    public int modeType() {
        return ReportMode.MODE_QUANTITY;
    }

    @Override
    public Object[] getConfig() {
        return new Object[]{quantity};
    }

    /**
     * 设置容量
     * @param quantity
     * @return
     */
    public QuantifyModeConfig setQuantity(int quantity){
        if(quantity < MINVALUE){
            this.quantity = MINVALUE;
        }else if(quantity > MAXVALUE){
            this.quantity = MAXVALUE;
        }else{
            this.quantity =  quantity;
        }
        return this;
    }
}
