package com.eebbk.behavior.demo;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.eebbk.behavior.demo.common.ABaseActivity;
import com.eebbk.behavior.demo.test.TestMenuActivity;
import com.eebbk.behavior.demo.utils.DADemoUtils;
import com.eebbk.behavior.demo.utils.ExecutorsUtils;
import com.eebbk.bfc.common.devices.DeviceUtils;
import com.eebbk.bfc.common.file.BBKStorageUtils;
import com.eebbk.bfc.common.file.EncryptUtils;
import com.eebbk.bfc.common.file.FileUtils;
import com.eebbk.bfc.sdk.behavior.BehaviorCollector;
import com.eebbk.bfc.sdk.behavior.utils.Utils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hesn
 * @function 主菜单界面
 * @date 16-8-30
 * @company 步步高教育电子有限公司
 */

public class MenuActivity extends ABaseActivity {

    private static final String TAG = "MenuActivity";

    private TextView versionTv;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_menu_layout;
    }

    @Override
    protected void initView() {
        versionTv = findView(R.id.versionTv);
    }

    @Override
    protected void initData() {
        DADemoUtils.bindService(this);
        String anrPath = "data/anr/";

        versionTv.setText(TextUtils.concat(
                "行为采集jar版本号：", BehaviorCollector.getInstance().getBehaviorVersion(),
                "\n\n",
                "机器序列号:", DeviceUtils.getMachineId(getApplicationContext()),
                "\n\n",
                "模块名:", Utils.getModuleName(this.getApplicationContext()),
                "\n\n",
                anrPath, "是否存在:" + new File(anrPath).exists(),
                "\n\n",
                anrPath, "是否为文件夹:" + FileUtils.isDir(anrPath)
        ));
    }

    @Override
    protected boolean isShowLog() {
        return false;
    }

    /**
     * 采集事件类型演示
     *
     * @param view
     */
    public void onMenuEvent(View view) {
//        startActivity(new Intent(this, EventActivity.class));
//        startActivity(new Intent("com.eebbk.bfc.app.bfcbehavior.ACTION_MTK_LOG_ACTIVITY"));
//        Context context = selectContext(this);
//        Log.w("hesn","context:" + context.getPackageName());
//        try {
//            Log.i("hesn", "getStatics:" + new String(getStatics(), "gbk"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        Log.i("hesn", "bugreport start");
//        String path = BBKStorageUtils.getExternalStorage().toString() + File.separator + "bugreport3.txt";
//        ShellUtils.CommandResult result = ShellUtils.execCmd("bugreport > " + path, false, true);
//        Log.i("hesn", "result.result:" + result.result
//                + "\nresult.successMsg:" + result.successMsg
//                + "\nresult.errorMsg:" + result.errorMsg);
////        String result = execCommand("bugreport");
//        Log.i("hesn", "bugreport end " + result);

//        Log.i("hesn", "dumpsys package");
//        String path = BBKStorageUtils.getExternalStorage().toString() + File.separator + "dumpsysPackage.txt";
//        ShellUtils.CommandResult result = ShellUtils.execCmd("dumpsys package com.eebbk.musicplayer", false, true);
//        Log.i("hesn", "result.result:" + result.result
//                + "\nresult.successMsg:" + result.successMsg
//                + "\nresult.errorMsg:" + result.errorMsg);
//        save2File(dumpMsg());

        bugreport();
//        getBugreport();

//        Intent intent = getPackageManager().getLaunchIntentForPackage("com.bbk.feedback_h10");
//        startActivity(intent);
        Log.i(TAG, "end");
    }

    private void getBugreport(){
        final String zipPath = "/data/user_de/0/com.android.shell/files/bugreports/bugreport-NRD90M-2019-01-15-15-23-58.zip";
        Log.i("hesn", "zipPath:" + zipPath);
        ExecutorsUtils.execute(new Runnable() {
            @Override
            public void run() {
                String fileName = FileUtils.getFileName(new File(zipPath));
                String srcPath = File.separator + "bugreports" + File.separator + fileName;
                String destPath = BBKStorageUtils.getExternalStorage().toString() + File.separator + fileName;
                Log.i("hesn", "srcFile:" + File.separator + "bugreports");
                Log.i("hesn", "isFileExists:" + FileUtils.isFileExists(File.separator + "bugreports"));
                if(FileUtils.isFileExists(srcPath)){
                    File srcFile = new File(srcPath);
                    Log.i("hesn", "destPath:" + destPath);
                    copyOrMoveFile(srcFile, new File(destPath), true);
                }
//                String cmd = "cp " + srcPath + " " + destPath;
//                Log.i("hesn", "cmd:" + cmd);
//                ShellUtils.execCmd(cmd,false,false);
//                Log.i("hesn", "cmd end");
            }
        });
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

    private void bugreport(){
        int version = DeviceUtils.getSDK();
        if(version >= 28){
            ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            try {
                // android.intent.action.BUGREPORT_FINISHED
                Method getService = am.getClass().getDeclaredMethod("getService");
                Object iam = getService.invoke(null);
                Method requestBugReport = iam.getClass().getDeclaredMethod("requestBugReport", int.class);
                requestBugReport.invoke(iam, 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if(version >= android.os.Build.VERSION_CODES.N){
            try {
                Class<?> amn = Class.forName("android.app.ActivityManagerNative");
                Method getDefault = amn.getMethod("getDefault");
                Object oamn = getDefault.invoke(amn);
                Method requestBugReport = oamn.getClass().getMethod("requestBugReport", int.class);
                requestBugReport.invoke(oamn, 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void save2File(String dumpMsg){
        String path = BBKStorageUtils.getExternalStorage().toString() + File.separator + "dumpsysPackage.txt";
        FileUtils.createFileOrExists(path);
        try {
            FileUtils.writeFile(new File(path), dumpMsg, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Pattern mPattern = Pattern.compile("codePath=(.*)");

    private String dumpMsg(){
        String separator = "\n\n\n";
        String dumpPackageCmd = "dumpsys package com.eebbk.musicplayer";
        Log.d(TAG, "dumpPackageCmd:" + dumpPackageCmd);
//        ShellUtils.CommandResult dumpPackageResult = ShellUtils.execCmd(dumpPackageCmd, false, true);
        String dumpPackageResult = execCommand(dumpPackageCmd);
        StringBuilder sb = new StringBuilder(dumpPackageResult.length() + 100);
        sb.append("=========== ").append(dumpPackageCmd).append(" ===========\n").append(dumpPackageResult).append(separator);
        Matcher matcher = mPattern.matcher(dumpPackageResult);
        List<String> apkPaths = new ArrayList<>();
        while (matcher.find()) {
            String tmp = matcher.group();
            String[] apks = tmp.split("=");
            if (apks != null) {
                if (apks.length == 1) {
                    apkPaths.add(apks[0]);
                } else if (apks.length == 2) {
                    apkPaths.add(apks[1]);
                }
            }
        }

        for (String apk : apkPaths) {
            String lsCmd = "ls -lZR " + apk;
            Log.d(TAG, "lsCmd:" + lsCmd);
//            ShellUtils.CommandResult lsResult = ShellUtils.execCmd(lsCmd, false, true);
            String lsResult = execCommand(lsCmd);
            sb.append(separator).append("=========== ").append(lsCmd).append(" ===========\n").append(lsResult).append(separator);
            Log.d(TAG, "lsResult:" +lsResult);

            // TODO report files md5sum
            sb.append("=========== MD5 ===========\n");
            sb.append(getFileMd5(apk));
        }
        return sb.toString();
    }

    private String getFileMd5(String path){
        if(FileUtils.isFile(path)){
            String fileMd5 = EncryptUtils.md5Hex(path);
            Log.d(TAG, "path:" + path + "\nmd5sum:" + fileMd5);
            return path + " --> " + fileMd5;
        }else if(FileUtils.isDir(path)){
            Log.i(TAG, "list files:" + path);
            List<File> files = listFilesInDir(new File(path));
            if(files == null || files.size() == 0){
                return "";
            }
            StringBuilder sb = new StringBuilder(50);
            for (File file : files) {
                String md5 = getFileMd5(file.getAbsolutePath());
                if(TextUtils.isEmpty(md5)){
                    continue;
                }
                sb.append(md5).append("\n");
            }
            return sb.toString();
        }
        return "";
    }

    public static List<File> listFilesInDir(File dir) {
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files == null) {
            return null;
        }
        for (File file : files) {
            list.add(file);
            if (file.isDirectory()) {
                List<File> list1 = listFilesInDir(file);
                if(list1 != null && list1.size() > 0){
                    list.addAll(list1);
                }
            }
        }
        return list;
    }

    private String execCommand(String cmd) {
        Process process = null;
        BufferedReader reader = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
            process.waitFor();

            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            StringBuilder sb = new StringBuilder();
            while ((line=reader.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            close(reader);
            if (process != null) {
                process.destroy();
            }
        }
        return "nul";
    }

    private void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] getStatics() {
        try {
            Class<?> serviceManagerClass = Class.forName("android.os.ServiceManager");
            Method getServiceMethod = serviceManagerClass.getMethod("getService", java.lang.String.class);
            Object batteryStatsService = getServiceMethod.invoke(null, "batterystats");
            Class<?> batteryStatsStubClass = Class.forName("com.android.internal.app.IBatteryStats$Stub");
            Object batteryStatsStubObject = batteryStatsStubClass.getMethod("asInterface", android.os.IBinder.class).invoke(null, batteryStatsService);
            return (byte[]) batteryStatsStubClass.getMethod("getStatistics").invoke(batteryStatsStubObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 测试
     *
     * @param view
     */
    public void onMenuTest(View view) {
        startActivity(new Intent(this, TestMenuActivity.class));
    }

    @Override
    protected void onDestroy() {
        DADemoUtils.unbindService(this);
        super.onDestroy();
    }
}
