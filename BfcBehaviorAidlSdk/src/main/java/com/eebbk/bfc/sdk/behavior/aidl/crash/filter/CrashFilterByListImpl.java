package com.eebbk.bfc.sdk.behavior.aidl.crash.filter;

import android.text.TextUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 当从固定列表中查是否为其他模块异常
 *
 */

public class CrashFilterByListImpl extends ABaseCrashFilter {

    private List<ModuleCrashInfo> mModuleCrashList = new CopyOnWriteArrayList<>(Arrays.asList(new ModuleCrashInfo[]{
            new ModuleCrashInfo("bfc-account-aidl","com.eebbk.bfc.sdk.account.aidl"),
            new ModuleCrashInfo("bfc-accountsdk","com.eebbk.account.sdk"),
            new ModuleCrashInfo("bfc-behavior-aidl","com.eebbk.bfc.sdk.behavior.aidl"),
            new ModuleCrashInfo("bfc-behavior","com.eebbk.bfc.sdk.behavior"),
            new ModuleCrashInfo("bfc-blockcanary","com.github.moduth.blockcanary"),
            new ModuleCrashInfo("bfc-blockcanary-no-op","com.github.moduth.blockcanary"),
//            new ModuleCrashInfo("bfc-book-shelf-ui","com.eebbk.bookshelf"),
            new ModuleCrashInfo("bfc-camera","com.eebbk.camera.device"),
            new ModuleCrashInfo("bfc-common","com.eebbk.bfc.common"),
            new ModuleCrashInfo("bfc-common-res","com.eebbk.bfc.commonres"),
            new ModuleCrashInfo("bfc-common-res-pad","com.eebbk.bfc_common_res"),
            new ModuleCrashInfo("bfc-component-ui","com.eebbk.bfc.component.library"),
            new ModuleCrashInfo("bfc-content-view-ui","com.eebbk.contentview"),
            new ModuleCrashInfo("bfc-crypto","eebbk.com.crypto"),
            new ModuleCrashInfo("bfc-db","com.eebbk.bfc.sdk.db"),
            new ModuleCrashInfo("bfc-db-generator","com.eebbk.bfc.db.generator"),
            new ModuleCrashInfo("bfc-download","com.eebbk.bfc.sdk.downloadmanager"),
            new ModuleCrashInfo("bfc-feedback","com.eebbk.bfc.feedback"),
            new ModuleCrashInfo("bfc-feedback-pad-ui","com.eebbk.feedback"),
            new ModuleCrashInfo("bfc-feedback-phone-ui","com.eebbk.feedback"),
            new ModuleCrashInfo("bfc-http","com.eebbk.bfc.http"),
            new ModuleCrashInfo("bfc-imageloader","com.eebbk.bfc.imageloader"),
            new ModuleCrashInfo("bfc-leakcanary-no-op","com.squareup.leakcanary.android.noop"),
            new ModuleCrashInfo("bfc-leakcanary","com.squareup.leakcanary"),
            new ModuleCrashInfo("bfc-log","com.eebbk.bfc.bfclog"),
            new ModuleCrashInfo("bfc-mic-support","com.eebbk.bfc.micsupport"),
            new ModuleCrashInfo("bfc-net-state-ui","com.eebbk.netstateuisdk"),
            new ModuleCrashInfo("bfc-network-data-ui","com.eebbk.bfcnetworkdatasdk"),
            new ModuleCrashInfo("bfc-password-ui","com.eebbk.passworduisdk"),
            new ModuleCrashInfo("bfc-permission","com.eebbk.bfc.bfcpermission"),
            new ModuleCrashInfo("bfc-push","com.eebbk.bfc.im"),
            new ModuleCrashInfo("bfc-push-common","com.eebbk.bfc.im"),
            new ModuleCrashInfo("bfc-ringtone-ui","com.eebbk.ringtoneuisdk"),
            new ModuleCrashInfo("bfc-sequencetools-json","com.eebbk.bfc.sequence"),
            new ModuleCrashInfo("bfc-uploadsdk","com.eebbk.bfc.uploadsdk"),
            new ModuleCrashInfo("bfc-version","com.eebbk.bfc.sdk.version"),
            new ModuleCrashInfo("bfc-version-core","com.eebbk.bfc.core.sdk.version"),
            new ModuleCrashInfo("bfc-version-update-ui","com.eebbk.tool.versionupdate"),
            new ModuleCrashInfo("bfc-custom-widget-ui","com.eebbk.common.widget"),
            new ModuleCrashInfo("bfc-ijkplayer","com.eebbk.ijkvideoplayer"),
            new ModuleCrashInfo("bfc-ijkplayer-java","tv.danmaku.ijk.media.player")
    }));

    @Override
    protected ModuleCrashInfo checkIsOtherModuleCrash(ModuleCrashInfo moduleInfo){
        for (ModuleCrashInfo moduleCrashInfo : mModuleCrashList) {
            if(moduleInfo.getFirstEebbkStackLine().startsWith(moduleCrashInfo.getModulePackageName())){
                return moduleInfo.setModulePackageName(moduleCrashInfo.getModulePackageName())
                        .setModuleName(moduleCrashInfo.getModuleName())
                        .setModuleCrash(true);
            }
        }
        return moduleInfo;
    }

    @Override
    protected ModuleCrashInfo setModuleInfo(ModuleCrashInfo moduleInfo){
        if(TextUtils.isEmpty(moduleInfo.getModulePackageName())){
            return moduleInfo.setModuleCrash(false);
        }
        return moduleInfo.setVersionName(String.valueOf(getBuildConfigValue(moduleInfo.getModulePackageName(), BUILD_CONFIG_VERSION_NAME)));
    }

}
