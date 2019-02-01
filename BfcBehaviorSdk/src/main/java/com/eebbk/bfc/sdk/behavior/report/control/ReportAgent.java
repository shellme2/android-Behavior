package com.eebbk.bfc.sdk.behavior.report.control;

import android.os.SystemClock;

import com.eebbk.bfc.sdk.behavior.control.report.entity.NotifyReportInfo;
import com.eebbk.bfc.sdk.behavior.report.common.constants.ConstData;
import com.eebbk.bfc.sdk.behavior.report.common.constants.ReportMode;
import com.eebbk.bfc.sdk.behavior.report.common.util.ModeConfig;
import com.eebbk.bfc.sdk.behavior.report.upload.interfaces.IReportModeConfig;
import com.eebbk.bfc.sdk.behavior.report.upload.mode.config.QuantifyModeConfig;
import com.eebbk.bfc.sdk.behavior.report.upload.mode.config.ReportModeConfigFactory;
import com.eebbk.bfc.sdk.behavior.utils.ConfigAgent;
import com.eebbk.bfc.sdk.behavior.utils.ContextUtils;
import com.eebbk.bfc.sdk.behavior.utils.ExecutorsUtils;
import com.eebbk.bfc.sdk.behavior.utils.NetUtils;
import com.eebbk.bfc.sdk.behavior.utils.UploadUtils;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

import java.util.Arrays;

/**
 * 文  件：ReportAgent.java
 * 公  司：步步高教育电子
 * 日  期：2016/8/16  10:23
 * 作  者：HeChangPeng
 */

public class ReportAgent {

    private static long lastNotifyTime = 0;
    private static final String TAG = "ReportAgent";

    /**
     * 初始化上报模块
     */
    public static void initReport() {
        ReportController.getInstance().init(ReportModeConfigFactory.create(ConfigAgent.getBehaviorConfig().reportConfig.reportModeType));
    }

    /**
     * 设置上报模式和配置信息
     * @param config   某种上报类型及其需要设定的参数值
     * <p> eg: </p>
     * <p> QuantifyModeConfig 定量上传模式 </p>
     * <p> FixedTimeModeConfig 定时上传模式 </p>
     * <p> PeriodicityModeConfig 周期性上报模式 </p>
     * <p> PushModeConfig 推送上报模式 </p>
     * <p> RealTimeModeConfig 即时上报模式 </p>
     */
    public static void setReportMode(IReportModeConfig...config) {
        ReportController.getInstance().init(config);
    }

    /**
     * 获取上报模式
     */
    public static int[] getReportMode() {
        return ConfigAgent.getBehaviorConfig().reportConfig.reportModeType;
    }

    /**
     * 触发上报
     * @param triggerMode 触发上报模式集合.此参用来判断触发上报操作是否和当前上报模式匹配
     */
    public static void doReport(int...triggerMode) {
        ReportController.getInstance().doReport(triggerMode);
    }

    /**
     * 开启/关闭数据上报功能
     */
    public static void enableReport(boolean enable) {
        ConfigAgent.getBehaviorConfig().reportConfig.usable = enable;
    }

    /**
     * 采集内部调用的接口，缓存池满通知上报
     */
    public static void notifyReport(final NotifyReportInfo notifyReportInfo) {
        LogUtils.d(TAG, "缓存池满通知上报");
        if (notifyReportInfo == null) {
            return;
        }
        if (Math.abs(SystemClock.elapsedRealtime() - lastNotifyTime) <= ConstData.NOTIFY_INTERVAL) {
            LogUtils.d(TAG, "判断频率为5秒!!");
            return;
        }
        lastNotifyTime = SystemClock.elapsedRealtime();
        ExecutorsUtils.getInstance().notifyExecutor(new Runnable() {
            @Override
            public void run() {
                analasisCondition(notifyReportInfo
                        , ReportMode.MODE_QUANTITY
                        , ReportMode.MODE_PERIOD);
            }
        });
    }

    /**
     *
     * @param notifyReportInfo
     * @param triggerMode 触发上报模式集合
     */
    private static void analasisCondition(NotifyReportInfo notifyReportInfo, int...triggerMode) {
        int[] types = ReportAgent.getReportMode();
        Arrays.sort(types);
        if (Arrays.binarySearch(types, ReportMode.MODE_QUANTITY) >= 0) {
            if (notifyReportInfo.getTotal() >= ConfigAgent.getBehaviorConfig().reportConfig.reportModeConfig.quantity) {
                doReport(triggerMode);
            } else {
                lastNotifyTime = 0;
                LogUtils.d(TAG, "记录没有达到上报阈值，目前记录总数=" + notifyReportInfo.getTotal());
            }
            return;
        }
        doReport(triggerMode);
    }

    private ReportAgent(){
        //prevent the instance
    }
}
