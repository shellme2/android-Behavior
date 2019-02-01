package com.eebbk.bfc.sdk.behavior.error;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hesn
 * @function 大数据采集错误码
 * <p> 完整的错误码由：项目编号 + 模块编号 + 错误编号 组成 </p>
 * <p> 具体请参考《中间件错误码项目标号列表》 </p>
 * @date 16-9-29
 * @company 步步高教育电子有限公司
 */

public class ErrorCode extends ErrorCodeStandard {
    private static Map<String, String> mErrCodes;

    /**
     * 数据入库失败，请先调用BehaviorCollector.getInstance().init()进行初始化
     * <br> 模块：控制层
     * <br> 异常类型：初始化异常
     */
    public static final String CONTROL_INIT_INSERT = PROJECT_BEHAVIOR
            + MODULE_CONTROL + INIT_EXCEPTION + "01";
    /**
     * 异常捕获模块初始化失败
     * <br> 模块：控制层
     * <br> 异常类型：初始化异常
     */
    public static final String CONTROL_INIT_CRASH = PROJECT_BEHAVIOR
            + MODULE_CONTROL + INIT_EXCEPTION + "02";
    /**
     * 在调用 pageEnd() 前必须要先调用 pageBegin()
     * <br> 模块：控制层
     * <br> 异常类型：规范操作
     */
    public static final String CONTROL_PAGE_ORDER = PROJECT_BEHAVIOR
            + MODULE_CONTROL + OPERATION_EXCEPTION + "01";
    /**
     * 在调用 recordFunctionEnd() 前必须要先调用 recordFunctionBegin()
     * <br> 模块：控制层
     * <br> 异常类型：规范操作
     */
    public static final String CONTROL_RECORD_FUNCTION_ORDER = PROJECT_BEHAVIOR
            + MODULE_CONTROL + OPERATION_EXCEPTION + "02";
    /**
     * 数据入库失败，该functionName已经存在
     * <br> 模块：控制层
     * <br> 异常类型：规范操作
     */
    public static final String CONTROL_PAGE_FUNCTIONNAME_MULTIDEX = PROJECT_BEHAVIOR
            + MODULE_CONTROL + OPERATION_EXCEPTION + "03";
    /**
     * 记录的时间有问题，计算得到使用时长小于0
     * <br> 模块：控制层
     * <br> 异常类型：参数异常
     */
    public static final String CONTROL_ILLEGAL_ARGUMENT_RECORD_FUNCTION = PROJECT_BEHAVIOR
            + MODULE_CONTROL + ILLEGAL_ARGUMENT_EXCEPTION + "01";
    /**
     * 缓存配置设置失败，cacheConfig不能为空
     * <br> 模块：控制层
     * <br> 异常类型：空指针
     */
    public static final String CONTROL_NULL_POINTER_CACHE_CONFIG = PROJECT_BEHAVIOR
            + MODULE_CONTROL + NULL_POINTER_EXCEPTION + "01";
    /**
     * EventAttr参数不能为空
     * <br> 模块：控制层
     * <br> 异常类型：空指针
     */
    public static final String CONTROL_NULL_POINTER_EVENT_ATTR = PROJECT_BEHAVIOR
            + MODULE_CONTROL + NULL_POINTER_EXCEPTION + "02";
    /**
     * 数据入库失败，数据为空
     * <br> 模块：控制层
     * <br> 异常类型：空指针
     */
    public static final String CONTROL_NULL_POINTER_IEVENT = PROJECT_BEHAVIOR
            + MODULE_CONTROL + NULL_POINTER_EXCEPTION + "03";
    /**
     * functionName不能为空
     * <br> 模块：控制层
     * <br> 异常类型：空指针
     */
    public static final String CONTROL_NULL_POINTER_PAGE_FUNCTIONNAME = PROJECT_BEHAVIOR
            + MODULE_CONTROL + NULL_POINTER_EXCEPTION + "04";
    /**
     * 配置信息设置失败，behaviorConfig不能为空
     * <br> 模块：控制层
     * <br> 异常类型：空指针
     */
    public static final String CONTROL_NULL_POINTER_BEHAVIOR_CONFIG = PROJECT_BEHAVIOR
            + MODULE_CONTROL + NULL_POINTER_EXCEPTION + "05";
    /**
     * Application context不能设置为空
     * <br> 模块：控制层
     * <br> 异常类型：空指针
     */
    public static final String CONTROL_NULL_POINTER_APPLICATION = PROJECT_BEHAVIOR
            + MODULE_CONTROL + NULL_POINTER_EXCEPTION + "06";

