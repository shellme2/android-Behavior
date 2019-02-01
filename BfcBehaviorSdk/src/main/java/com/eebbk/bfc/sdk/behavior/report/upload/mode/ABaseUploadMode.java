package com.eebbk.bfc.sdk.behavior.report.upload.mode;

import android.content.Context;

import com.eebbk.bfc.sdk.behavior.report.analysis.interfaces.ISort;
import com.eebbk.bfc.sdk.behavior.report.common.util.CustomUtil;
import com.eebbk.bfc.sdk.behavior.report.upload.interfaces.IReportMode;
import com.eebbk.bfc.sdk.behavior.report.upload.tasks.ReportTask;
import com.eebbk.bfc.sdk.behavior.utils.ExecutorsUtils;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

/**
 * @author hesn
 *         2017/12/21
 */

abstract class ABaseUploadMode implements IReportMode {

    /**
     * 是否开启此模式
     */
    private boolean mIsOpen = false;

    abstract String getModeName();

    abstract void doReport(Context context);

    @Override
    public void openMode(boolean flag) {
        mIsOpen = flag;
    }

    @Override
    public boolean isModeOpen() {
        return mIsOpen;
    }

    @Override
    public void doReport(final Context context, final int...triggerMode) {
        ExecutorsUtils.getInstance().reportExecute(new Runnable() {
            @Override
            public void run() {
                if (CustomUtil.judgeParamsNull(context, isModeOpen(), getModeName(), modeType(), triggerMode)) {
                    return;
                }
                doReport(context);
            }
        });
    }

    void dealUpload(Context context){
        LogUtils.d("ABaseUploadMode", "执行" + getModeName());
        new ReportTask(context).run();
    }
}
