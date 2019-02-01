package com.eebbk.behavior.demo.test.function.query.report;

import android.content.Context;
import android.text.TextUtils;

import com.eebbk.behavior.demo.test.function.query.QueryConfigInfo;
import com.eebbk.bfc.common.app.AppUtils;

import java.util.List;

/**
 * @author hesn
 * 2018/7/12
 */
public abstract class AbstractReport implements IReport {
    private int mCount = 0;
    private StringBuilder appName = new StringBuilder();
    private static final String SEPARATOR = "\n";

    abstract boolean isReport(QueryConfigInfo info);

    abstract String setTip();

    abstract String[] setRemarks();

    /**
     * 是否需要判断获取配置信息成功后再检查
     * @return
     */
    abstract boolean needReadConfigSuccess();

    @Override
    public void clear() {
        mCount = 0;
        appName.setLength(0);
    }

    @Override
    public void check(Context context, List<QueryConfigInfo> configList) {
        for (QueryConfigInfo info : configList) {
            if(needReadConfigSuccess()){
                if(TextUtils.isEmpty(info.getJson()) || info.getBehaviorConfig() == null){
                    continue;
                }
                if(!info.isInit()){
                    continue;
                }
            }
            if(isReport(info)){
                mCount++;
                appName.append("【").append(info.getAppName()).append("】")
                        .append("  v").append(AppUtils.getVersionName(context, info.getPackageName()))
                        .append("  ").append(info.getPackageName()).append(SEPARATOR);
            }
        }
    }

    @Override
    public String getBriefReport() {
        return setTip() + "：" + mCount + SEPARATOR;
    }

    @Override
    public String getDetailReport() {
        String detail = "-------------- " + setTip() + " --------------\n" + appName.toString() + SEPARATOR + SEPARATOR;
        String remarksStr = "";
        String[] remarks = setRemarks();
        if(remarks != null && remarks.length > 0){
            remarksStr = "以下原因都会导致“" + setTip() + "”：" + SEPARATOR;
            for (int i = 0; i < remarks.length; i++) {
                remarksStr += "  (" + (i + 1) + ")" + remarks[i] + ";" + SEPARATOR;
            }
            remarksStr += SEPARATOR + SEPARATOR;
        }
        return mCount == 0 ? "" : detail + remarksStr;
    }
}
