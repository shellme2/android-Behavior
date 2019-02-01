package com.eebbk.behavior.demo.test.performance.usetime;

import com.eebbk.behavior.demo.MyApplication;
import com.eebbk.behavior.demo.common.EventFactory;
import com.eebbk.bfc.sdk.behavior.BehaviorCollector;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.constant.EType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hesn
 * @function 需要执行耗时测试的函数列表
 * @date 16-9-29
 * @company 步步高教育电子有限公司
 */

public class RunningList {

    /**
     * 要耗时测试运行的信息
     */
    private List<UseTimeTestInfo> list = new ArrayList<UseTimeTestInfo>();

    private static final String FUNCTION_NAME = "耗时测试";
    private static final String ACTIVITY_NAME = UserTimeTestActivity.class.getName();

    public List<UseTimeTestInfo> getList() {
        return list;
    }

    public RunningList(){
        createList();
    }

    private void createList(){
        list.add(createInfo(new Runnable() {
            @Override
            public void run() {
                BehaviorCollector.getInstance().appLaunch();
            }
        }, "appLaunch()"));

        list.add(createInfo(new Runnable() {
            @Override
            public void run() {
                BehaviorCollector.getInstance().appLaunch(System.currentTimeMillis()+"", null);
            }
        }, "appLaunch(String tValue, String curActivityName)"));

        list.add(createInfo(new Runnable() {
            @Override
            public void run() {
                BehaviorCollector.getInstance().clickEvent(FUNCTION_NAME, MyApplication.getContext());
            }
        }, "clickEvent(String functionName, Context context)"));

        list.add(createInfo(new Runnable() {
            @Override
            public void run() {
                BehaviorCollector.getInstance().clickEvent(FUNCTION_NAME, "");
            }
        }, "clickEvent(String functionName, String curActivityName)"));

        list.add(createInfo(new Runnable() {
            @Override
            public void run() {
                BehaviorCollector.getInstance().clickEvent(FUNCTION_NAME, null, null);
            }
        }, "clickEvent(String functionName, String curActivityName, String extend)"));

        list.add(createInfo(new Runnable() {
            @Override
            public void run() {
                BehaviorCollector.getInstance().clickEvent(FUNCTION_NAME, null, null, null);
            }
        }, "clickEvent(String functionName, String curActivityName, String extend, String moduleDetail)"));

        list.add(createInfo(new Runnable() {
            @Override
            public void run() {
                BehaviorCollector.getInstance().clickEvent(FUNCTION_NAME, null, null, null, null);
            }
        }, "clickEvent(String functionName, String curActivityName, String extend, String moduleDetail, DataAttr dataAttr)"));

        list.add(createInfo(new Runnable() {
            @Override
            public void run() {
                BehaviorCollector.getInstance().clickEvent(
                        EventFactory.eventAttr(EType.TYPE_CLICK, EType.NAME_CLICK, FUNCTION_NAME), null);
            }
        }, "clickEvent(EventAttr eventAttr, DataAttr dataAttr)"));

        list.add(createInfo(new Runnable() {
            @Override
            public void run() {
                BehaviorCollector.getInstance().clickEvent(
                        EventFactory.eventAttr(EType.TYPE_CLICK, EType.NAME_CLICK, FUNCTION_NAME), null, null);
            }
        }, "clickEvent(EventAttr eventAttr, DataAttr dataAttr, OtherAttr otherAttr)"));

        list.add(createInfo(new Runnable() {
            @Override
            public void run() {
                BehaviorCollector.getInstance().clickEvent(EventFactory.clickEvent(ACTIVITY_NAME, FUNCTION_NAME));
            }
        }, "clickEvent(ClickEvent clickEvent)"));

        list.add(createInfo(new Runnable() {
            @Override
            public void run() {
                BehaviorCollector.getInstance().customEvent(null, FUNCTION_NAME, null);
            }
        }, "customEvent(String eventName, String functionName, String curActivityName)"));

        list.add(createInfo(new Runnable() {
            @Override
            public void run() {
                BehaviorCollector.getInstance().customEvent(null, FUNCTION_NAME, null, null);
            }
        }, "customEvent(String eventName, String functionName, String curActivityName, String extend)"));

        list.add(createInfo(new Runnable() {
            @Override
            public void run() {
                BehaviorCollector.getInstance().customEvent(null, FUNCTION_NAME, null, null, null);
            }
        }, "customEvent(String eventName, String functionName, String curActivityName, String extend, String moduleDetail)"));

        list.add(createInfo(new Runnable() {
            @Override
            public void run() {
                BehaviorCollector.getInstance().customEvent(null, FUNCTION_NAME, null, null, null, null);
            }
        }, "customEvent(String eventName, String functionName, String curActivityName, String extend, String moduleDetail, DataAttr dataAttr)"));

        list.add(createInfo(new Runnable() {
            @Override
            public void run() {
                BehaviorCollector.getInstance().customEvent(
                        EventFactory.eventAttr(EType.TYPE_CUSTOM, EType.NAME_CUSTOM, FUNCTION_NAME), null);
            }
        }, "customEvent(EventAttr eventAttr, DataAttr dataAttr)"));

        list.add(createInfo(new Runnable() {
            @Override
            public void run() {
                BehaviorCollector.getInstance().customEvent(
                        EventFactory.eventAttr(EType.TYPE_CUSTOM, EType.NAME_CUSTOM, FUNCTION_NAME), null, null);
            }
        }, "customEvent(EventAttr eventAttr, DataAttr dataAttr, OtherAttr otherAttr)"));

    }

    private UseTimeTestInfo createInfo(Runnable runnable, String methodName){
        return new UseTimeTestInfo()
                .setMethod(runnable)
                .setMethodName(methodName);
    }

}
