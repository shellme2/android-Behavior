package com.eebbk.behavior.demo.test.function.errorcode;

import android.text.TextUtils;

import com.eebbk.bfc.sdk.behavior.BehaviorCollector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author hesn
 * @function 错误码查询逻辑处理类
 * @date 16-9-30
 * @company 步步高教育电子有限公司
 */

public class ErrorCodePresenter {
    /**
     * 错误码原数据
     */
    private Map<String, String> errorCodeMap;
    /**
     * 所有错误码数据列表
     */
    private List<Map<String, String>> errorCodeAllList = new ArrayList<Map<String, String>>();
    /**
     * 错误码查询结果列表
     */
    private List<Map<String, String>> searchList = new ArrayList<Map<String, String>>();


    public ErrorCodePresenter(){
        initData();
    }

    public List<Map<String, String>> getData(String search){
        if(TextUtils.isEmpty(search)){
            return errorCodeAllList;
        }
        return getSearchList(search);
    }

    private void initData(){
        errorCodeMap = BehaviorCollector.getInstance().getErrorCodes();
        errorCodeAllList.clear();
        Iterator<Map.Entry<String, String>> it2 = errorCodeMap.entrySet().iterator();
        while(it2.hasNext()){
            Map<String, String> map = new HashMap<String, String>();
            Map.Entry<String, String> me = it2.next();
            map.put(me.getKey(), me.getValue());
            errorCodeAllList.add(map);
        }
    }

    private List<Map<String, String>> getSearchList(String search){
        searchList.clear();
        if(errorCodeMap.containsKey(search)){
            Map<String, String> map = new HashMap<String, String>();
            map.put(search, errorCodeMap.get(search));
            searchList.add(map);
        }
        return searchList;
    }
}
