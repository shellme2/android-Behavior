package com.eebbk.behavior.demo.test.performance.stress.real;

/**
 * 作者： liming
 * 日期： 2018/12/21.
 * 公司： 步步高教育电子有限公司
 * 描述：
 */
class CommonAttr {
    private static final String SDK_VERSION = "realtime.v5.0.0";

    /**
     * 机器序列号
     */
    private String mId;
    /**
     * 机型
     */
    private String devName;
    /**
     * 应用包名
     */
    private String packageName;
    /**
     * 模块名 应用名
     */
    private String moduleName;
    /**
     * 应用版本
     */
    private String appVer;
    /**
     * 采集库版本
     */
    private String daVer;

    public CommonAttr(String mId,
                      String devName,
                      String packageName,
                      String moduleName,
                      String appVer) {
        this.mId = mId;
        this.devName = devName;
        this.packageName = packageName;
        this.moduleName = moduleName;
        this.appVer = appVer;
        this.daVer = SDK_VERSION;
    }

    @Override
    public String toString() {
        return "CommonAttr{" +
                "mId='" + mId + '\'' +
                ", devName='" + devName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", appVer='" + appVer + '\'' +
                ", daVer='" + daVer + '\'' +
                '}';
    }

    private CommonAttr() {
    }
}
