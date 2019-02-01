package com.eebbk.behavior.demo.test.function.parameter;

import com.eebbk.behavior.demo.test.function.parameter.interfaces.IEventModle;
import com.eebbk.behavior.demo.test.function.parameter.modle.event.CLickEventModle;
import com.eebbk.behavior.demo.test.function.parameter.modle.event.CountEventModle;
import com.eebbk.behavior.demo.test.function.parameter.modle.event.CustomEventModle;
import com.eebbk.behavior.demo.test.function.parameter.modle.event.PageEventModle;
import com.eebbk.behavior.demo.test.function.parameter.modle.event.SearchEventModle;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.constant.EType;

/**
 * @author hesn
 * @function 采集自定义参数数模型工厂
 * @date 16-8-31
 * @company 步步高教育电子有限公司
 */

public class EventModleFactory {

    public static IEventModle createModle(int eventType){
        IEventModle eventModle = null;
        switch (eventType){
            case EType.TYPE_CLICK:
                eventModle = new CLickEventModle();
                break;
            case EType.TYPE_COUNT:
                eventModle = new CountEventModle();
                break;
            case EType.TYPE_CUSTOM:
                eventModle = new CustomEventModle();
                break;
            case EType.TYPE_SEARCH:
                eventModle = new SearchEventModle();
                break;
            case EType.TYPE_ACTIVITY_OUT:
                eventModle = new PageEventModle();
                break;
            default:
                break;
        }
        return eventModle;
    }

}
