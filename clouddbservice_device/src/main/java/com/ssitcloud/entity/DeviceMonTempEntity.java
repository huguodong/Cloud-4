package com.ssitcloud.entity;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.annotate.JsonRootName;

@JsonRootName(value="device_monitor_template")
public class DeviceMonTempEntity{
	
	
	/**
	 * device_monitor_template
	 *  device_mon_tpl_idx	int(11) NOT NULL
		device_mon_tpl_id	varchar(10) NOT NULL显示模板ID
		device_mon_tpl_desc	varchar(500) NOT NULL显示模板描述
		device_mon_type_idx	int(11) NOT NULL设备类型
	 */
	private Integer device_mon_tpl_idx;
	private String device_mon_tpl_id;
	private String device_mon_tpl_desc;
	private Integer device_mon_type_idx;
	public Integer getDevice_mon_tpl_idx() {
		return device_mon_tpl_idx;
	}
	public void setDevice_mon_tpl_idx(Integer device_mon_tpl_idx) {
		this.device_mon_tpl_idx = device_mon_tpl_idx;
	}
	public String getDevice_mon_tpl_id() {
		return device_mon_tpl_id;
	}
	public void setDevice_mon_tpl_id(String device_mon_tpl_id) {
		this.device_mon_tpl_id = device_mon_tpl_id;
	}
	public String getDevice_mon_tpl_desc() {
		return device_mon_tpl_desc;
	}
	public void setDevice_mon_tpl_desc(String device_mon_tpl_desc) {
		this.device_mon_tpl_desc = device_mon_tpl_desc;
	}
	public Integer getDevice_mon_type_idx() {
		return device_mon_type_idx;
	}
	public void setDevice_mon_type_idx(Integer device_mon_type_idx) {
		this.device_mon_type_idx = device_mon_type_idx;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof DeviceMonTempEntity){
			DeviceMonTempEntity d=(DeviceMonTempEntity)obj;
			boolean eq=StringUtils.equals(d.getDevice_mon_tpl_id(), this.getDevice_mon_tpl_id());
			if(!eq) return eq;
			eq=StringUtils.equals(d.getDevice_mon_tpl_desc(), this.getDevice_mon_tpl_desc());
			if(!eq) return eq;
			eq=d.getDevice_mon_type_idx()==this.getDevice_mon_type_idx()?true:false;
			return eq;
		}
		return false;
	}
	
}
