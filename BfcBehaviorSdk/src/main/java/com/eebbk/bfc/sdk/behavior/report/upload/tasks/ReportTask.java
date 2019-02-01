package com.eebbk.bfc.sdk.behavior.report.upload.tasks;

import android.content.Context;
import android.text.TextUtils;

import com.eebbk.bfc.common.file.FileUtils;
import com.eebbk.bfc.sdk.behavior.Constant;
import com.eebbk.bfc.sdk.behavior.control.report.NotifyAgent;
import com.eebbk.bfc.sdk.behavior.db.entity.Record;
import com.eebbk.bfc.sdk.behavior.error.ErrorCode;
import com.eebbk.bfc.sdk.behavior.report.analysis.interfaces.ISort;
import com.eebbk.bfc.sdk.behavior.report.common.constants.ConstData;
import com.eebbk.bfc.sdk.behavior.report.common.util.CustomUtil;
import com.eebbk.bfc.sdk.behavior.report.common.util.ModeConfig;
import com.eebbk.bfc.sdk.behavior.utils.BASE64Coder;
import com.eebbk.bfc.sdk.behavior.utils.ConfigAgent;
import com.eebbk.bfc.sdk.behavior.utils.JsonUtils;
import com.eebbk.bfc.sdk.behavior.utils.ListUtils;
import com.eebbk.bfc.sdk.behavior.utils.UploadUtils;
import com.eebbk.bfc.sdk.behavior.utils.ZIP4J;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ReportTask{

    private static final String PASSWORD = "BbKeEbBk";
    private Context mContext;
    private ISort mSort;
    public static final String TAG = "ReportTask";

    public ReportTask(Context context) {
        this.mContext = context == null ? null : context.getApplicationContext();
        this.mSort = ConfigAgent.getBehaviorConfig().reportConfig.sort;
    }

    public void run() {
        LogUtils.i(TAG, "=======DA数据开始上报=========");
        if (mContext == null) {
            LogUtils.bfcExceptionLog(TAG, ErrorCode.REPORT_TASK_CONTEXT_NULL);
            return;
        }
        synchronized (TAG){
            // 重试上报失败任务
            ModeConfig.saveKeyLongValue(ModeConfig.PreferencesKey.REPORT_LAST_TIME, System.currentTimeMillis());
            start();
        }
    }

    private void start(){
        List<Record> srcList = NotifyAgent.getRecords(ConstData.UPLOAD_MAX_RECORDS);
        List<Record> tmpList;
        do{
            if (ListUtils.isEmpty(srcList)) {
                LogUtils.w(TAG, "没有上传任务");
                return;
            }
//            tmpList = CustomUtil.sortData(srcList, mSort, new SortByAnr());
            tmpList = CustomUtil.sortData(srcList, mSort);
            doUpload(tmpList);
            srcList.clear();
            tmpList.clear();
            srcList = NotifyAgent.getRecords(ConstData.UPLOAD_MAX_RECORDS);
        }while (srcList.size() > 0);
    }

    private void doUpload(List<Record> dataList) {
        LogUtils.i(TAG, "上传记录数=" + dataList.size());
        String strEncode = toJson(dataList);
        if (TextUtils.isEmpty(strEncode)) {
            //数据异常或者OOM,抛弃
            return;
        }
        File srcFile = createLocalFile(mContext, strEncode);
        if (srcFile == null || !srcFile.exists()) {
            LogUtils.bfcExceptionLog(TAG, ErrorCode.REPORT_CREATE_FILE_ERROR);
            NotifyAgent.saveRecords(dataList);
            return;
        }
        File zipFile = zipCompress(mContext, srcFile.getAbsolutePath());
        if (zipFile == null || !zipFile.exists()) {
            LogUtils.bfcExceptionLog(TAG, ErrorCode.REPORT_CREATE_ZIP_ERROR);
            NotifyAgent.saveRecords(dataList);
            return;
        }
        LogUtils.i(TAG, "upload zip:" + zipFile.getAbsolutePath());
        List<Integer> ids = new ArrayList<>();
        for (Record record : dataList) {
            ids.add(record.getId());
        }
        UploadUtils.getInstance().uploadFile(zipFile, ids);
    }

    private File zipCompress(Context context, String path) {
        File dirFile = context.getExternalFilesDir(Constant.Report.CACHE_DIR_NAME);
        if (dirFile == null || !dirFile.exists()) {
            dirFile = context.getFilesDir();
        }
        String zipPath = ZIP4J.zip("UTF-8", path,
                new File(dirFile, System.currentTimeMillis() + ".zip")
                        .getAbsolutePath(), PASSWORD);
        if (TextUtils.isEmpty(zipPath)) {
            return null;
        }
        LogUtils.d(TAG, "此次上传数据压缩包路径=" + zipPath);
        return new File(zipPath);
    }

    private File createLocalFile(Context context, String strEncode) {
        FileOutputStream fos = null;
        File file = null;
        try {
            File dirFile = context.getExternalFilesDir(Constant.Report.CACHE_DIR_NAME);

            if (dirFile == null || !dirFile.exists()) {
                dirFile = context.getFilesDir();
            }

            file = new File(dirFile, ConstData.TAG);
            fos = new FileOutputStream(file);
            fos.write(strEncode.getBytes("UTF-8"));
            LogUtils.d(TAG, "创建文件:" + file.getAbsolutePath());
        } catch (Exception e2) {
            file = null;
            e2.printStackTrace();
        } finally {
            FileUtils.closeIO(fos);
        }
        return file;
    }

    private String toJson(List<Record> lists) {
        try {
            StringBuilder buffer = new StringBuilder(lists.size() * 20);
            String gsonString;
            if (ListUtils.isEmpty(lists)) {
                return buffer.toString();
            }
            for (Record record : lists) {
                gsonString = JsonUtils.toJson(record);
                String strEncode = null;
                try {
                    strEncode = dataEncode(gsonString);
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.bfcExceptionLog(TAG, "一条数据转JOSN失败");
                }
                buffer.append(strEncode);
                buffer.append("\r\n");
            }
            LogUtils.d(TAG, "生成JOSN数量：" + lists.size());
            return buffer.toString();
        } catch (Throwable e) {
            e.printStackTrace();
            divideOOMTaskList(lists);
        }
        return null;
    }

    /**
     * 数据转json中OOM处理
     *
     * @param lists
     */
    private void divideOOMTaskList(List<Record> lists) {
        if(!ConfigAgent.getBehaviorConfig().isRetryLargeFile){
            return;
        }
        if (ListUtils.isEmpty(lists)) {
            return;
        }
        int size = lists.size();
        if (size == 1) {
            //一条数据就OOM,那就只能抛弃了
            LogUtils.w(TAG, "抛弃一条导致OOM采集数据");
            return;
        }
        int newSize = getListSize(size);
        LogUtils.i(TAG, "数据打包OOM,重新计算上报数据大小:" + newSize);
        List<List<Record>> taskList = CustomUtil.divideTask(lists, newSize);
        for (List<Record> dataList : taskList) {
            doUpload(dataList);
        }
    }

    /**
     * 重新计算上传数据大小
     *
     * @param size
     * @return
     */
    private int getListSize(int size) {
        int newSize = (size + 1) / 2;
        return newSize <= 0 ? 0 : newSize;
    }

    private String dataEncode(String srcStr) throws Exception {
        if (TextUtils.isEmpty(srcStr)) {
            return null;
        }
        return BASE64Coder.encode(srcStr.getBytes("UTF-8"));
    }
}

