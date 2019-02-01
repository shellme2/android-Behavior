package com.eebbk.behavior.demo.common;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author hesn
 * @function 测试基activity类
 * @date 16-8-24
 * @company 步步高教育电子有限公司
 */

public abstract class ABaseActivity extends Activity{

    private LogPresenter mLogPresenter;
    private AidlModeViewPresenter mAidlModeViewPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isShowLog()){
            mLogPresenter.startRefreshLog();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isShowLog()){
            mLogPresenter.stopRefreshLog();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isShowLog()){
            mLogPresenter.destroy();
        }
    }

    /**
     * 设置布局文件　layout id
     * @return
     */
    protected abstract int setLayoutId();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 是否显示打印日志
     */
    protected abstract boolean isShowLog();

    /**
     * 是否显示Aidl埋点模式
     */
    protected boolean isShowAidlMode(){
        return false;
    }

    /**
     * 是否使用Aidl埋点模式
     * @return
     */
    protected boolean isCheckedAidlMode(){
        return mAidlModeViewPresenter == null ? false : mAidlModeViewPresenter.isChecked();
    }

    /**
     * Aidl埋点模式 CheckBox 是否可用
     * @param enable
     */
    protected void setAidlModeCheckBoxEnable(boolean enable){
        if(mAidlModeViewPresenter == null){
            return;
        }
        mAidlModeViewPresenter.setEnabled(enable);
    }

    /**
     * 根据id找控件
     * @param id
     * @param <T>
     * @return
     */
    protected <T> T findView(int id){
        return (T)findViewById(id);
    }

    /**
     * 限制频繁打印
     * @param limitRefresh
     */
    protected void setLimitRefresh(boolean limitRefresh) {
        if(isShowLog()){
            mLogPresenter.setLimitRefresh(limitRefresh);
        }
    }

    private void init(){
        setContentView(setLayoutId());
        initView();
        if(isShowAidlMode()){
            mAidlModeViewPresenter = new AidlModeViewPresenter(this);
        }
        if(isShowLog()){
            mLogPresenter = new LogPresenter(this);
        }
        initData();
    }

}
