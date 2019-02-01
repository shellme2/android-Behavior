package com.eebbk.bfc.sdk.behavior.aidl.crash.check;

/**
 * @author hesn
 * @function
 * @date 17-8-21
 * @company 步步高教育电子有限公司
 */

public interface CrashType {

    String TYPE_ALL = "All";

    String TYPE_ANR = "ANR";

    String TYPE_NATIVE = "NativeCrash";

    String TYPE_STRICT_MODE = "StrictMode";

    String DROP_BOX_TYPE_ANR_DATA = "data_app_anr";

    String DROP_BOX_TYPE_ANR_SYSTEM = "system_app_anr";

    String DROP_BOX_TYPE_NATIVE_DATA = "data_app_native_crash";

    String DROP_BOX_TYPE_NATIVE_SYSTEM = "system_app_native_crash";

    String DROP_BOX_TYPE_STRICT_MODE_DATA = "data_app_strictmode";

    String DROP_BOX_TYPE_STRICT_MODE_SYSTEM = "system_app_strictmode";
}
