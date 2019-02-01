package com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event;

import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.constant.EType;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.EventAttr;

public class MonitorURLEvent extends AEvent<MonitorURLEvent> {
	public static final long serialVersionUID = 1L;
	/**
	 * URL监控
	 */
	private String functionName;
	/**
	 * 要监测的URL
	 */
	public String moduleDetail;
	/**
	 * 为接口响应时间，单位毫秒
	 */
	public String trigValue;

	/**
	 * 设置事件类型
	 * @return 参考EType
	 */
	@Override
	public int eventType() {
		return EType.TYPE_MONITOR_URL;
	}
	/**
	 * 设置事件类型名称
	 * @return 参考EType
	 */
	@Override
	public String eventName() {
		return EType.FUNCTION_MONITOR_URL;
	}
	/**
	 * 封装app采集信息
	 * <P> 此函数作用为：封装app采集信息 <P/>
	 * @return
	 */
	@Override
	protected EventAttr packagEventAttr() {
		return new EventAttr()
				.setEventName(EType.NAME_MONITOR_URL)
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

	public MonitorURLEvent setFunctionName(String functionName) {
		this.functionName = functionName;
		return this;
	}

	public MonitorURLEvent setModuleDetail(String moduleDetail) {
		this.moduleDetail = moduleDetail;
		return this;
	}

	public MonitorURLEvent setTrigValue(String trigValue) {
		this.trigValue = trigValue;
		return this;
	}
}
