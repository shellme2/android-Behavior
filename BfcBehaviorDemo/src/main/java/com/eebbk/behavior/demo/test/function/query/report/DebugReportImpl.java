package com.eebbk.behavior.demo.test.function.query.report;

import com.eebbk.behavior.demo.test.function.query.QueryConfigInfo;

/**
 * @author hesn
 * 2018/7/12
 */
public class DebugReportImpl extends AbstractReport {
    @Override
    boolean isReport(QueryConfigInfo info) {
        return info.getBehaviorConfig().isDebugMode;
    }

    @Override
    String setTip() {
        return "打开debug模式";
    }

    @Override
    String[] setRemarks() {
        return new String[]{"setDebugMode(true)。debug模式会影响性能，发布版本请关闭。如果要关闭，请设置为false"};
    }

    @Override
    boolean needReadConfigSuccess() {
        return true;
    }
}
