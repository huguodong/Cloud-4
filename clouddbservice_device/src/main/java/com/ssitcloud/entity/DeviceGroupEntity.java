package com.ssitcloud.entity;

/**
 * 
 * @comment 设备组表。表名：device_group
 * 
 * @author hwl
 * @data 2016年4月6日
 */
public class DeviceGroupEntity {

	/* 设备组自增长ID */
	private Integer device_group_idx;
	/* 馆id */
	private Integer library_idx;
	/* 设备组名称 */
	private String device_group_name;
	/* 设备组描述 */
	private String device_group_desc;

	private String device_group_id;
	
	private String lib_id;
	
	private Integer version_stamp;
	
	public Integer getDevice_group_idx() {
		return device_group_idx;
	}

	public void setDevice_group_idx(Integer device_group_idx) {
		this.device_group_idx = device_group_idx;
	}

	public Integer getLibrary_idx() {
		return library_idx;
	}

	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}

	public String getDevice_group_name() {
		return device_group_name;
	}

	public void setDevice_group_name(String device_group_name) {
		this.device_group_name = device_group_name;
	}

	public String getDevice_group_desc() {
		return device_group_desc;
	}

	public void setDevice_group_desc(String device_group_desc) {
		this.device_group_desc = device_group_desc;
	}

	public String getDevice_group_id() {
		return device_group_id;
	}

	public void setDevice_group_id(String device_group_id) {
		this.device_group_id = device_group_id;
	}

	public String getLib_id() {
		return lib_id;
	}

	public void setLib_id(String lib_id) {
		this.lib_id = lib_id;
	}

	public Integer getVersion_stamp() {
		return version_stamp;
	}

	public void setVersion_stamp(Integer version_stamp) {
		this.version_stamp = version_stamp;
	}

	
	

}