    /**
     * 获取IMEI失败,请动态申请权限 android.permission.READ_PHONE_STATE
     * <br> 模块：控制层
     * <br> 异常类型：其他异常
     */
    public static final String NO_READ_PHONE_STATE_PERMISSION = PROJECT_BEHAVIOR
            + MODULE_CONTROL + OTHER_EXCEPTION + "01";

    /**
     * 数据数据为空，无法缓存上报
     * <br> 模块：缓冲层
     * <br> 异常类型：空指针异常
     */
    public static final String CACHE_EVENT_NULL_POINTER = PROJECT_BEHAVIOR
            + MODULE_CACHE + NULL_POINTER_EXCEPTION + "01";

    /**
     * 数据库provider初始化异常
     * <br> 模块：数据层
     * <br> 异常类型：初始化异常
     */
    public static final String CONTROL_INIT_PROVIDER = PROJECT_BEHAVIOR
            + MODULE_DB + INIT_EXCEPTION + "01";

    /**
     * 上报模块初始化失败
     * <br> 模块：上报层
     * <br> 异常类型：初始化异常
     */
    public static final String CONTROL_INIT_REPORT = PROJECT_BEHAVIOR
            + MODULE_REPORT + INIT_EXCEPTION + "01";
    /**
     * 初始化异常，无法执行上报操作
     * <br> 模块：上报层
     * <br> 异常类型：初始化异常
     */
    public static final String CONTROL_INIT_DOREPORT = PROJECT_BEHAVIOR
            + MODULE_REPORT + INIT_EXCEPTION + "02";
    /**
     * 注册监听home键失败
     * <br> 模块：上报层
     * <br> 异常类型：初始化异常
     */
    public static final String REPORT_HOME_KEY_REG = PROJECT_BEHAVIOR
            + MODULE_REPORT + INIT_EXCEPTION + "03";
    /**
     * 注销home键监听失败
     * <br> 模块：上报层
     * <br> 异常类型：初始化异常
     */
    public static final String REPORT_HOME_KEY_UNREG = PROJECT_BEHAVIOR
            + MODULE_REPORT + INIT_EXCEPTION + "04";
    /**
     * 数据上报失败,bfc-uploadsdk异常,数据可能丢失
     * <br> 模块：上报层
     * <br> 异常类型：空指针
     */
    public static final String REPORT_BFC_UPLOAD_INIT_EXCEPTION = PROJECT_BEHAVIOR
            + MODULE_REPORT + INIT_EXCEPTION + "05";
    /**
     * 没有网络，无法执行上报操作
     * <br> 模块：上报层
     * <br> 异常类型：网络异常
     */
    public static final String REPORT_NO_NET = PROJECT_BEHAVIOR
            + MODULE_REPORT + NET_EXCEPTION + "01";
    /**
     * 当前非wifi网络状态，仅在wifi下上报
     * <br> 模块：上报层
     * <br> 异常类型：网络异常
     */
    public static final String REPORT_ONLY_WIFI = PROJECT_BEHAVIOR
            + MODULE_REPORT + NET_EXCEPTION + "02";

