package com.eebbk.bfc.sdk.behavior.aidl.crash;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;

import com.eebbk.bfc.sdk.behavior.aidl.utils.FileUtils;
import com.eebbk.bfc.sdk.behavior.aidl.utils.LogUtils;
import com.eebbk.bfc.sdk.behavior.aidl.utils.StorageUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

/**
 * Some store related small tools.
 *
 * @author humingming <hmm@dw.gdbbk.com>
 */
class StoreUtils {

    private static final String TAG = "StoreUtils";
    private static final int GB = 1024 * 1024 * 1024;
    private static final long MB = 1024 * 1024L;
    private static final int KB = 1024;

    private StoreUtils() {
        //工具类的空构造
    }

    /**
     * Get external store total size.
     *
     * @return long
     */
    public static long getExternalStoreTotalSize() {
//		if (externalStoreAvailable()) {
//			File storePath = Environment.getExternalStorageDirectory();
//			StatFs sf = new StatFs(storePath.getPath());
//			long blockSize = sf.getBlockSize();
//			long totalBlocks = sf.getBlockCount();
//			return totalBlocks * blockSize;
//		} else {
//			return 0;
//		}
        return StorageUtils.getExternalStorageTotalSize();
    }

    /**
     * Get external store available size.
     *
     * @return long
     */
    public static long getExternalStoreAvailableSize() {
//		if (externalStoreAvailable()) {
//			File storePath = Environment.getExternalStorageDirectory();
//			StatFs sf = new StatFs(storePath.getPath());
//			long blockSize = sf.getBlockSize();
//			long availableBlocks = sf.getAvailableBlocks();
//			return availableBlocks * blockSize;
//		} else {
//			return 0;
//		}
        return StorageUtils.getDataAvailableSize();
    }

    /**
     * Get internal store total size.
     *
     * @return long
     */
    public static long getInternalStoreTotalSize() {
        File path = Environment.getRootDirectory();
        StatFs sf = new StatFs(path.getPath());
        long blockSize = sf.getBlockSize();
        long totalBlocks = sf.getBlockCount();
        return totalBlocks * blockSize;
    }

    /**
     * Get internal store available size.
     *
     * @return long
     */
    public static long getInternalStoreAvailableSize() {
        File storePath = Environment.getRootDirectory();
        StatFs sf = new StatFs(storePath.getPath());
        long blockSize = sf.getBlockSize();
        long availableBlocks = sf.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * Get give path file total size.
     * If path is a directory, it will compute total file size.
     *
     * @param path Get size path.
     * @return Path total size.
     */
    public static long getFileSize(String path) {
        if (null == path) {
            return 0;
        }

        File file = new File(path);
        File[] files;

        if (file.isDirectory()) {
            files = file.listFiles();
            if (files == null) {
                return 0;
            }
        } else {
            return file.length();
        }

        long size = 0;
        for (File file1 : files) {
            if (file1.isDirectory()) {
                size += getFileSize(file1.getAbsolutePath());
            } else {
                size += file1.length();
            }
        }

        return size;
    }

    /**
     * Delete folder. If give path is a file, it will delete
     * a single file, if is a directory it will delete all file and
     * child directory in this directory.
     *
     * @param path Folder path.
     * @return True delete success, otherwise false.
     */
    public static boolean deleteFolder(String path) {
        if (null == path) {
            return false;
        }

        File file = new File(path);
        if (!file.exists()) {
            return false;
        }

        if (file.isFile()) {
            return deleteFile(path);
        } else {
            return deleteDirectory(path);
        }
    }

    /**
     * Check give parent directory of file path whether existed.
     * If not existed will try to create it.
     *
     * @param fileName Check file path name.
     * @return True means parent directory existed or create success, other false.
     */
    public static boolean checkFileDirExisted(String fileName) {
        String dir = getParentDir(fileName);
        if (null == dir) {
            return false;
        }

        File fDir = new File(dir);
        try {
            if (!fDir.exists() && !fDir.mkdirs()) {
                LogUtils.d(TAG, "create folder " + dir + " failed");
            }
            return true;
        } catch (SecurityException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean compare2Sec(long time1, long time2, int dur) {
        Log.v(TAG, (Math.abs(time1 - time2) / 1000) + "");
        return (Math.abs(time1 - time2) / 1000) > dur;
    }

    /**
     * Convert app size unit from byte to "xx MB" or "xx GB" string.
     *
     * @param size Size in byte.
     * @return Formated string.
     */
    public static String convertSizeUnit(long size) {
        String unit;
        if (size >= GB) {
            unit = String.format(Locale.CHINA, "%.02f GB", (float) size / (float) GB);
        } else if (size >= MB && size < GB) {
            unit = String.format(Locale.CHINA, "%.02f MB", (float) size / (float) MB);
        } else {
            unit = String.format(Locale.CHINA, "%.02f KB", (float) size / (float) KB);
        }

        return unit;
    }

    /**
     * gaowenzhao add 2016.02.20
     * 可用内存占百分比
     *
     * @param availableSize
     * @param totalSize
     * @return
     */
    public static String availablepercent(long availableSize, long totalSize) {
        String availablepercent;
        if (totalSize > 0) {
            long percent = availableSize * 100 / totalSize;
            availablepercent = percent + "%";
        } else {
            return null;
        }
        return availablepercent;
    }

    /**
     * gaowenzhao add 2016.02.20
     * 获取系统总内存
     *
     * @return 总内存大单位为B。
     */
    public static long getMemoryTotalSize() {
        String dir = "/proc/meminfo";
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(dir);
            br = new BufferedReader(fr, 2048);
            String memoryLine = br.readLine();
            if (!TextUtils.isEmpty(memoryLine)) {
                String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
                return Integer.parseInt(subMemoryLine.replaceAll("\\D+", "")) * 1024L;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            FileUtils.closeIO(br);
            FileUtils.closeIO(fr);
        }
        return 0;
    }

    /**
     * gaowenzhao add 2016.02.20
     * 获取当前可用内存，返回数据以字节为单位。
     *
     * @param context 可传入应用程序上下文。
     * @return 当前可用内存单位为B。
     */
    public static long getMemoryAvailable(Context context) {
        ActivityManager am = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }

    /**
     * Judge whether external store available.
     *
     * @return boolean True means available, false invalid.
     */
    private static boolean externalStoreAvailable() {
        String status = Environment.getExternalStorageState();
        return !(status == null || !status.equals(Environment.MEDIA_MOUNTED));
    }

    /**
     * Delete a single file.
     *
     * @param path File path(Absolute).
     * @return True delete success, otherwise false.
     */
    private static boolean deleteFile(String path) {
        return FileUtils.deleteFile(path);
    }

    /**
     * Delete a directory.
     *
     * @param path Directory path.
     * @return True delete success, otherwise false.
     */
    private static boolean deleteDirectory(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }

        File dirFile = new File(path);
        return FileUtils.deleteDir(dirFile);
    }

    /**
     * Get give path parent directory.
     *
     * @param path Path string.
     * @return Parent path or null.
     */
    private static String getParentDir(String path) {
        if (null == path) {
            return null;
        }

        try {
            int last = path.lastIndexOf("/");
            if (last <= -1) {
                return null;
            }

            return path.substring(0, last);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}