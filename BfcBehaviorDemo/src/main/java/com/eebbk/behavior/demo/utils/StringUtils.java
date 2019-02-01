package com.eebbk.behavior.demo.utils;

import android.text.TextUtils;
import android.widget.EditText;

/**
 * @author hesn
 * @function
 * @date 16-10-10
 * @company 步步高教育电子有限公司
 */

public class StringUtils {

    /**
     * 获取 EditText 的数据
     * @param et
     * @return
     */
    public static String getStrByEt(EditText et){
        return et == null ? null : et.getText().toString().trim();
    }

    public static int str2Int(String str){
        return str2Int(str,0);
    }

    public static int str2Int(String str, int defaultValue){
        if(TextUtils.isEmpty(str)){
            return defaultValue;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static long str2Long(String str){
        return str2Long(str,0);
    }

    public static long str2Long(String str, int defaultValue){
        if(TextUtils.isEmpty(str)){
            return defaultValue;
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    private StringUtils(){

    }
}
