package com.ssitcloud.entity;

/**
 * 
 * @comment 设备分组关联表。表名：rel_device_group
 * 
 * @author hwl
 * @data 2016年4月6日
 */
public class RelDeviceGroupEntity {

	/* 自增长id */
	private Integer rel_device_idx;
	/* 设备组id */
	private Integer device_group_idx;
	/* 设备id */
	private Integer device_idx;
	/* 图书馆id */
	private Integer library_idx;

	public Integer getRel_device_idx() {
		return rel_device_idx;
	}

	public void setRel_device_idx(Integer rel_device_idx) {
		this.rel_device_idx = rel_device_idx;
	}

	public Integer getDevice_group_idx() {
		return device_group_idx;
	}

	public void setDevice_group_idx(Integer device_group_idx) {
		this.device_group_idx = device_group_idx;
	}

	public Integer getDevice_idx() {
		return device_idx;
	}

	public void setDevice_idx(Integer device_idx) {
		this.device_idx = device_idx;
	}

	public Integer getLibrary_idx() {
		return library_idx;
	}

	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}

}
