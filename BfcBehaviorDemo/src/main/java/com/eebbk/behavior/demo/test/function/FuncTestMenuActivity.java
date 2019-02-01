package com.eebbk.behavior.demo.test.function;

import android.content.Intent;
import android.view.View;

import com.eebbk.behavior.demo.common.ABaseActivity;
import com.eebbk.behavior.demo.R;
import com.eebbk.behavior.demo.test.function.crash.CrashActivity;
import com.eebbk.behavior.demo.test.function.errorcode.ErrorCodeActivity;
import com.eebbk.behavior.demo.test.function.query.QueryActivity;
import com.eebbk.behavior.demo.test.function.log.LogActivity;
import com.eebbk.behavior.demo.test.function.mode.ModeActivity;
import com.eebbk.behavior.demo.test.function.mswitch.ModuleSwitchActivity;
import com.eebbk.behavior.demo.test.function.page.PageTestActivity;
import com.eebbk.behavior.demo.test.function.parameter.ParameterActivity;
import com.eebbk.behavior.demo.test.function.query.QueryConfigActivity;

/**
 * @author hesn
 * @function 功能测试菜单界面
 * @date 16-10-13
 * @company 步步高教育电子有限公司
 */

public class FuncTestMenuActivity extends ABaseActivity{
    @Override
    protected int setLayoutId() {
        return R.layout.activity_test_func_menu_layout;
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
     * 采集自定义参数测试
     * @param view
     */
    public void onEvent(View view){
        startActivity(new Intent(this, ParameterActivity.class));
    }

    /**
     * 页面切换计时测试
     * @param view
     */
    public void onPage(View view){
        startActivity(new Intent(this, PageTestActivity.class));
    }

    /**
     * 上报和缓存模式功能测试
     * @param view
     */
    public void onMode(View view){
        startActivity(new Intent(this, ModeActivity.class));
    }

    /**
     * 模块开关测试
     * @param view
     */
    public void onModuleSwitch(View view){
        startActivity(new Intent(this, ModuleSwitchActivity.class));
    }

    /**
     * 日志查询
     * @param view
     */
    public void onLog(View view){
        startActivity(new Intent(this, LogActivity.class));
    }

    /**
     * 本地采集数据查询
     * @param view
     */
    public void onQuery(View view){
        startActivity(new Intent(this, QueryActivity.class));
    }

    public void onQueryConfig(View view){
        startActivity(new Intent(this, QueryConfigActivity.class));
    }

    /**
     * 错误码查询
     * @param view
     */
    public void onMenuErrorCode(View view){
        startActivity(new Intent(this, ErrorCodeActivity.class));
    }

    /**
     * 异常捕获测试
     * @param view
     */
    public void onMenuCrash(View view){
        startActivity(new Intent(this, CrashActivity.class));
    }

    /**
     * 打开日志采集界面
     * @param view
     */
    public void onLogView(View view){
        startActivity(new Intent("com.eebbk.bfc.app.bfcbehavior.ACTION_MTK_LOG_ACTIVITY"));
    }
}