    /**
     * 上传参数有问题!! context = null
     * <br> 模块：上报层
     * <br> 异常类型：空指针
     */
    public static final String REPORT_TASK_CONTEXT_NULL = PROJECT_BEHAVIOR
            + MODULE_REPORT + NULL_POINTER_EXCEPTION + "01";
    /**
     * 上传参数有问题!! zipFile = null
     * <br> 模块：上报层
     * <br> 异常类型：空指针
     */
    public static final String REPORT_ZIP_NULL = PROJECT_BEHAVIOR
            + MODULE_REPORT + NULL_POINTER_EXCEPTION + "02";
    /**
     * 上传参数有问题!! zipFile文件不存在:
     * <br> 模块：上报层
     * <br> 异常类型：空指针
     */
    public static final String REPORT_ZIP_NOT_EXISTS = PROJECT_BEHAVIOR
            + MODULE_REPORT + NULL_POINTER_EXCEPTION + "03";
    /**
     * 收到定时上报消息，但当前非定时上报模式，不做处理
     * <br> 模块：上报层
     * <br> 异常类型：其他异常
     */
    public static final String REPORT_ILLEGAL_MODE_START_FIXED_TIME = PROJECT_BEHAVIOR
            + MODULE_REPORT + OTHER_EXCEPTION + "01";
    /**
     * 收到推送上报消息，但当前非推送上报模式，不做处理
     * <br> 模块：上报层
     * <br> 异常类型：其他异常
     */
    public static final String REPORT_ILLEGAL_MODE_START_PUSH = PROJECT_BEHAVIOR
            + MODULE_REPORT + OTHER_EXCEPTION + "02";
    /**
     * 此上报模式尚未开启
     * <br> 模块：上报层
     * <br> 异常类型：其他异常
     */
    public static final String REPORT_THE_MODE_ALREADY_CLOSED = PROJECT_BEHAVIOR
            + MODULE_REPORT + OTHER_EXCEPTION + "03";
//    /**
//     * 无效上报触发.
//     * <br> 模块：上报层
//     * <br> 异常类型：其他异常
//     */
//    public static final String REPORT_INVALID_TRIGGER = PROJECT_BEHAVIOR
//            + MODULE_REPORT + OTHER_EXCEPTION + "04";
    /**
     * 创建上传文件失败
     * <br> 模块：上报层
     * <br> 异常类型：其他异常
     */
    public static final String REPORT_CREATE_FILE_ERROR = PROJECT_BEHAVIOR
            + MODULE_REPORT + OTHER_EXCEPTION + "05";
    /**
     * 上传文件压缩失败
     * <br> 模块：上报层
     * <br> 异常类型：其他异常
     */
    public static final String REPORT_CREATE_ZIP_ERROR = PROJECT_BEHAVIOR
            + MODULE_REPORT + OTHER_EXCEPTION + "06";
    /**
     * 上报行为采集数据不支持类型,　taskType:
     * <br> 模块：上报层
     * <br> 异常类型：其他异常
     */
    public static final String REPORT_UNKNOW_FILE_TYPE = PROJECT_BEHAVIOR
            + MODULE_REPORT + OTHER_EXCEPTION + "07";
    /**
     * 磁盘剩余空间不足,上报失败
     * <br> 模块：上报层
     * <br> 异常类型：其他异常
     */
    public static final String REPORT_INSUFFICIENT_REMAINING_SPACE = PROJECT_BEHAVIOR
            + MODULE_REPORT + OTHER_EXCEPTION + "08";

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
        //控制层
        //02010101
        mErrCodes.put(CONTROL_INIT_INSERT, "数据入库失败，请先调用BehaviorCollector.getInstance().init()进行初始化");
        //02010102
        mErrCodes.put(CONTROL_INIT_CRASH, "异常捕获模块初始化失败");
        //02010201
        mErrCodes.put(CONTROL_PAGE_ORDER, "在调用 pageEnd() 前必须要先调用 pageBegin()");
        //02010202
        mErrCodes.put(CONTROL_RECORD_FUNCTION_ORDER, "在调用 recordFunctionEnd() 前必须要先调用 recordFunctionBegin()");
        //02010203
        mErrCodes.put(CONTROL_PAGE_FUNCTIONNAME_MULTIDEX, "数据入库失败，该functionName已经存在");
        //02010301
        mErrCodes.put(CONTROL_ILLEGAL_ARGUMENT_RECORD_FUNCTION, "记录的时间有问题，计算得到使用时长小于0");
        //02010601
        mErrCodes.put(CONTROL_NULL_POINTER_CACHE_CONFIG, "缓存配置设置失败，cacheConfig不能为空！");
        //02010602
        mErrCodes.put(CONTROL_NULL_POINTER_EVENT_ATTR, "EventAttr参数不能为空");
        //02010603
        mErrCodes.put(CONTROL_NULL_POINTER_IEVENT, "数据入库失败，数据为空");
        //02010604
        mErrCodes.put(CONTROL_NULL_POINTER_PAGE_FUNCTIONNAME, "functionName不能为空");
        //02010605
        mErrCodes.put(CONTROL_NULL_POINTER_BEHAVIOR_CONFIG, "配置信息设置失败，behaviorConfig不能为空");
        //02010606
        mErrCodes.put(CONTROL_NULL_POINTER_APPLICATION, "Application context不能设置为空");
        //0201ff01
        mErrCodes.put(NO_READ_PHONE_STATE_PERMISSION, "获取IMEI失败,请动态申请权限 android.permission.READ_PHONE_STATE.");

