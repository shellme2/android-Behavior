package com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity;

/**
 * @author hesn
 * @function
 * @date 17-4-28
 * @company 步步高教育电子有限公司
 */

public class DaVer {
    private String serviceDaVer;

    private String servicePackage;

    private String aidlDaVer;

    public String getServiceDaVer() {
        return serviceDaVer;
    }

    public DaVer setServiceDaVer(String serviceDaVer) {
        this.serviceDaVer = serviceDaVer;
        return this;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public DaVer setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
        return this;
    }

    public String getAidlDaVer() {
        return aidlDaVer;
    }

    public DaVer setAidlDaVer(String aidlDaVer) {
        this.aidlDaVer = aidlDaVer;
        return this;
    }
}
