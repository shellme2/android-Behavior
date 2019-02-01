package com.eebbk.behavior.demo.test.function.parameter.modle.event;

import com.eebbk.behavior.demo.test.function.parameter.contants.InputDefaultModle;
import com.eebbk.behavior.demo.test.function.parameter.contants.TipDefaultModle;
import com.eebbk.behavior.demo.test.function.parameter.interfaces.IEventModle;
import com.eebbk.behavior.demo.utils.DADemoUtils;
import com.eebbk.bfc.sdk.behavior.BehaviorCollector;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.constant.EType;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.DataAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.CountEvent;

/**
 * @author hesn
 * @function
 * @date 16-8-31
 * @company 步步高教育电子有限公司
 */

public class CountEventModle implements IEventModle {

    @Override
    public int eventType() {
        return EType.TYPE_COUNT;
    }

    @Override
    public String eventName() {
        return EType.NAME_COUNT;
    }

    @Override
    public String activityEtTip() {
        return InputDefaultModle.CountEventModle.activity;
    }

    @Override
    public String functionNameEtTip() {
        return InputDefaultModle.CountEventModle.functionName;
    }

    @Override
    public String moduleDetailEtTip() {
        return InputDefaultModle.CountEventModle.moduleDetail;
    }

    @Override
    public String extendEtTip() {
        return InputDefaultModle.CountEventModle.extend;
    }

    @Override
    public String trigValueEtTip() {
        return InputDefaultModle.CountEventModle.trigValue;
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
        return TipDefaultModle.DEFAULT_TRIG_VALUE;
    }

    @Override
    public String extendTvTip() {
        return TipDefaultModle.DEFAULT_EXTEND;
    }

    @Override
    public boolean showTrigValue() {
        return true;
    }

    @Override
    public void save(boolean isAidlBehavior, DataAttr dataAttr, String... values) {
        if (isAidlBehavior) {
            DADemoUtils.countEventAidl(values[TipDefaultModle.INDEX_ACTIVITY]
                    , values[TipDefaultModle.INDEX_FUNCTION_NAME]
                    , values[TipDefaultModle.INDEX_MODULE_DETAIL]
                    , values[TipDefaultModle.INDEX_TRIG_VALUE]
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
            CountEvent countEvent = new CountEvent();
            countEvent.activity = values[TipDefaultModle.INDEX_ACTIVITY];
            countEvent.functionName = values[TipDefaultModle.INDEX_FUNCTION_NAME];
            countEvent.moduleDetail = values[TipDefaultModle.INDEX_MODULE_DETAIL];
            countEvent.trigValue = values[TipDefaultModle.INDEX_TRIG_VALUE];
            countEvent.extend = values[TipDefaultModle.INDEX_EXTEND];
            countEvent.dataAttr = dataAttr;
            BehaviorCollector.getInstance().countEvent(countEvent);
        }
    }
}
