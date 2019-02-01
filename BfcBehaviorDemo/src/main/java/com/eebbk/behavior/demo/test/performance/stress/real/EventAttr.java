package com.eebbk.behavior.demo.test.performance.stress.real;

import java.util.List;

/**
 * 作者： liming
 * 日期： 2018/12/21.
 * 公司： 步步高教育电子有限公司
 * 描述：
 */
 class EventAttr {
    private CommonAttr common;
    private List<DataAttr> data;

    public EventAttr(CommonAttr common, List<DataAttr> data) {
        this.common = common;
        this.data = data;
    }

    @Override
    public String toString() {
        return "EventAttr{" +
                "common=" + common +
                ", data=" + data +
                '}';
    }

    private EventAttr() {
    }
}
