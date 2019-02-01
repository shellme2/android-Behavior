package com.eebbk.bfc.sdk.behavior.aidl.crash.filter;

/**
 * @author hesn
 *         2018/3/8
 */

public interface ICrashFilter {

    ModuleCrashInfo filter(String crash);

}
