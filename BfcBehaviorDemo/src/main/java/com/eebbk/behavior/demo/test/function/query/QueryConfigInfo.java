package com.eebbk.behavior.demo.test.function.query;

import com.eebbk.bfc.sdk.behavior.BehaviorConfig;

/**
 * @author hesn
 * 2018/7/11
 */
public class QueryConfigInfo{

    private BehaviorConfig behaviorConfig;
    private String packageName;
    private String appName;
    private int versionCode;
    private boolean init;
    private String json;
    private int errorCode;

    public BehaviorConfig getBehaviorConfig() {
        return behaviorConfig;
    }

    public QueryConfigInfo setBehaviorConfig(BehaviorConfig behaviorConfig) {
        this.behaviorConfig = behaviorConfig;
        return this;
    }

    public String getPackageName() {
        return packageName;
    }

    public QueryConfigInfo setPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public String getAppName() {
        return appName;
    }

    public QueryConfigInfo setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public QueryConfigInfo setVersionCode(int versionCode) {
        this.versionCode = versionCode;
        return this;
    }

    public boolean isInit() {
        return init;
    }

    public QueryConfigInfo setInit(boolean init) {
        this.init = init;
        return this;
    }

    public String getJson() {
        return json;
    }

    public QueryConfigInfo setJson(String json) {
        this.json = json;
        return this;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public QueryConfigInfo setErrorCode(int errorCode) {
        this.errorCode = errorCode;
        return this;
    }
}
