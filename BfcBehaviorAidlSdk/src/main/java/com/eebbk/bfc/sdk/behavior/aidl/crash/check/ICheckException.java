package com.eebbk.bfc.sdk.behavior.aidl.crash.check;

import android.content.Context;

import com.eebbk.bfc.sdk.behavior.aidl.listener.InnerListener;

/**
 * @author hesn
 * @function
 * @date 17-6-16
 * @company 步步高教育电子有限公司
 */

public interface ICheckException {

    /**
     * 检查是否有异常日志
     *
     * @param context
     * @return
     */
    boolean check(Context context);

    /**
     * 异常类型
     * <p>
     * 用于去重,类型区分,保持
     * </p>
     *
     * @return
     */
    String getExceptionType();

    /**
     * 不采集设置的这个时间戳之前的异常信息
     *
     * @param context
     * @param timeMillis
     */
    ICheckException ignoreBefore(Context context, long timeMillis);

    /**
     * aidl用到的回调监听
     *
     * @param innerListener
     */
    ICheckException setListener(InnerListener innerListener);
}
