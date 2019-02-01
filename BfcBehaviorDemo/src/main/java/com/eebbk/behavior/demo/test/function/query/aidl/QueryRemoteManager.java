package com.eebbk.behavior.demo.test.function.query.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.eebbk.bfc.sdk.behavior.IQueryRemoteService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hesn
 * @function 访问大数据采集信息
 * @date 16-8-25
 * @company 步步高教育电子有限公司
 */

public class QueryRemoteManager {
    private IQueryRemoteService mRemoteService;
    private static final String ACTION_SUFFIX = ".behavior.QueryRemoteService";

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
        context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
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
     * 获取数据库数据
     * @return
     */
    public List<String> queryDB(){
        if(mRemoteService == null){
            return new ArrayList<String>();
        }
        try {
            return mRemoteService.queryDB();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return new ArrayList<String>();
    }

    /**
     * 获取缓存池数据
     * @return
     */
    public List<String> queryCache(){
        if(mRemoteService == null){
            return new ArrayList<String>();
        }
        try {
            return mRemoteService.queryCache();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return new ArrayList<String>();
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mRemoteService = IQueryRemoteService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mRemoteService = null;
        }
    };
}
