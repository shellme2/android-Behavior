/**
 * 文  件：ConstData.java
 * 公  司：步步高教育电子
 * 日  期：2016/7/5  11:26
 * 作  者：HeChangPeng
 */
package com.eebbk.bfc.sdk.behavior.report.common.constants;

public interface ConstData {
    /**
     * 行为采集库依赖应用的applicationId
     * 内容提供者AUTHORITY前面部分，用以区分不同应用使用相同内容提供者导致无法安装
     */
    String BFC_DOWNLOAD_HOST_APP_ID = "BFC_BEHAVIOR_HOST_APP_ID";

    String VALUE_MODULE = "BFC";

    String VALUE_FUNCTION_BC = "UserBehavior";

    String TAG = "behaviorReport";

    String UPLOAD_CLOUD_BUCKET_ID = "com.eebbk.bfc.app.bfcbehavior";

    String UPLOAD_BY_DA = "studyos.sdk.da.upload";
    /**
     * 上传完成的广播
     */
    String ACTION_UPLOAD_COMPLETE = "com.eebbk.sdk.datacollect.action.uploadcomplete";

    /**
     * 云巴推送的广播
     */
    String PUSH_MESSAGE_ACTION = "io.yunba.android.MESSAGE_RECEIVED_ACTION";

    /**
     * 定时触发时发出的广播
     */
    String FIXTIME_TRIGER_ACTION = "com.eebbk.sdk.datacollect.action.fixtimetriger";

    String KERNEL_FILE_NAME = "kernel_filename";
    String KERNEL_FILE_PATH = "kernel_filepath";

    String FRAMEWORK_FILE_NAME = "framework_filename";
    String FRAMEWORK_FILE_PATH = "framework_filepath";

    String VALUE_FUNCTION_SYSLOG = "Syslog";
    String VALUE_FUNCTION_FRAMEWORKLOG = "FrameworkLog";

    /**
     * 默认上传失败后重传次数
     */
    int UPLOAD_RETRY_DEFAULT = 5;

    /**
     * 每个上报任务最大记录数
     */
    int UPLOAD_MAX_RECORDS = 100;

    /**
     * 每个上报任务最小记录数
     */
    int UPLOAD_MIN_RECORDS = 1;

    /**
     * 上报线程数
     */
    int MAX_UPLOAD_THREAD = 10;

    /**
     * 缓存满时通知上报的时间间隔 默认：5秒
     */
    long NOTIFY_INTERVAL = 5000;
    /**
     * 采集数据触发时间保存格式
     */
    String DATA_TRIGGER_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
}
