package com.eebbk.behavior.demo.test.function.crash;

import android.os.Environment;
import android.view.View;

import com.eebbk.behavior.demo.R;
import com.eebbk.behavior.demo.common.ABaseActivity;
import com.eebbk.behavior.demo.utils.ConfigUtils;
import com.eebbk.behavior.demo.utils.JNIUtils;
import com.eebbk.bfc.common.app.AppUtils;
import com.eebbk.bfc.common.file.FileUtils;
import com.eebbk.bfc.sdk.behavior.utils.DeviceUtil;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;
import com.eebbk.bfc.uploadsdk.upload.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author hesn
 * @function 异常捕获测试
 * @date 16-8-30
 * @company 步步高教育电子有限公司
 */

public class CrashActivity extends ABaseActivity {

    @Override
    protected int setLayoutId() {
        return R.layout.activity_crash_layout;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean isShowLog() {
        return true;
    }

    @Override
    protected boolean isShowAidlMode() {
        return false;
    }

    /**
     * 触发异常捕获
     *
     * @param view
     */
    public void onCrashEvent(View view) {
        if(!ConfigUtils.isMonkeyTest){
            String nullStr = null;
            nullStr.equals("");
        }
    }

    /**
     * 触发ANR
     * @param view
     */
    public void onAnrEvent(View view){
        if(!ConfigUtils.isMonkeyTest) {
            int sleepTime = 15000;
            LogUtils.w("anr", "开始睡" + sleepTime + "毫秒!");
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LogUtils.w("anr", "sleep end");
        }
    }

    /**
     * 触发严苛模式
     * @param view
     */
    public void onStrictModeEvent(View view){
        if(!ConfigUtils.isMonkeyTest) {
            File newxmlfile = new File(Environment.getExternalStorageDirectory(), "test.txt");
            try {
                FileUtils.createFileOrExists(newxmlfile);
                FileWriter fw = new FileWriter(newxmlfile);
                fw.write("Strict mode test");
                //fw.close(); 触发严苛模式
            } catch (IOException e) {
                e.printStackTrace();
            }
            finish();
        }
    }

    /**
     * 触发native异常
     *
     * @param view
     */
    public void onNativeEvent(View view) {
        if(!ConfigUtils.isMonkeyTest){
            LogUtils.i("native", new JNIUtils().getString());
        }
    }

    /**
     * 触发行为采集库异常
     */
    public void onBfcBehaviorEvent(View view){
        if(!ConfigUtils.isMonkeyTest){
            DeviceUtil.getAppKeyFromMetaData(null, null);
        }
    }

    public void onBfcCommonEvent(View view){
        if(!ConfigUtils.isMonkeyTest){
            AppUtils.isAppInstalled(null, null);
        }
    }

    public void onBfcUploadEvent(View view){
        if(!ConfigUtils.isMonkeyTest){
            Utils.getMIMEType(null);
        }
    }
}
