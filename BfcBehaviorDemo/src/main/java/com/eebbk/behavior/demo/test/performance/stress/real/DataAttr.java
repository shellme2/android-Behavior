package com.eebbk.behavior.demo.test.performance.stress.real;

import android.content.Context;

import com.eebbk.bfc.sdk.behavior.utils.FormatUtils;

/**
 * 作者： liming
 * 日期： 2018/12/21.
 * 公司： 步步高教育电子有限公司
 * 描述：
 */
public class DataAttr {
    private static final String EVENT_TYPE = "5";

    private String activityName;
    private String moduleDetail;
    private String functionName;
    /**
     * 触发时间
     */
    private String trigTime;
    /**
     * 事件类型
     */
    private String eventType;
    private String extend;

    private DataAttr() {
    }

    public DataAttr(String eventType,
                    String activityName,
                    String moduleDetail,
                    String functionName,
                    String trigTime,
                    String extend) {
        this.activityName = activityName;
        this.moduleDetail = moduleDetail;
        this.functionName = functionName;
        this.trigTime = trigTime;
        this.eventType = eventType;
        this.extend = extend;
    }

    @Override
    public String toString() {
        return "DataAttr{" +
                "activityName='" + activityName + '\'' +
                ", moduleDetail='" + moduleDetail + '\'' +
                ", functionName='" + functionName + '\'' +
                ", trigTime='" + trigTime + '\'' +
                ", eventType='" + eventType + '\'' +
                ", extend=" + extend +
                '}';
    }
}
