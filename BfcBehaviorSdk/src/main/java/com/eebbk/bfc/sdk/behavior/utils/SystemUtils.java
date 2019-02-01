package com.eebbk.bfc.sdk.behavior.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.eebbk.bfc.common.devices.DeviceUtils;
import com.eebbk.bfc.sdk.behavior.report.common.constants.ConstData;

import java.util.UUID;

/**
 * @author hesn
 * @function
 * @date 16-8-9
 * @company 步步高教育电子有限公司
 */

public class SystemUtils {


    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取连接路由的MAC地址
     * @param context
     * @return
     */
    public static String getRouterMac(Context context) {
        String netMac = "";
        try {
            if(context != null){
                WifiManager mWifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                if (mWifi!=null&&mWifi.isWifiEnabled()) {
                    WifiInfo wifiInfo = mWifi.getConnectionInfo();
                    netMac = wifiInfo.getBSSID(); // 获取被连接网络的mac地址
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return netMac;
    }

    /**
     * 获取机器的MAC地址
     * @param context
     * @return
     */
    public static String getMac(Context context) {
        try {
            return DeviceUtils.getMac(context);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取推广的渠道
     * @return
     */
    public static String getChannleId(Context context){
        String channleId="";
        ApplicationInfo appInfo;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            if(appInfo.metaData != null){
                channleId = appInfo.metaData.getString("ChannleId");
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channleId;
    }

    public static String getHostAppId(Context appContext) throws IllegalArgumentException {
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = appContext.getPackageManager().getApplicationInfo(appContext.getPackageName(), PackageManager.GET_META_DATA);
            if(applicationInfo == null){
                throw new IllegalArgumentException(" get application info = null, has no meta data! ");
            }
            return applicationInfo.metaData.getString(ConstData.BFC_DOWNLOAD_HOST_APP_ID);
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalArgumentException(" get application info error! ", e);
        }
    }

    private SystemUtils(){
        //prevent the instance
    }
}
