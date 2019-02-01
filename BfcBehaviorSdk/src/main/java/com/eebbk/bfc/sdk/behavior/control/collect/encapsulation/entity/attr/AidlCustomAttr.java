package com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr;

import android.content.ContentValues;
import android.text.TextUtils;

import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.DaVer;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.interfaces.IAttr;
import com.eebbk.bfc.sdk.behavior.db.constant.BFCColumns;
import com.eebbk.bfc.sdk.behavior.utils.ContextUtils;
import com.eebbk.bfc.sdk.behavior.utils.JsonUtils;
import com.eebbk.bfc.sdk.behavior.version.Build;

import java.util.Map;

public class AidlCustomAttr implements IAttr {

    Map<String, String> map;

    @Override
    public void insert(ContentValues values) {
        if (map == null || map.isEmpty()) {
            return;
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            values.put(key, value);
            insertDaVer(values, key, value);
        }
    }

    //处理DA版本号
    private void insertDaVer(ContentValues values, String key, String value) {
        if (!TextUtils.equals(key, BFCColumns.COLUMN_OA_DAVER)) {
            return;
        }
        try {
            // 已经设置了da版本,可能是aidl传过来,合并两个版本号,都上传
            values.put(BFCColumns.COLUMN_OA_DAVER, JsonUtils.toJson(new DaVer()
                    .setAidlDaVer(value)
                    .setServiceDaVer(Build.VERSION.VERSION_NAME)
                    .setServicePackage(ContextUtils.isEmpty() ? "" : ContextUtils.getContext().getPackageName())
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
