package com.eebbk.behavior.demo.test.function.query.report;

import com.eebbk.behavior.demo.test.function.query.QueryConfigInfo;

/**
 * @author hesn
 * 2018/7/12
 */
public class MasterSwitchReportImpl extends AbstractReport {
    @Override
    boolean isReport(QueryConfigInfo info) {
        return !info.getBehaviorConfig().usable;
    }

    @Override
    String setTip() {
        return "关闭总开关";
    }

    @Override
    String[] setRemarks() {
        return new String[]{"enable(false)，如果要打开，请设置为true"};
    }

    @Override
    boolean needReadConfigSuccess() {
        return true;
    }
}
