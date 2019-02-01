package com.eebbk.bfc.sdk.behavior.utils;

import android.text.TextUtils;

import com.eebbk.bfc.common.file.FileUtils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author hesn
 * @function
 * @date 16-11-7
 * @company 步步高教育电子有限公司
 */

public class ZIP4J {

    public ZIP4J() {
    }

    public static File[] unzip(String charset, String zip, String dest, String passwd) throws ZipException {
        File zipFile = new File(zip);
        return unzip(charset, zipFile, dest, passwd);
    }

    public static File[] unzip(String charset, String zip, String passwd) throws ZipException {
        File zipFile = new File(zip);
        File parentDir = zipFile.getParentFile();
        return unzip(charset, zipFile, parentDir.getAbsolutePath(), passwd);
    }

    public static File[] unzip(String charset, File zipFile, String dest, String passwd) throws ZipException {
        ZipFile zFile = new ZipFile(zipFile);
        zFile.setFileNameCharset(charset);
        if(!zFile.isValidZipFile()) {
            throw new ZipException("压缩文件不合法,可能被损坏.");
        } else {
            File destDir = new File(dest);
            FileUtils.createDirOrExists(destDir);

            if(zFile.isEncrypted()) {
                zFile.setPassword(passwd.toCharArray());
            }

            zFile.extractAll(dest);
            List headerList = zFile.getFileHeaders();
            ArrayList extractedFileList = new ArrayList();
            Iterator extractedFiles = headerList.iterator();

            while(extractedFiles.hasNext()) {
                FileHeader fileHeader = (FileHeader)extractedFiles.next();
                if(!fileHeader.isDirectory()) {
                    extractedFileList.add(new File(destDir, fileHeader.getFileName()));
                }
            }

            File[] extractedFiles1 = new File[extractedFileList.size()];
            extractedFileList.toArray(extractedFiles1);
            return extractedFiles1;
        }
    }

    public static String zip(String charset, String src) {
        return zip(charset, src, null);
    }

    public static String zip(String charset, String src, String passwd) {
        return zip(charset, src, null, passwd);
    }

    public static String zip(String charset, String src, String dest, String passwd) {
        return zip(charset, src, dest, true, passwd);
    }

    public static String zip(String charset, String src, String dest, boolean isCreateDir, String passwd) {
        File srcFile = new File(src);
        dest = buildDestinationZipFilePath(srcFile, dest);
        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(8);
        parameters.setCompressionLevel(5);
        if(!TextUtils.isEmpty(passwd)) {
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(0);
            parameters.setPassword(passwd.toCharArray());
        }

        try {
            ZipFile e = new ZipFile(dest);
            e.setFileNameCharset(charset);
            if(srcFile.isDirectory()) {
                if(!isCreateDir) {
                    File[] subFiles = srcFile.listFiles();
                    if(subFiles == null || subFiles.length == 0){
                        return null;
                    }
                    ArrayList temp = new ArrayList();
                    Collections.addAll(temp, subFiles);
                    e.addFiles(temp, parameters);
                    return dest;
                }
                e.addFolder(srcFile, parameters);
            } else {
                e.addFile(srcFile, parameters);
            }

            return dest;
        } catch (ZipException var10) {
            var10.printStackTrace();
            return null;
        }
    }

    private static String buildDestinationZipFilePath(File srcFile, String destParam) {
        String fileName;
        if(TextUtils.isEmpty(destParam)) {
            if(srcFile.isDirectory()) {
                destParam = srcFile.getParent() + File.separator + srcFile.getName() + ".zip";
            } else {
                fileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
                destParam = srcFile.getParent() + File.separator + fileName + ".zip";
            }
        } else {
            createDestDirectoryIfNecessary(destParam);
            if(destParam.endsWith(File.separator)) {
                if(srcFile.isDirectory()) {
                    fileName = srcFile.getName();
                } else {
                    fileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
                }

                destParam = destParam + fileName + ".zip";
            }
        }

        System.out.println("destParam :" + destParam);
        return destParam;
    }

    private static void createDestDirectoryIfNecessary(String destParam) {
        File destDir;
        if(destParam.endsWith(File.separator)) {
            destDir = new File(destParam);
        } else {
            destDir = new File(destParam.substring(0, destParam.lastIndexOf(File.separator)));
        }

        FileUtils.createDirOrExists(destDir);

    }
}
