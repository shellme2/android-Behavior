package com.eebbk.behavior.demo.common;

import android.app.Activity;
import android.graphics.Color;
import android.os.SystemClock;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.eebbk.bfc.common.tools.ThreadUtils;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;
import com.eebbk.bfc.sdk.behavior.utils.log.OnLogListener;

/**
 * @author hesn
 * @function 显示打印log
 * @date 16-10-24
 * @company 步步高教育电子有限公司
 */

public class LogPresenter implements OnLogListener{

    private TextView mLogTv;
    private String mLog;
    private static final int PADDING = 5;
    private long lastRefreshTime = -1;
    private boolean isLimitRefresh = false;

    @Override
    public void onLogRefresh(String log, int type) {
        if(isLimitRefresh){
            long time = SystemClock.elapsedRealtime();
            if (time - lastRefreshTime < 50) {
                return;
            }
            lastRefreshTime = time;
        }
        if(mLogTv != null && type == OnLogListener.LOG_TYPE_ALL){
            mLog = log;
            ThreadUtils.runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    mLogTv.setText(mLog);
                }
            });
        }
    }

    public LogPresenter(Activity activity){
        initTv(activity);
    }

    /**
     * 开始刷新监听日志
     */
    public void startRefreshLog(){
        LogUtils.setListener(this);
    }

    /**
     * 停止刷新监听日志
     */
    public void stopRefreshLog(){
        LogUtils.cancelListener();
    }

    private void initTv(Activity activity){
        mLogTv = new TextView(activity);
        mLogTv.setBackgroundColor(Color.BLACK);
        mLogTv.setTextColor(Color.WHITE);
        mLogTv.setPadding(PADDING, PADDING, PADDING, PADDING);
        View view = getRootView(activity);
        ViewGroup viewGroup;
        if(view instanceof ScrollView){
            //父容器是 ScrollView 则添加到其子类view中
            ScrollView rootView = (ScrollView) getRootView(activity);
            viewGroup = (ViewGroup) rootView.getChildAt(0);
        }else {
            //添加滚动效果
            mLogTv.setMovementMethod(new ScrollingMovementMethod());
            viewGroup = (ViewGroup) view;
        }
        viewGroup.addView(mLogTv,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void destroy(){
        mLogTv = null;
    }

    private View getRootView(Activity context){
        return ((ViewGroup)context.findViewById(android.R.id.content)).getChildAt(0);
    }

    public void setLimitRefresh(boolean limitRefresh) {
        isLimitRefresh = limitRefresh;
    }
}
