package com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr;

import android.content.ContentValues;

import com.eebbk.bfc.sdk.behavior.db.constant.BFCColumns;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.interfaces.IAttr;


public class ApplicationAttr implements IAttr {
	/**
	 * 应用ID
	 */
	private String appId = "";
	/**
	 * 应用版本
	 */
	private String appVersion = "";
	/**
	 * 应用包名
	 */
	private String packageName = "";
	/**
	 * 应用名
	 */
	private String moduleName = "";
	/**
	 * 渠道ID
	 */
	private String channleId="";

	@Override
	public void insert(ContentValues values) {
		values.put(BFCColumns.COLUMN_AA_APPID, appId);
		values.put(BFCColumns.COLUMN_AA_APPVER, appVersion);
		values.put(BFCColumns.COLUMN_AA_PACKAGENAME, packageName);
		values.put(BFCColumns.COLUMN_AA_MODULENAME, moduleName);
		values.put(BFCColumns.COLUMN_OA_CHANNELID, channleId);
	}
	
	public ApplicationAttr setAppId(String appId){
		this.appId = appId;
		return this;
	}
	
	public ApplicationAttr setAppVersion(String appVersion){
		this.appVersion = appVersion;
		return this;
	}
	
	public ApplicationAttr setPackageName(String packageName){
		this.packageName = packageName;
		return this;
	}
	
	public ApplicationAttr setModuleName(String moduleName){
		this.moduleName = moduleName;
		return this;
	}
	public ApplicationAttr setchannleId(String channleId){
		this.channleId = channleId;
		return this;
	}

	public String getAppId() {
		return appId;
	}

}
