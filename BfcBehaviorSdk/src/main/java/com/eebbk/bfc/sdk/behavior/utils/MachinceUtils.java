package com.eebbk.bfc.sdk.behavior.utils;

import android.content.Context;
import android.text.TextUtils;

import com.eebbk.bfc.common.devices.DeviceUtils;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.MachineAttr;

/**
 * @author hesn
 * @function
 * @date 16-8-16
 * @company 步步高教育电子有限公司
 */

public class MachinceUtils {

    /**
     * 自动采集设备基本信息(imoo 平板)
     *
     * @param context
     * @return
     */
    public static MachineAttr makeMachinceAttrImoo(Context context) {
        String serial = DeviceUtils.getMachineId(context);

        if (!TextUtils.isEmpty(serial)) {
            return getBaseMachineAttr().setmId(serial)
                    .setOsVersion(android.os.Build.VERSION.INCREMENTAL);
        }

        String iMEI = DeviceUtil.getIMEI(context);
        if (!TextUtils.isEmpty(iMEI)) {
            return getBaseMachineAttr().setmId(iMEI)
                    .setOsVersion(android.os.Build.VERSION.RELEASE);
        }

        String wifiMac = "unknow_machineid";
        String macAddr = DeviceUtils.getMac(context);
        return getBaseMachineAttr().setmId(TextUtils.isEmpty(macAddr) ? wifiMac : macAddr)
                .setOsVersion(android.os.Build.VERSION.INCREMENTAL);
    }

    /**
     * 获取不受机型影响的基础属性
     *
     * @return
     */
    private static MachineAttr getBaseMachineAttr() {
        return new MachineAttr()
                .setDevName(android.os.Build.MODEL)
                .setBrand(android.os.Build.BRAND);
    }

    private MachinceUtils() {
        //prevent the instance
    }

}
