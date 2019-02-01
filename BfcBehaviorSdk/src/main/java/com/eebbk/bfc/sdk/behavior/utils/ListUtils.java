package com.eebbk.bfc.sdk.behavior.utils;

import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.interfaces.IEvent;
import com.eebbk.bfc.sdk.behavior.db.entity.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hesn
 * @function
 * @date 16-8-18
 * @company 步步高教育电子有限公司
 */

public class ListUtils {

    /**
     * 判断数组是否为空
     * @param list
     * @return
     */
    public static boolean isEmpty(List list){
        return list == null || list.size() == 0;
    }

    /**
     * List<IEvent> 转 List<Record>
     * @param src List<IEvent>
     * @return List<Record>
     */
    public static List<Record> eventList2RecordList(List<IEvent> src){
        List<Record> results = new ArrayList<Record>();
        if(isEmpty(src)){
            return results;
        }
        for(IEvent event : src){
            if(event == null){
                continue;
            }
            results.add(new Record(event.getContentValues()));
        }
        return results;
    }

    /**
     * List<Record> 转 json List
     * @param src List<Record>
     * @return List<String>
     */
    public static List<String> recordList2JsonList(List<Record> src){
        List<String> results = new ArrayList<String>();
        if(isEmpty(src)){
            return results;
        }
        for (Record record : src){
            results.add(JsonUtils.toJson(record));
        }
        return results;
    }

    /**
     * List<IEvent> 转 json List
     * @param src List<IEvent>
     * @return List<Record>
     */
    public static List<String> eventList2JsonList(List<IEvent> src){
        List<String> results = new ArrayList<String>();
        if(isEmpty(src)){
            return results;
        }
        for(IEvent event : src){
            results.add(JsonUtils.toJson(new Record(event.getContentValues())));
        }
        return results;
    }

    private ListUtils(){
        //prevent the instance
    }
}
