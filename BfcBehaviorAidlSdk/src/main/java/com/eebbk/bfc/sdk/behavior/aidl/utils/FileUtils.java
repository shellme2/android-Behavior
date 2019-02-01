package com.eebbk.bfc.sdk.behavior.aidl.utils;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * 文件工具类, 提供基本的文件操作
 * Created by ZouYun on 2016/9/29.
 */
public class FileUtils {
    private static final String TAG = "BfcCommon_FileUtils";

    /******************** 存储相关常量 ********************/
    /**
     * Byte与Byte的倍数
     */
    public static final int BYTE = 1;
    /**
     * KB与Byte的倍数
     */
    public static final int KB = 1024;
    /**
     * MB与Byte的倍数
     */
    public static final int MB = 1024 * KB;
    /**
     * GB与Byte的倍数
     */
    public static final int GB = 1024 * MB;

    private FileUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    @Deprecated
    public static String getUriRealyPath(Context context, @NonNull Uri uri){
        return getUriReallyPath(context, uri);
    }

    /***
     * 从Uri中获取文件的路径
     *
     * @return 文件在本地的路径; 如果文件并不在本地,返回 null
     */
    public static String getUriReallyPath(Context context, @NonNull Uri uri) {
        if (context==null || null == uri) return null;
        if (Build.VERSION.SDK_INT > 19 && DocumentsContract.isDocumentUri(context, uri)) {
            final String authority = uri.getAuthority();
            String docId = DocumentsContract.getDocumentId(uri);

            if ("com.android.providers.media.documents".equals(authority)) {
                final String[] split = docId.split(":");
                final String type = split[0];


                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                return getDataColumn(context, contentUri, null, null);
            } else if ("com.android.providers.downloads.documents".equals(authority)) {
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.parseLong(docId));

                return getDataColumn(context, contentUri, null, null);
            } else if ("com.android.externalstorage.documents".equals(authority)) {
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                } else {
                    // TODO: 2016/12/7 获取不是主磁盘的文件夹, 还需要确定一下, 没找到明确的文档, 目前可以用
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

            }
        }

