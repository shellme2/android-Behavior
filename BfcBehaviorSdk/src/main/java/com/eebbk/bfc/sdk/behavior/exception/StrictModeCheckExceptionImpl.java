package com.eebbk.bfc.sdk.behavior.exception;

import com.eebbk.bfc.sdk.behavior.aidl.crash.check.AStrictModeCheckException;

/**
 * @author hesn
 * @function 严苛模式异常采集
 * @date 17-6-19
 * @company 步步高教育电子有限公司
 */

public class StrictModeCheckExceptionImpl extends AStrictModeCheckException {

    @Override
    public void reportException(String msg, String version) {
        CheckExceptionAgent.reportException(getExceptionType(), msg, version);
    }
}
