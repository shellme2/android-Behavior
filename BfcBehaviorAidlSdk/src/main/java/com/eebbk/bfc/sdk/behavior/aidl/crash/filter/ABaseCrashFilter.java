package com.eebbk.bfc.sdk.behavior.aidl.crash.filter;

import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author hesn
 *         2018/3/8
 */

public abstract class ABaseCrashFilter implements ICrashFilter {

    private List<String> eebbkPrefixList = new ArrayList<>(Arrays.asList(new String[]{
            "com.eebbk.", "com.bbk.", "com.xtc.","tv.danmaku.ijk.media.player"
    }));

    static final String BUILD_CONFIG_LIBRARY_NAME = "LIBRARY_NAME";
    static final String BUILD_CONFIG_VERSION_NAME = "VERSION_NAME";
    static final String BUILD_CONFIG_APPLICATION_ID = "APPLICATION_ID";

    private ModuleCrashInfo mModuleCrashInfo;

    /**
     * 检查是否为第三方模块的异常
     *
     * @param moduleInfo
     * @return
     */
    protected abstract ModuleCrashInfo checkIsOtherModuleCrash(ModuleCrashInfo moduleInfo);

    /**
     * 设置异常模块的信息
     *
     * @param moduleCrashInfo
     * @return
     */
    protected abstract ModuleCrashInfo setModuleInfo(ModuleCrashInfo moduleCrashInfo);

    @Override
    public ModuleCrashInfo filter(String crash) {
        mModuleCrashInfo = new ModuleCrashInfo().setCrash(crash).setModuleCrash(false);
        if (TextUtils.isEmpty(crash)) {
            return mModuleCrashInfo;
        }
        // 1.查找第一行报的步步高包名的异常函数全路径
        mModuleCrashInfo.setFirstEebbkStackLine(getFirstEebbkStackLine());
        if (TextUtils.isEmpty(mModuleCrashInfo.getFirstEebbkStackLine())) {
            return mModuleCrashInfo;
        }
        // 2.这行报的是不是第三方模块的异常（如bfc）
        setModuleCrashInfo(checkIsOtherModuleCrash(mModuleCrashInfo));
        // 3.收集此异常的第三方模块的基本信息，准备上报
        if (mModuleCrashInfo.isModuleCrash()) {
            setModuleCrashInfo(setModuleInfo(mModuleCrashInfo));
        }
        return mModuleCrashInfo;
    }

    Object getBuildConfigValue(String packName, String fieldName) {
        try {
            Class<?> clazz = Class.forName(packName + ".BuildConfig");
            Field field = clazz.getField(fieldName);
            return field.get(null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setModuleCrashInfo(ModuleCrashInfo srcInfo) {
        mModuleCrashInfo = srcInfo == null ?
                new ModuleCrashInfo().setCrash(mModuleCrashInfo.getCrash())
                        .setFirstEebbkStackLine(mModuleCrashInfo.getFirstEebbkStackLine()) : srcInfo;
    }

    private String getFirstEebbkStackLine() {
        String[] stacks = mModuleCrashInfo.getCrash().split("at ");
        for (String stack : stacks) {
            // 找第一个“at ”是我们自己软件的函数堆栈，系统的就跳过
            for (String eebbkPrefix : eebbkPrefixList) {
                if (!TextUtils.isEmpty(stack) && stack.startsWith(eebbkPrefix)) {
                    return stack;
                }
            }
        }
        return null;
    }

}
