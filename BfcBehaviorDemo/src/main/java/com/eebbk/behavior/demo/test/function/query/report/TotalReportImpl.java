package com.eebbk.behavior.demo.test.function.query.report;

import com.eebbk.behavior.demo.test.function.query.QueryConfigInfo;

/**
 * @author hesn
 * 2018/7/12
 */
public class TotalReportImpl extends AbstractReport{

    @Override
    public String getDetailReport() {
        // 不需要显示详情信息
        return "";
    }

    @Override
    boolean isReport(QueryConfigInfo info) {
        return true;
    }

    @Override
    String setTip() {
        return "本次总共检查";
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
