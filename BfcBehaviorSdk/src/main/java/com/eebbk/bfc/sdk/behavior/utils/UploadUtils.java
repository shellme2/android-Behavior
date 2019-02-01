package com.eebbk.bfc.sdk.behavior.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.text.TextUtils;

import com.eebbk.bfc.common.devices.MediaLibraryUtils;
import com.eebbk.bfc.common.file.FileUtils;
import com.eebbk.bfc.sdk.behavior.db.DBManager;
import com.eebbk.bfc.sdk.behavior.db.constant.BCConstants;
import com.eebbk.bfc.sdk.behavior.db.entity.Record;
import com.eebbk.bfc.sdk.behavior.error.ErrorCode;
import com.eebbk.bfc.sdk.behavior.report.common.constants.ConstData;
import com.eebbk.bfc.sdk.behavior.report.upload.tasks.ReportTask;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;
import com.eebbk.bfc.uploadsdk.upload.share.UploadConstants;
import com.eebbk.bfc.uploadsdk.uploadmanage.uploadmanager.HttpTask;
import com.eebbk.bfc.uploadsdk.uploadmanage.uploadmanager.ITask;
import com.eebbk.bfc.uploadsdk.uploadmanage.uploadmanager.MultiCloudTask;
import com.eebbk.bfc.uploadsdk.uploadmanage.uploadmanager.Query;
import com.eebbk.bfc.uploadsdk.uploadmanage.uploadmanager.UploadController;
import com.eebbk.bfc.uploadsdk.uploadmanage.uploadmanager.UploadListener;
import com.eebbk.bfc.uploadsdk.uploadmanage.uploadmanager.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author hesn
 * @function
 * @date 16-11-14
 * @company 步步高教育电子有限公司
 */

public class UploadUtils {

    private static final String UPLOAD_ADDRESS = "http://da.eebbk.net:80/v3/receiveFile";
    private UploadController mUploadController = null;
    private static final String TAG = "UploadUtils";

    private static class InstanceHolder {
        private static final UploadUtils mInstance = new UploadUtils();
    }

    public static UploadUtils getInstance() {
        return InstanceHolder.mInstance;
    }

    private UploadUtils() {
//        initUplpadController();
    }

    /**
     * 上报anr log
     *
     * @param file
     */
    public void uploadFile(File file, Record record) {
        if (file == null) {
            LogUtils.bfcExceptionLog(TAG, ErrorCode.REPORT_ZIP_NULL);
            return;
        }
        if (!file.exists()) {
            LogUtils.bfcExceptionLog(TAG, ErrorCode.REPORT_ZIP_NOT_EXISTS, file.getAbsolutePath());
            return;
        }
        initUplpadController(ContextUtils.getContext());
        if (!checkUploadController(ContextUtils.getContext())) {
            return;
        }
        String fileName = FileUtils.getFileName(file);
        String filePath = file.getAbsolutePath();
        ITask mRequest = new MultiCloudTask();
        mRequest.setOverWrite(true);
        mRequest.addFile(fileName, filePath);
//        mRequest.setBucketId(ConstData.UPLOAD_CLOUD_BUCKET_ID);
        // 设置扩展字段的扩展信息，选择性添加，用于添加一些额外信息
        mRequest.putExtra(BCConstants.KEY_FILE_NAME, fileName);
        mRequest.putExtra(BCConstants.KEY_FILE_PATH, filePath);
        mRequest.putExtra(BCConstants.KEY_MODULE, ConstData.VALUE_MODULE);
        mRequest.putExtra(BCConstants.KEY_FUNCTION, ConstData.VALUE_FUNCTION_BC);
        mRequest.putExtra(BCConstants.KEY_TASK_FLAG, ConstData.VALUE_FUNCTION_BC);
        mRequest.putExtra(BCConstants.KEY_RECORD, JsonUtils.toJson(record));
        mRequest.setNetworkTypes(ConfigAgent.getBehaviorConfig().reportConfig.mNetworkTypes);
        mRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
        // 第三步：添加上传任务，返回数据库的ID值
        mUploadController.addTask(mUploadListener, mRequest);
        LogUtils.i(TAG, "添加上传任务 fileName:" + fileName + ", filePath:" + filePath);
    }

