package com.ssitcloud.entity.page;

import com.ssitcloud.common.entity.DatagridPageEntity;

/**
 * 
 * 设备监控配置模板页面  实体
 * @author lbh
 *
 */
public class DeviceMonTempPageEntity extends DatagridPageEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//监控配置模板IDX
	private Integer device_mon_tpl_idx;
	//监控配置模板ID
	private String device_mon_tpl_id;
	//监控配置模板ID名称
	private String device_mon_tpl_desc;
	//设备类型IDX
	private Integer device_mon_type_idx;
	//设备类型IDX
	private Integer meta_devicetype_idx;
	//设备类型
	private String device_type;
	//设备类型名称
	private String device_type_desc;
	//模板内容（logic_obj_desc concat）
	private String temp_content;
	
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
	public String getTemp_content() {
		return temp_content;
	}
	public void setTemp_content(String temp_content) {
		this.temp_content = temp_content;
	}
	public Integer getMeta_devicetype_idx() {
		return meta_devicetype_idx;
	}
	public void setMeta_devicetype_idx(Integer meta_devicetype_idx) {
		this.meta_devicetype_idx = meta_devicetype_idx;
	}
	

}
