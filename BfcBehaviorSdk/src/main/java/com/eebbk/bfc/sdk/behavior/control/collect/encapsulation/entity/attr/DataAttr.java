package com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr;

import android.content.ContentValues;

import com.eebbk.bfc.sdk.behavior.db.constant.BFCColumns;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.interfaces.IAttr;

public class DataAttr implements IAttr {
	/**
	 * 数据ID
	 */
	String dataId = "";
	/**
	 * 数据标题
	 */
	String dataTitle = "";
	/**
	 * 数据版本
	 */
	String dataEdition = "";
	/**
	 * 数据类型
	 */
	String dataType = "";
	/**
	 * 数据年级
	 */
	String dataGrade = "";
	/**
	 * 数据科目
	 */
	String dataSubject = "";
	/**
	 * 数据出版者
	 */
	String dataPublisher = "";
	/**
	 * 数据扩展
	 */
	String dataExtend = "";
	
	public DataAttr(String dataId, String dataTitle, String dataEdition,
			String dataType, String dataGrade, String dataSubject,
			String dataPublisher, String dataExtend) {
		super();
		this.dataId = dataId;
		this.dataTitle = dataTitle;
		this.dataEdition = dataEdition;
		this.dataType = dataType;
		this.dataGrade = dataGrade;
		this.dataSubject = dataSubject;
		this.dataPublisher = dataPublisher;
		this.dataExtend = dataExtend;
	}

	@Override
	public void insert(ContentValues values) {
		values.put(BFCColumns.COLUMN_DA_DATAID, dataId);
		values.put(BFCColumns.COLUMN_DA_DATATITLE, dataTitle);
		values.put(BFCColumns.COLUMN_DA_DATAEDITION, dataEdition);
		values.put(BFCColumns.COLUMN_DA_DATATYPE, dataType);
		values.put(BFCColumns.COLUMN_DA_DATAGRADE, dataGrade);
		values.put(BFCColumns.COLUMN_DA_DATASUBJECT, dataSubject);
		values.put(BFCColumns.COLUMN_DA_DATAPUBLISHER, dataPublisher);
		values.put(BFCColumns.COLUMN_DA_DATAEXTEND, dataExtend);
	}
	
	public DataAttr(){
		super();
	}

	public DataAttr setDataId(String dataId) {
		this.dataId = dataId;
		return this;
	}

	public DataAttr setDataTitle(String dataTitle) {
		this.dataTitle = dataTitle;
		return this;
	}

	public DataAttr setDataEdition(String dataEdition) {
		this.dataEdition = dataEdition;
		return this;
	}

	public DataAttr setDataType(String dataType) {
		this.dataType = dataType;
		return this;
	}

	public DataAttr setDataGrade(String dataGrade) {
		this.dataGrade = dataGrade;
		return this;
	}

	public DataAttr setDataSubject(String dataSubject) {
		this.dataSubject = dataSubject;
		return this;
	}

	public DataAttr setDataPublisher(String dataPublisher) {
		this.dataPublisher = dataPublisher;
		return this;
	}

	public DataAttr setDataExtend(String dataExtend) {
		this.dataExtend = dataExtend;
		return this;
	}

}
