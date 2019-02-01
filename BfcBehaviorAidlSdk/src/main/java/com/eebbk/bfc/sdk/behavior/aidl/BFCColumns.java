package com.eebbk.bfc.sdk.behavior.aidl;

public class BFCColumns {

    /**
     * 机器属性(自动采集)
     */
    public static final String COLUMN_MA_MID = "mId";
    public static final String COLUMN_MA_DEVICE = "devName";
    public static final String COLUMN_MA_OSVERSION = "osVer";
    public static final String COLUMN_MA_BRAND = "brand";

    /**
     * 用户属性(非自动采集)
     */
    public static final String COLUMN_UA_USERID = "userId";
    public static final String COLUMN_UA_USERNAME = "userName";
    public static final String COLUMN_UA_SEX = "sex";
    public static final String COLUMN_UA_BIRTHDAY = "birthday";
    public static final String COLUMN_UA_GRADE = "grade";
    public static final String COLUMN_UA_PHONENUM = "phoneNum";
    public static final String COLUMN_UA_USEREXTEND = "userExtend";
    //add 2017-03-21 兼容老版本
    public static final String COLUMN_UA_AGE = "age";
    public static final String COLUMN_UA_SCHOOL = "school";
    public static final String COLUMN_UA_GRADETYPE = "gradeType";
    public static final String COLUMN_UA_SUBJECTS = "subjects";
    /**
     * 应用属性(自动采集)
     */
    public static final String COLUMN_AA_APPID = "appId";
    public static final String COLUMN_AA_APPVER = "appVer";
    public static final String COLUMN_AA_PACKAGENAME = "packageName";
    public static final String COLUMN_AA_MODULENAME = "moduleName";
    /**
     * 事件属性(非自动采集)
     */
    public static final String COLUMN_EA_EVENTNAME = "eventName";//自动设置
    public static final String COLUMN_EA_FUNCTIONNAME = "functionName";
    public static final String COLUMN_EA_EVENTTYPE = "eventType";//自动设置
    public static final String COLUMN_EA_TTIME = "trigTime";
    public static final String COLUMN_EA_TVALUE = "trigValue";
    public static final String COLUMN_EA_PAGE = "page";
    public static final String COLUMN_EA_MODULEDETAIL = "moduleDetail";

    /**
     * 数据属性(非自动采集)
     */
    public static final String COLUMN_DA_DATAID = "dataId";
    public static final String COLUMN_DA_DATATITLE = "dataTitle";
    public static final String COLUMN_DA_DATAEDITION = "dataEdition";
    public static final String COLUMN_DA_DATATYPE = "dataType";
    public static final String COLUMN_DA_DATAGRADE = "dataGrade";
    public static final String COLUMN_DA_DATASUBJECT = "dataSubject";
    public static final String COLUMN_DA_DATAPUBLISHER = "dataPublisher";
    public static final String COLUMN_DA_DATAEXTEND = "dataExtend";

    /**
     * 其它(自动采集)
     */
    public static final String COLUMN_EA_SESSIONID = "sessionId";//用来记录App当次打开的会话唯一标识。
    public static final String COLUMN_OA_EXTEND = "extend";
    public static final String COLUMN_OA_DAVER = "daVer";//采集Jar包的版本
    public static final String COLUMN_OA_ROUTERMAC = "routerMac";//所连接路由的MAC地址
    public static final String COLUMN_OA_CHANNELID = "channelId";//渠道ID
    public static final String COLUMN_OA_EXTENDJUDGMENT = "judgmentExtend";//额外扩展上传判断条件
    public static final String COLUMN_OA_IMEI = "imei";

    /**
     * 启动事件扩展信息(自动采集)
     */
    public static final String LAUNCH_EVENT_EXTEND_SCREEN_WIDTH = "ScreenWidth";//屏幕宽度
    public static final String LAUNCH_EVENT_EXTEND_SCREEN_HEIGHT = "ScreenHeight";//屏幕高度
    public static final String LAUNCH_EVENT_EXTEND_ANDROID_SDK = "androidSdk";//系统的api版本
    public static final String LAUNCH_EVENT_EXTEND_MANUFACTURER = "manufacturer";//设备的制造厂商

    private BFCColumns() {
        //prevent the instance
    }
}