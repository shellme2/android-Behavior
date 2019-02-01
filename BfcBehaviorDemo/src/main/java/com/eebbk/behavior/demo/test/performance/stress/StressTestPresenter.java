package com.eebbk.behavior.demo.test.performance.stress;

import com.eebbk.behavior.demo.common.EventFactory;
import com.eebbk.behavior.demo.utils.DADemoUtils;
import com.eebbk.behavior.demo.utils.ExecutorsUtils;
import com.eebbk.bfc.sdk.behavior.BehaviorCollector;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.constant.EType;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.interfaces.IEvent;

/**
 * @author hesn
 * @function
 * @date 16-8-24
 * @company 步步高教育电子有限公司
 */

public class StressTestPresenter {

    private boolean isStressTestRunning = false;
    private static final String FUNCTION_NAME = "压力测试";
    private static final String ACTIVITY_NAME = "StressTestActivity";
    private IEvent mEvent;
    private int eventType = EType.TYPE_CLICK;
    // 是否每次都创建新对象测试
    private boolean isNewEvent = true;

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
    public void startStressTest(final boolean isCheckedAidlMode){
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
                            mEvent = EventFactory.createEvent(eventType, ACTIVITY_NAME, FUNCTION_NAME);
                        }
                        BehaviorCollector.getInstance().event(mEvent);
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

}
