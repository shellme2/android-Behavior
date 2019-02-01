package com.eebbk.bfc.sdk.behavior.aidl;

/**
 * @author hesn
 * @function
 * @date 17-4-10
 * @company 步步高教育电子有限公司
 */

public class Settings implements Cloneable{

    /**
     * aidl采集开关
     * <p>
     *     注:此开关不影响 com.eebbk.bfc.sdk.behavior.BehaviorCollector 中的开关
     * </p>
     */
    public boolean enable = true;

    /**
     * app在后台sessionTimeout后，再进入前台则调用app启动事件
     */
    public long sessionTimeout = 30 * 1000;

    /**
     * 调式模式
     */
    public boolean isDebugMode = false;

    /**
     * 自动采集activity使用时长
     */
    public boolean openActivityDurationTrack = true;

    /**
     * 自动采集app启动事件
     */
    public boolean enableCollectLaunch = true;

    /**
     * 异常捕获 开启
     */
    public boolean crashEnable = true;

    /**
     * anr捕获 开启
     */
    public boolean crashAnrEnable = true;

    /**
     * 开启/关闭 自动过滤信息不全的anr异常
     * @return
     */
    public boolean autoFilterAnr = true;

    /**
     * 开启/关闭 严苛模式采集
     * @return
     */
    public boolean crashStrictModeEnable = false;

    /**
     * 开启/关闭 native异常采集
     * @return
     */
    public boolean crashNativeEnable = true;

    /**
     * 忽略设置的时间之前的异常信息 anr异常采集
     * @return
     */
    public long ignoreBeforeAnr = -1;

    /**
     * 忽略设置的时间之前的异常信息 严苛模式采集
     * @return
     */
    public long ignoreBeforeStrictMode = -1;

    /**
     * 忽略设置的时间之前的异常信息 native异常采集
     * @return
     */
    public long ignoreBeforeNative = -1;

    /**
     * Toast提示异常捕获
     */
    public boolean isToastCrash = true;

    public String moduleName;

    @Override
    protected Settings clone() throws CloneNotSupportedException {
        Settings settings = new Settings();
        settings.enable = enable;
        settings.sessionTimeout = sessionTimeout;
        settings.isDebugMode = isDebugMode;
        settings.openActivityDurationTrack = openActivityDurationTrack;
        settings.crashEnable = crashEnable;
        settings.crashAnrEnable = crashAnrEnable;
        settings.crashStrictModeEnable = crashStrictModeEnable;
        settings.crashNativeEnable = crashNativeEnable;
        settings.ignoreBeforeAnr = ignoreBeforeAnr;
        settings.ignoreBeforeStrictMode = ignoreBeforeStrictMode;
        settings.ignoreBeforeNative = ignoreBeforeNative;
        settings.isToastCrash = isToastCrash;
        settings.moduleName = moduleName;
        return settings;
    }
}
