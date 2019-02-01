package com.eebbk.bfc.sdk.behavior.aidl.crash.ui;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import com.eebbk.bfc.sdk.behavior.aidl.utils.AppUtils;


/**
 * @author hesn
 * @function
 * @date 17-3-2
 * @company 步步高教育电子有限公司
 */

public class ToastCrashReportUiImpl implements ICrashReportUI{

    @Override
    public void show(String errMsg, final Context context) {
        if(context == null){
            return;
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    Looper.prepare();
                    Toast.makeText(context, "很抱歉,\""
                                    + AppUtils.getAppName(context) + "\"程序出现异常,即将退出.",
                            Toast.LENGTH_SHORT).show();
                    Looper.loop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