    /**
     * 上报app采集文件
     *
     * @param file
     */
    public void uploadFile(File file, List<Integer> ids) {
        if (file == null) {
            LogUtils.bfcExceptionLog(TAG, ErrorCode.REPORT_ZIP_NULL);
            return;
        }
        if (!file.exists()) {
            LogUtils.bfcExceptionLog(TAG, ErrorCode.REPORT_ZIP_NOT_EXISTS, file.getAbsolutePath());
            return;
        }
        initUplpadController(ContextUtils.getContext());
        if (!checkUploadController(ContextUtils.getContext())) {
            return;
        }

        StringBuilder idStr = new StringBuilder();
        int size = ids.size();
        for (int i = 0; i < size; i++) {
            if (i == 0) {
                idStr.append(Integer.toString(ids.get(i)));
            } else {
                idStr.append(",").append(Integer.toString(ids.get(i)));
            }
        }

        ITask mRequest = new HttpTask(UPLOAD_ADDRESS);
        mRequest.setFileName("同步用户信息..");
        // 添加需要上传的文件
        mRequest.addFileBody("file", file.getAbsolutePath());
        // 设置扩展字段的扩展信息，选择性添加，用于添加一些额外信息
        mRequest.putExtra(BCConstants.KEY_MODULE, ConstData.VALUE_MODULE);
        mRequest.putExtra(BCConstants.KEY_FUNCTION, ConstData.VALUE_FUNCTION_BC);
        mRequest.putExtra(BCConstants.KEY_TASK_FLAG, ConstData.VALUE_FUNCTION_BC);
        mRequest.putExtra(BCConstants.KEY_IDS, idStr.toString());
        mRequest.setNetworkTypes(ConfigAgent.getBehaviorConfig().reportConfig.mNetworkTypes);
        LogUtils.v(TAG, "添加上传任务 ids:" + Arrays.toString(ids.toArray()));
        // 第三步：添加上传任务，返回数据库的ID值
        int id = mUploadController.addTask(mUploadListener, mRequest);
        LogUtils.v(TAG, "upload task id:" + id);
    }

    public void uploadTaskRecheck(){
        synchronized (ReportTask.TAG) {
            deleteSuccessTask(ContextUtils.getContext());
            reloadAbnormalRecord();
            // 重试上报失败任务
            if (!NetUtils.isAllowUpload(ContextUtils.getContext())) {
                LogUtils.w(TAG, "网络不满足上传条件,不重试上传失败任务");
                return;
            }
            restartFailedTask(ContextUtils.getContext());
        }
    }

    private void reloadAbnormalRecord(){
        if(!UploadUtils.getInstance().hasUploadTask(ContextUtils.getContext())){
            DBManager.getInstance().reloadAbnormalRecord();
        }
    }

    private synchronized void restartFailedTask(final Context context) {
        LogUtils.i(TAG, "restartFailedTask()");
        if(context == null){
            return;
        }
        initUplpadController(context);
        if (!checkUploadController(context)) {
            return;
        }
        Query query = new Query();
        query.setFilterByStatus(UploadConstants.STATUS_PAUSED | UploadConstants.STATUS_FAILED);
        query.addFilterByExtras(BCConstants.KEY_MODULE, ConstData.VALUE_MODULE);
        query.addFilterByExtras(BCConstants.KEY_FUNCTION, ConstData.VALUE_FUNCTION_BC);
        query.addFilterByExtras(BCConstants.KEY_TASK_FLAG, ConstData.VALUE_FUNCTION_BC);
        ArrayList<ITask> list = mUploadController.getTask(query);
        if (ListUtils.isEmpty(list)) {
            LogUtils.d(TAG, "没有上报失败的任务需要重试");
            return;
        }
        String appPName = context.getPackageName();
        for (ITask task : list) {
            if(task == null){
                continue;
            }
            LogUtils.d(TAG, "retry task packageName:" + task.getPackageName());
            LogUtils.i(TAG, "retry task:" + task.toString());
            if (!TextUtils.equals(appPName, task.getPackageName())) {
                continue;
            }
            if (canRetry(task)) {
                mUploadController.reloadTask(mUploadListener, task);
            }
        }
    }

    private boolean hasUploadTask(final Context context) {
        LogUtils.i(TAG, "restartFailedTask()");
        if(context == null){
            return true;
        }
        initUplpadController(context);
        if (!checkUploadController(context)) {
            return true;
        }
        Query query = new Query();
        query.addFilterByExtras(BCConstants.KEY_MODULE, ConstData.VALUE_MODULE);
        query.addFilterByExtras(BCConstants.KEY_FUNCTION, ConstData.VALUE_FUNCTION_BC);
        query.addFilterByExtras(BCConstants.KEY_TASK_FLAG, ConstData.VALUE_FUNCTION_BC);
        ArrayList<ITask> list = mUploadController.getTask(query);
        return !ListUtils.isEmpty(list);
    }

