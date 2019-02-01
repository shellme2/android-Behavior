package com.eebbk.behavior.demo.test.performance.stress;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.eebbk.behavior.demo.R;
import com.eebbk.behavior.demo.common.AEventSpinnerActivity;

import java.util.List;

/**
 * @author hesn
 * @function 压力测试
 * @date 16-8-24
 * @company 步步高教育电子有限公司
 */

public class StressTestActivity extends AEventSpinnerActivity {

    private Button startBtn;
    private Button stopBtn;
    private CheckBox repeatDataCB;
    private StressTempTestPresenter mPresenter;

    @Override
    protected int setLayoutId() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        return R.layout.activity_test_stress_layout;
    }

    @Override
    protected void initView() {
        startBtn = findView(R.id.function1BtnId);
        stopBtn = findView(R.id.function2BtnId);
        repeatDataCB = findView(R.id.repeatData);
    }

    @Override
    protected void initData() {
        mPresenter = new StressTempTestPresenter();
        startBtn.setText(R.string.stress_test_start);
        stopBtn.setText(R.string.stress_test_stop);
        repeatDataCB.setOnCheckedChangeListener(checkedChangeListener);
        mPresenter.setNewEvent(!repeatDataCB.isChecked());
        setLimitRefresh(true);
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

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.function1BtnId:
                //开始压力测试
                setAidlModeCheckBoxEnable(false);
                mPresenter.startStressTest(getApplicationContext(), isCheckedAidlMode());
                break;
            case R.id.function2BtnId:
                //停止压力测试
                setAidlModeCheckBoxEnable(true);
                mPresenter.stopStressTest();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //停止压力测试
        mPresenter.stopStressTest();
    }

    private CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(mPresenter != null){
                mPresenter.setNewEvent(!isChecked);
            }
        }
    };
}
