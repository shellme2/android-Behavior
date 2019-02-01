package com.eebbk.behavior.demo.test.performance;

import android.content.Intent;
import android.view.View;

import com.eebbk.behavior.demo.common.ABaseActivity;
import com.eebbk.behavior.demo.R;
import com.eebbk.behavior.demo.test.performance.largechar.LargeCharActivity;
import com.eebbk.behavior.demo.test.performance.stress.StressTestActivity;
import com.eebbk.behavior.demo.test.performance.succratio.SuccessRatioTestActivity;
import com.eebbk.behavior.demo.test.performance.usetime.UserTimeTestActivity;
import com.eebbk.behavior.demo.utils.ConfigUtils;

/**
 * @author hesn
 * @function 性能测试菜单界面
 * @date 16-8-30
 * @company 步步高教育电子有限公司
 */

public class PerfTestMenuActivity extends ABaseActivity {

    @Override
    protected int setLayoutId() {
        return R.layout.activity_perf_test_menu_layout;
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
     * 压力测试
     * @param view
     */
    public void onStressTest(View view){
        startActivity(new Intent(this, StressTestActivity.class));
    }

    /**
     * 耗时测试
     * @param view
     */
    public void onUseTimeTest(View view){
        startActivity(new Intent(this, UserTimeTestActivity.class));
    }

    /**
     * 成功率测试
     * @param view
     */
    public void onSuccRatioTest(View view){
        startActivity(new Intent(this, SuccessRatioTestActivity.class));
    }

    /**
     * 超大字符测试
     * @param view
     */
    public void onLargeChar(View view){
        if(!ConfigUtils.isMonkeyTest){
            startActivity(new Intent(this, LargeCharActivity.class));
        }
    }

}
