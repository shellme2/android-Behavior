package com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.interfaces;

import android.content.ContentValues;

/**
 * @author hesn
 * @function
 * @date 16-8-9
 * @company 步步高教育电子有限公司
 */

public interface IEvent {
    /**
     * 设置事件类型名称
     * @return 参考EType
     */
    String eventName();
    /**
     * 设置事件类型
     * @return 参考EType
     */
    int eventType();
    /**
     * 生成数据
     * <p> 因为有缓存，所以要考虑生成的数据的实时性 </p>
     */
    void makeData();
    /**
     * 获取采集数据的 ContentValues 对象
     * <p> 用于数据库保存 </p>
     * @return
     */
    ContentValues getContentValues();
}
