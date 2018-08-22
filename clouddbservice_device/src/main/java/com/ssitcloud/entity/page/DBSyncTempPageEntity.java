package com.ssitcloud.entity.page;

import com.ssitcloud.common.entity.DatagridPageEntity;

public class DBSyncTempPageEntity extends DatagridPageEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//DeviceDBSyncTempEntity中数据
	private String device_tpl_ID;
	private String device_tpl_desc; 
	private Integer device_tpl_idx;
	private Integer device_type;
	//DeviceDBSyncConfigEntity中数据
	private Integer device_dbsync_id;
	private Integer device_tpl_type;

	
	
	public Integer getDevice_tpl_idx() {
		return device_tpl_idx;
	}

	public void setDevice_tpl_idx(Integer device_tpl_idx) {
		this.device_tpl_idx = device_tpl_idx;
	}

	public Integer getDevice_type() {
		return device_type;
	}

	public void setDevice_type(Integer device_type) {
		this.device_type = device_type;
	}

	public Integer getDevice_dbsync_id() {
		return device_dbsync_id;
	}

	public void setDevice_dbsync_id(Integer device_dbsync_id) {
		this.device_dbsync_id = device_dbsync_id;
	}

	public Integer getDevice_tpl_type() {
		return device_tpl_type;
	}

	public void setDevice_tpl_type(Integer device_tpl_type) {
		this.device_tpl_type = device_tpl_type;
	}

	public String getDevice_tpl_ID() {
		return device_tpl_ID;
	}

	public void setDevice_tpl_ID(String device_tpl_ID) {
		this.device_tpl_ID = device_tpl_ID;
	}

	public String getDevice_tpl_desc() {
		return device_tpl_desc;
	}

	public void setDevice_tpl_desc(String device_tpl_desc) {
		this.device_tpl_desc = device_tpl_desc;
	}

	
}
