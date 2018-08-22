package com.ssitcloud.interfaceconfig.entity;

public class ConfigDataEntity {
	private int libId;
	// private String deviceType;
	private Integer deviceIdx;
	private String operation;
	private String createtime;
	private String operator;
	private String operator_type;

	public int getLibId() {
		return libId;
	}

	public void setLibId(int libId) {
		this.libId = libId;
	}

	public Integer getDeviceIdx() {
		return deviceIdx;
	}

	public void setDeviceIdx(Integer deviceIdx) {
		this.deviceIdx = deviceIdx;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
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

	public String getOperator_type() {
		return operator_type;
	}

	public void setOperator_type(String operator_type) {
		this.operator_type = operator_type;
	}

	@Override
	public String toString() {
		return "ConfigDataEntity [libId=" + libId + ", deviceIdx=" + deviceIdx + ", operation=" + operation + ", createtime=" + createtime + ", operator=" + operator + ", operator_type="
				+ operator_type + "]";
	}
}
