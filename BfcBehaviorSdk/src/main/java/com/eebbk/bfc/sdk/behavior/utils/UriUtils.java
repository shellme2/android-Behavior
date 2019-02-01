package com.eebbk.bfc.sdk.behavior.utils;

import android.content.Context;
import android.net.Uri;

/**
 * @author hesn
 * @function
 * @date 16-12-8
 * @company 步步高教育电子有限公司
 */

public class UriUtils {

    public static Uri getContentUri(Context context){
        return Uri.parse("content://" + getAuthority(context) + "/userbehavior");
    }

    public static String getAuthority(Context context){
        return SystemUtils.getHostAppId(context) + ".provider";
    }
}
