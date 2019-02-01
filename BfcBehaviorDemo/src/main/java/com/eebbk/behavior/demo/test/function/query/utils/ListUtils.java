package com.eebbk.behavior.demo.test.function.query.utils;

import android.content.ContentValues;

import com.eebbk.bfc.sdk.behavior.db.entity.Record;
import com.eebbk.bfc.sdk.behavior.utils.JsonUtils;

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
     *
     * @param list
     * @return
     */
    public static boolean isEmpty(List list) {
        return list == null || list.size() == 0;
    }

    /**
     * json List 转 List<ContentValues>
     *
     * @param src List<String>
     * @return List<ContentValues>
     */
    public static List<ContentValues> jsonList2ValueList(List<String> src) {
        List<ContentValues> results = new ArrayList<>();
        if (isEmpty(src)) {
            return results;
        }
        ContentValues values;
        Record record;
        for (String json : src) {
            record = JsonUtils.fromJson(json, Record.class);
            values = new ContentValues();
            values.putAll(record.getContentValues());
            results.add(values);
        }
        return results;
    }

    /**
     * 获取list的大小
     *
     * @param list
     * @return
     */
    public static int size(List list) {
        return isEmpty(list) ? 0 : list.size();
    }
}
