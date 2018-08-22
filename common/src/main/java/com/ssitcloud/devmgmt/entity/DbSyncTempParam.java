package com.ssitcloud.devmgmt.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>2016年4月27日 上午11:53:12
 * @author hjc
 */
public class DbSyncTempParam {

	/**
    device_tpl_idx	int(11) NOT NULL
	device_tpl_ID	varchar(10) NULL模板ID
	device_tpl_desc	varchar(100) NULL模板描述
	device_type	    int(11) NULL设备类型IDX
	*/
	/** device_dbsync_template 自增idx */
	private String device_tpl_idx;
	/** 模板id */
	private String device_tpl_ID;
	/** 模板描述 */
	private String device_tpl_desc;
	/** 设备类型 */
	private String device_type;
	
	
	private List<DbSyncTempDetailParam> dbSyncDeatilList = new ArrayList<>();
	
	
	
	public String getDevice_tpl_idx() {
		return device_tpl_idx;
	}
	public void setDevice_tpl_idx(String device_tpl_idx) {
		this.device_tpl_idx = device_tpl_idx;
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
	public String getDevice_type() {
		return device_type;
	}
	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}
	public List<DbSyncTempDetailParam> getDbSyncDeatilList() {
		return dbSyncDeatilList;
	}
	public void setDbSyncDeatilList(List<DbSyncTempDetailParam> dbSyncDeatilList) {
		this.dbSyncDeatilList = dbSyncDeatilList;
	}
	
	
	

}
