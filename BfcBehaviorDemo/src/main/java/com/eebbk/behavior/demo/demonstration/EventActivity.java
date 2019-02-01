package com.eebbk.behavior.demo.demonstration;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.eebbk.behavior.demo.R;
import com.eebbk.behavior.demo.common.ABaseActivity;
import com.eebbk.behavior.demo.utils.ConfigUtils;
import com.eebbk.behavior.demo.utils.DADemoUtils;
import com.eebbk.behavior.demo.utils.ExecutorsUtils;
import com.eebbk.bfc.common.devices.MediaLibraryUtils;
import com.eebbk.bfc.common.devices.ShellUtils;
import com.eebbk.bfc.common.file.BBKStorageUtils;
import com.eebbk.bfc.common.file.FileUtils;
import com.eebbk.bfc.common.tools.ThreadUtils;
import com.eebbk.bfc.sdk.behavior.BehaviorCollector;
import com.eebbk.bfc.sdk.behavior.db.entity.Record;
import com.eebbk.bfc.sdk.behavior.utils.BASE64Coder;
import com.eebbk.bfc.sdk.behavior.utils.JsonUtils;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hesn
 * @function 采集类型使用演示界面
 * @date 16-8-30
 * @company 步步高教育电子有限公司
 */

public class EventActivity extends ABaseActivity {

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main_layout;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean isShowLog() {
        return true;
    }

    @Override
    protected boolean isShowAidlMode() {
        return true;
    }

    /**
     * 计次事件
     *
     * @param view
     */
    public void onClickEvent(View view) {
        if(isCheckedAidlMode()){
            DADemoUtils.clickEventAidl(EventActivity.class.getName()
                    , "点击难题反馈", "难题反馈","");
        }else {
            DADemoUtils.clickEvent(EventActivity.class.getName()
                    , "点击难题反馈", "难题反馈","");
        }
//        ExecutorsUtils.execute(new Runnable() {
//            @Override
//            public void run() {
//                FilenameFilter filter = new F();
////                List<File> files =  FileUtils.listFilesInDirWithFilter(getExternalFilesDir("UserBahavior"), filter);
//                String path = "/mnt/sdcard" + File.separator + "behaviorReport";
//                String savePath = "/mnt/sdcard" + File.separator + "1.txt";
//                FileUtils.createFileOrExists(new File(savePath));
////                LogUtils.e("EventActivity", "root path：" + path);
////                List<File> files =  FileUtils.listFilesInDirWithFilter(new File(path), filter);
//                List<RecordTest> allList = new ArrayList<>();
//                List<RecordTest> fileList = new ArrayList<>();
////                int size = files.size();
////                for (int i = 0; i < size ; i++) {
////        for (File f : files) {
////            LogUtils.i("EventActivity", f.getAbsolutePath());
////                    File f = files.get(i);
//                        File f = new File(path);
//                    try {
//                        String jsonEncode = new String(FileUtils.readFile2Bytes(f),"utf-8");
////                LogUtils.i("EventActivity", "" + jsonEncode);
//                        String[] jsonEncodeList = jsonEncode.split("\r\n");
//                        fileList.clear();
//                        Log.e("hesn", "size:" + jsonEncodeList.length);
//                        for (String jsonEncodeCell : jsonEncodeList) {
//                            Log.v("hesn", "" + jsonEncodeCell);
//                            String json = BASE64Coder.decode(jsonEncodeCell.getBytes("UTF-8"));
//                            Log.i("hesn", "" + json);
//                            FileUtils.writeFile(new File(savePath), json + "\n\n", true);
//                            RecordTest record = JsonUtils.fromJson(json, RecordTest.class);
////                    LogUtils.i("EventActivity", "" + record.getTrigTime());
//                            record.setPath(f.getAbsolutePath());
//                            contains(fileList, record, "file");
////                            contains(allList, record, "all");
////                            allList.add(record);
//                            fileList.add(record);
////                            if(TextUtils.equals(record.getTrigTime(), "2018-05-21 17:26:48.141")
////                                    || TextUtils.equals(record.getTrigTime(), "2018-05-21 17:26:51.099")
////                                    || TextUtils.equals(record.getTrigTime(), "2018-05-21 17:26:54.572")
////                                    || TextUtils.equals(record.getTrigTime(), "2018-05-21 17:26:55.899")){
////                                LogUtils.e("EventActivity", "" + json);
////                                LogUtils.e("EventActivity", "path:" + record.getPath());
////                            }
//                        }
////                        LogUtils.i("EventActivity", i + "/" + size);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
////                }
//                MediaLibraryUtils.scanFile(getApplicationContext(),savePath);
//                Log.e("hesn", "end");
//            }
//        });
//        Log.d("hesn", "dmesg start:" );
//        ShellUtils.CommandResult commandResult = ShellUtils.execCmd("dmesg > /sdcard/kmesg31.txt", false, true);
//        Log.d("hesn", "dmesg end:" + commandResult);
    }

