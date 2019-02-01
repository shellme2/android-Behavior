package com.eebbk.behavior.demo.test.performance.stress.real;

import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.eebbk.bfc.http.BfcHttp;
import com.eebbk.bfc.http.config.BfcRequestConfigure;
import com.eebbk.bfc.http.error.BfcHttpError;
import com.eebbk.bfc.http.toolbox.IBfcErrorListener;
import com.eebbk.bfc.http.toolbox.StringCallBack;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.BaseAttrManager;
import com.eebbk.bfc.sdk.behavior.utils.JsonUtils;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者： liming
 * 日期： 2018/12/21.
 * 公司： 步步高教育电子有限公司
 * 描述：实时上报DA工具类
 */
public class RealReport {
    private static final String TAG = "RealReport";
    private static final String URL = "https://rt.da.eebbk.net/v5/rt/jydz";
    private static final int RETRY_TIME = 2;//重试次数


    private RealReport() {
    }

    private static class InstanceHolder {
        private static final RealReport mInstance = new RealReport();
    }

    public static RealReport getInstance() {
        return InstanceHolder.mInstance;
    }

    public void report(Context context, DataAttr dataAttr) {
        List<DataAttr> dataAttrs = new ArrayList<>();
        dataAttrs.add(dataAttr);
        report(context, dataAttrs);
    }

    public void report(Context context, List<DataAttr> dataAttrs) {
        CommonAttr commonAttr = getCommonAttr();
        EventAttr eventAttr = new EventAttr(commonAttr, dataAttrs);
        String eventJson = JsonUtils.toJson(eventAttr);
        LogUtils.d(TAG, "eventJson =" + eventJson);

        Map<String, String> headParams = new HashMap<>(2);
        headParams.put("Content-Type", "application/json");

        Map<String, String> bodyParams = new HashMap<>(2);
        bodyParams.put("Content-Type", "application/json");
        try {
            post(context, TAG, URL, headParams, bodyParams, eventJson.getBytes("UTF-8"), RETRY_TIME);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void post(Context context, String tag, String url, Map<String, String> headerParams, Map<String, String> bodyParams, byte[] body, int retryTime) {
        BfcRequestConfigure conf = new BfcRequestConfigure.Builder()
                .setType(BfcRequestConfigure.Type.STRING)
                .setBody(body)
                .setHeader(headerParams)
                .setTag(tag)
                .setRetryTimes(retryTime)//请求次数 失败可以重复请求
                .build();
        BfcHttp.post(context, url, bodyParams, conf, new StringCallBack() {
            @Override
            public void onResponse(String response) {
                LogUtils.d(TAG, "onResponse =" + response);
            }
        }, new IBfcErrorListener() {
            @Override
            public void onError(BfcHttpError error) {
                LogUtils.d(TAG, "onError =" + error.toString());
            }
        });
    }


    @NonNull
    private CommonAttr getCommonAttr() {
        ContentValues values = new ContentValues();
        BaseAttrManager.getInstance().getMachinceAttr().insert(values);
        BaseAttrManager.getInstance().getApplicationAttr().insert(values);

        String mId = values.getAsString("mId");
        String devName = values.getAsString("devName");
        String packageName = values.getAsString("packageName");
        String moduleName = values.getAsString("moduleName");
        String appVer = values.getAsString("appVer");
        CommonAttr commonAttr = new CommonAttr(mId, devName, packageName, moduleName, appVer);
        return commonAttr;
    }


}
