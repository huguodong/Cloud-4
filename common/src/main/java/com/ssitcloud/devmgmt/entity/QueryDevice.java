package com.ssitcloud.devmgmt.entity;

public class QueryDevice {
	private int device_idx;// 设备idx
	private String library_idx; // 图书馆
	private String device_model_idx; // 设备的id号
	private String device_name; // 设备名

	public int getDevice_idx() {
		return device_idx;
	}

	public void setDevice_idx(int device_idx) {
		this.device_idx = device_idx;
	}

	public String getLibrary_idx() {
		return library_idx;
	}

	public void setLibrary_idx(String library_idx) {
		this.library_idx = library_idx;
	}

	public String getDevice_model_idx() {
		return device_model_idx;
	}

	public void setDevice_model_idx(String device_model_idx) {
		this.device_model_idx = device_model_idx;
	}

	public String getDevice_name() {
		return device_name;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
}
