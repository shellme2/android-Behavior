package com.eebbk.bfc.sdk.behavior.cache.interfaces;

import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.interfaces.IEvent;

import java.util.List;

/**
 * @author hesn
 * @function 缓存池接口
 * @date 16-8-13
 * @company 步步高教育电子有限公司
 */

public interface ICachePool {
    /**
     * 保存到缓存中
     * @param
     * @return
     */
    void cache(IEvent event);

    /**
     * 获取所有数据
     * @return
     */
    List<IEvent> getAll();

    /**
     * 缓存数据条数
     * @return
     */
    int size();

    /**
     * 清空入库缓存池
     */
    void clear();
}
