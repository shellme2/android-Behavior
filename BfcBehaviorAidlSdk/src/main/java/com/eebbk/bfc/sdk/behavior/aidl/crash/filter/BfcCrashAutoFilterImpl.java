package com.eebbk.bfc.sdk.behavior.aidl.crash.filter;

import android.text.TextUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 当从CrashFilterByListImpl的固定列表中查不到时（固定列表更新不及时），
 * BfcCrashAutoFilterImpl通过是否包含’.bfc.’包名来检查
 */

public class BfcCrashAutoFilterImpl extends ABaseCrashFilter {

    private List<String> bfcStrKeyList = new CopyOnWriteArrayList<>(Arrays.asList(new String[]{
            ".bfc."
    }));

    @Override
    protected ModuleCrashInfo checkIsOtherModuleCrash(ModuleCrashInfo moduleInfo) {
        for (String bfcStrKey : bfcStrKeyList) {
            if(moduleInfo.getFirstEebbkStackLine().contains(bfcStrKey)){
                return moduleInfo.setModuleCrash(true);
            }
        }
        return moduleInfo;
    }

    @Override
    protected ModuleCrashInfo setModuleInfo(ModuleCrashInfo moduleCrashInfo) {
        moduleCrashInfo.setModulePackageName(findBfcSdkPackage(moduleCrashInfo));
        if(TextUtils.isEmpty(moduleCrashInfo.getModulePackageName())){
            return moduleCrashInfo.setModuleCrash(false);
        }
        Object libName = getBuildConfigValue(moduleCrashInfo.getModulePackageName(), BUILD_CONFIG_LIBRARY_NAME);
        if(libName == null){
            return moduleCrashInfo.setModuleCrash(false);
        }
        return moduleCrashInfo.setModuleName(String.valueOf(libName))
                .setVersionName(String.valueOf(getBuildConfigValue(moduleCrashInfo.getModulePackageName(), BUILD_CONFIG_VERSION_NAME)));
    }

    private String findBfcSdkPackage(ModuleCrashInfo moduleCrashInfo){
        String[] packageCells = moduleCrashInfo.getFirstEebbkStackLine().split(".");
        String packageName = null;
        for (String packageCell : packageCells) {
            if(TextUtils.isEmpty(packageName)){
                packageName = packageCell;
                continue;
            }
            packageName += "." + packageCell;
            Object applicationId = getBuildConfigValue(packageName, BUILD_CONFIG_APPLICATION_ID);
            if(applicationId == null){
                continue;
            }
            return packageName;
        }
        return null;
    }

}
