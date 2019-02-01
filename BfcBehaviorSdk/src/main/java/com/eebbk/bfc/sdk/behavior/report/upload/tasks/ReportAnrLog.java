package com.eebbk.bfc.sdk.behavior.report.upload.tasks;

//import android.content.Context;
//import android.text.TextUtils;
//
//import com.eebbk.bfc.common.devices.DeviceUtils;
//import com.eebbk.bfc.common.file.FileUtils;
//import com.eebbk.bfc.sdk.behavior.Constant;
//import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.ExceptionEvent;
//import com.eebbk.bfc.sdk.behavior.db.DBManager;
//import com.eebbk.bfc.sdk.behavior.db.entity.Record;
//import com.eebbk.bfc.sdk.behavior.utils.ContextUtils;
//import com.eebbk.bfc.sdk.behavior.utils.JsonUtils;
//import com.eebbk.bfc.sdk.behavior.utils.ListUtils;
//import com.eebbk.bfc.sdk.behavior.utils.UploadUtils;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.List;
//import java.util.Map;
//
///**
// * 提取anr异常文件中的log信息单独上报后，异常信息里只上报log信息的下载地址
// * @author hesn
// * 2018/10/11
// */
//public class ReportAnrLog {
//    private static final String TAG = "ReportAnrLog";
//
//    public void start(List<Record> list){
//        Context context = ContextUtils.getContext();
//        if(ListUtils.isEmpty(list) || context == null){
//            return;
//        }
//        try {
//            File cacheDir = context.getExternalFilesDir(Constant.Report.CACHE_DIR_NAME);
//            FileUtils.createDirOrExists(cacheDir);
//            for (Record record : list) {
//                Map<String, String> trigValue = getTrigValueMap(record);
//                if(trigValue == null){
//                    // 上面已经判断过一次，理论进不来这里。丢弃数据？
//                    continue;
//                }
//                String stack = trigValue.get(ExceptionEvent.TrigValueKey.STACK);
//                if(TextUtils.isEmpty(stack) || !stack.contains(Constant.Exception.FLAG_ANR_LOG)){
//                    // 上面已经判断过一次，理论进不来这里。丢弃数据？
//                    continue;
//                }
//                File anrLogFile = new File(cacheDir, "anr_log_" + DeviceUtils.getMachineId(context)
//                        + "_" + record.getTrigTime().replaceAll(":", "-").replaceAll(" ", "_")
//                        + ".txt");
//                FileUtils.createFileOrExists(anrLogFile);
//                FileUtils.writeFile(anrLogFile, stack, false);
//
//                String newStack = getStackWithoutLog(anrLogFile);
//                trigValue.put(ExceptionEvent.TrigValueKey.STACK, newStack);
//                record.setTrigValue(JsonUtils.toJson(trigValue));
//
//                // 旧数据虽然已经置了正在上传状态，但是还是要删除，避免触发重试逻辑导致重复上报
//                DBManager.getInstance().deleteRepeatRecord(record.getId());
//                UploadUtils.getInstance().uploadFile(anrLogFile, record);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 获取除堆栈外的信息上报,获取到有自己的Stack就停
//     * （用split直接截取有问题）
//     * @param file
//     * @return
//     */
//    private String getStackWithoutLog(File file){
//        InputStream inputStream = null;
//        BufferedReader bufferReader = null;
//        StringBuilder content = new StringBuilder(100);
//        String lineSeparator = "\n";
//        String readline;
//        boolean isMayStack = false;
//        try {
//            inputStream = new FileInputStream(file);
//            bufferReader = new BufferedReader(new InputStreamReader(inputStream));
//            while ((readline = bufferReader.readLine()) != null) {
//                if(isMayStack && readline.startsWith("----- pid")){
//                    break;
//                }
//                if(readline.startsWith("Cmd line:") && readline.contains(ContextUtils.getContext().getPackageName())){
//                    isMayStack = true;
//                }
//                content.append(readline).append(lineSeparator);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            FileUtils.closeIO(bufferReader);
//            FileUtils.closeIO(inputStream);
//        }
//        return content.toString();
//    }
//
//    private Map getTrigValueMap(Record record){
//        try {
//            return JsonUtils.fromJson(record.getTrigValue(), Map.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
