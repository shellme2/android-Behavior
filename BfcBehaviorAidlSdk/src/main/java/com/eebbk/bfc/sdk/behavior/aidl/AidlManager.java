package com.eebbk.bfc.sdk.behavior.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.eebbk.bfc.sdk.behavior.aidl.error.ErrorCode;
import com.eebbk.bfc.sdk.behavior.aidl.listener.InnerListener;
import com.eebbk.bfc.sdk.behavior.aidl.utils.LogUtils;

import java.util.Map;

/**
 * @author hesn
 * @function
 * @date 16-8-25
 * @company 步步高教育电子有限公司
 */

class AidlManager {
    private static final String TAG = "AidlManager";
    private IBehaviorAidlInterface mRemoteService;
    private static final String ACTION_SUFFIX = ".behavior.BehaviorRemoteService";
    private final AttrManager mAttrManager;
    private InnerListener mInnerListener;

    public AidlManager(InnerListener l, Settings settings) {
        mAttrManager = new AttrManager(settings);
        mInnerListener = l;
    }

    /**
     * 绑定远程查询服务
     *
     * @param context
     * @param appPackageName 　要查询的应用包名
     */
    boolean bindService(Context context, String appPackageName) {
        if (context == null) {
            LogUtils.bfcExceptionLog(TAG, ErrorCode.BIND_SERVICE_ERROR, "context == null");
            return false;
        }
        try {
            Intent intent = new Intent(appPackageName + ACTION_SUFFIX);
            intent.setPackage(appPackageName);
            mAttrManager.initAppAttr(context);
            return context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
        } catch (Exception e) {
            e.printStackTrace();
            mRemoteService = null;
        }
        return false;
    }

    /**
     * 解绑远程查询服务
     *
     * @param context
     */
    void unbindService(Context context) {
        if (context == null || mRemoteService == null) {
            return;
        }
        try {
            context.unbindService(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mRemoteService = null;
    }

    void event(int type, String curActivityName, String functionName, String moduleDetail, String trigValue, String extend
            , String dataId, String dataTitle, String dataEdition, String dataType, String dataGrade
            , String dataSubject, String dataPublisher, String dataExtend) {
        if (!isConnection()) {
            LogUtils.bfcExceptionLog(TAG, ErrorCode.BIND_SERVICE_ERROR, "数据入库失败.");
            return;
        }
        try {
            mRemoteService.event(type, curActivityName, functionName, moduleDetail, trigValue, extend
                    , dataId, dataTitle, dataEdition, dataType, dataGrade
                    , dataSubject, dataPublisher, dataExtend, mAttrManager.getAttrExtend());
        } catch (Exception e) {
            e.printStackTrace();
            mRemoteService = null;
        }
    }

    void event(int type, Map<String, String> map){
        if (!isConnection()) {
            LogUtils.bfcExceptionLog(TAG, ErrorCode.BIND_SERVICE_ERROR, "数据入库失败.");
            return;
        }
        try {
            mRemoteService.event(type, null, null, null, null, null
                    , null, null, null, null, null
                    , null, null, null, mAttrManager.getAttrExtend(map));
        } catch (Exception e) {
            e.printStackTrace();
            mRemoteService = null;
        }
    }

    String eventJson(int type, String json) {
        LogUtils.i(TAG, "type:" + type + " json:" + json);
        if (!isConnection()) {
            return null;
        }
        try {
            return mRemoteService.eventJson(type, json);
        } catch (Exception e) {
            e.printStackTrace();
            mRemoteService = null;
        }
        return null;
    }

    void realTime2Upload() {
        if (!isConnection()) {
            LogUtils.bfcExceptionLog(TAG, ErrorCode.BIND_SERVICE_ERROR, "调用马上上报数据失败.");
            return;
        }
        try {
            mRemoteService.realTime2Upload();
        } catch (Exception e) {
            e.printStackTrace();
            mRemoteService = null;
        }
    }

    String getBehaviorVersion() {
        if (!isConnection()) {
            LogUtils.bfcExceptionLog(TAG, ErrorCode.BIND_SERVICE_ERROR, "获取行为采集版本信息失败.");
            return null;
        }
        try {
            return mRemoteService.getBehaviorVersion();
        } catch (Exception e) {
            e.printStackTrace();
            mRemoteService = null;
        }
        return null;
    }

    AttrManager getAttrManager() {
        return mAttrManager;
    }

    boolean isConnection() {
        return mRemoteService != null;
    }

    void destroy(){
        mRemoteService = null;
        mInnerListener = null;
    }

    private final ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtils.i(TAG, "onServiceConnected() ComponentName:" + name);
            mRemoteService = IBehaviorAidlInterface.Stub.asInterface(service);
            if(mInnerListener != null){
                mInnerListener.onServiceConnected();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.i(TAG, "onServiceDisconnected() ComponentName:" + name.getPackageName());
            mRemoteService = null;
            if(mInnerListener != null){
                mInnerListener.onServiceDisconnected();
            }
        }
    };
}
