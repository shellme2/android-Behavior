package com.eebbk.bfc.sdk.behavior.exception;

/**
 * @author hesn
 * @function
 * @date 16-8-22
 * @company 步步高教育电子有限公司
 */

public class CrashConfig {
    /**
     * 开启/关闭捕捉异常
     * @return
     */
    public boolean usable = true;
    /**
     * 决定着要不要弹崩溃界面
     * @return
     */
    public boolean isUIReport = false;
    /**
     * 要不要弹Toast
     * @return
     */
    public boolean isToast = true;
    /**
     * 开启/关闭 anr异常采集
     * @return
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
}
