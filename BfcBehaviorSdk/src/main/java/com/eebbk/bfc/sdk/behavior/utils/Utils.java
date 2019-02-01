package com.eebbk.bfc.sdk.behavior.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.eebbk.bfc.common.app.AppUtils;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

import java.io.File;

/**
 * @author hesn
 * @function
 * @date 16-11-7
 * @company 步步高教育电子有限公司
 */

public class Utils {

    private static final String TAG = "Utils";

    private Utils() {
    }

    public static String getModuleName(Context context) {
        return AppUtils.getAppName(context);
    }

    public static String getAppId(Context context) throws Exception {
        return (new MD5Coder(16)).encode(context.getPackageName().getBytes("UTF-8"));
    }

    public static String getVersionName(Context context) {
        return AppUtils.getVersionName(context);
    }

    /**
     * 通知媒体库更新
     */
    public static void scanFile(Context context, String filePath) {
        if(context == null){
            LogUtils.w(TAG,"media scanner scan file fail, context == null");
            return;
        }
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(Uri.fromFile(new File(filePath)));
        context.sendBroadcast(scanIntent);
    }
}
