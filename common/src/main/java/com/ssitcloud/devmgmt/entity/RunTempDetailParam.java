package com.ssitcloud.devmgmt.entity;

/**
 * <p>2016年4月26日 下午8:10:04
 * @author hjc
 */
public class RunTempDetailParam {

	//device_run_template
	/** device_run_template自增IDX */
	private String device_tpl_idx; 
	/** 模板ID */
	private String device_tpl_ID; 
	/** 模板描述 */
	private String device_tpl_desc; 
	/** 设备类型IDX */
	private String device_type;
	
	
	//device_run_config
//	device_tpl_typeint(11) NOT NULL模板区分 0模板 1数据
//	device_run_idint(11) NOT NULL模板ID或设备ID
//	library_idxint(11) NOT NULL馆ID
//	run_config_idxint(11) NOT NULL设备端参数配置初始数据
//	run_conf_valuemediumtext NOT NULL基础配置参数
//	updatetimetimestamp NOT NULL
	
	/** 模板区分 0模板 1数据 */
	private String device_tpl_type;
	/** 模板IDX或设备IDX 如果 device_tpl_type为0是模板idx，如果是设备则是设备的idx*/
	private String device_run_id;
	/** 馆IDX */
	private String library_idx;
	/** 设备端参数配置初始数据 */
	private String run_config_idx;
	/** 基础配置参数 */
	private String run_conf_value;
	/** device_run_config的更新时间 */
	private String updatetime;
	

//	meta_run_cfg_idxint(11) NOT NULL
//	run_conf_typevarchar(100) NOT NULL
//	run_conf_type_descvarchar(100) NULL
//	updatetimedatetime NULL
	
	//metadata_run_config
	/** */
	private String meta_run_cfg_idx;
	private String run_conf_type;
	private String run_conf_type_desc;
//	private String updatetime;
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
	public String getDevice_tpl_type() {
		return device_tpl_type;
	}
	public void setDevice_tpl_type(String device_tpl_type) {
		this.device_tpl_type = device_tpl_type;
	}
	public String getDevice_run_id() {
		return device_run_id;
	}
	public void setDevice_run_id(String device_run_id) {
		this.device_run_id = device_run_id;
	}
	public String getLibrary_idx() {
		return library_idx;
	}
	public void setLibrary_idx(String library_idx) {
		this.library_idx = library_idx;
	}
	public String getRun_config_idx() {
		return run_config_idx;
	}
	public void setRun_config_idx(String run_config_idx) {
		this.run_config_idx = run_config_idx;
	}
	public String getRun_conf_value() {
		return run_conf_value;
	}
	public void setRun_conf_value(String run_conf_value) {
		this.run_conf_value = run_conf_value;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getMeta_run_cfg_idx() {
		return meta_run_cfg_idx;
	}
	public void setMeta_run_cfg_idx(String meta_run_cfg_idx) {
		this.meta_run_cfg_idx = meta_run_cfg_idx;
	}
	public String getRun_conf_type() {
		return run_conf_type;
	}
	public void setRun_conf_type(String run_conf_type) {
		this.run_conf_type = run_conf_type;
	}
	public String getRun_conf_type_desc() {
		return run_conf_type_desc;
	}
	public void setRun_conf_type_desc(String run_conf_type_desc) {
		this.run_conf_type_desc = run_conf_type_desc;
	}
	
	
	
	
	
}
