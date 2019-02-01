package com.eebbk.bfc.sdk.behavior.aidl.crash.check;

import android.text.TextUtils;

import com.eebbk.bfc.sdk.behavior.aidl.BFCColumns;
import com.eebbk.bfc.sdk.behavior.aidl.EType;
import com.eebbk.bfc.sdk.behavior.aidl.listener.InnerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hesn
 * @function
 * @date 17-8-22
 * @company 步步高教育电子有限公司
 */

public class AidlCheckExceptionAgent {

    public static void reportException(InnerListener innerListener, String exceptionType, String msg, String version) {
        if(innerListener == null){
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", exceptionType);
            jsonObject.put("stack", msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Map<String, String> map = new HashMap<>();
        map.put(BFCColumns.COLUMN_EA_TVALUE, jsonObject.toString());

        //如果能获取到异常app的版本号,就替换
        if(!TextUtils.isEmpty(version)){
            map.put(BFCColumns.COLUMN_AA_APPVER, version);
        }
        if (innerListener != null) {
            innerListener.onEvent(EType.TYPE_EXCEPTION, map);
        }
    }
}