    /**
     * 删除上传成功但是没有删除的文件
     * @param context
     */
    private synchronized void deleteSuccessTask(final Context context) {
        LogUtils.i(TAG, "deleteSuccessTask()");
        if(context == null){
            return;
        }
        initUplpadController(context);
        if (!checkUploadController(context)) {
            return;
        }

        Query query = new Query();
        query.setFilterByStatus(UploadConstants.STATUS_SUCCESSFUL);
        query.addFilterByExtras(BCConstants.KEY_MODULE, ConstData.VALUE_MODULE);
        query.addFilterByExtras(BCConstants.KEY_FUNCTION, ConstData.VALUE_FUNCTION_BC);
        query.addFilterByExtras(BCConstants.KEY_TASK_FLAG, ConstData.VALUE_FUNCTION_BC);
        ArrayList<ITask> list = mUploadController.getTask(query);
        if (ListUtils.isEmpty(list)) {
            LogUtils.d(TAG, "没有上报成功但没有删除的任务");
            return;
        }
        String appPName = context.getPackageName();
        for (ITask task : list) {
            if(task == null){
                continue;
            }
            if (!TextUtils.equals(appPName, task.getPackageName())) {
                continue;
            }
            LogUtils.i(TAG, "删除上报成功但没有删除的任务和压缩文件");
            updateRecordState2Uploaded(task.getExtrasMap());
            mUploadController.deleteTask(task);
            deleteReportFile(task);
        }
    }

    /**
     * 限制重传次数
     *
     * @param task
     * @return
     */
    private static boolean canRetry(ITask task) {
        int retry = task.getIntExtra("uploadmanager-retry", ConstData.UPLOAD_RETRY_DEFAULT);
        Calendar calendar = Calendar.getInstance();
        int dayofyear = calendar.get(Calendar.DAY_OF_YEAR);
        int lastdayofyear = task.getIntExtra("uploadmanager-lastdayofyear", dayofyear);
        if (dayofyear != lastdayofyear) {
            retry = ConstData.UPLOAD_RETRY_DEFAULT;
        }
        retry--;
        task.putExtra("uploadmanager-retry", retry);
        task.putExtra("uploadmanager-lastdayofyear", dayofyear);
        return retry >= 0;
    }

    private void onHttpTaskCompleted(ITask task) {
        long id = task.getId();
        if (task.getState() != UploadConstants.STATUS_SUCCESSFUL) {
            //前面已经过滤了不是成功就是失败
            LogUtils.d(TAG, "上报BC数据失败，state不是success id:" + id);
            return;
        }

        Map<String, String> extras = task.getExtrasMap();

        if (isReportTaskExtraOK(extras)) {
            updateRecordState2Uploaded(extras);
            LogUtils.i(TAG, "上报成功，删除上报数据库的这条记录, task id:" + id);
            mUploadController.deleteTask(id);
            boolean del = deleteReportFile(task);
            LogUtils.i(TAG, "上报成功，删除上报完成后的压缩文件：" + del);
        }
    }

//    private void onMultiCloudTaskCompleted(ITask task) {
//        long id = task.getId();
//        if (task.getState() != UploadConstants.STATUS_SUCCESSFUL) {
//            //前面已经过滤了不是成功就是失败
//            LogUtils.d(TAG, "上报BC数据失败，state不是success id:" + id);
//            return;
//        }
//        Map<String, String> extras = task.getExtrasMap();
//        if (isReportTaskExtraOK(extras)) {
//            LogUtils.i(TAG, "文件上传成功，删除上报数据库的这条记录");
//            mUploadController.deleteTask(id);
//            String path = task.getExtrasMap().get(BCConstants.KEY_FILE_PATH);
//            FileUtils.deleteFile(path);
//            MediaLibraryUtils.scanFile(ContextUtils.getContext(), path);
//            LogUtils.i(TAG, "文件上传成功，删除上传文件");
//            anrUrlSave2DB(task);
//        }
//    }
//
//    private void anrUrlSave2DB(ITask task){
//        try {
//            List<Record> list = new ArrayList<>();
//            Record record = JsonUtils.fromJson(task.getStringExtra(BCConstants.KEY_RECORD), Record.class);
//            String fileName = task.getStringExtra(BCConstants.KEY_FILE_NAME);
//            if (TextUtils.isEmpty(fileName)) {
//                LogUtils.w(TAG, "extra get fail.");
//                return;
//            }
//            String url = task.getFileUri(fileName);
//            LogUtils.i(TAG, "anr log url:" + url);
//            list.add(setUrl2Record(record, url));
//            DBManager.getInstance().insertRecords(list);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

//    private Record setUrl2Record(Record record, String url){
//        try {
//            Map<String, String> trigValue = JsonUtils.fromJson(record.getTrigValue(), Map.class);
//            if(trigValue == null){
//                // 上面已经判断过一次，理论进不来这里。丢弃数据？
//                return record;
//            }
//            String stack = trigValue.get(ExceptionEvent.TrigValueKey.STACK);
//            trigValue.put(ExceptionEvent.TrigValueKey.STACK, stack + "\n\n完整日志下载地址：" + url);
//            trigValue.put(ExceptionEvent.TrigValueKey.URL, url);
//            record.setTrigValue(JsonUtils.toJson(trigValue));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return record;
//    }

