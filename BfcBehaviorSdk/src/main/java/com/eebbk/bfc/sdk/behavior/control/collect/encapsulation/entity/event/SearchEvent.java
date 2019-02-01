package com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event;

import android.text.TextUtils;

import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.constant.EType;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.DataAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.EventAttr;

import org.json.JSONException;
import org.json.JSONObject;

public class SearchEvent extends AEvent<SearchEvent> {
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
	 * 搜索关键字
	 */
	public String keyWrod;
	/**
	 * 搜索结果
	 */
	public String resultCount;
	/**
	 * 数据属性
	 */
	public DataAttr dataAttr;

	/**
	 * 设置事件类型
	 * @return 参考EType
	 */
	@Override
	public int eventType() {
		return EType.TYPE_SEARCH;
	}
	/**
	 * 设置事件类型名称
	 * @return 参考EType
	 */
	@Override
	public String eventName() {
		return EType.NAME_SEARCH;
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
				.setTrigValue(keyWrod);
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
		return toJsonstr(resultCount);
	}

	private String toJsonstr(String str){
		String jsonstr="";
		if(!TextUtils.isEmpty(str)){
			JSONObject jsonObject = new JSONObject();
//			JsonObject jsonObject=new JsonObject();
			try {
				jsonObject.put("resultCount", str);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			jsonstr=jsonObject.toString();
		}
		return jsonstr;
	}

	public SearchEvent setActivity(String activity) {
		this.activity = activity;
		return this;
	}

	public SearchEvent setFunctionName(String functionName) {
		this.functionName = functionName;
		return this;
	}

	public SearchEvent setModuleDetail(String moduleDetail) {
		this.moduleDetail = moduleDetail;
		return this;
	}

	public SearchEvent setKeyWrod(String keyWrod) {
		this.keyWrod = keyWrod;
		return this;
	}

	public SearchEvent setResultCount(String resultCount) {
		this.resultCount = resultCount;
		return this;
	}

	public SearchEvent setDataAttr(DataAttr dataAttr) {
		this.dataAttr = dataAttr;
		return this;
	}
}
