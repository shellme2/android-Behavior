package com.eebbk.behavior.demo.test.function.parameter.modle.event;

import com.eebbk.behavior.demo.test.function.parameter.contants.InputDefaultModle;
import com.eebbk.behavior.demo.test.function.parameter.contants.TipDefaultModle;
import com.eebbk.behavior.demo.test.function.parameter.interfaces.IEventModle;
import com.eebbk.behavior.demo.utils.DADemoUtils;
import com.eebbk.bfc.sdk.behavior.BehaviorCollector;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.constant.EType;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.DataAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.SearchEvent;

/**
 * @author hesn
 * @function
 * @date 16-8-31
 * @company 步步高教育电子有限公司
 */

public class SearchEventModle implements IEventModle {

    @Override
    public int eventType() {
        return EType.TYPE_SEARCH;
    }

    @Override
    public String eventName() {
        return EType.NAME_SEARCH;
    }

    @Override
    public String activityEtTip() {
        return InputDefaultModle.SearchEventModle.activity;
    }

    @Override
    public String functionNameEtTip() {
        return InputDefaultModle.SearchEventModle.functionName;
    }

    @Override
    public String moduleDetailEtTip() {
        return InputDefaultModle.SearchEventModle.moduleDetail;
    }

    @Override
    public String extendEtTip() {
        return InputDefaultModle.SearchEventModle.resultCount;
    }

    @Override
    public String trigValueEtTip() {
        return InputDefaultModle.SearchEventModle.keyWrod;
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
        return "keyWrod";
    }

    @Override
    public String extendTvTip() {
        return "searchResult";
    }

    @Override
    public boolean showTrigValue() {
        return true;
    }

    @Override
    public void save(boolean isAidlBehavior, DataAttr dataAttr, String... values) {
        if (isAidlBehavior) {
            DADemoUtils.searchEventAidl(values[TipDefaultModle.INDEX_ACTIVITY]
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
            SearchEvent searchEvent = new SearchEvent();
            searchEvent.activity = values[TipDefaultModle.INDEX_ACTIVITY];
            searchEvent.functionName = values[TipDefaultModle.INDEX_FUNCTION_NAME];
            searchEvent.moduleDetail = values[TipDefaultModle.INDEX_MODULE_DETAIL];
            searchEvent.keyWrod = values[TipDefaultModle.INDEX_TRIG_VALUE];
            searchEvent.resultCount = values[TipDefaultModle.INDEX_EXTEND];
            searchEvent.dataAttr = dataAttr;
            BehaviorCollector.getInstance().searchEvent(searchEvent);
        }
    }
}
