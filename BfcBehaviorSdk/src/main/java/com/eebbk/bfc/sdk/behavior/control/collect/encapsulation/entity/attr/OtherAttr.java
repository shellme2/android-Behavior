package com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr;

import android.content.ContentValues;

import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.DaVer;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.interfaces.IAttr;
import com.eebbk.bfc.sdk.behavior.db.constant.BFCColumns;
import com.eebbk.bfc.sdk.behavior.utils.JsonUtils;

public class OtherAttr implements IAttr {
    /**
     * 扩展信息
     */
    private String extend = "";
    /**
     * 采集Jar包的版本
     */
    private String daVer;
    /**
     * 所连接路由的MAC地址
     */
    private String routerMac;
    /**
     * app每次启动的 session id
     */
    private String sessionid;
    /**
     * 上传条件判断扩展字段
     */
    private String extendJudgment;
    /**
     * imei
     */
    private String imei;
    /**
     * cpuid
     */
    private String innerModel;
    /**
     * 机器MAC地址
     */
    private String romver;

    @Override
    public void insert(ContentValues values) {
        values.put(BFCColumns.COLUMN_EA_SESSIONID, sessionid);
        values.put(BFCColumns.COLUMN_OA_EXTEND, extend);
        values.put(BFCColumns.COLUMN_OA_ROUTERMAC, routerMac);
        values.put(BFCColumns.COLUMN_OA_EXTENDJUDGMENT, extendJudgment);
        values.put(BFCColumns.COLUMN_OA_IMEI, imei);
        values.put(BFCColumns.COLUMN_OA_DAVER, JsonUtils.toJson(new DaVer().setServiceDaVer(daVer)));
        values.put(BFCColumns.COLUMN_OA_INNER_MODEL, innerModel);
        values.put(BFCColumns.COLUMN_OA_ROMVER, romver);
    }

    public String getDaVer() {
        return daVer;
    }

    public OtherAttr setDaVer(String daVer) {
        this.daVer = daVer;
        return this;
    }

    public String getRouterMac() {
        return routerMac;
    }

    public OtherAttr setRouterMac(String routerMac) {
        this.routerMac = routerMac;
        return this;
    }

    public String getExtend() {
        return extend;
    }

    public String getExtendJudgment() {
        return extendJudgment;
    }

    public OtherAttr setExtendJudgment(String extendJudgment) {
        this.extendJudgment = extendJudgment;
        return this;
    }

    public OtherAttr setExtend(String extend) {
        this.extend = extend;
        return this;
    }

    public OtherAttr setSessionid(String sessionid) {
        this.sessionid = sessionid;
        return this;
    }

    public String getImei() {
        return imei;
    }

    public OtherAttr setImei(String imei) {
        this.imei = imei;
        return this;
    }

    public String getInnerModel() {
        return innerModel;
    }

    public OtherAttr setInnerModel(String innerModel) {
        this.innerModel = innerModel;
        return this;
    }

    public String getRomver() {
        return romver;
    }

    public OtherAttr setRomver(String romver) {
        this.romver = romver;
        return this;
    }
}