        final String scheme = uri.getScheme();
        if (scheme == null) {
            return uri.getPath();
        }
        if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            return uri.getPath();
        }

        String data = null;
        if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return data;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private static String getDataColumn(Context context, Uri uri, String selection,String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * 根据文件路径获取文件
     *
     * @param filePath 文件路径
     */
    static File getFileByPath(String filePath) {
        return StringUtils.isSpace(filePath) ? null : new File(filePath);
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public static boolean isFileExists(String filePath) {
        return isFileExists(getFileByPath(filePath));
    }

    /**
     * 判断文件是否存在
     *
     * @param file 文件
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public static boolean isFileExists(File file) {
        return file != null && file.exists();
    }

    /**
     * 判断是否是目录
     *
     * @param dirPath 目录路径
     * @return {@code true}: 文件存在,并且是文件夹<br>{@code false}: 否
     */
    public static boolean isDir(String dirPath) {
        return isDir(getFileByPath(dirPath));
    }

    /**
     * 判断是否是目录
     *
     * @param file 文件
     * @return {@code true}: 文件存在,并且是文件夹<br>{@code false}: 否
     */
    public static boolean isDir(File file) {
        return isFileExists(file) && file.isDirectory();
    }

    /**
     * 判断是否是文件
     *
     * @param filePath 文件路径
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isFile(String filePath) {
        return isFile(getFileByPath(filePath));
    }

    /**
     * 判断是否是文件
     *
     * @param file 文件
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isFile(File file) {
        return isFileExists(file) && file.isFile();
    }

    /**
     * 创建文件夹<br/>
     * <p>
     * 如果存在,则返回; 如果不存在则创建
     *
     * @param dirPath 文件夹路径
     * @return {@code true}: 存在或创建成功<br>{@code false}: 创建失败
     */
    public static boolean createDirOrExists(String dirPath) {
        return createDirOrExists(getFileByPath(dirPath));
    }

    /**
     * 创建文件夹<br/>
     * <p>
     * 如果存在,则返回; 如果不存在则创建
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 创建失败
     */
    public static boolean createDirOrExists(File file) {
        // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 创建文件
     * <p>
     * 如果文件存在,则返回; 不存在,就创建
     *
     * @param filePath 文件路径
     * @return {@code true}: 存在或创建成功<br>
     * {@code false}: 创建文件失败
     */
    public static boolean createFileOrExists(String filePath) {
        return createFileOrExists(getFileByPath(filePath));
    }

    /**
     * 创建文件
     * <p>
     * 如果文件存在,则返回; 不存在,就创建
     *
     * @param file 需要创建的文件
     * @return {@code true}: 存在或创建成功<br>
     * {@code false}: 创建文件失败
     */
    public static boolean createFileOrExists(File file) {
        if (file == null) return false;
        // 如果存在，是文件则返回true，是目录则返回false
        if (file.exists()) return file.isFile();
        if (!createDirOrExists(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 创建文件
     * <p>
     * <b>如果存在,则删除后重新创建</b>
     *
     * @param filePath 文件路径
     * @return {@code true}: 创建成功<br>{@code false}: 创建失败
     */
    public static boolean createFileByDeleteOld(String filePath) {
        return createFileByDeleteOld(getFileByPath(filePath));
    }

    /**
     * 创建文件
     * <p>
     * <b>如果存在,则删除后重新创建</b>
     *
     * @param file 文件
     * @return {@code true}: 创建成功<br>{@code false}: 创建失败
     */
    public static boolean createFileByDeleteOld(File file) {
        if (file == null) return false;
        // 文件存在并且删除失败返回false
        if (file.exists() && file.isFile() && !file.delete()) return false;
        // 创建目录失败返回false
        if (!createDirOrExists(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 暂时不开放, 设为私有
     * <p>
     * 复制或移动目录
     *
     * @param srcDir  源目录
     * @param destDir 目标目录
     * @param isMove  是否移动
     * @return {@code true}: 复制或移动成功<br>{@code false}: 复制或移动失败
     */
    private static boolean copyOrMoveDir(File srcDir, File destDir, boolean isMove) {
        if (srcDir == null || destDir == null) return false;
        // 如果目标目录在源目录中则返回false，看不懂的话好好想想递归怎么结束
        // srcPath : F:\\MyGithub\\AndroidUtilCode\\utilcode\\src\\test\\res
        // destPath: F:\\MyGithub\\AndroidUtilCode\\utilcode\\src\\test\\res1
        // 为防止以上这种情况出现出现误判，须分别在后面加个路径分隔符
        String srcPath = srcDir.getPath() + File.separator;
        String destPath = destDir.getPath() + File.separator;
        if (destPath.contains(srcPath)) return false;
        // 源文件不存在或者不是目录则返回false
        if (!srcDir.exists() || !srcDir.isDirectory()) return false;
        // 目标目录不存在返回false
        if (!createDirOrExists(destDir)) return false;
        File[] files = srcDir.listFiles();
        for (File file : files) {
            File oneDestFile = new File(destPath + file.getName());
            if (file.isFile()) {
                // 如果操作失败返回false
                if (!copyOrMoveFile(file, oneDestFile, isMove)) return false;
            } else if (file.isDirectory()) {
                // 如果操作失败返回false
                if (!copyOrMoveDir(file, oneDestFile, isMove)) return false;
            }
        }
        return !isMove || deleteDir(srcDir);
    }


    /**
     * 暂时不开放, 设为私有
     * <p>
     * 复制或移动文件
     *
     * @param srcFile  源文件
     * @param destFile 目标文件
     * @param isMove   是否移动
     * @return {@code true}: 复制或移动成功<br>{@code false}: 复制或移动失败
     */
    public static boolean copyOrMoveFile(File srcFile, File destFile, boolean isMove) {
        if (srcFile == null || destFile == null) return false;
        // 源文件不存在或者不是文件则返回false
        if (!srcFile.exists() || !srcFile.isFile()) return false;
        // 目标文件存在且是文件则返回false
        if (destFile.exists() && destFile.isFile()) return false;
        // 目标目录不存在返回false
        if (!createDirOrExists(destFile.getParentFile())) return false;
        try {
            return writeFile(destFile, new FileInputStream(srcFile), false)
                    && !(isMove && !deleteFile(srcFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除目录
     *
     * @param dir 目录
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public static boolean deleteDir(File dir) {
        if (dir == null) return false;
        // 目录不存在返回true
        if (!dir.exists()) return true;
        // 不是目录返回false
        if (!dir.isDirectory()) return false;
        // 现在文件存在且是文件夹
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                if (!deleteFile(file)) return false;
            } else if (file.isDirectory()) {
                if (!deleteDir(file)) return false;
            }
        }
        return dir.delete();
    }

    /**
     * 删除文件
     *
     * @param srcFilePath 文件路径
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public static boolean deleteFile(String srcFilePath) {
        return deleteFile(getFileByPath(srcFilePath));
    }

    /**
     * 删除文件
     *
     * @param file 文件
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public static boolean deleteFile(File file) {
        return file != null && (!file.exists() || file.isFile() && file.delete());
    }


    /**
     * 获取目录下所有文件
     *
     * @param dir         目录
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    public static List<File> listFilesInDir(File dir, boolean isRecursive) {
        if (isRecursive) return listFilesInDir(dir);
        if (dir == null || !isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        Collections.addAll(list, dir.listFiles());
        return list;
    }


    /**
     * 获取目录下所有文件包括子目录
     *
     * @param dir 目录
     * @return 文件链表
     */
    public static List<File> listFilesInDir(File dir) {
        if (dir == null || !isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        for (File file : files) {
            list.add(file);
            if (file.isDirectory()) {
                list.addAll(listFilesInDir(file));
            }
        }
        return list;
    }


    /**
     * 获取目录下所有后缀名为suffix的文件
     * <p>大小写忽略</p>
     *
     * @param dir         目录
     * @param suffix      后缀名
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    public static List<File> listFilesInDirWithFilter(File dir, String suffix, boolean isRecursive) {
        if (isRecursive) return listFilesInDirWithFilter(dir, suffix);
        if (dir == null || !isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.getName().toUpperCase().endsWith(suffix.toUpperCase())) {
                list.add(file);
            }
        }
        return list;
    }

    /**
     * 获取目录下所有后缀名为suffix的文件包括子目录
     * <p>大小写忽略</p>
     *
     * @param dir    目录
     * @param suffix 后缀名
     * @return 文件链表
     */
    public static List<File> listFilesInDirWithFilter(File dir, String suffix) {
        if (dir == null || !isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.getName().toUpperCase().endsWith(suffix.toUpperCase())) {
                list.add(file);
            }
            if (file.isDirectory()) {
                list.addAll(listFilesInDirWithFilter(file, suffix));
            }
        }
        return list;
    }


    /**
     * 获取目录下所有符合filter的文件
     *
     * @param dir         目录
     * @param filter      过滤器
     * @param isRecursive 是否递归进子目录
     * @return 文件链表
     */
    public static List<File> listFilesInDirWithFilter(File dir, FilenameFilter filter, boolean isRecursive) {
        if (isRecursive) return listFilesInDirWithFilter(dir, filter);
        if (dir == null || !isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        for (File file : files) {
            if (filter.accept(file.getParentFile(), file.getName())) {
                list.add(file);
            }
        }
        return list;
    }


    /**
     * 获取目录下所有符合filter的文件包括子目录
     *
     * @param dir    目录
     * @param filter 过滤器
     * @return 文件链表
     */
    public static List<File> listFilesInDirWithFilter(File dir, FilenameFilter filter) {
        if (dir == null || !isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        for (File file : files) {
            if (filter.accept(file.getParentFile(), file.getName())) {
                list.add(file);
            }
            if (file.isDirectory()) {
                list.addAll(listFilesInDirWithFilter(file, filter));
            }
        }
        return list;
    }


    /**
     * 像文件中写入数据;
     *
     * @param file   写入数据的文件文件; 如果文件不存在,将自动创建文件
     * @param is     被写入文件的数据流
     * @param append 是否追加在文件末尾
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    public static boolean writeFile(File file, InputStream is, boolean append) throws IOException {
        if (file == null || is == null) return false;
        if (!createFileOrExists(file)) return false;
        OutputStream os = new FileOutputStream(file, append);
        transfer(is, os);
        return true;
    }


    /**
     * 像文件中写入数据;
     *
     * @param file    写入数据的文件文件; 如果文件不存在,将自动创建文件
     * @param content 被写入文件的字符串
     * @param append  是否追加在文件末尾
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    public static boolean writeFile(File file, String content, boolean append) throws IOException {
        if (file == null || content == null) return false;
        if (!createFileOrExists(file)) return false;
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file, append);
            fileWriter.write(content);
            fileWriter.flush();
            return true;
        } finally {
            closeIO(fileWriter);
        }
    }

    /**
     * 像文件中写入数据;
     *
     * @param file   写入数据的文件文件; 如果文件不存在,将自动创建文件
     * @param data   被写入的数据
     * @param append 是否追加在文件末尾
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    public static boolean writeFile(File file, byte[] data, boolean append) throws IOException {
        if (file == null || data == null) return false;
        if (!createFileOrExists(file)) return false;
        OutputStream os = null;
        try {
            os = new FileOutputStream(file, append);
            os.write(data);
            os.flush();
            return true;
        } finally {
            closeIO(os);
        }
    }

    /**
     * 从数据流读取数据, 写入到输出流
     * <p>
     * 将循环从输入流中读取1kb数据,然后写入到输出流, 知道输入流结束;
     *
     * @param is 输入流, 将从中读取数据
     * @param os 输出留, 将把读到的数据写入该流
     */
    public static void transfer(@NonNull InputStream is, @NonNull OutputStream os) throws IOException {
        try {
            os = new BufferedOutputStream(os);
            byte data[] = new byte[KB];
            int len;
            while ((len = is.read(data, 0, KB)) != -1) {
                os.write(data, 0, len);
            }
            os.flush();
        } finally {
            closeIO(is);
            closeIO(os);
        }
    }


    /**
     * 读取文件到字符数组中
     *
     * @param filePath 文件路径
     * @return 字节数组; 如果读取过程中发生异常, 将返回null
     */
    public static byte[] readFile2Bytes(String filePath) throws FileNotFoundException {
        return readFile2Bytes(getFileByPath(filePath));
    }

    /**
     * 读取文件到字符数组中
     *
     * @param file 文件
     * @return 字节数组; 如果读取过程中发生异常, 将返回null
     * @throws FileNotFoundException 文件不存在时 抛出异常
     */
    public static byte[] readFile2Bytes(File file) throws FileNotFoundException {
        return readFile2Bytes(new FileInputStream(file));
    }

    /**
     * inputStream转byteArr
     *
     * @param is 输入流
     * @return 字节数组; 如果读取过程中发生异常, 将返回null
     */
    public static byte[] readFile2Bytes(InputStream is) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            transfer(is, os);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return os.toByteArray();
    }


    /**
     * 获取文件大小, 返回数据是byte
     * 文件不存在时, 返回的大小是 -1
     * 如果想转换成友好的字符串形式, 可调用 {@link #byte2FitSize(long)}
     *
     * @param filePath 文件路径
     * @return 文件大小 byte; 如果文件不存在, 返回 -1;
     */
    public static long getFileSize(String filePath) {
        return getFileSize(getFileByPath(filePath));
    }

    /**
     * 获取文件大小, 返回数据是byte
     * 文件不存在时, 返回的大小是 -1
     * 如果想转换成友好的字符串形式, 可调用 {@link #byte2FitSize(long)}
     *
     * @param file 文件
     * @return 文件大小, byte; 如果文件不存在, 返回 -1
     */
    public static long getFileSize(File file) {
        if (!isFileExists(file)) return -1L;
        return file.length();
    }


    /**
     * 获取文件可用大小<br/>
     * 如无特殊情况, 一般返回的都是剩余的磁盘空间
     *
     * @return 文件可用大小, 单位是byte;
     */
    public static long getFileAvaiableSize(@NonNull String filePath) {
        return getFileAvaiableSize( new File(filePath) );
    }

    /**
     * 获取文件可用大小<br/>
     * 如无特殊情况, 一般返回的都是剩余的磁盘空间
     *
     * @return 文件可用大小, 单位是byte;
     */
    public static long getFileAvaiableSize(@NonNull File file) {
        return file.getUsableSpace();
    }

    /**
     * 关闭IO
     *
     * @param closeable closeable
     */
    public static void closeIO(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
            closeable = null;
        } catch (IOException e) {
            closeable = null;
            e.printStackTrace();
        }
    }

    /**
     * 获取全路径中的最长目录
     *
     * @param file 文件
     * @return filePath最长目录
     */
    public static String getDirName(File file) {
        if (file == null) return null;
        return getDirName(file.getAbsolutePath());
    }

    /**
     * 获取全路径中的最长目录
     *
     * @param filePath 文件路径
     * @return filePath最长目录
     */
    private static String getDirName(String filePath) {
        if (StringUtils.isEmpty(filePath)) return filePath;
        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? "" : filePath.substring(0, lastSep + 1);
    }

    /**
     * 获取全路径中的文件名
     *
     * @param file 文件
     * @return 文件名
     */
    public static String getFileName(File file) {
        if (file == null) return null;
        return getFileName(file.getAbsolutePath());
    }

    /**
     * 获取全路径中的文件名
     *
     * @param filePath 文件路径
     * @return 文件名
     */
    private static String getFileName(String filePath) {
        if (StringUtils.isEmpty(filePath)) return filePath;
        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? filePath : filePath.substring(lastSep + 1);
    }

    /**
     * 获取全路径中的文件拓展名
     *
     * @param file 文件
     * @return 文件拓展名
     */
    public static String getFileExtension(File file) {
        if (file == null) return null;
        return getFileExtension(file.getAbsolutePath());
    }

    /**
     * 获取全路径中的文件拓展名
     *
     * @param filePath 文件路径
     * @return 文件拓展名
     */
    private static String getFileExtension(String filePath) {
        if (StringUtils.isEmpty(filePath)) return filePath;
        int lastPoi = filePath.lastIndexOf('.');
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastPoi == -1 || lastSep >= lastPoi) return "";
        return filePath.substring(lastPoi + 1);
    }


    /**
     * 字节数转换成合适大小的字符串, 转换成 GB, MB, KB等
     * <p>保留3位小数</p>
     *
     * @param byteNum 字节数
     * @return 1...1024 unit
     */
    public static String byte2FitSize(long byteNum) {
        if (byteNum < 0) {
            return "shouldn't be less than zero!";
        } else if (byteNum < KB) {
            return String.format(Locale.getDefault(), "%.3fB", (double) byteNum);
        } else if (byteNum < MB) {
            return String.format(Locale.getDefault(), "%.3fKB", (double) byteNum / KB);
        } else if (byteNum < GB) {
            return String.format(Locale.getDefault(), "%.3fMB", (double) byteNum / MB);
        } else {
            return String.format(Locale.getDefault(), "%.3fGB", (double) byteNum / GB);
        }
    }

    /**
     * 通知媒体库更新
     */
    public static void scanFile(Context context, String filePath) {
        if(context == null){
            LogUtils.w(TAG,"media scanner scan file fail, context == null");
            return;
        }
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(Uri.fromFile(new File(filePath)));
        context.sendBroadcast(scanIntent);
    }

}