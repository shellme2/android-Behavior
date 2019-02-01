package com.eebbk.behavior.demo.test.function.mode;

import android.util.SparseIntArray;
import android.widget.CheckBox;

import com.eebbk.behavior.demo.utils.StringUtils;
import com.eebbk.bfc.sdk.behavior.BehaviorCollector;
import com.eebbk.bfc.sdk.behavior.BehaviorConfig;
import com.eebbk.bfc.sdk.behavior.cache.CacheManager;
import com.eebbk.bfc.sdk.behavior.cache.constant.PolicyType;
import com.eebbk.bfc.sdk.behavior.report.common.constants.ReportMode;
import com.eebbk.bfc.sdk.behavior.report.control.ReportAgent;
import com.eebbk.bfc.sdk.behavior.report.upload.interfaces.IReportModeConfig;
import com.eebbk.bfc.sdk.behavior.report.upload.mode.config.ExitModeConfig;
import com.eebbk.bfc.sdk.behavior.report.upload.mode.config.FixedTimeModeConfig;
import com.eebbk.bfc.sdk.behavior.report.upload.mode.config.LaunchModeConfig;
import com.eebbk.bfc.sdk.behavior.report.upload.mode.config.PeriodicityModeConfig;
import com.eebbk.bfc.sdk.behavior.report.upload.mode.config.QuantifyModeConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author hesn
 * @function
 * @date 16-10-13
 * @company 步步高教育电子有限公司
 */

class ModePresenter {
    private SparseIntArray mReportRadioBtnIds;
    private BehaviorConfig mBehaviorConfig;

    public ModePresenter() {
        mReportRadioBtnIds = new SparseIntArray();
        mBehaviorConfig = BehaviorCollector.getInstance().getConfig();
    }

    /**
     * 设置上报模式对应控件id
     *
     * @param ids
     */
    public void setReportRadioBtnIds(int... ids) {
        mReportRadioBtnIds.put(ReportMode.MODE_QUANTITY, ids[0]);
        mReportRadioBtnIds.put(ReportMode.MODE_FIXTIME, ids[1]);
        mReportRadioBtnIds.put(ReportMode.MODE_PERIOD, ids[2]);
//        mReportRadioBtnIds.put(ReportMode.MODE_PUSH, ids[3]);
        mReportRadioBtnIds.put(ReportMode.MODE_EXIT, ids[3]);
        mReportRadioBtnIds.put(ReportMode.MODE_LAUNCH, ids[4]);
    }

    /**
     * 获取但前上报模式对应选中的控件id
     *
     * @return
     */
    public List<Integer> cheakedRadioBtnId() {
        int[] modeTypes = ReportAgent.getReportMode();
        List<Integer> ids = new ArrayList<>();
        for (int modeType : modeTypes) {
            ids.add(mReportRadioBtnIds.get(modeType));
        }
        return ids;
    }

    /**
     * 是否选中时间缓存策略
     *
     * @return
     */
    public boolean isCheakedCacheTime() {
        return isCheackedPolicy(PolicyType.TIME);
    }

    public String quantify() {
        return String.valueOf(mBehaviorConfig.reportConfig.reportModeConfig.quantity);
    }

    public String fixedTimeHour() {
        return String.valueOf(mBehaviorConfig.reportConfig.reportModeConfig.fixedTimeHour);
    }

    public String fixedTimeMinute() {
        return String.valueOf(mBehaviorConfig.reportConfig.reportModeConfig.fixedTimeMinute);
    }

    public String fixedTimeSecond() {
        return String.valueOf(mBehaviorConfig.reportConfig.reportModeConfig.fixedTimeSeconds);
    }

    public String periodicity() {
        return String.valueOf(mBehaviorConfig.reportConfig.reportModeConfig.periodSeconds);
    }

    public String cacheQuantify() {
        return String.valueOf(mBehaviorConfig.cacheConfig.getCacheSize());
    }

