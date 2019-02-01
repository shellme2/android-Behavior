package com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr;

import android.content.ContentValues;

import com.eebbk.bfc.sdk.behavior.db.constant.BFCColumns;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.interfaces.IAttr;

public class EventAttr implements IAttr {
	/**
	 * 事件名
	 */
	String eventName = "";
	/**
	 * 功能名
	 */
	String functionName = "";
	/**
	 * 事件类型
	 */
	int eventType = 0;
	/**
	 * 触发时间
	 */
	String trigTime = "";
	/**
	 * 触发值
	 */
	String trigValue = "";
	/**
	 * 当前界面
	 */
	String page = "";
	/**
	 * 模块细节
	 */
	String moduleDetail = "";

	public EventAttr(){

	}
	
	public EventAttr(String eventName, String functionName, int type,
			String trigTime, String trigValue, String page,
			String moduleDetail) {
		this.eventName = eventName;
		this.functionName = functionName;
		this.eventType = type;
		this.trigTime = trigTime;
		this.trigValue = trigValue;
		this.page = page;
		this.moduleDetail = moduleDetail;
	}
	
	@Override
	public void insert(ContentValues values) {
		values.put(BFCColumns.COLUMN_EA_EVENTNAME, eventName);
		values.put(BFCColumns.COLUMN_EA_FUNCTIONNAME, functionName);
		values.put(BFCColumns.COLUMN_EA_EVENTTYPE, eventType);
		values.put(BFCColumns.COLUMN_EA_TTIME, trigTime);
		values.put(BFCColumns.COLUMN_EA_TVALUE, trigValue);
		values.put(BFCColumns.COLUMN_EA_PAGE, page);
		values.put(BFCColumns.COLUMN_EA_MODULEDETAIL, moduleDetail);
	}

	public EventAttr setEventType(int eventType) {
		this.eventType = eventType;
		return this;
	}

	public int getEventType(){
		return this.eventType;
	}

	public EventAttr setEventName(String eventName) {
		this.eventName = eventName;
		return this;
	}
   
	public String getFunctionName(){
		return this.functionName;
	}

	public EventAttr setFunctionName(String functionName) {
		this.functionName = functionName;
		return this;
	}

	public EventAttr setTrigTime(String trigTime) {
		this.trigTime = trigTime;
		return this;
	}

	public EventAttr setTrigValue(String trigValue) {
		this.trigValue = trigValue;
		return this;
	}

	public String getPage(){
    	return this.page;
    }

	public EventAttr setPage(String page) {
		this.page = page;
		return this;
	}

	public EventAttr setModuleDetail(String moduleDetail) {
		this.moduleDetail = moduleDetail;
		return this;
	}

	public String getModuleDetail(){
	    	return this.moduleDetail;
	    }

	public String getEventName() {
		return eventName;
	}
}
