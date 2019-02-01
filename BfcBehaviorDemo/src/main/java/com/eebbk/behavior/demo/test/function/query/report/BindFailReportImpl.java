package com.eebbk.behavior.demo.test.function.query.report;

import com.eebbk.behavior.demo.test.function.query.QueryConfigInfo;

/**
 * 不能获取配置信息
 * (1)没有集成采集库；
 * (2)使用aidl版本的采集库；
 * (3)非“com.eebbk.”和“com.bbk.”前缀包名无法唤起检查
 *
 * @author hesn
 * 2018/7/12
 */
public class BindFailReportImpl extends AbstractReport {

    @Override
    boolean isReport(QueryConfigInfo info) {
        return info.getErrorCode() == Error.Code.ERROR_BIND_FAIL;
    }

    @Override
    String setTip() {
        return "读取配置失败";
    }

    @Override
    String[] setRemarks() {
        return new String[]{
                "没有集成采集库",
                "使用aidl版本的采集库",
                "非“com.eebbk.”和“com.bbk.”前缀包名无法唤起检查"};
    }

    @Override
    boolean needReadConfigSuccess() {
        return false;
    }
}