        //缓冲层
        //02020601
        mErrCodes.put(CACHE_EVENT_NULL_POINTER, "数据数据为空，无法缓存上报");

        //数据层
        //02030101
        mErrCodes.put(CONTROL_INIT_PROVIDER, "数据库provider初始化异常");

        //上报层
        //02040101
        mErrCodes.put(CONTROL_INIT_REPORT, "上报模块初始化失败");
        //02040102
        mErrCodes.put(CONTROL_INIT_DOREPORT, "初始化异常，无法执行上报操作");
        //02040103
        mErrCodes.put(REPORT_HOME_KEY_REG, "注册监听home键失败");
        //02040104
        mErrCodes.put(REPORT_HOME_KEY_UNREG, "注销home键监听失败");
        //02040105
        mErrCodes.put(REPORT_BFC_UPLOAD_INIT_EXCEPTION, "数据上报失败,bfc-uploadsdk异常,数据可能丢失");
        //02040401
        mErrCodes.put(REPORT_NO_NET, "没有网络，无法执行上报操作");
        //02040402
        mErrCodes.put(REPORT_ONLY_WIFI, "当前非wifi网络状态，仅在wifi下上报");
        //02040601
        mErrCodes.put(REPORT_TASK_CONTEXT_NULL, "上传参数有问题!! context = null");
        //02040602
        mErrCodes.put(REPORT_ZIP_NULL, "上传参数有问题!! zipFile = null");
        //02040603
        mErrCodes.put(REPORT_ZIP_NOT_EXISTS, "上传参数有问题!! zipFile文件不存在:");
        //0204ff01
        mErrCodes.put(REPORT_ILLEGAL_MODE_START_FIXED_TIME, "收到定时上报消息，但当前非定时上报模式，不做处理");
        //0204ff02
        mErrCodes.put(REPORT_ILLEGAL_MODE_START_PUSH, "收到推送上报消息，但当前非推送上报模式，不做处理");
        //0204ff03
        mErrCodes.put(REPORT_THE_MODE_ALREADY_CLOSED, "此上报模式尚未开启");
        //0204ff04
//        mErrCodes.put(REPORT_INVALID_TRIGGER, "无效上报触发.");
        //0204ff05
        mErrCodes.put(REPORT_CREATE_FILE_ERROR, "创建上传文件失败");
        //0204ff06
        mErrCodes.put(REPORT_CREATE_ZIP_ERROR, "创建上传文件压缩失败");
        //0204ff07
        mErrCodes.put(REPORT_UNKNOW_FILE_TYPE, "上报行为采集数据不支持类型,　taskType:");
        //0204ff08
        mErrCodes.put(REPORT_INSUFFICIENT_REMAINING_SPACE, "磁盘剩余空间不足,上报失败");
        return new HashMap<String, String>(mErrCodes);
    }

    private ErrorCode() {
        //prevent the instance
    }
}
