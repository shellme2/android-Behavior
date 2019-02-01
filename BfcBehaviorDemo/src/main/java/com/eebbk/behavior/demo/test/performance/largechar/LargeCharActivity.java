package com.eebbk.behavior.demo.test.performance.largechar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.eebbk.behavior.demo.common.ABaseActivity;
import com.eebbk.behavior.demo.R;
import com.eebbk.behavior.demo.utils.StringUtils;

/**
 * @author hesn
 * @function 超大字符测试
 * @date 16-10-10
 * @company 步步高教育电子有限公司
 */

public class LargeCharActivity extends ABaseActivity implements LargeCharPresenter.OnLargeCharListener{

    private EditText mStrEv;
    private EditText mTimesEv;
    private TextView mStrLengthTv;
    private TextView mTipTv;
    private LargeCharPresenter mPresenter;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_large_char_layout;
    }

    @Override
    protected void initView() {
        mStrEv = (EditText) findViewById(R.id.strEv);
        mTimesEv = (EditText) findViewById(R.id.timesEv);
        mStrLengthTv = (TextView) findViewById(R.id.strLengthTv);
        mTipTv = (TextView) findViewById(R.id.tipTv);
    }

    @Override
    protected void initData() {
        mPresenter = new LargeCharPresenter(this);
        mStrEv.addTextChangedListener(mWatcher);
    }

    @Override
    protected boolean isShowLog() {
        return false;
    }

    /**
     * 循环复制字符
     * @param view
     */
    public void onLoopCopy(View view){
        mPresenter.loopCopy(StringUtils.getStrByEt(mStrEv), StringUtils.getStrByEt(mTimesEv));
    }

    /**
     * 把输入框内容复制到粘帖板
     * @param view
     */
    public void onCopy2Clip(View view){
        mPresenter.copy2Clip(mStrEv.getText().toString());
    }


    @Override
    public void onTip(final String tip) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTipTv.setText(tip);
            }
        });
    }

    @Override
    public void copyContent(final String content) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mStrEv.setText(content);
            }
        });
    }

    private TextWatcher mWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mStrLengthTv.setText(mPresenter.getWordCount(s.toString()));
        }
    };
}
