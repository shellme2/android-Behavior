package com.eebbk.behavior.demo.common;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.eebbk.bfc.sdk.behavior.utils.ListUtils;

import java.util.List;

/**
 * @author hesn
 * @function 有选择采集事件的下拉选择框界面基类
 * @date 16-10-13
 * @company 步步高教育电子有限公司
 */

public abstract class AEventSpinnerActivity extends ABaseActivity{

    private Spinner mSpinner;
    private ArrayAdapter<String> mAdapter;
    private EventSpinnerPresenter mEventSpinnerPresenter;

    /**
     * 下拉框控件id
     * @return
     */
    protected abstract int setSpinnerId();

    /**
     * 设置下拉框数据
     * <br> 如果为空,则赋予 EventSpinnerPresenter 里的数据
     * @return
     */
    protected abstract List<String> setSpinnerList();

    /**
     * 初始化下拉框控件相关数据
     * <br> 在此下拉框才初始化完成,避免空指针
     * @return
     */
    protected abstract void initSpinnerData();

    /**
     * 选中的采集事件
     * @param position
     * @param eventType 选中的采集事件类型
     */
    protected abstract void onSelectedEventType(int position, int eventType);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initEventSpinner();
        initSpinnerData();
    }

    /**
     * 根据下拉列表的下标获取事件类型
     * @param index
     * @return
     */
    protected int getEventTypeBySpinnerIndex(int index){
        return mEventSpinnerPresenter.getEventTypeBySpinnerIndex(index);
    }

    private void initEventSpinner(){
        mEventSpinnerPresenter = new EventSpinnerPresenter();
        mSpinner = findView(setSpinnerId());
        List<String> data = setSpinnerList();
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item
                , ListUtils.isEmpty(data) ?
                mEventSpinnerPresenter.getSpinnerList() : data);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mAdapter);
        mSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onSelectedEventType(position, mEventSpinnerPresenter.getEventTypeBySpinnerIndex(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
