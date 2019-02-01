package com.eebbk.behavior.demo.utils;

import com.eebbk.bfc.sdk.behavior.BehaviorConfig;
import com.eebbk.bfc.sdk.behavior.cache.CacheConfig;
import com.eebbk.bfc.sdk.behavior.exception.CrashConfig;
import com.eebbk.bfc.sdk.behavior.report.ReportConfig;

/**
 * @author hesn
 * @function 行为采集的配置
 * <br> 因为 BCLifeCycleCallback 无法序列化,所以要另外配置
 * @date 17-3-7
 * @company 步步高教育电子有限公司
 */

public class SDKConfig {

    public SDKConfig(BehaviorConfig config){
        usable = config.usable;
        openActivityDurationTrack = config.openActivityDurationTrack;
        sessionTimeout = config.sessionTimeout;
        isDebugMode = config.isDebugMode;
        isCacheLog = config.isCacheLog;
        minStoreAvailableSize = config.minStoreAvailableSize;
        cacheConfig = config.cacheConfig;
        reportConfig = config.reportConfig;
        crashConfig = config.crashConfig;
    }

    public BehaviorConfig getBehaviorConfig(BehaviorConfig config){
        config.usable = usable;
        config.openActivityDurationTrack = openActivityDurationTrack;
        config.sessionTimeout = sessionTimeout;
        config.isDebugMode = isDebugMode;
        config.isCacheLog = isCacheLog;
        config.minStoreAvailableSize = minStoreAvailableSize;
        config.cacheConfig.policyTypes = cacheConfig.policyTypes;
        config.cacheConfig.usable = cacheConfig.usable;
        config.cacheConfig.ignoreCacheEventType = cacheConfig.ignoreCacheEventType;
        config.cacheConfig.setCacheSize(cacheConfig.getCacheSize());
        config.cacheConfig.setCacheTime(cacheConfig.getCacheTime());
        config.reportConfig.reportModeConfig = reportConfig.reportModeConfig;
        config.reportConfig.reportModeType = reportConfig.reportModeType;
        config.reportConfig.usable = reportConfig.usable;
        config.reportConfig.sort = reportConfig.sort;
        config.crashConfig.usable = crashConfig.usable;
        config.crashConfig.isUIReport = crashConfig.isUIReport;
        config.crashConfig.isToast = crashConfig.isToast;
        config.reportConfig.mNetworkTypes = reportConfig.mNetworkTypes;

        return config;
    }

    /**
     * 大数据采集总开关
     */
    public boolean usable = true;
    /**
     * 自动采集activity使用时长
     */
    public boolean openActivityDurationTrack = false;
    /**
     * app在后台sessionTimeout后，再进入前台则调用app启动事件
     */
    public long sessionTimeout = 30 * 1000;
    /**
     * 调式模式
     */
    public boolean isDebugMode = false;
    /**
     * 是否缓存log日志
     */
    public boolean isCacheLog = false;
    /**
     * 最小剩余空间
     * <br> 为了数据打包成文件上报时,有足够空间.
     * <br> 小于此值则默认无法执行上报
     * <br> 单位 b
     */
    public long minStoreAvailableSize = 5 * 1024 * 1024;
    /**
     * 缓存配置
     */
    public CacheConfig cacheConfig = new CacheConfig();
    /**
     * 上报配置
     */
    public ReportConfig reportConfig = new ReportConfig();
    /**
     * 异常捕获配置
     */
    public CrashConfig crashConfig = new CrashConfig();
}
