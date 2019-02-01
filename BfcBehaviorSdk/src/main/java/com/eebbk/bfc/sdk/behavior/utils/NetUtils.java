package com.eebbk.bfc.sdk.behavior.utils;

import android.content.Context;

import com.eebbk.bfc.sdk.behavior.error.ErrorCode;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;
import com.eebbk.bfc.uploadsdk.upload.net.NetworkType;

/**
 * @author hesn
 * @function
 * @date 16-8-16
 * @company 步步高教育电子有限公司
 */

public class NetUtils {

    private static final String TAG = "NetUtils";

    public static boolean isAllowUpload(Context context) {
        if (!ConfigAgent.getBehaviorConfig().reportConfig.usable) {
            LogUtils.w(TAG, "DA上报模块已关闭");
            return false;
        }

        int settingType = ConfigAgent.getBehaviorConfig().reportConfig.mNetworkTypes;
        int type = com.eebbk.bfc.common.devices.NetUtils.getNetWorkType(context);

        if(type == com.eebbk.bfc.common.devices.NetUtils.NETWORK_NO
                || type == com.eebbk.bfc.common.devices.NetUtils.NETWORK_UNKNOWN){
            LogUtils.bfcExceptionLog(TAG, ErrorCode.REPORT_NO_NET);
            return false;
        }

        if (containsNetworkType(settingType, NetworkType.NETWORK_WIFI)
                && type == com.eebbk.bfc.common.devices.NetUtils.NETWORK_WIFI) {
            LogUtils.d(TAG, "Netword is Wifi, behavior data upload.");
            return true;
        }

        if (containsNetworkType(settingType, NetworkType.NETWORK_MOBILE)
                && com.eebbk.bfc.common.devices.NetUtils.isMobileDataConnected(context)) {
            LogUtils.d(TAG, "Netword is mobile, behavior data upload.");
            return true;
        }

        LogUtils.w(TAG, "settingNetword:" + Integer.toBinaryString(settingType) + " network:" + Integer.toBinaryString(type) + ", behavior do not upload.");
        return false;
    }

    /**
     * 设置中是否可使用某网络类型
     *
     * @param curNetworkTypes 当前网络类型
     * @param networkTypes 网络类型
     * @return true支持，false不支持
     */
    public static boolean containsNetworkType(int curNetworkTypes, int networkTypes){
        return (curNetworkTypes & networkTypes) != 0;
    }

    private NetUtils() {
        //prevent the instance
    }

}
