package com.eebbk.behavior.demo.common;

import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.constant.EType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hesn
 * @function 下拉框选择采集事件
 * @date 16-10-13
 * @company 步步高教育电子有限公司
 */

public class EventSpinnerPresenter {

    private List<String> list = new ArrayList<String>();

    public EventSpinnerPresenter(){
        list.add(EType.NAME_CLICK);
        list.add(EType.NAME_COUNT);
        list.add(EType.NAME_CUSTOM);
        list.add(EType.NAME_SEARCH);
        list.add(EType.NAME_PAGE);
    }

    public List<String> getSpinnerList(){
        return list;
    }

    /**
     * 根据下拉列表的下标获取事件类型
     * @param index
     * @return
     */
    public int getEventTypeBySpinnerIndex(int index){
        int eventType = EType.TYPE_CLICK;
        switch (index){
            case 0:
                eventType = EType.TYPE_CLICK;
                break;
            case 1:
                eventType = EType.TYPE_COUNT;
                break;
            case 2:
                eventType = EType.TYPE_CUSTOM;
                break;
            case 3:
                eventType = EType.TYPE_SEARCH;
                break;
            case 4:
                eventType = EType.TYPE_ACTIVITY_OUT;
                break;
            default:
                break;
        }
        return eventType;
    }
}
