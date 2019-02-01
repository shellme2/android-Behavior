package com.eebbk.behavior.demo.test.function.mode;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.eebbk.behavior.demo.common.ABaseActivity;
import com.eebbk.behavior.demo.R;
import com.eebbk.behavior.demo.test.function.parameter.contants.InputDefaultModle;
import com.eebbk.behavior.demo.utils.ConfigUtils;
import com.eebbk.behavior.demo.utils.ExecutorsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hesn
 * @function 上报和缓存模式功能测试
 * @date 16-10-12
 * @company 步步高教育电子有限公司
 */

public class ModeActivity extends ABaseActivity {
    private EditText mQuantifyEt;
    private EditText mFixedTimeHourEt;
    private EditText mFixedTimeMinuteEt;
    private EditText mFixedTimeSecondEt;
    private EditText mPeriodicityEt;
    private CheckBox mCacheTimeCb;
    private EditText mCacheTimeEt;
    private CheckBox mCacheQuantifyCb;
    private EditText mCacheQuantifyEt;
//    private RadioGroup mRadioGroup;
    private int[] mCheckIds;

    private ModePresenter mPresenter;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_mode_layout;
    }

    @Override
    protected void initView() {
        mQuantifyEt = (EditText) findViewById(R.id.quantifyEt);
        mFixedTimeHourEt = (EditText) findViewById(R.id.fixedTimeHourEt);
        mFixedTimeMinuteEt = (EditText) findViewById(R.id.fixedTimeMinuteEt);
        mFixedTimeSecondEt = (EditText) findViewById(R.id.fixedTimeSecondEt);
        mPeriodicityEt = (EditText) findViewById(R.id.periodicityEt);
        mCacheTimeCb = (CheckBox) findViewById(R.id.cacheTimeCb);
        mCacheTimeEt = (EditText) findViewById(R.id.cacheTimeEt);
        mCacheQuantifyCb = (CheckBox) findViewById(R.id.cacheQuantifyCb);
        mCacheQuantifyEt = (EditText) findViewById(R.id.cacheQuantifyEt);
//        mRadioGroup = findView(R.id.reportGroup);
    }

    @Override
    protected void initData() {
        initPresenter();
        List<Integer> ids = mPresenter.cheakedRadioBtnId();
        for (int id : ids) {
            ((CheckBox) findViewById(id)).setChecked(true);
        }
        mCacheQuantifyCb.setChecked(mPresenter.isCheakedCacheCapacity());
        mCacheTimeCb.setChecked(mPresenter.isCheakedCacheTime());
        mQuantifyEt.setText(mPresenter.quantify());
        mFixedTimeHourEt.setText(mPresenter.fixedTimeHour());
        mFixedTimeMinuteEt.setText(mPresenter.fixedTimeMinute());
        mFixedTimeSecondEt.setText(mPresenter.fixedTimeSecond());
        mPeriodicityEt.setText(mPresenter.periodicity());
        mCacheTimeEt.setText(InputDefaultModle.CachePolicy.TimePolicy.time);
        mCacheQuantifyEt.setText(InputDefaultModle.CachePolicy.CapacityPolicy.capacity);
    }

    @Override
    protected boolean isShowLog() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveReportMode();
        saveCache();
        ExecutorsUtils.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ConfigUtils.saveConfig();
            }
        });
    }

    private void initPresenter(){
        mCheckIds = new int[]{
                R.id.quantifyCb,
                R.id.fixedTimeCb,
                R.id.periodicityCb,
                R.id.exitCb,
                R.id.launchCb
        };
        mPresenter = new ModePresenter();
        mPresenter.setReportRadioBtnIds(mCheckIds);
    }

    /**
     * 保存上报模式
     */
    private void saveReportMode(){
        List<Integer> ids = new ArrayList<>();
        for (int id : mCheckIds) {
            if(((CheckBox) findViewById(id)).isChecked()){
                ids.add(id);
            }
        }
        mPresenter.saveReport(ids
                , getTextFromEt(mQuantifyEt)
                , getTextFromEt(mFixedTimeHourEt)
                , getTextFromEt(mFixedTimeMinuteEt)
                , getTextFromEt(mFixedTimeSecondEt)
                , getTextFromEt(mPeriodicityEt)
        );
    }

    /**
     * 保存缓存模式
     */
    private void saveCache(){
        mPresenter.saveCache(mCacheTimeCb.isChecked()
                , mCacheQuantifyCb.isChecked()
                , getTextFromEt(mCacheTimeEt)
                , getTextFromEt(mCacheQuantifyEt)
        );
    }

    private String getTextFromEt(EditText et){
        return et.getText().toString().trim();
    }

}
