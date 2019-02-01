package com.eebbk.bfc.sdk.behavior.control.collect.encapsulation;

import android.text.TextUtils;

import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.ApplicationAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.MachineAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.OtherAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.UserAttr;
import com.eebbk.bfc.sdk.behavior.utils.ContextUtils;
import com.eebbk.bfc.sdk.behavior.utils.DeviceUtil;
import com.eebbk.bfc.sdk.behavior.utils.MachinceUtils;
import com.eebbk.bfc.sdk.behavior.utils.SessionAgent;
import com.eebbk.bfc.sdk.behavior.utils.SystemUtils;
import com.eebbk.bfc.sdk.behavior.utils.Utils;
import com.eebbk.bfc.sdk.behavior.version.Build;

/**
 * @author hesn
 * @function
 * @date 16-8-9
 * @company 步步高教育电子有限公司
 */

public class BaseAttrManager {
    private ApplicationAttr mApplicationAttr;
    private MachineAttr mMachineAttr;
    private UserAttr mUserAttr;
    private String mModuleName;

    private static class InstanceHolder {
        private static final BaseAttrManager mInstance = new BaseAttrManager();
    }

    public static BaseAttrManager getInstance() {
        return InstanceHolder.mInstance;
    }

    public void setUserAttr(UserAttr userAttr) {
        this.mUserAttr = userAttr;
    }

    /**
     * 用户信息
     *
     * @return
     */
    public UserAttr getUserAttr() {
        return mUserAttr;
    }

    /**
     * 其他信息
     *
     * @param extend         采集扩展字段
     * @param extendJudgment 上传条件判断扩展字段
     * @return
     */
    public OtherAttr getOtherAttr(String extend, String extendJudgment) {
        OtherAttr otherAttr = new OtherAttr();
        otherAttr.setSessionid(SessionAgent.getSessionId());
        otherAttr.setDaVer(Build.VERSION.VERSION_NAME);
        otherAttr.setRouterMac(SystemUtils.getRouterMac(ContextUtils.getContext()));
        otherAttr.setExtend(extend);
        otherAttr.setExtendJudgment(extendJudgment);
        otherAttr.setImei(DeviceUtil.getIMEI(ContextUtils.getContext()));
        otherAttr.setInnerModel(DeviceUtil.getCpuId());
        otherAttr.setRomver(SystemUtils.getMac(ContextUtils.getContext()));
        return otherAttr;
    }

    public void setModuleName(String moduleName){
        mModuleName = moduleName;
        if(mApplicationAttr != null){
            mApplicationAttr.setModuleName(moduleName);
        }
    }

    /**
     * app信息
     *
     * @return
     */
    public ApplicationAttr getApplicationAttr() {
        if (mApplicationAttr == null || TextUtils.isEmpty(mApplicationAttr.getAppId())) {
            String appId = "";
            try {
                appId = Utils.getAppId(ContextUtils.getContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
            mApplicationAttr = new ApplicationAttr().setAppId(appId)
                    .setAppVersion(Utils.getVersionName(ContextUtils.getContext()))
                    .setModuleName(TextUtils.isEmpty(mModuleName) ? Utils.getModuleName(ContextUtils.getContext()) : mModuleName)
                    .setchannleId(SystemUtils.getChannleId(ContextUtils.getContext()))
                    .setPackageName(ContextUtils.getContext().getPackageName());
        }

        return mApplicationAttr;
    }

    /**
     * 机器信息
     *
     * @return
     */
    public MachineAttr getMachinceAttr() {
        if(mMachineAttr != null){
            return mMachineAttr;
        }
        mMachineAttr = MachinceUtils.makeMachinceAttrImoo(ContextUtils.getContext());
        return mMachineAttr;
    }

    private BaseAttrManager() {
        //prevent the instance
    }
}
