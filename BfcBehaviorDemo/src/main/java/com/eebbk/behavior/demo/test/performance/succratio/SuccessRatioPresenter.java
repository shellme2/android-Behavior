package com.eebbk.behavior.demo.test.performance.succratio;

import android.text.TextUtils;

import com.eebbk.behavior.demo.common.EventFactory;
import com.eebbk.behavior.demo.utils.DADemoUtils;
import com.eebbk.behavior.demo.utils.ExecutorsUtils;
import com.eebbk.behavior.demo.utils.StringUtils;
import com.eebbk.bfc.sdk.behavior.BehaviorCollector;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.constant.EType;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.interfaces.IEvent;

/**
 * @author hesn
 * @function 测试上报成功率
 * @date 16-10-14
 * @company 步步高教育电子有限公司
 */

class SuccessRatioPresenter {

    private static final String FUNCTION_NAME = "成功率测试";
    private static final String ACTIVITY_NAME = SuccessRatioTestActivity.class.getName();
    private IEvent mEvent;
    private OnSuccRatioListener mListener;
    private boolean isRunning = false;
    private int eventType = EType.TYPE_CLICK;

    SuccessRatioPresenter(OnSuccRatioListener l){
        mListener = l;
    }

    /**
     * 选中要测试的事件类型
     * @param eventType
     */
    public void selectedEvent(int eventType){
        this.eventType = eventType;
        mEvent = EventFactory.createEvent(eventType, ACTIVITY_NAME, FUNCTION_NAME);
    }

    /**
     * 开始测试
     * @param timesStr 测试次数
     */
    public void start(String timesStr,final boolean isCheckedAidlMode) {
        if(mListener == null){
            return;
        }
        if(isRunning){
            mListener.onSuccRatioTestTip("正在运行,请稍后...");
            return;
        }
        if(TextUtils.isEmpty(timesStr)){
            mListener.onSuccRatioTestTip("请输入正确的测试次数");
            mListener.onEnd();
            return;
        }
        final long times = StringUtils.str2Long(timesStr);
        if(times <= 0){
            mListener.onSuccRatioTestTip("请输入正确的测试次数");
            mListener.onEnd();
            return;
        }
        if(times > 100){
            mListener.onSuccRatioTestTip("输入次数不宜超过100,否则会对测试准确性有影响.");
            mListener.onEnd();
            return;
        }
        isRunning = true;
        mListener.onSuccRatioTestTip("");

        ExecutorsUtils.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < times; i++) {
                    if(isCheckedAidlMode){
                        DADemoUtils.eventAidl(eventType, ACTIVITY_NAME, FUNCTION_NAME
                                , null, null, null, null, null, null, null, null, null, null, null);
                    }else {
                        BehaviorCollector.getInstance().event(mEvent);
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(isCheckedAidlMode){
                    DADemoUtils.realTime2UploadAidl();
                }else {
                    BehaviorCollector.getInstance().realTime2Upload();
                }
                isRunning = false;
                mListener.onEnd();
            }
        });
    }

    public interface OnSuccRatioListener{
        /**
         * 提示
         * @param result
         */
        void onSuccRatioTestTip(String result);

        /**
         * 结束
         */
        void onEnd();
    }
}
