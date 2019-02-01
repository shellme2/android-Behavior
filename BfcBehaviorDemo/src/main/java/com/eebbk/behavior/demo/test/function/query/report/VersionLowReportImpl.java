package com.eebbk.behavior.demo.test.function.query.report;

import com.eebbk.behavior.demo.test.function.query.QueryConfigInfo;

/**
 * 版本过低
 * @author hesn
 * 2018/7/12
 */
public class VersionLowReportImpl extends AbstractReport {

    @Override
    boolean isReport(QueryConfigInfo info) {
        return info.getErrorCode() == Error.Code.ERROR_VERSION_LOW;
    }

    @Override
    String setTip() {
        return "采集库版本过低";
    }

    @Override
    String[] setRemarks() {
        return new String[0];
    }

    @Override
    boolean needReadConfigSuccess() {
        return false;
    }
}
