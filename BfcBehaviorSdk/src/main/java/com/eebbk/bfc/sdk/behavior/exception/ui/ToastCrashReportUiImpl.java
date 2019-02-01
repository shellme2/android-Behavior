package com.eebbk.bfc.sdk.behavior.exception.ui;

import android.os.Looper;
import android.widget.Toast;

import com.eebbk.bfc.common.app.AppUtils;
import com.eebbk.bfc.common.tools.ThreadUtils;
import com.eebbk.bfc.sdk.behavior.utils.ContextUtils;

/**
 * @author hesn
 * @function
 * @date 17-3-2
 * @company 步步高教育电子有限公司
 */

public class ToastCrashReportUiImpl implements ICrashReportUI{

    @Override
    public void show(String errMsg) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Looper.prepare();
                    Toast.makeText(ContextUtils.getContext(), "很抱歉,\""
                                    + AppUtils.getAppName(ContextUtils.getContext()) + "\"程序出现异常,即将退出.",
                            Toast.LENGTH_SHORT).show();
                    Looper.loop();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Looper.myLooper().quit();
                }
            }
        }.start();
    }
}
