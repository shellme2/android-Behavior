package com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event;

import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.EventAttr;

/**
 * @author hesn
 * @function 自定义IAttr类型事件
 * <p> 为了兼容以前版本直接传实现IAttr的参数，如：EventAttr </p>
 * @date 16-9-19
 * @company 步步高教育电子有限公司
 */

public class CustomAttrEvent extends AEvent<CustomAttrEvent>{
    /**
     * 事件属性
     */
    public EventAttr eventAttr;

    /**
     * 设置事件类型
     * @return 参考EType
     */
    @Override
    public int eventType() {
        return eventAttr == null ? 0 : eventAttr.getEventType();
    }
    /**
     * 设置事件类型名称
     * @return 参考EType
     */
    @Override
    public String eventName() {
        return eventAttr == null ? null : eventAttr.getEventName();
    }
    /**
     * 封装app采集信息
     * <P> 此函数作用为：封装app采集信息 <P/>
     * @return
     */
    @Override
    protected EventAttr packagEventAttr() {
        return eventAttr;
    }
    /**
     * 扩展 IAttr 信息
     * <p> 注：各个实现IAttr的类如：DataAttr是直接对应数据库表格字段的 </p>
     * <p> 此函数作用为：添加额外的基础采集信息，如：DataAttr等 </p>
     * <p> 可以覆盖默认的系统采集数据，如：MachineAttr,OtherAttr等 </p>
     */
    @Override
    protected void packagExtendAttr() {
    }
    /**
     * 扩展字段,必须json格式
     * <p> 如果有额外信息，请在此返回 </p>
     * @return
     */
    @Override
    protected String getJsonExtend() {
        return null;
    }

    public CustomAttrEvent setEventAttr(EventAttr eventAttr) {
        this.eventAttr = eventAttr;
        return this;
    }
}
