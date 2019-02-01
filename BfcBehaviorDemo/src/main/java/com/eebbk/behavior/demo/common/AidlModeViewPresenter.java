package com.eebbk.behavior.demo.common;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ScrollView;

/**
 * @author hesn
 * @function 显示 aidl埋点模式
 * @date 16-10-24
 * @company 步步高教育电子有限公司
 */

public class AidlModeViewPresenter {

    private static final int PADDING = 10;
    private CheckBox checkBox;

    public AidlModeViewPresenter(Activity activity){
        init(activity);
    }

    public boolean isChecked(){
        return checkBox == null ? false : checkBox.isChecked();
    }

    public void setEnabled(boolean enabled){
        checkBox.setEnabled(enabled);
    }

    private void init(Activity activity){
        checkBox = new CheckBox(activity);
        checkBox.setText("使用aidl埋点");
        checkBox.setPadding(PADDING, PADDING, 0, 0);
        View view = getRootView(activity);
        ViewGroup viewGroup;
        if(view instanceof ScrollView){
            //父容器是 ScrollView 则添加到其子类view中
            ScrollView rootView = (ScrollView) getRootView(activity);
            viewGroup = (ViewGroup) rootView.getChildAt(0);
        }else {
            viewGroup = (ViewGroup) view;
        }
        viewGroup.addView(checkBox,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private View getRootView(Activity context){
        return ((ViewGroup)context.findViewById(android.R.id.content)).getChildAt(0);
    }
}
