package com.eebbk.bfc.sdk.behavior.control.collect.interfaces;

import android.content.Context;

import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.DataAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.EventAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.OtherAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.UserAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.ClickEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.CountEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.CustomEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.ExceptionEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.MonitorURLEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.SearchEvent;

public interface ICollector {

	/**
	 * app启动事件
	 */
	void appLaunch();
	/**
	 * app启动事件
	 * @param tValue 进入的时间
	 * @param curActivityName 进入的activity
	 */
	@Deprecated
	void appLaunch(
			String tValue,
			String curActivityName);
	/**
	 * 点击事件(次数)
	 * @param functionName 功能名称
	 * @param context Context
	 */
	@Deprecated
	void clickEvent(
			String functionName,
			Context context);
	/**
	 * 点击事件(次数)
	 * @param functionName 功能名称
	 * @param curActivityName 进入的activity
	 */
	@Deprecated
	void clickEvent(
			String functionName,
			String curActivityName);
	/**
	 * 点击事件（次数）
	 * @param functionName 功能名称
	 * @param curActivityName 进入的activity
	 * @param extend 扩展信息
	 */
	@Deprecated
	void clickEvent(
			String functionName,
			String curActivityName,
			String extend  );
	/**
	 * 点击事件（次数）
	 * @param functionName 功能名称
	 * @param curActivityName 进入的activity
	 * @param extend 扩展信息
	 * @param moduleDetail 模块详细
	 */
	@Deprecated
	void clickEvent(
			String functionName,
			String curActivityName,
			String extend ,
			String moduleDetail);
	/**
	 * 点击事件（次数）
	 * @param functionName 功能名称
	 * @param curActivityName 进入的activity
	 * @param extend 扩展信息
	 * @param moduleDetail 模块详细
	 * @param dataAttr 数据属性
	 */
	@Deprecated
	void clickEvent(
			String functionName,
			String curActivityName,
			String extend ,
			String moduleDetail,
			DataAttr dataAttr);
	/**
	 * 点击事件（次数）
	 *
	 * @param eventAttr 事件属性
	 * @param dataAttr 数据属性
	 */
	@Deprecated
	void clickEvent(
			EventAttr eventAttr,
			DataAttr dataAttr);
	/**
	 * 点击事件（次数）
	 *
	 * @param eventAttr 事件属性
	 * @param dataAttr 数据属性
	 * @param otherAttr 其它属性
	 */
	@Deprecated
	void clickEvent(
			EventAttr eventAttr,
			DataAttr dataAttr,
			OtherAttr otherAttr);
	/**
	 * 计次事件
	 * @param clickEvent 事件属性
	 */
	void clickEvent(ClickEvent clickEvent);
	/**
	 * 自定义事件(次数)
	 * @param eventName 事件名称
	 * @param functionName 功能名称
	 * @param curActivityName 当前activity
	 */
	@Deprecated
	void customEvent(
			String eventName,
			String functionName,
			String curActivityName);
	/**
	 * 自定义事件带扩展信息（次数 + 附加信息）
	 * @param eventName  事件名称
	 * @param functionName 功能名称
	 * @param curActivityName 当前activity
	 * @param extend 扩展信息
	 */
	@Deprecated
	void customEvent(
			String eventName,
			String functionName,
			String curActivityName,
			String extend);
	/**
	 * 自定义事件带扩展信息（次数 + 附加信息）
	 * @param eventName 事件名称
	 * @param functionName 功能名称
	 * @param curActivityName 当前activity
	 * @param extend 扩展信息
	 * @param moduleDetail 模块详细
	 */
	@Deprecated
	void customEvent(
			String eventName,
			String functionName,
			String curActivityName,
			String extend ,
			String moduleDetail);
	/**
	 * 自定义事件带扩展信息（次数 + 附加信息）
	 * @param eventName 事件名称
	 * @param functionName 功能名称
	 * @param curActivityName 当前activity
	 * @param extend 扩展信息
	 * @param moduleDetail 模块详细
	 * @param dataAttr 数据属性
	 */
	@Deprecated
	void customEvent(
			String eventName,
			String functionName,
			String curActivityName,
			String extend ,
			String moduleDetail,
			DataAttr dataAttr);
	/**
	 * 自定义事件带扩展信息（次数 + 附加信息）
	 * @param eventAttr  事件属性
	 * @param dataAttr 数据属性
	 */
	@Deprecated
	void customEvent(
			EventAttr eventAttr,
			DataAttr dataAttr);
	/**
	 * 自定义事件带扩展信息（次数 + 附加信息）
	 * @param eventAttr 事件属性
	 * @param dataAttr 数据属性
	 * @param otherAttr 其它属性
	 */
	@Deprecated
	void customEvent(
			EventAttr eventAttr,
			DataAttr dataAttr,
			OtherAttr otherAttr);
	/**
	 * 自定义事件带扩展信息（次数 + 附加信息）
	 * @param customEvent 事件信息
	 */
	void customEvent(CustomEvent customEvent);
	/**
	 * 计数事件
	 * @param countEvent
	 */
	void countEvent(CountEvent countEvent);
	/**
	 * 记录功能点开始（时长 + 次数）,begin 与 end 接口要配对使用
	 * @param functionName 功能名称
	 * @param curActivityName 当前界面名称
	 */
	@Deprecated
	void recordFunctionBegin(
			String functionName ,
			String curActivityName);
	/**
	 * 记录功能点开始（时长 + 次数）,begin 与 end 接口要配对使用
	 * @param functionName 功能名称
	 * @param curActivityName 当前界面名称
	 * @param extend 扩展信息
	 * @param moduleDetail 模块详细
	 */
	@Deprecated
	void recordFunctionBegin(
			String functionName ,
			String curActivityName ,
			String extend ,
			String moduleDetail);
	/**
	 * 记录功能点开始（时长 + 次数）,begin 与 end 接口要配对使用
	 * @param functionName 功能名称
	 * @param curActivityName 当前界面名称
	 * @param extend 扩展信息
	 * @param moduleDetail 模块详细
	 * @param dataAttr 数据属性
	 */
	@Deprecated
	void recordFunctionBegin(
			String functionName ,
			String curActivityName ,
			String extend ,
			String moduleDetail,
			DataAttr dataAttr);
	/**
	 * 记录功能点开始（时长 + 次数）,begin 与 end 接口要配对使用
	 * @param eventAttr 事件属性
	 * @param dataAttr 数据属性
	 */
	@Deprecated
	void recordFunctionBegin(
			EventAttr eventAttr,
			DataAttr dataAttr);
	/**
	 * 记录功能点开始（时长 + 次数）,begin 与 end 接口要配对使用
	 * @param eventAttr 事件属性
	 * @param dataAttr 数据属性
	 * 	@param otherAttr 其它属性
	 */
	@Deprecated
	void recordFunctionBegin(
			EventAttr eventAttr,
			DataAttr dataAttr,
			OtherAttr otherAttr);
	/**
	 * 记录功能点结束（时长 + 次数）
	 * @param functionName 功能名称
	 * @param curActivityName 当前界面名称
	 */
	@Deprecated
	void recordFunctionEnd(
			String functionName ,
			String curActivityName );
	/**
	 * 记录功能点结束（时长 + 次数）
	 * @param functionName 功能名称
	 * @param curActivityName 当前界面名称
	 * @param extend 扩展信息
	 * @param moduleDetail 模块详细
	 */
	@Deprecated
	void recordFunctionEnd(
			String functionName ,
			String curActivityName ,
			String extend ,
			String moduleDetail);
	/**
	 * 记录功能点结束（时长 + 次数）
	 * @param functionName 功能名称
	 * @param curActivityName 当前界面名称
	 * @param extend 扩展信息
	 * @param moduleDetail 模块详细
	 * @param dataAttr 数据属性
	 */
	@Deprecated
	void recordFunctionEnd(
			String functionName ,
			String curActivityName ,
			String extend ,
			String moduleDetail,
			DataAttr dataAttr);
	/**
	 * 记录功能点结束（时长 + 次数）
	 * @param eventAttr 事件属性
	 * @param dataAttr 数据属性
	 */
	@Deprecated
	void recordFunctionEnd(
			EventAttr eventAttr,
			DataAttr dataAttr);
	/**
	 * 记录功能点结束（时长 + 次数）
	 * @param eventAttr 事件属性
	 * @param dataAttr 数据属性
	 * @param otherAttr 其它属性
	 */
	@Deprecated
	void recordFunctionEnd(
			EventAttr eventAttr,
			DataAttr dataAttr,
			OtherAttr otherAttr);
	/**
	 * 搜索事件（次数 + 内容）
	 * @param functionName 功能名称
	 * @param content 搜索的内容
	 * @param context Context
	 */
	@Deprecated
	void searchEvent(
			String functionName ,
			String content,
			Context context);
	/**
	 * 搜索事件（次数 + 内容）
	 * @param functionName 功能名称
	 * @param content 搜索的内容
	 * @param curActivityName 当前activity
	 */
	@Deprecated
	void searchEvent(
			String functionName ,
			String content,
			String curActivityName);
	/**
	 * 搜索事件（次数 + 内容）
	 * @param functionName 功能名称
	 * @param content 搜索的内容
	 * @param curActivityName 当前activity
	 * @param extend 扩展信息
	 */
	@Deprecated
	void searchEvent(
			String functionName ,
			String content,
			String curActivityName,
			String extend );
	/**
	 * 搜索事件（次数 + 内容 + 附加信息）
	 * @param functionName 功能名称
	 * @param content 搜索的内容
	 * @param curActivityName 进入的activity
	 * @param extend 扩展信息
	 * @param moduleDetail 模块详细
	 */
	@Deprecated
	void searchEvent(
			String functionName ,
			String content,
			String curActivityName,
			String extend ,
			String moduleDetail);
	/**
	 * 搜索事件（次数 + 内容 + 附加信息 + 数据属性）
	 * @param functionName 功能名称
	 * @param content 搜索的内容
	 * @param curActivityName 进入的activity
	 * @param extend 扩展信息
	 * @param moduleDetail 模块详细
	 * @param dataAttr 数据属性
	 */
	@Deprecated
	void searchEvent(
			String functionName ,
			String content,
			String curActivityName,
			String extend ,
			String moduleDetail,
			DataAttr dataAttr);
	/**
	 * 搜索事件（次数 + 内容 + 附加信息 + 数据属性）
	 * @param eventAttr 事件属性
	 * @param dataAttr 数据属性
	 */
	@Deprecated
	void searchEvent(
			EventAttr eventAttr,
			DataAttr dataAttr);
	/**
	 * 搜索事件（次数 + 内容 + 附加信息 + 数据属性）
	 * @param eventAttr 事件属性
	 * @param dataAttr 数据属性
	 * @param otherAttr 其它属性
	 */
	@Deprecated
	void searchEvent(
			EventAttr eventAttr,
			DataAttr dataAttr,
			OtherAttr otherAttr);
	/**
	 * 搜索事件（次数 + 内容 + 附加信息 + 数据属性）
	 * @param searchEvent 事件属性
	 */
	void searchEvent(SearchEvent searchEvent);
	/**
	 * 界面进入事件
	 * @param page 为 activity或fragment
	 */
	void pageBegin(String page);
	/**
	 * 界面进入事件
	 * @param page 为 activity或fragment
	 * @param functionName 为 功能名称
	 * @param moduleDetail 为 模块详细
	 */
	void pageBegin(String page,String functionName,String moduleDetail);
	/**
	 * 界面退出事件
	 * @param page 为 activity或fragment
	 */
	boolean pageEnd(String page);
	/**
	 * 界面退出事件
	 * @param page 为 activity或fragment
	 * @param functionName 为 功能名称
	 * @param moduleDetail 为 模块详细
	 */
	boolean pageEnd(String page,String functionName,String moduleDetail);
	/**
	 * 界面退出事件
	 * @param page 为 activity或fragment
	 * @param functionName 为 功能名称
	 * @param moduleDetail 为 模块详细
	 * @param dataAttr 数据属性
	 * @param extend 扩展属性
	 */
	boolean pageEnd(String page, String functionName, String moduleDetail,
				 DataAttr dataAttr, String extend);
	/**
	 * 界面时长统计
	 * @param curActivityName 当前的activity
	 * @param duaring 本次在该 activity 的停留时间
	 */
	@Deprecated
	void activityPaused(
			String curActivityName,
			long duaring,
			String pidName,
			String label);
	/**
	 * 异常捕获事件
	 * @param eventAttr 事件属性
	 * @param dataAttr 数据属性
	 */
	@Deprecated
	void exceptionEvent(
			EventAttr eventAttr,
			DataAttr dataAttr);
	/**
	 * 异常捕获事件
	 * @param exceptionInfo 事件属性
	 */
	void exceptionEvent(ExceptionEvent exceptionInfo);
    void monitorURLEvent(MonitorURLEvent monitorurlevent);
	/**
	 * 主动调用实时上传
	 */
	void realTime2Upload();
	/**
	 * 用户信息初始化
	 * @param userinfo 用户属性
	 */
	void initUserInfo(UserAttr userinfo);
	/**
	 * 获取行为采集版本
	 * @return
     */
	String getBehaviorVersion();
}
