package com.eebbk.bfc.sdk.behavior.utils;

import android.app.Application;

import com.eebbk.bfc.sdk.behavior.error.ErrorCode;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

/**
 * @author hesn
 * @function
 * @date 16-8-18
 * @company 步步高教育电子有限公司
 */

public class ContextUtils {

    private static Application mContext = null;
    private static final String TAG = "ContextUtils";

    /**
     * 获取上下文
     * @return
     */
    public static Application getContext(){
        return mContext;
    }

    /**
     * 设置上下文
     * @return
     */
    public static void setContext(Application app){
        if(app == null){
            LogUtils.bfcExceptionLog(TAG, ErrorCode.CONTROL_NULL_POINTER_APPLICATION);
            return;
        }
        mContext = app;
    }

    /**
     * 判断上下文是否为空
     * @return
     */
    public static boolean isEmpty(){
        return getContext() == null;
    }

    /**
     * 清空上下文
     */
    public static void clean(){
        mContext = null;
    }

    private ContextUtils(){
        //prevent the instance
    }
}
