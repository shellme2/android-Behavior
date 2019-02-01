package com.eebbk.behavior.demo.test.function.parameter.interfaces;

import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.DataAttr;

/**
 * @author hesn
 * @function 自定义采集事件类型模型接口
 * @date 16-8-30
 * @company 步步高教育电子有限公司
 */

public interface IEventModle {

    int eventType();
    String eventName();

    String activityEtTip();
    String functionNameEtTip();
    String moduleDetailEtTip();
    String extendEtTip();
    String trigValueEtTip();

    String activityTvTip();
    String functionNameTvTip();
    String moduleDetailTvTip();
    String trigValueTvTip();
    String extendTvTip();

    boolean showTrigValue();

    void save(boolean isAidlBehavior, DataAttr dataAttr, String...values);
}
