package com.eebbk.behavior.demo.test.function.page;

import android.widget.TextView;

import com.eebbk.behavior.demo.common.ABaseActivity;
import com.eebbk.behavior.demo.R;
import com.eebbk.behavior.demo.utils.DADemoUtils;
import com.eebbk.bfc.sdk.behavior.BehaviorCollector;

/**
 * @author hesn
 * @function 页面切换计时测试
 * @date 16-10-13
 * @company 步步高教育电子有限公司
 */

public class PageTestActivity extends ABaseActivity implements PagePresenter.OnCurrentTimeLitener {

    private TextView mEnterTimeTv;
    private TextView mCurrentTimeTv;
    private PagePresenter mPresenter;
    private static final String TAG = "PageTestActivity";

    @Override
    protected int setLayoutId() {
        return R.layout.activity_test_page_layout;
    }

    @Override
    protected void initView() {
        mEnterTimeTv = (TextView) findViewById(R.id.enterTimeTv);
        mCurrentTimeTv = (TextView) findViewById(R.id.currentTimeTv);
    }

    @Override
    protected void initData() {
        mPresenter = new PagePresenter(this);
    }

    @Override
    protected boolean isShowLog() {
        return true;
    }

    @Override
    protected boolean isShowAidlMode() {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isCheckedAidlMode()) {
            DADemoUtils.pageBeginAidl(TAG);
        } else {
            BehaviorCollector.getInstance().pageBegin(TAG);
        }
        mEnterTimeTv.setText(mPresenter.enterTime());
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isCheckedAidlMode()) {
            DADemoUtils.pageEndAidl(TAG, "页面切换计时测试", null, null);
        } else {
            BehaviorCollector.getInstance().pageEnd(TAG, "页面切换计时测试", null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void onCurrentTime(final String time) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mCurrentTimeTv.setText(time);
            }
        });
    }
}
