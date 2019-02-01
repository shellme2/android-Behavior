package com.eebbk.behavior.demo.test.function.parameter.modle.event;

import com.eebbk.behavior.demo.test.function.parameter.contants.InputDefaultModle;
import com.eebbk.behavior.demo.test.function.parameter.contants.TipDefaultModle;
import com.eebbk.behavior.demo.test.function.parameter.interfaces.IEventModle;
import com.eebbk.behavior.demo.utils.DADemoUtils;
import com.eebbk.bfc.sdk.behavior.BehaviorCollector;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.constant.EType;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.DataAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.ClickEvent;

/**
 * @author hesn
 * @function
 * @date 16-8-30
 * @company 步步高教育电子有限公司
 */

public class CLickEventModle implements IEventModle {

    @Override
    public int eventType() {
        return EType.TYPE_CLICK;
    }

    @Override
    public String eventName() {
        return EType.NAME_CLICK;
    }

    @Override
    public String activityEtTip() {
        return InputDefaultModle.ClickEventModle.activity;
    }

    @Override
    public String functionNameEtTip() {
        return InputDefaultModle.ClickEventModle.functionName;
    }

    @Override
    public String moduleDetailEtTip() {
        return InputDefaultModle.ClickEventModle.moduleDetail;
    }

    @Override
    public String extendEtTip() {
        return InputDefaultModle.ClickEventModle.extend;
    }

    @Override
    public String trigValueEtTip() {
        return "";
    }

    @Override
    public String activityTvTip() {
        return TipDefaultModle.DEFAULT_ACTIVITY;
    }

    @Override
    public String functionNameTvTip() {
        return TipDefaultModle.DEFAULT_FUNCTION_NAME;
    }

    @Override
    public String moduleDetailTvTip() {
        return TipDefaultModle.DEFAULT_MODULE_DETAIL;
    }

    @Override
    public String trigValueTvTip() {
        return null;
    }

    @Override
    public String extendTvTip() {
        return TipDefaultModle.DEFAULT_EXTEND;
    }

    @Override
    public boolean showTrigValue() {
        return false;
    }

    @Override
    public void save(boolean isAidlBehavior, DataAttr dataAttr, String... values) {
        if (isAidlBehavior) {
            DADemoUtils.clickEventAidl(values[TipDefaultModle.INDEX_ACTIVITY]
                    , values[TipDefaultModle.INDEX_FUNCTION_NAME]
                    , values[TipDefaultModle.INDEX_MODULE_DETAIL]
                    , values[TipDefaultModle.INDEX_EXTEND]
                    , values[5]
                    , values[6]
                    , values[7]
                    , values[8]
                    , values[9]
                    , values[10]
                    , values[11]
                    , values[12]
            );
        } else {
            ClickEvent clickEvent = new ClickEvent();
            clickEvent.activity = values[TipDefaultModle.INDEX_ACTIVITY];
            clickEvent.functionName = values[TipDefaultModle.INDEX_FUNCTION_NAME];
            clickEvent.moduleDetail = values[TipDefaultModle.INDEX_MODULE_DETAIL];
            clickEvent.extend = values[TipDefaultModle.INDEX_EXTEND];
            clickEvent.dataAttr = dataAttr;
            BehaviorCollector.getInstance().clickEvent(clickEvent);
        }
    }
}
