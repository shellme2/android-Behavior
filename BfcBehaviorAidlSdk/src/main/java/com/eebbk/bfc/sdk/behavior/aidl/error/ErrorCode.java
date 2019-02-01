package com.eebbk.bfc.sdk.behavior.aidl.error;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hesn
 * @function
 * @date 17-4-10
 * @company 步步高教育电子有限公司
 */

public class ErrorCode extends ErrorCodeStandard {

    private static final String LINE_SEPARATOR = "\n";
    private static Map<String, String> mErrCodes;

    /**
     * 绑定行为采集aidl失败
     * <br> 模块：aidl采集层
     * <br> 异常类型：初始化异常
     */
    public static final String BIND_SERVICE_ERROR = PROJECT_BEHAVIOR
            + MODULE_AIDL + INIT_EXCEPTION + "01";

    /**
     * 没有绑定行为采集aidl,或app没有引入行为采集库
     * <br> 模块：aidl采集层
     * <br> 异常类型：初始化异常
     */
    public static final String NO_BIND_SERVICE = PROJECT_BEHAVIOR
            + MODULE_AIDL + INIT_EXCEPTION + "02";

    /**
     * 注册监听home键失败
     * <br> 模块：aidl采集层
     * <br> 异常类型：初始化异常
     */
    public static final String REPORT_HOME_KEY_REG = PROJECT_BEHAVIOR
            + MODULE_AIDL + INIT_EXCEPTION + "03";
    /**
     * 注销home键监听失败
     * <br> 模块：aidl采集层
     * <br> 异常类型：初始化异常
     */
    public static final String REPORT_HOME_KEY_UNREG = PROJECT_BEHAVIOR
            + MODULE_AIDL + INIT_EXCEPTION + "04";

    /**
     * 异常捕获模块初始化失败
     * <br> 模块：aidl采集层
     * <br> 异常类型：初始化异常
     */
    public static final String CONTROL_INIT_CRASH = PROJECT_BEHAVIOR
            + MODULE_AIDL + INIT_EXCEPTION + "05";

    /**
     * 配置信息设置失败，Settings不能为空
     * <br> 模块：aidl采集层
     * <br> 异常类型：空指针
     */
    public static final String CONTROL_NULL_POINTER_BEHAVIOR_CONFIG = PROJECT_BEHAVIOR
            + MODULE_AIDL + NULL_POINTER_EXCEPTION + "01";

    /**
     * 打印所有错误码信息
     *
     * @return key:code  value:description
     */
    public synchronized static Map<String, String> getErrorCodes() {
        if (mErrCodes != null) {
            return new HashMap<String, String>(mErrCodes);
        }
        mErrCodes = new HashMap<String, String>();

        //02050101
        mErrCodes.put(BIND_SERVICE_ERROR, "绑定行为采集aidl失败");
        //02050102
        mErrCodes.put(NO_BIND_SERVICE, "没有绑定行为采集aidl,或app没有引入行为采集库");
        //02050103
        mErrCodes.put(REPORT_HOME_KEY_REG, "注册监听home键失败");
        //02050104
        mErrCodes.put(REPORT_HOME_KEY_UNREG, "注销home键监听失败");
        //02050105
        mErrCodes.put(CONTROL_INIT_CRASH, "异常捕获模块初始化失败");
        //02050601
        mErrCodes.put(CONTROL_NULL_POINTER_BEHAVIOR_CONFIG, "配置信息设置失败，Settings不能为空");

        return new HashMap<String, String>(mErrCodes);
    }

    private ErrorCode() {
        //prevent the instance
    }
}
