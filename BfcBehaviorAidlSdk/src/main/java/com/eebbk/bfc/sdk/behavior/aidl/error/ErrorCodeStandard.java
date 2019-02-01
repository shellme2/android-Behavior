package com.eebbk.bfc.sdk.behavior.aidl.error;

/**
 * @author hesn
 * @function 错误码规范
 * @date 16-9-30
 * @company 步步高教育电子有限公司
 */
class ErrorCodeStandard {
    /**
     * 项目编号
     * <p> 根据《中间件错误码项目标号列表》中决定 </p>
     */
    static final String PROJECT_BEHAVIOR = "02";

    /**
     * 控制层
     */
    static final String MODULE_CONTROL = "01";
    /**
     * 缓冲层
     */
    static final String MODULE_CACHE = "02";
    /**
     * 数据层
     */
    static final String MODULE_DB = "03";
    /**
     * 上报层
     */
    static final String MODULE_REPORT = "04";
    /**
     * AIDL采集层
     */
    static final String MODULE_AIDL = "05";

    //01XX：初始化异常
    //02XX：没有按规范操作
    //03XX：参数异常
    //04XX：网络异常
    //05XX：数据处理异常
    //06XX：空指针
    //ffXX：其他
    /**
     * 01XX：初始化异常
     */
    static final String INIT_EXCEPTION = "01";
    /**
     * 02XX：规范操作
     */
    static final String OPERATION_EXCEPTION = "02";
    /**
     * 03XX：参数异常
     */
    static final String ILLEGAL_ARGUMENT_EXCEPTION = "03";
    /**
     * 04XX：网络异常
     */
    static final String NET_EXCEPTION = "04";
    /**
     * 05XX：数据处理异常
     */
    protected static final String DATA_EXCEPTION = "05";
    /**
     * 06XX：空指针
     */
    static final String NULL_POINTER_EXCEPTION = "06";
    /**
     * ffXX：其他
     */
    static final String OTHER_EXCEPTION = "ff";
}
