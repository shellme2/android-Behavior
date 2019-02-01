package com.eebbk.behavior.demo.test.function.query.report;

import android.text.TextUtils;

import com.eebbk.behavior.demo.test.function.query.QueryConfigInfo;

/**
 * @author hesn
 * 2018/7/12
 */
public class InitFailReportImpl extends AbstractReport {

    @Override
    boolean isReport(QueryConfigInfo info) {
        return !TextUtils.isEmpty(info.getJson()) && info.getBehaviorConfig() != null && !info.isInit();
    }

    @Override
    String setTip() {
        return "获取配置信息不准确";
    }

    @Override
    String[] setRemarks() {
        return new String[]{"app没有在application中初始化,需要手动启动此app后再检查"};
    }

    @Override
    boolean needReadConfigSuccess() {
        return false;
    }
}
