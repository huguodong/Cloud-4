package com.ssitcloud.dblib.entity;

import java.sql.Timestamp;




public class DeviceRunConfigEntity{

	
	/**
	 * 
	device_tpl_type	int(11) NOT NULL模板区分 0模板 1数据
	device_run_id	int(11) NOT NULL模板ID或设备ID
	library_idx		int(11) NOT NULL馆ID
	run_config_idx	int(11) NOT NULL设备端参数配置初始数据
	run_conf_value	mediumtext NOT NULL基础配置参数
	updatetime		timestamp NOT NULL
	 */
	private Integer  device_tpl_type;
	private Integer  device_run_id;
	private Integer  library_idx;
	private Integer  run_config_idx;//relation table idx
	private String  run_conf_value;
	private Timestamp  updatetime;
	
	private String run_conf_type;
	private String run_conf_type_desc;
	
	
	public static final Integer DEVICE_TPL_TYPE_IS_MODEL=0;
	public static final Integer DEVICE_TPL_TYPE_IS_DATA=1;

	public DeviceRunConfigEntity(){}
	
	public DeviceRunConfigEntity(Integer device_run_id){
		this.device_run_id=device_run_id;
	}
	public DeviceRunConfigEntity(Integer device_run_id,Integer  device_tpl_type){
		this.device_run_id=device_run_id;
		this.device_tpl_type=device_tpl_type;
	}
	public Integer getDevice_tpl_type() {
		return device_tpl_type;
	}
	public void setDevice_tpl_type(Integer device_tpl_type) {
		this.device_tpl_type = device_tpl_type;
	}
	public Integer getDevice_run_id() {
		return device_run_id;
	}
	public void setDevice_run_id(Integer device_run_id) {
		this.device_run_id = device_run_id;
	}
	public Integer getLibrary_idx() {
		return library_idx;
	}
	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}
	public Integer getRun_config_idx() {
		return run_config_idx;
	}
	public void setRun_config_idx(Integer run_config_idx) {
		this.run_config_idx = run_config_idx;
	}
	public String getRun_conf_value() {
		return run_conf_value;
	}
	public void setRun_conf_value(String run_conf_value) {
		this.run_conf_value = run_conf_value;
	}
	public Timestamp getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}
	public String getRun_conf_type_desc() {
		return run_conf_type_desc;
	}
	public void setRun_conf_type_desc(String run_conf_type_desc) {
		this.run_conf_type_desc = run_conf_type_desc;
	}
	public String getRun_conf_type() {
		return run_conf_type;
	}
	public void setRun_conf_type(String run_conf_type) {
		this.run_conf_type = run_conf_type;
	}
	
	
}