    public String cacheTime() {
        return String.valueOf(mBehaviorConfig.cacheConfig.getCacheTime());
    }

    /**
     * 是否选中空间缓存策略
     *
     * @return
     */
    public boolean isCheakedCacheCapacity() {
        return isCheackedPolicy(PolicyType.CAPACITY);
    }

    /**
     * 保存缓存策略
     *
     * @param time
     * @param capacity
     * @param values
     */
    public void saveCache(boolean time, boolean capacity, String... values) {
        if (!time && !capacity) {
            mBehaviorConfig.cacheConfig.policyTypes = null;
            BehaviorCollector.getInstance().setConfig(mBehaviorConfig);
            CacheManager.getInstance().initPolicy();
            return;
        }
        List<Integer> type = new ArrayList<Integer>();
        if (time) {
            type.add(PolicyType.TIME);
            mBehaviorConfig.cacheConfig.setCacheTime(StringUtils.str2Long(values[0]));
        }
        if (capacity) {
            type.add(PolicyType.CAPACITY);
            mBehaviorConfig.cacheConfig.setCacheSize(StringUtils.str2Int(values[1]));
        }
        int size = type.size();
        int[] policyType = new int[size];
        for (int i = 0; i < size; i++) {
            policyType[i] = type.get(i);
        }
        mBehaviorConfig.cacheConfig.policyTypes = policyType;
        BehaviorCollector.getInstance().setConfig(mBehaviorConfig);
        CacheManager.getInstance().initPolicy();

    }

    /**
     * 保存上传模式
     *
     * @param cheakedIds
     * @param values
     */
    public void saveReport(List<Integer> cheakedIds, String... values) {
        List<IReportModeConfig> configs = new ArrayList<>();
        for (int cheakedId : cheakedIds) {
            int mode = getReportModeRadioBtnId(cheakedId);
            IReportModeConfig modeConfig;
            switch (mode) {
                case ReportMode.MODE_QUANTITY:
                    modeConfig = new QuantifyModeConfig();
                    ((QuantifyModeConfig) modeConfig).setQuantity(StringUtils.str2Int(values[0]));
                    break;
                case ReportMode.MODE_FIXTIME:
                    modeConfig = new FixedTimeModeConfig();
                    ((FixedTimeModeConfig) modeConfig).setHour(StringUtils.str2Int(values[1]));
                    ((FixedTimeModeConfig) modeConfig).setMinute(StringUtils.str2Int(values[2]));
                    ((FixedTimeModeConfig) modeConfig).setSecond(StringUtils.str2Int(values[3]));
                    break;
                case ReportMode.MODE_PERIOD:
                    modeConfig = new PeriodicityModeConfig();
                    ((PeriodicityModeConfig) modeConfig).setModeValue(StringUtils.str2Long(values[4]));
                    break;
                case ReportMode.MODE_EXIT:
                    modeConfig = new ExitModeConfig();
                    break;
                case ReportMode.MODE_LAUNCH:
                    modeConfig = new LaunchModeConfig();
                    break;
                default:
                    modeConfig = new LaunchModeConfig();
                    break;
            }
            configs.add(modeConfig);
        }
        ReportAgent.setReportMode(configs.toArray(new IReportModeConfig[configs.size()]));
    }

    /**
     * 是否选中某缓存策略
     *
     * @param policyType
     * @return
     */
    private boolean isCheackedPolicy(int policyType) {
        int[] types = BehaviorCollector.getInstance().getConfig().cacheConfig.policyTypes;
        if (types == null || types.length <= 0) {
            return false;
        }
        Arrays.sort(types);
        return Arrays.binarySearch(types, policyType) >= 0;
    }

    /**
     * 获取但前上报模式对应选中的控件id
     *
     * @return
     */
    private int getReportModeRadioBtnId(int id) {
        return mReportRadioBtnIds.keyAt(mReportRadioBtnIds.indexOfValue(id));
    }

}
