package com.eebbk.bfc.sdk.behavior;

import com.eebbk.bfc.sdk.behavior.cache.CacheConfig;
import com.eebbk.bfc.sdk.behavior.exception.CrashConfig;
import com.eebbk.bfc.sdk.behavior.report.ReportConfig;

/**
 * @author hesn
 * @function 采集配置信息类
 * @date 16-9-22
 * @company 步步高教育电子有限公司
 */

public class BehaviorConfig {
    /**
     * 大数据采集总开关
     */
    public boolean usable = true;
    /**
     * 自动采集activity使用时长
     */
    public boolean openActivityDurationTrack = true;
    /**
     * 自动采集app启动事件
     */
    public boolean enableCollectLaunch = true;
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
     * 是否无视用户体验计划
     */
    public boolean isIgnoreUserPlan = true;
    /**
     * 是否重试上传OOM文件
     */
    public boolean isRetryLargeFile = true;
    /**
     * 缓存配置
     */
    public final CacheConfig cacheConfig = new CacheConfig();
    /**
     * 上报配置
     */
    public final ReportConfig reportConfig = new ReportConfig();
    /**
     * 异常捕获配置
     */
    public final CrashConfig crashConfig = new CrashConfig();
    /**
     * 生命周期监听
     */
    public final transient BCLifeCycleCallback bcLifeCycleCallback = new BCLifeCycleCallback();

}
