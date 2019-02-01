package com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity;

import java.io.Serializable;

public class RealtimeReportEntity implements Serializable{
	private static final long serialVersionUID = 1L;
    public int eventType;
    public String activity;
    public String functionName;
    public String moduleDetail;
}
