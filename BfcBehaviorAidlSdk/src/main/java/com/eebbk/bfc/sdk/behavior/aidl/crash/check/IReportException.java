package com.eebbk.bfc.sdk.behavior.aidl.crash.check;

/**
 * @author hesn
 * @function
 * @date 17-6-30
 * @company 步步高教育电子有限公司
 */

public interface IReportException {

    /**
     * 上报异常信息
     * <p>
     * 因为aidl库和基础库上报的方式不一样,所以回调上去
     * </p>
     *
     * @param msg
     */
    void reportException(String msg, String version);
}
