package com.eebbk.bfc.sdk.behavior.aidl.crash.check;

import android.content.Context;
import android.text.TextUtils;

import com.eebbk.bfc.sdk.behavior.aidl.utils.LogUtils;

import java.util.List;

/**
 * @author hesn
 * @function
 * @date 17-6-30
 * @company 步步高教育电子有限公司
 */

public abstract class AStrictModeCheckException extends ACheckException implements IReportException{

    private static final String TAG = "AStrictModeCheckException";
    private static final String FILE_PREFIX = "strictmode--";// 保存的文件前缀
    private static final String FILE_SUFFIX = ".txt";// 保存的文件后缀

    @Override
    public String getExceptionType() {
        return CrashType.TYPE_STRICT_MODE;
    }

    @Override
    protected String getSaveFilePrefix() {
        return FILE_PREFIX;
    }

    @Override
    protected String getSaveFileSuffix() {
        return FILE_SUFFIX;
    }

    @Override
    protected String[] getDropboxTypes() {
        return new String[]{CrashType.DROP_BOX_TYPE_STRICT_MODE_SYSTEM, CrashType.DROP_BOX_TYPE_STRICT_MODE_DATA};
    }

    @Override
    protected boolean findDropBoxPackage(String type, String lineContent) {
        if (TextUtils.isEmpty(lineContent)) {
            return false;
        }
        return lineContent.startsWith("Package:");
    }

    @Override
    public boolean check(Context context) {
        LogUtils.v(TAG, "start check strict mode");

        if(!checkParameter(context)){
            return false;
        }

        if (TextUtils.isEmpty(savePath)) {
            savePath = getDefaultSaveLogFilePath(context);
        }

        // 检查data/system/dropbox/中有没有StrictMode文件
        List<DropBoxInfo> dropBoxList = checkDropBox(context, savePath);

        // 上报
        report(dropBoxList, isReport);

        checkendEnd(context);
        return true;
    }

    /**
     * 回调上报大数据
     *
     * @param dropBoxList
     */
    private void report(List<DropBoxInfo> dropBoxList, boolean isReport) {
        if (!canReport(dropBoxList, isReport)) {
            return;
        }
        for (DropBoxInfo info : dropBoxList) {
            if (info == null || TextUtils.isEmpty(info.content)) {
                continue;
            }
            reportException(info.content, info.versionName);
            LogUtils.w(TAG, "StrictMode:\n" + info.content);
        }
    }
}
