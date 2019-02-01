package com.eebbk.behavior.demo.test.performance.stress;

import android.content.Context;
import android.text.format.DateUtils;

import com.eebbk.behavior.demo.common.EventFactory;
import com.eebbk.behavior.demo.test.performance.stress.real.DataAttr;
import com.eebbk.behavior.demo.test.performance.stress.real.RealReport;
import com.eebbk.behavior.demo.utils.DADemoUtils;
import com.eebbk.behavior.demo.utils.ExecutorsUtils;
import com.eebbk.bfc.sdk.behavior.BehaviorCollector;
import com.eebbk.bfc.sdk.behavior.aidl.utils.SharedPreferenceUtils;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.constant.EType;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.ClickEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.CountEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.CustomEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.PageEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.interfaces.IEvent;
import com.eebbk.bfc.sdk.behavior.utils.FormatUtils;
import com.eebbk.bfc.sdk.behavior.utils.MapUtils;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hesn
 *  functionname改成固定的"完整性测试"，moduledetail里不用填了，改成在extend里加三个字段，
 * 第一个是dayTick（天累计次数，每天清0，每次重启也清0），
 * 第二个是tick（全局累计次数，重启之后才清0，
 * 第三个是ts（当前时间戳）
 * @date 16-8-24
 * @company 步步高教育电子有限公司
 */

public class StressTempTestPresenter {

    private boolean isStressTestRunning = false;
    private static final String FUNCTION_NAME = "完整性测试";
    private static final String FUNCTION_NAME_REAL = "实时完整性测试";
    private static final String ACTIVITY_NAME = "StressTestActivity";
    private IEvent mEvent;
    private int eventType = EType.TYPE_CLICK;
    // 是否每次都创建新对象测试
    private boolean isNewEvent = true;
    private static final AtomicInteger DAY_TICK = new AtomicInteger();
    private static final AtomicInteger TICK = new AtomicInteger();
    private static final AtomicInteger DAY_TICK_REAL = new AtomicInteger();
    private static final AtomicInteger TICK_REAL = new AtomicInteger();

    /**
     * 选中要测试的事件类型
     * @param eventType
     */
    public void selectedEvent(int eventType){
        this.eventType = eventType;
        mEvent = EventFactory.createEvent(eventType, ACTIVITY_NAME, FUNCTION_NAME);
    }

    public void setNewEvent(boolean newEvent) {
        isNewEvent = newEvent;
    }

    /**
     * 开始压力测试
     */
    public void startStressTest(final Context context, final boolean isCheckedAidlMode){
        if(isStressTestRunning){
            return;
        }
        isStressTestRunning = true;
        ExecutorsUtils.execute(new Runnable() {
            @Override
            public void run() {
                while (isStressTestRunning){
                    if(isCheckedAidlMode){
                        DADemoUtils.eventAidl(eventType, ACTIVITY_NAME, FUNCTION_NAME
                                , null, null, null, null, null, null, null, null, null, null, null);
                    }else {
                        if(isNewEvent){
                            mEvent = createEvent(context, eventType, ACTIVITY_NAME, FUNCTION_NAME);
                        }
                        BehaviorCollector.getInstance().event(mEvent);
                        realReport(context);
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 停止压力测试
     */
    public void stopStressTest(){
        isStressTestRunning = false;
    }

    public static IEvent createEvent(Context context, int eventType, String activity, String funcName){
        switch (eventType){
            case EType.TYPE_CLICK:
                return clickEvent(context, activity, funcName);
            case EType.TYPE_COUNT:
                return countEvent(context, activity, funcName);
            case EType.TYPE_CUSTOM:
                return customEvent(context, activity, funcName);
            case EType.TYPE_SEARCH:
                return clickEvent(context, activity, funcName);
            case EType.TYPE_ACTIVITY_OUT:
                return pageEvent(context, activity, funcName);
            default:
                return null;
        }
    }

    private static ClickEvent clickEvent(Context context, String activity, String funcName){
        return new ClickEvent().setActivity(activity).setFunctionName(funcName).setExtend(getExtend(context));
    }

    private static CountEvent countEvent(Context context, String activity, String funcName){
        return new CountEvent().setActivity(activity).setFunctionName(funcName).setExtend(getExtend(context));
    }

    private static CustomEvent customEvent(Context context, String activity, String funcName){
        return new CustomEvent().setActivity(activity).setFunctionName(funcName).setExtend(getExtend(context));
    }

    private static PageEvent pageEvent(Context context, String activity, String funcName){
        return new PageEvent().setActivity(activity).setFunctionName(funcName).setExtend(getExtend(context));
    }

    private static Map<String, String> getExtend(Context context){
        checkTick(context);
        Map<String, String> map = new HashMap<>();
        map.put("dayTick", String.valueOf(DAY_TICK.getAndIncrement()));
        map.put("tick", String.valueOf(TICK.getAndIncrement()));
        map.put("ts", String.valueOf(System.currentTimeMillis()));
        return map;
    }

    private synchronized static void checkTick(Context context){
        if(DAY_TICK.get() % 120 == 0){
            long lastDayTick = SharedPreferenceUtils.getInstance(context).get("dayTick", -1L);
            if(lastDayTick == -1 || !DateUtils.isToday(lastDayTick)){
                SharedPreferenceUtils.getInstance(context).put("dayTick", System.currentTimeMillis());
                DAY_TICK.set(0);
                LogUtils.i("StressTempTestPresenter", "reset dayTick 0");
            }
        }
    }

    private static void realReport(Context context){
        RealReport.getInstance().report(context, new DataAttr(
                "5",
                ACTIVITY_NAME,
                null,
                FUNCTION_NAME_REAL,
                FormatUtils.getDate(),
                MapUtils.map2Json(getRealExtend(context))));
    }

    private static Map<String, String> getRealExtend(Context context){
        checkRealTick(context);
        Map<String, String> map = new HashMap<>();
        map.put("dayTick", String.valueOf(DAY_TICK_REAL.getAndIncrement()));
        map.put("tick", String.valueOf(TICK_REAL.getAndIncrement()));
        map.put("ts", String.valueOf(System.currentTimeMillis()));
        return map;
    }

    private synchronized static void checkRealTick(Context context){
        if(DAY_TICK_REAL.get() % 120 == 0){
            long lastDayTick = SharedPreferenceUtils.getInstance(context).get("realDayTick", -1L);
            if(lastDayTick == -1 || !DateUtils.isToday(lastDayTick)){
                SharedPreferenceUtils.getInstance(context).put("realDayTick", System.currentTimeMillis());
                DAY_TICK_REAL.set(0);
                LogUtils.i("StressTempTestPresenter", "reset real dayTick 0");
            }
        }
    }
}
