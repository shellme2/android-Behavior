package com.eebbk.behavior.demo.test.performance.succratio;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.eebbk.behavior.demo.R;
import com.eebbk.behavior.demo.common.AEventSpinnerActivity;

import java.util.List;

/**
 * @author hesn
 * @function 测试上报成功率
 * @date 16-10-14
 * @company 步步高教育电子有限公司
 */

public class SuccessRatioTestActivity extends AEventSpinnerActivity implements SuccessRatioPresenter.OnSuccRatioListener {

    private EditText mTimesEt;
    private TextView mShowTv;
    private SuccessRatioPresenter mPresenter;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_test_success_ratio_layout;
    }

    @Override
    protected void initView() {
        mTimesEt = (EditText) findViewById(R.id.timesEt);
        mShowTv = (TextView) findViewById(R.id.showTv);
    }

    @Override
    protected void initData() {
        mPresenter = new SuccessRatioPresenter(this);
    }

    @Override
    protected boolean isShowLog() {
        return true;
    }

    @Override
    protected int setSpinnerId() {
        return R.id.spinner;
    }

    @Override
    protected List<String> setSpinnerList() {
        return null;
    }

    @Override
    protected void initSpinnerData() {
        mPresenter.selectedEvent(getEventTypeBySpinnerIndex(0));
    }

    @Override
    protected void onSelectedEventType(int position, int eventType) {
        mPresenter.selectedEvent(eventType);
    }

    @Override
    protected boolean isShowAidlMode() {
        return true;
    }

    /**
     * 开始测试
     *
     * @param view
     */
    public void onStart(View view) {
        setAidlModeCheckBoxEnable(false);
        mPresenter.start(mTimesEt.getText().toString().trim(), isCheckedAidlMode());
    }

    @Override
    public void onSuccRatioTestTip(String result) {
        mShowTv.setText(result);
    }

    @Override
    public void onEnd() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setAidlModeCheckBoxEnable(true);
            }
        });
    }
}
