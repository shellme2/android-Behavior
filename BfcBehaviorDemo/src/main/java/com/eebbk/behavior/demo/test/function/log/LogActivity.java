package com.eebbk.behavior.demo.test.function.log;

import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.eebbk.behavior.demo.R;
import com.eebbk.behavior.demo.common.ABaseActivity;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

/**
 * @author hesn
 * @function
 * @date 16-10-11
 * @company 步步高教育电子有限公司
 */

public class LogActivity extends ABaseActivity {

    private TextView mLogTv;
    private static final int CHANGE_SIZE = 2;
    private static final int LOG_UNIT = TypedValue.COMPLEX_UNIT_SP;
    private int logCurrentSize = 18;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_log_layout;
    }

    @Override
    protected void initView() {
        mLogTv = (TextView) findViewById(R.id.logTv);
    }

    @Override
    protected void initData() {
        mLogTv.setMovementMethod(new ScrollingMovementMethod());
        mLogTv.setTextSize(LOG_UNIT, logCurrentSize);
    }

    @Override
    protected boolean isShowLog() {
        return false;
    }

    /**
     * 放大
     * @param view
     */
    public void onMagnify(View view){
        changeTextSize(CHANGE_SIZE);
    }

    /**
     * 缩小
     * @param view
     */
    public void onLessen(View view){
        changeTextSize(-CHANGE_SIZE);
    }

    /**
     * 查询
     * @param view
     */
    public void onSearch(View view){
        mLogTv.setText(LogUtils.readDebugModeLog());
    }

    /**
     * 查询SD卡所有日志
     * @param view
     */
    public void onSearchAll(View view){
//        mLogTv.setText("loading...");
//        ExecutorsUtils.execute(new Runnable() {
//            @Override
//            public void run() {
//                showAllLog(LogUtils.readDebugModeAllLog());
//            }
//        });
    }

    private void showAllLog(final String log){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLogTv.setText(log);
            }
        });
    }

    /**
     * 修改字体大小
     * @param changeSize
     */
    private void changeTextSize(int changeSize){
        logCurrentSize += changeSize;
        mLogTv.setTextSize(LOG_UNIT, logCurrentSize);
    }

}
