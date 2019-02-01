package com.eebbk.behavior.demo.common;

import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.constant.EType;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.EventAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.ClickEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.CountEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.CustomEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.PageEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.SearchEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.interfaces.IEvent;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hesn
 * @function 快速创建采集事件工厂
 * @date 16-10-9
 * @company 步步高教育电子有限公司
 */

public class EventFactory {

    private static final AtomicInteger ID = new AtomicInteger();

    /**
     * 生产采集事件
     * @param eventType
     * @param activity
     * @param funcName
     * @return
     */
    public static IEvent createEvent(int eventType, String activity, String funcName){
        switch (eventType){
            case EType.TYPE_CLICK:
                return clickEvent(activity, funcName);
            case EType.TYPE_COUNT:
                return countEvent(activity, funcName);
            case EType.TYPE_CUSTOM:
                return customEvent(activity, funcName);
            case EType.TYPE_SEARCH:
                return searchEvent(activity, funcName);
            case EType.TYPE_ACTIVITY_OUT:
                return pageEvent(activity, funcName);
            default:
                return null;
        }
    }

    public static EventAttr eventAttr(int type, String typeName, String funcName){
        return new EventAttr()
                .setEventType(type)
                .setEventName(typeName)
                .setFunctionName(getWithId(funcName));
    }

    public static ClickEvent clickEvent(String activity, String funcName){
//        ClickEvent clickEvent = new ClickEvent();
//        clickEvent.activity = activity;
//        clickEvent.functionName = getWithId(funcName);
//        return clickEvent;
        return new ClickEvent().setActivity(activity).setFunctionName(getWithId(funcName));
    }

    private static CountEvent countEvent(String activity, String funcName){
//        CountEvent countEvent = new CountEvent();
//        countEvent.activity = activity;
//        countEvent.functionName = getWithId(funcName);
//        return countEvent;
        return new CountEvent().setActivity(activity).setFunctionName(getWithId(funcName));
    }

    private static CustomEvent customEvent(String activity, String funcName){
//        CustomEvent customEvent = new CustomEvent();
//        customEvent.activity = activity;
//        customEvent.functionName = getWithId(funcName);
//        return customEvent;
        return new CustomEvent().setActivity(activity).setFunctionName(getWithId(funcName));
    }

    private static SearchEvent searchEvent(String activity, String funcName){
//        SearchEvent searchEvent = new SearchEvent();
//        searchEvent.activity = activity;
//        searchEvent.functionName = getWithId(funcName);
//        return searchEvent;
        return new SearchEvent().setActivity(activity).setFunctionName(getWithId(funcName));
    }

    private static PageEvent pageEvent(String activity, String funcName){
//        PageEvent pageEvent = new PageEvent();
//        pageEvent.activityName = activity;
//        pageEvent.functionName = getWithId(funcName);
//        return pageEvent;
        return new PageEvent().setActivity(activity).setFunctionName(getWithId(funcName));
    }

    private static String getWithId(String content){
        return content + "_" + ID.getAndIncrement();
    }

    private EventFactory(){

    }
}