    private void updateRecordState2Uploaded(Map<String, String> extras){
        if (extras == null || !extras.containsKey(BCConstants.KEY_IDS)) {
            return;
        }
        String ids = extras.get(BCConstants.KEY_IDS);
        if(TextUtils.isEmpty(ids)){
            return;
        }
        String[] idsStr = ids.split(",");
        List<Integer> idList = new ArrayList<>();
        for (String idStr : idsStr) {
            try {
                idList.add(Integer.valueOf(idStr));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        DBManager.getInstance().updateRecordState(idList, BCConstants.RecordState.UPLOADED);
    }

    private void uploadCompleted(ITask task) {
        if (task == null) {
            LogUtils.w(TAG, "task == null");
            return;
        }
        int taskType = task.getTaskType();
        switch (taskType){
            case UploadConstants.TASK_HTTP:
                LogUtils.i(TAG, "上报类型:Http");
                onHttpTaskCompleted(task);
                return;
//            case UploadConstants.TASK_MULTI_CLOUD:
//                LogUtils.i(TAG, "上报类型:multiCloud");
//                onMultiCloudTaskCompleted(task);
//                return;
            default:
                break;
        }
        LogUtils.bfcExceptionLog(TAG, ErrorCode.REPORT_UNKNOW_FILE_TYPE, "" + taskType);
    }

    private boolean isReportTaskExtraOK(Map<String, String> extras) {
        if (extras == null) {
            return false;
        } else if (!extras.containsKey(BCConstants.KEY_MODULE)
                || !extras.containsKey(BCConstants.KEY_FUNCTION)
                || !extras.get(BCConstants.KEY_MODULE).equals(
                ConstData.VALUE_MODULE)
                || !extras.get(BCConstants.KEY_FUNCTION).equals(
                ConstData.VALUE_FUNCTION_BC)) {
            LogUtils.d(ConstData.TAG, " 上报BC数据 module:" + extras.get(BCConstants.KEY_MODULE)
                    + " function:" + extras.get(BCConstants.KEY_FUNCTION));
            return false;
        }
        return true;
    }

    private boolean deleteReportFile(ITask reportTask) {
        for (Object o : reportTask.getFilesMap().entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            String key = (String) entry.getKey();
            String val = (String) entry.getValue();

            if (TextUtils.isEmpty(key)) {
                continue;
            }

            if (!key.startsWith(UploadTask.PREFIX_FILE)) {
                continue;
            }

            File file = new File(val);
            if (FileUtils.isFileExists(file)) {
                boolean delsuc = FileUtils.deleteFile(file);
                MediaLibraryUtils.scanFile(ContextUtils.getContext(), val);
                LogUtils.d(TAG, "delete file:" + val + " delsuc:" + delsuc);
                return true;
            } else {
                LogUtils.w(TAG, "the file:" + val + " don't exists!!");
                return false;
            }
        }
        return false;
    }

    private UploadListener mUploadListener = new UploadListener() {

        @Override
        public void onLoading(int id, long total, long current, long remainingMillis, long speed, int precent) {

        }

        @Override
        public void onSuccess(int id, String taskName, long total, List<String> urlList) {
            doUploadChange(id);
        }

        @Override
        public void onFailure(int id, String taskName, String msg) {
            LogUtils.d(TAG, "上报BC数据失败，state不是success id:" + id);
        }
    };

    private synchronized void doUploadChange(int id) {
        ITask task = mUploadController.getTaskById(id);
        if (task == null) {
            LogUtils.w(TAG, "upload success,but task = null");
            return;
        }
        uploadCompleted(task);
    }

    private boolean checkUploadController(Context context) {
        if (mUploadController == null) {
            LogUtils.v(TAG, "Try init uploadSdk again.");
            initUplpadController(context);
            LogUtils.bfcExceptionLog(TAG, ErrorCode.REPORT_BFC_UPLOAD_INIT_EXCEPTION);
            return false;
        }
        return true;
    }

    private void initUplpadController(Context context) {
        if (mUploadController != null) {
            return;
        }
        synchronized (UploadUtils.class) {
            if (mUploadController != null) {
                return;
            }
            try {
                mUploadController = new UploadController(context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
