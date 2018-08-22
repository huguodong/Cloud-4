package com.ssitcloud.devmgmt.entity;


/**
 * 设备监控模板<br/>
 *  device_monitor_template
 * <p>
 *  device_mon_tpl_idx	int(11) NOT NULL
 *	device_mon_tpl_id	varchar(10) NOT NULL显示模板ID
 *	device_mon_tpl_desc	varchar(500) NOT NULL显示模板描述
 *	device_mon_type_idx	int(11) NOT NULL设备类型
 * </p>
 */
public class DeviceMonTempEntity{
	
	private String device_mon_tpl_idx;
	private String device_mon_tpl_id;
	private String device_mon_tpl_desc;
	private String device_mon_type_idx;
	public String getDevice_mon_tpl_idx() {
		return device_mon_tpl_idx;
	}
	public void setDevice_mon_tpl_idx(String device_mon_tpl_idx) {
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
	public String getDevice_mon_type_idx() {
		return device_mon_type_idx;
	}
	public void setDevice_mon_type_idx(String device_mon_type_idx) {
		this.device_mon_type_idx = device_mon_type_idx;
	}
	
}