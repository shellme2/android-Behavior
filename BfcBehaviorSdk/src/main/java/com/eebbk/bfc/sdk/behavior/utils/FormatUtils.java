package com.eebbk.bfc.sdk.behavior.utils;

import android.text.TextUtils;

import com.eebbk.bfc.sdk.behavior.report.common.constants.ConstData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author hesn
 * @function 格式化工具
 * @date 16-9-12
 * @company 步步高教育电子有限公司
 */

public class FormatUtils {

    /**
     * 格式化异常捕获日志
     * @param log
     * @return
     */
    public static String formatExceptionLog(String log){
        if(TextUtils.isEmpty(log)){
            return log;
        }
        return log.replace("\\n","\n").replace("\\t","\t");
    }

    /**
     * 触发时间
     * @return
     */
    public static String getDate(){
        return new SimpleDateFormat(ConstData.DATA_TRIGGER_DATE_FORMAT, Locale.getDefault())
                .format(new Date());
    }

    private FormatUtils(){
        //prevent the instance
    }

}
