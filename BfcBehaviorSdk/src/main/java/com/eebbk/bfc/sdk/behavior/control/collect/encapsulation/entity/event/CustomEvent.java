package com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event;

import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.constant.EType;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.DataAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.EventAttr;

import java.util.Map;

public class CustomEvent extends AEvent<CustomEvent> {
	public static final long serialVersionUID = 1L;
	/**
	 * 进入的activity
	 */
	public String activity;
	/**
	 * 功能名称
	 */
	public String functionName;
	/**
	 * 模块详细
	 */
	public String moduleDetail;
	/**
	 * 触发值
	 */
	public String trigValue;
	/**
	 * 数据属性
	 */
	public DataAttr dataAttr;
	/**
	 * 扩展字段
	 */
	public String extend;
	/**
	 * 事件名
	 * @deprecated 无需设置事件名，统一事件名为“自定义事件”
	 */
	@Deprecated
	public String eventName;
	/**
	 * 事件类型
	 * @deprecated 无需设置事件类型
	 */
	@Deprecated
	public int eventType;

	/**
	 * 设置事件类型
	 * @return 参考EType
	 */
	@Override
	public int eventType() {
		return EType.TYPE_CUSTOM;
	}
	/**
	 * 设置事件类型名称
	 * @return 参考EType
	 */
	@Override
	public String eventName() {
		return EType.NAME_CUSTOM;
	}
	/**
	 * 封装app采集信息
	 * <P> 此函数作用为：封装app采集信息 <P/>
	 * @return
	 */
	@Override
	protected EventAttr packagEventAttr() {
		return new EventAttr()
				.setPage(activity)
				.setFunctionName(functionName)
				.setModuleDetail(moduleDetail)
				.setTrigValue(trigValue);
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

	public CustomEvent setActivity(String activity) {
		this.activity = activity;
		return this;
	}

	public CustomEvent setFunctionName(String functionName) {
		this.functionName = functionName;
		return this;
	}

	public CustomEvent setModuleDetail(String moduleDetail) {
		this.moduleDetail = moduleDetail;
		return this;
	}

	public CustomEvent setTrigValue(String trigValue) {
		this.trigValue = trigValue;
		return this;
	}

	public CustomEvent setDataAttr(DataAttr dataAttr) {
		this.dataAttr = dataAttr;
		return this;
	}

	public CustomEvent setExtend(String extend) {
		this.extend = extend;
		return this;
	}

	/**扩展字段*/
	public CustomEvent setExtend(Map map) {
		this.extend = hashMap2Json(map);
		return this;
	}
}
