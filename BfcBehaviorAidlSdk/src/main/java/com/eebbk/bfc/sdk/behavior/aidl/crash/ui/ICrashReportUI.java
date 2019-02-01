package com.eebbk.bfc.sdk.behavior.aidl.crash.ui;

import android.content.Context;

/**
 * @author hesn
 * @function 异常信息捕获提示界面接口
 * @date 17-3-2
 * @company 步步高教育电子有限公司
 */

public interface ICrashReportUI {

    /**
     * 显示异常信息
     *
     * @param errMsg 异常信息
     */
    void show(String errMsg, Context context);
}
