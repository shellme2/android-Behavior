package com.eebbk.bfc.sdk.behavior.aidl.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;

/**
 * @author hesn
 * @function
 * @date 17-5-10
 * @company 步步高教育电子有限公司
 */

public class AppUtils {

    /**
     * 获取App名称
     *
     * @param context 上下文
     * @return App名称
     */
    public static String getAppName(@NonNull Context context) {
        return getAppName(context, context.getPackageName());
    }

    /**
     * 获取指定app的名称
     *
     * @param context     上下文
     * @param packageName 包名
     * @return App名称, 如果不存在, 返回 null
     */
    public static String getAppName(Context context, @NonNull String packageName) {
        try {
            PackageManager pm = context.getApplicationContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    /**
     * 获取App的 VersionName
     *
     * @param context 上下文
     * @return App版本名
     */
    public static String getVersionName(Context context) {
        return getVersionName(context, context.getPackageName());
    }

    /**
     * 获取指定App的 VersionName
     *
     * @param context     上下文
     * @param packageName 包名
     * @return App版本名, 如果指定包名的app不存在,返回{@code null}
     */
    @Nullable
    public static String getVersionName(Context context, @NonNull String packageName) {
        try {
            PackageManager pm = context.getApplicationContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    /**
     * 获取App版本号
     *
     * @param context 上下文
     * @return App版本码
     */
    public static int getVersionCode(Context context) {
        return getVersionCode(context, context.getPackageName());
    }

    /**
     * 获取App版本码
     *
     * @param context     上下文
     * @param packageName 包名
     * @return App版本码; 如果指定包名的app不存在,返回 -1
     */
    public static int getVersionCode(Context context, String packageName) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return -1;
        }
    }

    /**
     * 判断App是否是系统应用
     *
     * @param context     上下文
     * @param packageName 包名
     * @return {@code true}: 是<br>{@code false}: 不是系统app或者没安装改app, 返回false
     */
    public static boolean isSystemApp(@NonNull Context context, @NonNull String packageName) {
        try {
            PackageManager pm = context.getApplicationContext().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static String getModuleName(Context context) {
        String moduleName = "";
        try {
            PackageManager e = context.getPackageManager();
            PackageInfo packageInfo = e.getPackageInfo(context.getPackageName(), 0);
            moduleName = packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
            if (TextUtils.isEmpty(moduleName)) {
                return "";
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return moduleName;
    }

    public static String getAppId(Context context) {
        try {
            return (new MD5Coder(16)).encode(context.getPackageName().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
