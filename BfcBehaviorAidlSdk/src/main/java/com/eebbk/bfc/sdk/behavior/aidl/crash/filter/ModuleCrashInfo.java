package com.eebbk.bfc.sdk.behavior.aidl.crash.filter;

/**
 * @author hesn
 *         2018/3/8
 */

public class ModuleCrashInfo {
    // crash完整信息
    private String crash;

    private boolean isModuleCrash;

    private String modulePackageName;

    private String moduleName;

    private String versionName;

    /**
     * 异常信息函数堆栈中出现的第一行eebbk包名的堆栈信息
     */
    private String firstEebbkStackLine;

    public ModuleCrashInfo(){}

    public ModuleCrashInfo(String moduleName, String modulePackageName){
        this.modulePackageName = modulePackageName;
        this.moduleName = moduleName;
    }

    public String getCrash() {
        return crash;
    }

    public ModuleCrashInfo setCrash(String mCrash) {
        this.crash = mCrash;
        return this;
    }

    public boolean isModuleCrash() {
        return isModuleCrash;
    }

    public ModuleCrashInfo setModuleCrash(boolean moduleCrash) {
        isModuleCrash = moduleCrash;
        return this;
    }

    public String getModulePackageName() {
        return modulePackageName;
    }

    public ModuleCrashInfo setModulePackageName(String modulePackageName) {
        this.modulePackageName = modulePackageName;
        return this;
    }

    public String getModuleName() {
        return moduleName;
    }

    public ModuleCrashInfo setModuleName(String moduleName) {
        this.moduleName = moduleName;
        return this;
    }

    public String getVersionName() {
        return versionName;
    }

    public ModuleCrashInfo setVersionName(String versionName) {
        this.versionName = versionName;
        return this;
    }

    public String getFirstEebbkStackLine() {
        return firstEebbkStackLine;
    }

    public ModuleCrashInfo setFirstEebbkStackLine(String firstEebbkStackLine) {
        this.firstEebbkStackLine = firstEebbkStackLine;
        return this;
    }

    @Override
    public String toString() {
        return "ModuleCrashInfo{" +
                "\ncrash='" + crash + '\'' +
                "\nisModuleCrash=" + isModuleCrash +
                "\nmodulePackageName='" + modulePackageName + '\'' +
                "\nmoduleName='" + moduleName + '\'' +
                "\nversionName='" + versionName + '\'' +
                "\nfirstEebbkStackLine='" + firstEebbkStackLine + '\'' +
                '}';
    }
}
