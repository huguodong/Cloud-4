package com.ssitcloud.entity;


import java.sql.Timestamp;


public class DeviceExtConfigEntity{
	/**
	 * 
		FieldTypeComment
		library_idx		int(11) NOT NULL
		device_tpl_type	int(11) NOT NULL模板区分 0模板 1数据
		device_ext_id	int(11) NOT NULL外设id
		ext_comm_conf	varchar(100) NULL外设通信方式
		ext_extend_conf	text NULL
		logic_obj_idx	int(11) NOT NULL外设类型
		ext_model_idx	int(11) NOT NULL外设型号
		updatetime	timestamp NULL
	 */
	private Integer library_idx;
	private Integer device_tpl_type;
	private Integer device_ext_id;
	private String ext_comm_conf;
	private String ext_extend_conf;
	private Integer logic_obj_idx;//logic_obj_idx
	private Integer ext_model_idx;//model_idx
	private Timestamp updatetime;
	public static final Integer DEVICE_TPL_TYPE_IS_MODEL=0;
	public static final Integer DEVICE_TPL_TYPE_IS_DATA=1;

	private String ext_model;
	private String ext_model_desc;
	private String ext_model_version;
	private String ext_type;
	
	private String logic_obj;
	private String logic_obj_desc;
	
	public DeviceExtConfigEntity() {
	}
	public DeviceExtConfigEntity(Integer device_ext_id) {
		this.device_ext_id=device_ext_id;
	}
	public DeviceExtConfigEntity(Integer device_ext_id,Integer device_tpl_type) {
		this.device_ext_id=device_ext_id;
		this.device_tpl_type=device_tpl_type;
	}
	public Integer getLibrary_idx() {
		return library_idx;
	}
	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}
	public Integer getDevice_tpl_type() {
		return device_tpl_type;
	}
	public void setDevice_tpl_type(Integer device_tpl_type) {
		this.device_tpl_type = device_tpl_type;
	}
	public Integer getDevice_ext_id() {
		return device_ext_id;
	}
	public void setDevice_ext_id(Integer device_ext_id) {
		this.device_ext_id = device_ext_id;
	}
	public String getExt_comm_conf() {
		return ext_comm_conf;
	}
	public void setExt_comm_conf(String ext_comm_conf) {
		this.ext_comm_conf = ext_comm_conf;
	}
	public String getExt_extend_conf() {
		return ext_extend_conf;
	}
	public void setExt_extend_conf(String ext_extend_conf) {
		this.ext_extend_conf = ext_extend_conf;
	}
	public Integer getLogic_obj_idx() {
		return logic_obj_idx;
	}
	public void setLogic_obj_idx(Integer logic_obj_idx) {
		this.logic_obj_idx = logic_obj_idx;
	}
	public Integer getExt_model_idx() {
		return ext_model_idx;
	}
	public void setExt_model_idx(Integer ext_model_idx) {
		this.ext_model_idx = ext_model_idx;
	}
	public Timestamp getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}
	public String getExt_model() {
		return ext_model;
	}
	public void setExt_model(String ext_model) {
		this.ext_model = ext_model;
	}
	public String getExt_model_desc() {
		return ext_model_desc;
	}
	public void setExt_model_desc(String ext_model_desc) {
		this.ext_model_desc = ext_model_desc;
	}
	public String getExt_model_version() {
		return ext_model_version;
	}
	public void setExt_model_version(String ext_model_version) {
		this.ext_model_version = ext_model_version;
	}
	public String getExt_type() {
		return ext_type;
	}
	public void setExt_type(String ext_type) {
		this.ext_type = ext_type;
	}
	public String getLogic_obj() {
		return logic_obj;
	}
	public void setLogic_obj(String logic_obj) {
		this.logic_obj = logic_obj;
	}
	public String getLogic_obj_desc() {
		return logic_obj_desc;
	}
	public void setLogic_obj_desc(String logic_obj_desc) {
		this.logic_obj_desc = logic_obj_desc;
	}
	
	
	
	
}
