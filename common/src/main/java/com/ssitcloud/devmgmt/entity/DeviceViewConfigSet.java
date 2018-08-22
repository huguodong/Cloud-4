package com.ssitcloud.devmgmt.entity;


import java.sql.Timestamp;

public class DeviceViewConfigSet {
	private Integer id;
	private Integer view_config_id;
	private String content;
	private Integer library_idx;
	private String description;
	private Integer operator_idx;
	private Timestamp operate_time;
	private String device_type;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getView_config_id() {
		return view_config_id;
	}
	public void setView_config_id(Integer view_config_id) {
		this.view_config_id = view_config_id;
	}
	
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getLibrary_idx() {
		return library_idx;
	}
	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getOperator_idx() {
		return operator_idx;
	}
	public void setOperator_idx(Integer operator_idx) {
		this.operator_idx = operator_idx;
	}
	public Timestamp getOperate_time() {
		return operate_time;
	}
	public void setOperate_time(Timestamp operate_time) {
		this.operate_time = operate_time;
	}
	public String getDevice_type() {
		return device_type;
	}
	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}
	
}
