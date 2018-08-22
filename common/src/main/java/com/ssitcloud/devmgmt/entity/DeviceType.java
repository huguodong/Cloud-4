package com.ssitcloud.devmgmt.entity;


public class DeviceType {
	private String library_idx; // 图书馆idx
	private Integer device_model_idx;// 设备类型码
	private String device_type; // 设备类型
	private String device_type_desc; // 设备类型描述

	public String getLibrary_idx() {
		return library_idx;
	}

	public void setLibrary_idx(String library_idx) {
		this.library_idx = library_idx;
	}

	public Integer getDevice_model_idx() {
		return device_model_idx;
	}

	public void setDevice_model_idx(Integer device_model_idx) {
		this.device_model_idx = device_model_idx;
	}

	public String getDevice_type() {
		return device_type;
	}

	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}

	public String getDevice_type_desc() {
		return device_type_desc;
	}

	public void setDevice_type_desc(String device_type_desc) {
		this.device_type_desc = device_type_desc;
	}
}