    private boolean contains(List<RecordTest> list, RecordTest newRecord, String tag){
        for (RecordTest r : list) {
            if(newRecord.compareTo(r) == -1){
                LogUtils.e("EventActivity", tag + "出现重复数据："
                        + "\n" + newRecord.getEventName()
                        + "\n" + newRecord.getFunctionName()
                        + "\n" + newRecord.getTrigTime()
                        + "\n" + newRecord.getPath()
                        + "\n" + r.getPath()
                );
            }
        }
        return false;
    }

    class RecordTest extends Record implements Comparable<RecordTest>{
        String path;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        @Override
        public int compareTo(@NonNull RecordTest another) {
            if(TextUtils.equals(another.getTrigTime(), getTrigTime())
                    && TextUtils.equals(another.getFunctionName(), getFunctionName())
                    && TextUtils.equals(another.getEventName(), getEventName())){
                return -1;
            }
            return 0;
        }
    }

    class F implements FilenameFilter {

        @Override
        public boolean accept(File dir, String filename) {
            return filename.contains("_");
        }
    }

    /**
     * 计数事件
     *
     * @param view
     */
    public void onCountEvent(View view) {
        if(isCheckedAidlMode()){
            DADemoUtils.countEventAidl(EventActivity.class.getName()
                    , "点击播放按钮", "孙悟空打妖精", "10分钟", "");
        }else {
            DADemoUtils.countEvent(EventActivity.class.getName()
                    , "点击播放按钮", "孙悟空打妖精", "10分钟", "");
        }
    }

    /**
     * 自定义事件
     *
     * @param view
     */
    public void onCustomEvent(View view) {
        if(isCheckedAidlMode()){
            DADemoUtils.customEventAidl(EventActivity.class.getName()
                    , "自定义fname", "自定义详情", "自定义10分钟", "");
        }else {
            DADemoUtils.customEvent(EventActivity.class.getName()
                    , "自定义fname", "自定义详情", "自定义10分钟", "");
        }
    }

    /**
     * 搜索事件
     *
     * @param view
     */
    public void onSearchEvent(View view) {
        if(isCheckedAidlMode()){
            DADemoUtils.searchEventAidl(EventActivity.class.getName()
                    , "点击智能搜学", "*", "81 + 19", "100");
        }else {
            DADemoUtils.searchEvent(EventActivity.class.getName()
                    , "点击智能搜学", "*", "81 + 19", "100");
        }
    }

    /**
     * 触发异常捕获
     *
     * @param view
     */
    public void onCrashEvent(View view) {
        if(!ConfigUtils.isMonkeyTest){
            String nullStr = null;
            nullStr.equals("");
        }
    }

    /**
     * 重置dataid
     * @param view
     */
    public void onResetDataId(View view){
        DADemoUtils.resetDataId();
    }

    /**
     * 马上上传
     *
     * @param view
     */
    public void onUpload(View view) {
        if(isCheckedAidlMode()){
            DADemoUtils.realTime2UploadAidl();
        }else {
            BehaviorCollector.getInstance().realTime2Upload();
        }
    }

}
