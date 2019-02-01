package com.eebbk.behavior.demo.test.function.query.report;

import android.content.Context;

import com.eebbk.behavior.demo.test.function.query.QueryConfigInfo;

import java.util.List;

/**
 * 不能获取配置信息
 * @author hesn
 * 2018/7/12
 */
public interface IReport {
    void clear();

    void check(Context context, List<QueryConfigInfo> configList);

    String getBriefReport();

    String getDetailReport();
}
