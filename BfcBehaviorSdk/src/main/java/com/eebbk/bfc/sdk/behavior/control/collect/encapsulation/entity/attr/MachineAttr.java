package com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr;

import android.content.ContentValues;

import com.eebbk.bfc.sdk.behavior.db.constant.BFCColumns;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.interfaces.IAttr;

public class MachineAttr implements IAttr {
	/**
	 * 序列号
	 */
	private String mId = "";
	/**
	 * 设备名
	 */
	private String devName = "";
	/**
	 * 系统版本名
	 */
	private String osVer = "";
	/**
	 * 厂商
	 */
	private String brand = "";

	@Override
	public void insert(ContentValues values) {
		values.put(BFCColumns.COLUMN_MA_MID, mId);
		values.put(BFCColumns.COLUMN_MA_OSVERSION, osVer);
		values.put(BFCColumns.COLUMN_MA_DEVICE, devName);
		values.put(BFCColumns.COLUMN_MA_BRAND, brand);
	}

	public MachineAttr setmId(String mId) {
		this.mId = mId;
		return this;
	}

	public MachineAttr setDevName(String devName) {
		this.devName = devName;
		return this;
	}

	public MachineAttr setOsVersion(String osVersion) {
		this.osVer = osVersion;
		return this;
	}

	public MachineAttr setBrand(String brand) {
		this.brand = brand;
		return this;
	}

}
