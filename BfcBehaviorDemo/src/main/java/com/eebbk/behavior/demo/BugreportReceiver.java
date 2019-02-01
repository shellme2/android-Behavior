package com.eebbk.behavior.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.eebbk.behavior.demo.utils.ExecutorsUtils;
import com.eebbk.bfc.common.file.BBKStorageUtils;
import com.eebbk.bfc.common.file.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author hesn
 * 2019/1/15
 */
public class BugreportReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(context == null || intent == null){
            return;
        }
        String action = intent.getAction();
        Log.i("hesn", "action:" + action);
        if(TextUtils.equals(action, "android.intent.action.BUGREPORT_STARTED")){

        }else if(TextUtils.equals(action, "android.intent.action.BUGREPORT_FINISHED")){
            final String zipPath = intent.getStringExtra("android.intent.extra.BUGREPORT");
            Log.i("hesn", "zipPath:" + zipPath);
            ExecutorsUtils.execute(new Runnable() {
                @Override
                public void run() {
                    String fileName = FileUtils.getFileName(new File(zipPath));
                    String srcPath = BBKStorageUtils.getExternalStorage().toString() + File.separator + "bbklog/bugreports/" + fileName;
                    Log.i("hesn", "srcFile:" + srcPath);
                    if(FileUtils.isFile(srcPath)){
                        File srcFile = new File(srcPath);
                        String destPath = BBKStorageUtils.getExternalStorage().toString() + File.separator + fileName;
                        Log.i("hesn", "destPath:" + destPath);
                        copyOrMoveFile(srcFile, new File(destPath), true);
                    }
                }
            });
        }
    }

    private boolean copyOrMoveFile(File srcFile, File destFile, boolean isMove) {
        if (srcFile != null && destFile != null) {
            if (srcFile.exists() && srcFile.isFile()) {
                if (destFile.exists() && destFile.isFile()) {
                    return false;
                } else if (!FileUtils.createDirOrExists(destFile.getParentFile())) {
                    return false;
                } else {
                    try {
                        return FileUtils.writeFile(destFile, (InputStream)(new FileInputStream(srcFile)), false) && (!isMove || FileUtils.deleteFile(srcFile));
                    } catch (FileNotFoundException var4) {
                        var4.printStackTrace();
                        return false;
                    } catch (IOException var5) {
                        var5.printStackTrace();
                        return false;
                    }
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
