package com.eebbk.behavior.demo.test.performance.usetime;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.eebbk.behavior.demo.R;
import com.eebbk.behavior.demo.common.AEventSpinnerActivity;

import java.util.List;

/**
 * @author hesn
 * @function 耗时测试
 * @date 16-9-29
 * @company 步步高教育电子有限公司
 */

public class UserTimeTestActivity extends AEventSpinnerActivity implements UseTimePresenter.OnUseTimeListener{

    private EditText mTimesEt;
    private TextView mShowTv;
    private UseTimePresenter mUseTimePresenter;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_test_usetime_layout;
    }

    @Override
    public void initView() {
        mTimesEt = (EditText) findViewById(R.id.timesEt);
        mShowTv = (TextView) findViewById(R.id.showTv);
    }

    @Override
    protected void initData() {
        mShowTv.setMovementMethod(new ScrollingMovementMethod());
        mUseTimePresenter = new UseTimePresenter(this);
    }

    @Override
    protected boolean isShowLog() {
        return false;
    }

    @Override
    protected int setSpinnerId() {
        return R.id.spinner;
    }

    @Override
    protected List<String> setSpinnerList() {
        return mUseTimePresenter.getSpinnerList();
    }

    @Override
    protected void initSpinnerData() {
        mUseTimePresenter.selected(0);
    }

    @Override
    protected void onSelectedEventType(int position, int eventType) {
        mUseTimePresenter.selected(position);
    }

    /**
     * 开始测试
     * @param view
     */
    public void onStart(View view) {
        mUseTimePresenter.start(mTimesEt.getText().toString().trim());
    }

    /**
     * 一键测试
     * @param view
     */
    public void onOneKeyTest(View view){
        mUseTimePresenter.startOnekeyTest(mTimesEt.getText().toString().trim());
    }

    /**
     * 耗时测试反馈结果
     * @param result
     */
    @Override
    public void onUseTimeTestResult(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mShowTv.setText(result);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUseTimePresenter.stop();
    }
}
