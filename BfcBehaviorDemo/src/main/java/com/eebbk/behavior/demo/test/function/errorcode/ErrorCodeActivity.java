package com.eebbk.behavior.demo.test.function.errorcode;

import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.eebbk.behavior.demo.common.ABaseActivity;
import com.eebbk.behavior.demo.R;

/**
 * @author hesn
 * @function 错误码查询
 * @date 16-9-30
 * @company 步步高教育电子有限公司
 */

public class ErrorCodeActivity extends ABaseActivity {

    private EditText mErrorCodeEt;
    private ListView mShowLv;
    private ErrorCodeAdapter mAdapter;
    private ErrorCodePresenter mPresenter;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_error_code_layout;
    }

    @Override
    public void initView() {
        mErrorCodeEt = (EditText) findViewById(R.id.errorCodeEt);
        mShowLv = (ListView) findViewById(R.id.showLv);
    }

    @Override
    protected void initData() {
        mPresenter = new ErrorCodePresenter();
        mAdapter = new ErrorCodeAdapter(this.getApplicationContext());
        mAdapter.setList(mPresenter.getData(""));
        mShowLv.setAdapter(mAdapter);
    }

    @Override
    protected boolean isShowLog() {
        return false;
    }

    /**
     * 查询
     * @param view
     */
    public void onSearch(View view){
        mAdapter.setList(mPresenter.getData(mErrorCodeEt.getText().toString().trim()));
        mAdapter.notifyDataSetChanged();
    }

}
