package com.ssitcloud.entity;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.annotate.JsonRootName;

/**
 * 
 * @package: com.ssitcloud.entity
 * @classFile: DeviceDBSyncTempEntity
 * @author: liuBh
 * @description: TODO
 */
@JsonRootName(value="device_dbsync_template")
public class DeviceDBSyncTempEntity {
	/**
	    device_tpl_idx	int(11) NOT NULL
		device_tpl_ID	varchar(10) NULL模板ID
		device_tpl_desc	varchar(100) NULL模板描述
		device_type	    int(11) NULL设备类型IDX
	 */
	private Integer device_tpl_idx;
	private String device_tpl_ID;
	private String device_tpl_desc;
	private Integer device_type;
	
	public DeviceDBSyncTempEntity(){}
	public DeviceDBSyncTempEntity(Integer device_tpl_idx){
		this.device_tpl_idx=device_tpl_idx;
	}
	public Integer getDevice_tpl_idx() {
		return device_tpl_idx;
	}
	public void setDevice_tpl_idx(Integer device_tpl_idx) {
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
	public Integer getDevice_type() {
		return device_type;
	}
	public void setDevice_type(Integer device_type) {
		this.device_type = device_type;
	}

}
