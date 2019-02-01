package com.eebbk.bfc.sdk.behavior.report.analysis.filter.time;

import com.eebbk.bfc.sdk.behavior.db.entity.Record;
import com.eebbk.bfc.sdk.behavior.report.analysis.interfaces.ISortTimeFilter;
import com.eebbk.bfc.sdk.behavior.report.common.constants.ConstData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author hesn
 * @function
 * @date 16-9-26
 * @company 步步高教育电子有限公司
 */

abstract class ASortTimeFilter implements ISortTimeFilter{

    //保存数据库中的时间格式
    private final SimpleDateFormat format = new SimpleDateFormat(ConstData.DATA_TRIGGER_DATE_FORMAT, Locale.getDefault());

    /**
     * 获取数据的触发时间
     * @param record
     * @return
     * @throws ParseException
     */
    protected long getTime(Record record) throws ParseException {
        return format.parse(record.getTrigTime()).getTime();
    }
}
