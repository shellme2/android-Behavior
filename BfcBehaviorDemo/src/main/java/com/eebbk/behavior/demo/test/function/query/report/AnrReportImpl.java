package com.eebbk.behavior.demo.test.function.query.report;

import com.eebbk.behavior.demo.test.function.query.QueryConfigInfo;

/**
 * @author hesn
 * 2018/7/12
 */
public class AnrReportImpl extends AbstractReport {
    @Override
    boolean isReport(QueryConfigInfo info) {
        return !info.getBehaviorConfig().crashConfig.crashAnrEnable;
    }

    @Override
    String setTip() {
        return "关闭anr采集开关";
    }

    @Override
    String[] setRemarks() {
        return new String[]{"crashAnrEnable(false)，如果要打开，请设置为true"};
    }

    @Override
    boolean needReadConfigSuccess() {
        return true;
    }
}
