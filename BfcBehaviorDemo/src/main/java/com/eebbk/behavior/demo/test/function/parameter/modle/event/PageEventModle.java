package com.eebbk.behavior.demo.test.function.parameter.modle.event;

import com.eebbk.behavior.demo.test.function.parameter.contants.InputDefaultModle;
import com.eebbk.behavior.demo.test.function.parameter.contants.TipDefaultModle;
import com.eebbk.behavior.demo.test.function.parameter.interfaces.IEventModle;
import com.eebbk.behavior.demo.utils.DADemoUtils;
import com.eebbk.bfc.sdk.behavior.BehaviorCollector;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.constant.EType;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.DataAttr;

/**
 * @author hesn
 * @function
 * @date 16-10-14
 * @company 步步高教育电子有限公司
 */

public class PageEventModle implements IEventModle {
    @Override
    public int eventType() {
        return EType.TYPE_ACTIVITY_OUT;
    }

    @Override
    public String eventName() {
        return EType.NAME_PAGE;
    }

    @Override
    public String activityEtTip() {
        return InputDefaultModle.PageEventModle.activity;
    }

    @Override
    public String functionNameEtTip() {
        return InputDefaultModle.PageEventModle.functionName;
    }

    @Override
    public String moduleDetailEtTip() {
        return InputDefaultModle.PageEventModle.moduleDetail;
    }

    @Override
    public String extendEtTip() {
        return InputDefaultModle.PageEventModle.extend;
    }

    @Override
    public String trigValueEtTip() {
        return null;
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
        String activity = values[TipDefaultModle.INDEX_ACTIVITY];
        if (isAidlBehavior) {
            DADemoUtils.pageBeginAidl(activity);
            DADemoUtils.pageEndAidl(activity
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
            BehaviorCollector.getInstance().pageBegin(activity);
            BehaviorCollector.getInstance().pageEnd(
                    activity,
                    values[TipDefaultModle.INDEX_FUNCTION_NAME],
                    values[TipDefaultModle.INDEX_MODULE_DETAIL],
                    dataAttr,
                    values[TipDefaultModle.INDEX_EXTEND]);
        }
    }
}
