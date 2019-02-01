package com.eebbk.bfc.sdk.behavior.exception.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.WindowManager;

import com.eebbk.bfc.sdk.behavior.utils.ContextUtils;

/**
 * @author hesn
 * @function
 * @date 17-3-2
 * @company 步步高教育电子有限公司
 */

public class DialogCrashReportUiImpl implements ICrashReportUI{

    @Override
    public void show(String errMsg) {
        //要加权限
        //<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
        //6.0需要确认,不好用
        AlertDialog.Builder builder = new AlertDialog.Builder(ContextUtils.getContext().getApplicationContext());
        builder.setMessage("你想恢复下载 ?").setCancelable(false).setPositiveButton("删除", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        }).setNeutralButton("恢复", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.setCancelable(false);
        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alert.show();
    }
}
