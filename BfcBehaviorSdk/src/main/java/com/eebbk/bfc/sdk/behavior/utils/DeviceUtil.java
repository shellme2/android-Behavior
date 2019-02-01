package com.eebbk.bfc.sdk.behavior.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.eebbk.bfc.common.devices.DeviceUtils;
import com.eebbk.bfc.common.file.FileUtils;
import com.eebbk.bfc.common.tools.StringUtils;
import com.eebbk.bfc.sdk.behavior.error.ErrorCode;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

/**
 * 文  件：DeviceUtil.java
 * 公  司：步步高教育电子
 * 日  期：2016/8/26  9:30
 * 作  者：HeChangPeng
 */

public class DeviceUtil {

    private static final String TAG = "DeviceUtil";
    /**
     * cpuid保存路径
     * mtk保存在rid，RK是ic_id
     */
    private static final String[] CPU_ID_PATH = new String[]{"/proc/rid", "/proc/ic_id"};
    private static String IMEI;
    private static String CPU_ID;

    public static String getAppKeyFromMetaData(Context context, String key) {
        ApplicationInfo appInfo;
        String appKey = null;
        try {
            appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            appKey = appInfo.metaData.getString(key);
            LogUtils.d("meta-data appkay:" + appKey);
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.e(e);
        }
        return appKey;
    }

    /**
     * 获取IMEI
     * <p>
     * <p> 需要权限 android.permission.READ_PHONE_STATE </p>
     */
    public static String getIMEI(Context context) {
        if(!TextUtils.isEmpty(IMEI)){
            return IMEI;
        }
        try {
            IMEI = DeviceUtils.getIMEI(context);
            return IMEI;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.bfcExceptionLog(TAG, ErrorCode.NO_READ_PHONE_STATE_PERMISSION);
        }
        return null;
    }

    public static String getCpuId(){
        if(!TextUtils.isEmpty(CPU_ID)){
            return CPU_ID;
        }
        try {
            for (String path : CPU_ID_PATH) {
                if(FileUtils.isFileExists(path)){
                    CPU_ID = StringUtils.bytes2HexString(FileUtils.readFile2Bytes(path));
                    break;
                }
            }
            Log.d(TAG, "cpuId = " + CPU_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CPU_ID;
    }

    private DeviceUtil() {
        //prevent the instance
    }
}
