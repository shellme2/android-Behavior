package com.eebbk.bfc.sdk.behavior.aidl.crash.filter;

import android.content.Context;
import android.text.TextUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * @author hesn
 *         2018/3/2
 */

public class CrashFilter {

    private List<ICrashFilter> mFilters = new CopyOnWriteArrayList<>();

    public CrashFilter(){
        mFilters.add(new CrashFilterByListImpl());
        mFilters.add(new BfcCrashAutoFilterImpl());
    }

    /**
     * 检查是不是别人的锅（比如是BFC的锅）
     * @param crash
     * @return
     */
    public ModuleCrashInfo checkIsOtherModuleException(String crash){
        if(TextUtils.isEmpty(crash)){
            return new ModuleCrashInfo().setModuleCrash(false);
        }
        for (ICrashFilter crashFilter : mFilters) {
            ModuleCrashInfo moduleCrashInfo = crashFilter.filter(crash);
            if(moduleCrashInfo.isModuleCrash()){
                return moduleCrashInfo;
            }
        }

        return new ModuleCrashInfo().setModuleCrash(false);
    }

}
