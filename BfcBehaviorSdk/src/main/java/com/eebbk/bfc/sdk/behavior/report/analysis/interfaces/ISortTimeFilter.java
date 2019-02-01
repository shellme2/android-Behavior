package com.eebbk.bfc.sdk.behavior.report.analysis.interfaces;

import com.eebbk.bfc.sdk.behavior.db.entity.Record;
import com.eebbk.bfc.sdk.behavior.report.analysis.conditions.TimeCondition;

import java.text.ParseException;

/**
 * @author hesn
 * @function 数据分析过滤接口
 * @date 16-9-26
 * @company 步步高教育电子有限公司
 */

public interface ISortTimeFilter {
    /**
     * 数据是否符合条件
     * @param record
     * @return
     */
    boolean eligible(Record record, TimeCondition condition) throws ParseException;
}
