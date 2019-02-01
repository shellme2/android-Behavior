package com.eebbk.bfc.sdk.behavior.aidl.utils;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by Simon on 2016/9/29.
 */
public class StorageUtils {
    private static final String TAG = "BfcCommon_StorageUtils";

    private StorageUtils() {

    }

    /**
     * 外置盘是否可移除, 现在的手机, 一般都不可移除;
     * 如果可移除, 外置盘即为sd卡, 如果不可移除, 内置盘则为手机自身的 eMMC
     *
     * @return true 则说明存在可移动的外置盘; 反之false
     */
    public static boolean isExternalStorageRemoveable() {
        return !Environment.isExternalStorageEmulated() && Environment.isExternalStorageRemovable();
    }

    /**
     * 外置盘是否可用
     */
    public static boolean isExternalStroageAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static File getExternalStorageFile() {
        return Environment.getExternalStorageDirectory();
    }

    /**
     * 获取外置盘的空间总大小, <b> 单位为 byte <b/>
     * <P>转换为Mb 需要除于 (1024*1024) </P>
     *
     * @return 返回总大小, 单位为 byte; 如果没有外置盘,或者外置盘没有挂载, 返回 -1
     */
    public static long getExternalStorageTotalSize() {
        if (isExternalStroageAvailable()) {
            File externalStorageFile = Environment.getExternalStorageDirectory();
            StatFs statFs = new StatFs(externalStorageFile.getPath());
            long blockSize = statFs.getBlockSize();
            long blockCount = statFs.getBlockCount();
            return blockCount * blockSize;
        }
        return -1;
    }

    /**
     * 获取外置盘的<b>可用空间</b>大小, <b> 单位为 byte <b/>
     * <P>转换为Mb 需要除于 (1024*1024) </P>
     *
     * @return 返回总大小, 单位为 byte; 如果没有外置盘,或者外置盘没有挂载, 返回 -1
     */
    public static double getExternalStorageAvailableSize() {
        if (isExternalStroageAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            return FileUtils.getFileAvaiableSize(path);
        }
        return -1;
    }

    public static File getDataFile() {
        return Environment.getDataDirectory();
    }

    /**
     * 获取手机内部剩余存储空间, 即 /data 目录的可用空间, 单位是 byte
     */
    public static long getDataAvailableSize() {
        File path = Environment.getDataDirectory();
        return FileUtils.getFileAvaiableSize(path);
    }


//    ###########################  SD卡相关  ################################################################

    /***
     * 获取sd卡路径;
     * 目前, sd卡的还未测试, 暂时请不要使用
     */
    public static String getSdCardPath() {
        final String rawExternalStorage = System.getenv("EXTERNAL_STORAGE");
        return rawExternalStorage;
    }

    private static final Pattern DIR_SEPORATOR = Pattern.compile("/");

    /**
     * Raturns all available SD-Cards in the system (include emulated)
     * <p>
     * Warning: Hack! Based on Android source code of version 4.3 (API 18)
     * Because there is no standart way to get it.
     * TODO: Test on future Android versions 4.4+
     *
     * @return paths to all available SD-Cards in the system (include emulated)
     */
    private static String[] getStorageDirectories() {
        // Final set of paths
        final Set<String> rv = new HashSet<String>();
        // Primary physical SD-CARD (not emulated)
        final String rawExternalStorage = System.getenv("EXTERNAL_STORAGE");
        // All Secondary SD-CARDs (all exclude primary) separated by ":"
        final String rawSecondaryStoragesStr = System.getenv("SECONDARY_STORAGE");
        // Primary emulated SD-CARD
        final String rawEmulatedStorageTarget = System.getenv("EMULATED_STORAGE_TARGET");
        if (TextUtils.isEmpty(rawEmulatedStorageTarget)) {
            // Device has physical external storage; use plain paths.
            if (TextUtils.isEmpty(rawExternalStorage)) {
                // EXTERNAL_STORAGE undefined; falling back to default.
                rv.add("/storage/sdcard0");
            } else {
                rv.add(rawExternalStorage);
            }
        } else {
            // Device has emulated storage; external storage paths should have
            // userId burned into them.
            final String rawUserId;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                rawUserId = "";
            } else {
                final String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                final String[] folders = DIR_SEPORATOR.split(path);
                final String lastFolder = folders[folders.length - 1];
                boolean isDigit = false;
                try {
                    Integer.valueOf(lastFolder);
                    isDigit = true;
                } catch (NumberFormatException ignored) {
                }
                rawUserId = isDigit ? lastFolder : "";
            }
            // /storage/emulated/0[1,2,...]
            if (TextUtils.isEmpty(rawUserId)) {
                rv.add(rawEmulatedStorageTarget);
            } else {
                rv.add(rawEmulatedStorageTarget + File.separator + rawUserId);
            }
        }
        // Add all secondary storages
        if (!TextUtils.isEmpty(rawSecondaryStoragesStr)) {
            // All Secondary SD-CARDs splited into array
            final String[] rawSecondaryStorages = rawSecondaryStoragesStr.split(File.pathSeparator);
            Collections.addAll(rv, rawSecondaryStorages);
        }


        return rv.toArray(new String[rv.size()]);
    }

}
