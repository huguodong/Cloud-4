package com.ssitcloud.view.interfaceconfig.entity;

public class ConfigDataEntity {
	private int libId;
	private int deviceId;
	private String context;
	private String createtime;
	private String operator;

	public int getLibId() {
		return libId;
	}

	public void setLibId(int libId) {
		this.libId = libId;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
}
