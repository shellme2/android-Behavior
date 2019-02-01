package com.eebbk.behavior.demo.test;

import android.content.Intent;
import android.view.View;

import com.eebbk.behavior.demo.R;
import com.eebbk.behavior.demo.common.ABaseActivity;
import com.eebbk.behavior.demo.test.function.FuncTestMenuActivity;
import com.eebbk.behavior.demo.test.performance.PerfTestMenuActivity;

/**
 * @author hesn
 * @function 测试功能菜单界面
 * @date 16-8-30
 * @company 步步高教育电子有限公司
 */

public class TestMenuActivity extends ABaseActivity {

    @Override
    protected int setLayoutId() {
        return R.layout.activity_test_menu_layout;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean isShowLog() {
        return false;
    }

    /**
     * 功能测试
     * @param view
     */
    public void onFuncTest(View view){
        startActivity(new Intent(this, FuncTestMenuActivity.class));
    }

    /**
     * 性能测试
     * @param view
     */
    public void onPerfTest(View view){
        startActivity(new Intent(this, PerfTestMenuActivity.class));
    }

}
