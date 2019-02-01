package com.eebbk.bfc.sdk.behavior.aidl.crash.check;

/**
 * @author hesn
 * @function
 * @date 17-6-19
 * @company 步步高教育电子有限公司
 */

public class AidlAnrCheckExceptionImpl extends AAnrCheckException {

    @Override
    public void reportException(String msg, String version) {
        AidlCheckExceptionAgent.reportException(mInnerListener, getExceptionType(), msg, version);
    }
}
