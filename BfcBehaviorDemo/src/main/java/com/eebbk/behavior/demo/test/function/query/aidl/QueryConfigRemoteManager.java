package com.eebbk.behavior.demo.test.function.query.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.eebbk.bfc.sdk.behavior.Constant;
import com.eebbk.bfc.sdk.behavior.IQueryRemoteService;
import com.eebbk.bfc.sdk.behavior.aidl.IBehaviorAidlInterface;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hesn
 * @function 访问大数据采集信息
 * @date 16-8-25
 * @company 步步高教育电子有限公司
 */

public class QueryConfigRemoteManager {
    private IBehaviorAidlInterface mRemoteService;
    private static final String ACTION_SUFFIX = ".behavior.BehaviorRemoteService";
    private OnRemoteConnectListener mOnRemoteConnectListener;

    /**
     * 绑定远程查询服务
     * @param context
     * @param appPackageName　要查询的应用包名
     */
    public void bindService(Context context, String appPackageName){
        if(context == null){
            return;
        }
        Intent intent = new Intent(appPackageName + ACTION_SUFFIX);
        intent.setPackage(appPackageName);
        boolean b = context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
        if(mOnRemoteConnectListener != null){
            if(b){
                mOnRemoteConnectListener.onBindSuccess(appPackageName);
            }else {
                mOnRemoteConnectListener.onBindFail(appPackageName);
            }
        }
        LogUtils.i("QueryConfigRemoteManager", appPackageName + " bind " + b);
    }

    /**
     * 解绑远程查询服务
     * @param context
     */
    public void unbindService(Context context){
        if(context == null || mRemoteService == null){
            return;
        }
        context.unbindService(conn);
        mRemoteService = null;
    }

    /**
     * 获取配置信息
     * @return
     */
    public String queryConfig(){
        if(mRemoteService == null){
            return null;
        }
        try {
            return mRemoteService.eventJson(Constant.Remote.Command.CONFIG, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setOnRemoteConnectListener(OnRemoteConnectListener l) {
        this.mOnRemoteConnectListener = l;
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mRemoteService = IBehaviorAidlInterface.Stub.asInterface(service);
            if(mOnRemoteConnectListener != null){
                mOnRemoteConnectListener.onConnectedSuccess(name.getPackageName());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mRemoteService = null;
        }
    };

    public interface OnRemoteConnectListener{

        void onBindSuccess(String packageName);

        void onBindFail(String packageName);

        void onConnectedSuccess(String packageName);
    }
}
