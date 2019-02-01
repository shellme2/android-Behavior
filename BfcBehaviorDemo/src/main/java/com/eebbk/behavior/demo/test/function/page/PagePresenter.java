package com.eebbk.behavior.demo.test.function.page;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author hesn
 * @function 页面切换计时测试
 * @date 16-10-13
 * @company 步步高教育电子有限公司
 */

class PagePresenter {

    private SimpleDateFormat sdf;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private OnCurrentTimeLitener mOnCurrentTimeLitener;

    public PagePresenter(OnCurrentTimeLitener l){
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        mOnCurrentTimeLitener = l;
        initTimer();
    }

    /**
     * 进入时间
     * @return
     */
    public String enterTime(){
        return getData();
    }

    public void destroy(){
        if(mTimer != null){
            mTimer.cancel();
        }
    }

    private void initTimer(){
        if(mOnCurrentTimeLitener == null){
            return;
        }
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                mOnCurrentTimeLitener.onCurrentTime(getData());
            }
        };
        mTimer.schedule(mTimerTask, 0, 1000);
    }

    private String getData(){
        return sdf.format(new Date());
    }

    interface OnCurrentTimeLitener{
        /**
         * 当前时间
         * @param time
         */
        void onCurrentTime(String time);
    }
}
