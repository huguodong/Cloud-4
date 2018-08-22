package com.ssitcloud.devmgmt.entity;

/**
 * 
 设备运行模板<br/>
 
device_run_template
<p>
device_tpl_idx	int(11) NOT NULL
device_tpl_ID	varchar(10) NULL模板ID
device_tpl_desc	varchar(100) NULL模板描述
device_type	varchar(100) NULL设备类型
</p> 
*/
public class DeviceRunTempEntity{
	
	
	private Integer device_tpl_idx;
	private String device_tpl_ID;
	private String device_tpl_desc;
	private String device_type;
	public DeviceRunTempEntity(){};
	public DeviceRunTempEntity(Integer device_tpl_idx){
		this.device_tpl_idx=device_tpl_idx;
	};
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
	public String getDevice_type() {
		return device_type;
	}
	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}
	
}
