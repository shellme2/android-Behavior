package com.eebbk.behavior.demo.test.function.query;

import android.content.ContentValues;

import java.util.List;

/**
 * @author hesn
 * @function
 * @date 16-8-25
 * @company 步步高教育电子有限公司
 */

public interface IRefreshUI {
    void onRefreshUI(List<ContentValues> cacheList
            , List<ContentValues> dbList, List<ContentValues> fileList);

    void onError(String tip);

    void onItemChange(List<Integer> checkedIndex);
}
