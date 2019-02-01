/**
 * 文  件：ReportController.java
 * 公  司：步步高教育电子
 * 日  期：2016/8/11  10:05
 * 作  者：HeChangPeng
 */
package com.eebbk.bfc.sdk.behavior.report.control;

import android.util.SparseArray;

import com.eebbk.bfc.sdk.behavior.error.ErrorCode;
import com.eebbk.bfc.sdk.behavior.report.common.constants.ReportMode;
import com.eebbk.bfc.sdk.behavior.report.upload.interfaces.IReportMode;
import com.eebbk.bfc.sdk.behavior.report.upload.interfaces.IReportModeConfig;
import com.eebbk.bfc.sdk.behavior.report.upload.mode.ExitMode;
import com.eebbk.bfc.sdk.behavior.report.upload.mode.FixedTimeMode;
import com.eebbk.bfc.sdk.behavior.report.upload.mode.LaunchMode;
import com.eebbk.bfc.sdk.behavior.report.upload.mode.PeriodicityMode;
import com.eebbk.bfc.sdk.behavior.report.upload.mode.QuantifyMode;
import com.eebbk.bfc.sdk.behavior.report.upload.mode.RealTimeMode;
import com.eebbk.bfc.sdk.behavior.userplan.UserPlan;
import com.eebbk.bfc.sdk.behavior.utils.ConfigAgent;
import com.eebbk.bfc.sdk.behavior.utils.ContextUtils;
import com.eebbk.bfc.sdk.behavior.utils.ExecutorsUtils;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

import java.util.Arrays;

/**
 * 采集上报管理类 亦是对外接口类
 */
public class ReportController {

    private UserPlan userPlan;
    private static final String TAG = "ReportController";
    private final SparseArray<IReportMode> mReportModeArr = new SparseArray<>();

    private static class InstanceHolder {
        private static final ReportController mInstance = new ReportController();
    }

    public static ReportController getInstance() {
        return InstanceHolder.mInstance;
    }

    private ReportController() {
        addReportMode(
            new FixedTimeMode(),
            new PeriodicityMode(),
            new QuantifyMode(),
            new RealTimeMode(),
            new ExitMode(),
            new LaunchMode());
        userPlan = new UserPlan();
    }

    /**
     * 设置上报模式
     */
    private synchronized void setReportMode(IReportModeConfig...reportModeConfigs) {
        int size = mReportModeArr.size();
        if (size == 0) {
            return;
        }
        for (int i = 0; i < size; i++) {
            int key = mReportModeArr.keyAt(i);
            IReportMode reportMode = mReportModeArr.get(key);
            if (reportMode == null) {
                continue;
            }
            reportMode.openMode(false);
        }
        int len = reportModeConfigs.length;
        int[] types = new int[len];
        for (int i = 0; i < len; i++) {
            IReportModeConfig config = reportModeConfigs[i];
            if(config == null){
                continue;
            }
            IReportMode reportMode = getReportMode(config.modeType());
            if (reportMode == null) {
                continue;
            }
            reportMode.openMode(true);
            types[i] = reportMode.modeType();
        }
        Arrays.sort(types);
        ConfigAgent.getBehaviorConfig().reportConfig.reportModeType = types;
    }

    /**
     * 初始化上报模式需要的参数
     */
    protected void init(final IReportModeConfig...reportModeConfigs) {
        if (ContextUtils.isEmpty() || reportModeConfigs == null || reportModeConfigs.length == 0) {
            LogUtils.bfcExceptionLog(TAG, ErrorCode.CONTROL_INIT_REPORT);
            return;
        }
        setReportMode(reportModeConfigs);
        ExecutorsUtils.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                for (IReportModeConfig reportModeConfig : reportModeConfigs) {
                    if(reportModeConfig == null){
                        continue;
                    }
                    getReportMode(reportModeConfig.modeType()).initMode(ContextUtils.getContext(), reportModeConfig);
                }
            }
        });
    }

    /**
     * 用户体验计划设置有修改,需要重新获取
     */
    public void userPlanSettingChange(){
        if(ContextUtils.isEmpty()){
            return;
        }
        userPlan.onChange(ContextUtils.getContext());
    }

    /**
     * 上报
     */
    public synchronized void doReport(int... triggerMode) {
        if (mReportModeArr.size() == 0 || ContextUtils.isEmpty()) {
            LogUtils.bfcExceptionLog(TAG, ErrorCode.CONTROL_INIT_DOREPORT);
            return;
        }

        if (!ConfigAgent.getBehaviorConfig().reportConfig.usable) {
            LogUtils.w(TAG, "DA上报模块已关闭");
            return;
        }

        if (!ConfigAgent.getBehaviorConfig().isIgnoreUserPlan && !userPlan.isEnabled(ContextUtils.getContext())) {
            LogUtils.w(TAG, "用户体验改进计划未打开,DA不上报数据");
            return;
        }

        if (triggerMode == null || triggerMode.length <= 0) {
            LogUtils.bfcWLog(TAG, "此触发上报未设置上报模式,默认设置为:" + Arrays.toString(ConfigAgent.getBehaviorConfig().reportConfig.reportModeType));
            return;
        }

        startReport(triggerMode);
    }

    private void addReportMode(IReportMode... reportModes) {
        if (reportModes == null || reportModes.length <= 0) {
            return;
        }
        for (IReportMode reportMode : reportModes) {
            if (reportMode == null) {
                continue;
            }
            mReportModeArr.put(reportMode.modeType(), reportMode);
        }
    }

    private IReportMode getReportMode(int checkType) {
        if (containsReportMode(checkType)) {
            return mReportModeArr.get(checkType);
        } else {
            return mReportModeArr.get(ReportMode.MODE_LAUNCH);
        }
    }

    private boolean containsReportMode(int checkType) {
        return mReportModeArr.indexOfKey(checkType) >= 0;
    }

    private void startReport(int... triggerMode){
        for (int modeType : triggerMode) {
            LogUtils.d(TAG, "startReport() modeType: " + modeType);
            if(!containsReportMode(modeType)){
                LogUtils.d(TAG, "startReport() do not contains report mode:" + modeType);
                continue;
            }
            IReportMode reportMode = getReportMode(modeType);
            if(!reportMode.isModeOpen()){
                LogUtils.d(TAG, "startReport() report mode not open:" + modeType);
                continue;
            }
            reportMode.doReport(ContextUtils.getContext(), triggerMode);
        }
    }
}
