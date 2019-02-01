package com.eebbk.bfc.sdk.behavior.report.analysis.filter;

import com.eebbk.bfc.sdk.behavior.report.analysis.conditions.TimeCondition;
import com.eebbk.bfc.sdk.behavior.report.analysis.filter.time.BeforeSomeTimeFilter;
import com.eebbk.bfc.sdk.behavior.report.analysis.filter.time.BeforeTheTimeFilter;
import com.eebbk.bfc.sdk.behavior.report.analysis.filter.time.DurationTimeFilter;
import com.eebbk.bfc.sdk.behavior.report.analysis.filter.time.SinceSomeTimeFilter;
import com.eebbk.bfc.sdk.behavior.report.analysis.filter.time.SinceTheTimeFilter;
import com.eebbk.bfc.sdk.behavior.report.analysis.interfaces.ISortTimeFilter;

/**
 * @author hesn
 * @function 数据分析过滤器工厂
 * @date 16-9-26
 * @company 步步高教育电子有限公司
 */

public class FilterFactory {

    /**
     * 获取时间分析过滤器
     * @param type
     * @return
     */
    public static ISortTimeFilter createTimeFilter(int type){
        switch (type) {
            case TimeCondition.DURATION_TIME:
                //某个时间区间
                return new DurationTimeFilter();
            case TimeCondition.BEFORE_SOMETIME:
                //某个时间之前一段时间
                return new BeforeSomeTimeFilter();
            case TimeCondition.BEFORE_THETIME:
                //某个时间之前的所有数据
                return new BeforeTheTimeFilter();
            case TimeCondition.SINCE_SOMETIME:
                //某个时间之后一段时间
                return new SinceSomeTimeFilter();
            case TimeCondition.SINCE_THETIME:
                //某个时间之后的所有数据
                return new SinceTheTimeFilter();
            default:
                //默认返回空
                return null;
        }
    }

    private FilterFactory(){
        //prevent the instance
    }
}
