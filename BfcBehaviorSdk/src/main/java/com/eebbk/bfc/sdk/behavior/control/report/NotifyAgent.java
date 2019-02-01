package com.eebbk.bfc.sdk.behavior.control.report;

import com.eebbk.bfc.sdk.behavior.cache.CacheManager;
import com.eebbk.bfc.sdk.behavior.control.report.entity.NotifyReportInfo;
import com.eebbk.bfc.sdk.behavior.db.DBManager;
import com.eebbk.bfc.sdk.behavior.db.entity.Record;
import com.eebbk.bfc.sdk.behavior.report.control.ReportAgent;
import com.eebbk.bfc.sdk.behavior.utils.ListUtils;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

import java.util.List;

/**
 * @author hesn
 * @function 采集和上报模块交互
 * @date 16-8-18
 * @company 步步高教育电子有限公司
 */

public class NotifyAgent {

    /**
     * 数据变动
     *
     * @param info 数据统计
     */
    public static void onChange(NotifyReportInfo info) {
        LogUtils.i("当前采集数据总共条数：" + info.getTotal());
        ReportAgent.notifyReport(info);
    }

    /**
     * 获取所有数据
     *
     * @return
     */
    public static List<Record> getRecords(int size) {
        return CacheManager.getInstance().getData(size);
    }

    /**
     * 保存数据
     *
     * @param records
     */
    public static void saveRecords(List<Record> records) {
        if (ListUtils.isEmpty(records)) {
            return;
        }
        DBManager.getInstance().insertRecords(records);
        CountManager.getInstance().addTotal(records.size());
    }

    /**
     * 获取记录条数
     *
     */
    public static int getRecordcount() {
        return DBManager.getInstance().getRecordcount();
    }

    private NotifyAgent() {
        //prevent the instance
    }
}
