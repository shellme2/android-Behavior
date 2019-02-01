package com.eebbk.bfc.sdk.behavior.aidl;

import android.content.Context;
import android.text.TextUtils;

import com.eebbk.bfc.sdk.behavior.aidl.utils.AppUtils;
import com.eebbk.bfc.sdk.behavior.aidl.utils.SessionAgent;
import com.eebbk.bfc.sdk.behavior.aidl.version.Build;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hesn
 * @function 系统应用跨进程埋点时需要修改的app信息
 * @date 17-4-25
 * @company 步步高教育电子有限公司
 */

class AttrManager {
    private Map<String, String> mAttrMap = new HashMap<>();
    private String mAttrJson = null;
    private Settings settings;

    void initAppAttr(Context context) {
        if (context == null) {
            return;
        }
        clean();
        // 设置当前app信息 (覆盖service端默认采集信息关键地方)
        // TODO bindDefaultSystemService()会冲掉通过 putAttr() 设置的参数
        mAttrMap.put(BFCColumns.COLUMN_AA_APPID, AppUtils.getAppId(context));
        mAttrMap.put(BFCColumns.COLUMN_AA_APPVER, AppUtils.getVersionName(context));
        mAttrMap.put(BFCColumns.COLUMN_AA_PACKAGENAME, context.getPackageName());
        mAttrMap.put(BFCColumns.COLUMN_AA_MODULENAME, TextUtils.isEmpty(settings.moduleName) ? AppUtils.getModuleName(context) : settings.moduleName);
        mAttrMap.put(BFCColumns.COLUMN_OA_DAVER, Build.VERSION.VERSION_NAME);
        mAttrMap.put(BFCColumns.COLUMN_EA_SESSIONID, SessionAgent.getSessionId());
    }

    void clean() {
        mAttrMap.clear();
        mAttrJson = null;
    }

    void put(String key, String value) {
        mAttrMap.put(key, value);
        mAttrJson = null;
    }

    void put(Map<String, String> map) {
        mAttrMap.putAll(map);
        mAttrJson = null;
    }

    void remove(String key) {
        if (!mAttrMap.containsKey(key)) {
            return;
        }
        mAttrMap.remove(key);
        mAttrJson = null;
    }

    void remove(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return;
        }
        mAttrMap.remove(map);
        mAttrJson = null;
    }

    Map<String, String> getAttr() {
        return new HashMap<>(mAttrMap);
    }

    String getAttrExtend() {
        if (!TextUtils.isEmpty(mAttrJson)) {
            return mAttrJson;
        }

        if (mAttrMap.isEmpty()) {
            return null;
        }

        mAttrJson = new JSONObject(mAttrMap).toString();
        return mAttrJson;
    }

    String getAttrExtend(Map<String, String> map) {
        if(mAttrMap.isEmpty() && (map == null || map.isEmpty())){
            return null;
        }

        if (mAttrMap.isEmpty()) {
            return new JSONObject(map).toString();
        }

        if(map == null || map.isEmpty()){
            return getAttrExtend();
        }

        // 如果 map 和 mAttrMap 有相同的key,最后取map中的值
        Map<String, String> parameterMap = new HashMap<>(mAttrMap);
        parameterMap.putAll(map);
        return new JSONObject(parameterMap).toString();
    }

    public AttrManager(Settings settings){
        this.settings = settings;
    }

}
