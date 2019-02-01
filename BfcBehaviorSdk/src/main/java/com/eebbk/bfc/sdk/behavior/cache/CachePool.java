package com.eebbk.bfc.sdk.behavior.cache;

import com.eebbk.bfc.sdk.behavior.cache.interfaces.ICachePool;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.interfaces.IEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hesn
 * @function 缓存池
 * @date 16-8-13
 * @company 步步高教育电子有限公司
 */

public class CachePool implements ICachePool{
    private final List<IEvent> mCacheList = new ArrayList<IEvent>();
    private static CachePool mInstance;

    public static CachePool getInstance() {
        if(mInstance != null){
            return mInstance;
        }
        synchronized (CachePool.class) {
            if (null == mInstance) {
                mInstance = new CachePool();
            }
        }
        return mInstance;
    }

    @Override
    public void cache(IEvent event) {
        mCacheList.add(event);
    }

    @Override
    public synchronized List<IEvent> getAll() {
        return new ArrayList<>(mCacheList);
    }

    @Override
    public int size() {
        return mCacheList.size();
    }

    @Override
    public synchronized void clear() {
        mCacheList.clear();
    }

    public void cache(List<IEvent> events) {
        mCacheList.addAll(events);
    }

    public boolean isEmpty(){
        return size() <= 0;
    }

    private CachePool(){
        //prevent the instance
    }
}
