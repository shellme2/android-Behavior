package com.eebbk.bfc.sdk.behavior.exception;

import android.text.TextUtils;

import com.eebbk.bfc.sdk.behavior.BehaviorCollector;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.AidlCustomAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.ExceptionEvent;
import com.eebbk.bfc.sdk.behavior.db.constant.BFCColumns;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hesn
 * @function
 * @date 17-8-22
 * @company 步步高教育电子有限公司
 */

public class CheckExceptionAgent {

    public static void reportException(String exceptionType, String msg, String version) {
        ExceptionEvent exceptionInfo = new ExceptionEvent();
        exceptionInfo.stack = msg;
        exceptionInfo.type = exceptionType;
        //如果能获取到异常app的版本号,就替换
        if(!TextUtils.isEmpty(version)) {
            AidlCustomAttr aidlCustomAttr = new AidlCustomAttr();
            Map<String, String> map = new HashMap<>();
            map.put(BFCColumns.COLUMN_AA_APPVER, version);
            aidlCustomAttr.setMap(map);
            exceptionInfo.addAttr(aidlCustomAttr);
        }
        BehaviorCollector.getInstance().exceptionEvent(exceptionInfo);
    }
}
