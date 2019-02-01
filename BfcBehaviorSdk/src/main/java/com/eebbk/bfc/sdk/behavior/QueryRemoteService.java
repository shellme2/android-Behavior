package com.eebbk.bfc.sdk.behavior;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.eebbk.bfc.sdk.behavior.cache.CachePool;
import com.eebbk.bfc.sdk.behavior.db.DBManager;
import com.eebbk.bfc.sdk.behavior.db.entity.Record;
import com.eebbk.bfc.sdk.behavior.utils.ListUtils;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

import java.util.List;

/**
 * @author hesn
 * @function
 * @date 16-8-25
 * @company 步步高教育电子有限公司
 */

public class QueryRemoteService extends Service{
    @Override
    public IBinder onBind(Intent intent) {
        return mbider;
    }

    private final IQueryRemoteService.Stub mbider = new IQueryRemoteService.Stub(){

        /**
         * 获取数据库所有信息
         * @return
         * @throws RemoteException
         */
        @Override
        public List queryDB() throws RemoteException {
            List<Record> list = DBManager.getInstance().queryRecordList();
            LogUtils.v("queryDB():" + list.size());
            return ListUtils.recordList2JsonList(list);
        }

        /**
         * 获取缓存池所有信息
         * @return
         * @throws RemoteException
         */
        @Override
        public List queryCache() throws RemoteException {
            LogUtils.v("queryCache():" + CachePool.getInstance().size());
            return ListUtils.eventList2JsonList(CachePool.getInstance().getAll());
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    };

}
