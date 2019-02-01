package com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event;

import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.constant.EType;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.DataAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.EventAttr;

import java.util.Map;

/**
 * @author hesn
 * @function
 * @date 16-8-9
 * @company 步步高教育电子有限公司
 */

public class PageEvent extends AEvent<PageEvent> {
    /**
     * 进入的activity
     */
    public String activityName;
    /**
     * 功能名称
     */
    public String functionName;
    /**
     * 模块详细
     */
    public String moduleDetail;
    /**
     * 使用时长
     */
    public String duaring;
    /**
     * 数据属性
     */
    public DataAttr dataAttr;
    /**
     * 扩展字段
     */
    public String extend;

    /**
     * 设置事件类型
     * @return 参考EType
     */
    @Override
    public int eventType() {
        return EType.TYPE_ACTIVITY_OUT;
    }
    /**
     * 设置事件类型名称
     * @return 参考EType
     */
    @Override
    public String eventName() {
        return EType.NAME_PAGE;
    }
    /**
     * 封装app采集信息
     * <P> 此函数作用为：封装app采集信息 <P/>
     * @return
     */
    @Override
    protected EventAttr packagEventAttr() {
        return new EventAttr()
                .setPage(activityName)
                .setTrigValue(duaring)
                .setModuleDetail(moduleDetail)
                .setFunctionName(functionName);
    }
    /**
     * 扩展 IAttr 信息
     * <p> 注：各个实现IAttr的类如：DataAttr是直接对应数据库表格字段的 </p>
     * <p> 此函数作用为：添加额外的基础采集信息，如：DataAttr等 </p>
     * <p> 可以覆盖默认的系统采集数据，如：MachineAttr,OtherAttr等 </p>
     */
    @Override
    protected void packagExtendAttr() {
        addAttr(dataAttr);
    }
    /**
     * 扩展字段,必须json格式
     * <p> 如果有额外信息，请在此返回 </p>
     * @return
     */
    @Override
    protected String getJsonExtend() {
        return extend;
    }

    public PageEvent setActivity(String activity) {
        this.activityName = activity;
        return this;
    }

    public PageEvent setFunctionName(String functionName) {
        this.functionName = functionName;
        return this;
    }

    public PageEvent setModuleDetail(String moduleDetail) {
        this.moduleDetail = moduleDetail;
        return this;
    }

    public PageEvent setDuaring(String duaring) {
        this.duaring = duaring;
        return this;
    }

    public PageEvent setDataAttr(DataAttr dataAttr) {
        this.dataAttr = dataAttr;
        return this;
    }

    public PageEvent setExtend(String extend) {
        this.extend = extend;
        return this;
    }

    /**扩展字段*/
    public PageEvent setExtend(Map map) {
        this.extend = hashMap2Json(map);
        return this;
    }
}
