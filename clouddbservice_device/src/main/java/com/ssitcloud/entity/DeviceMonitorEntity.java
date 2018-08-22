package com.ssitcloud.entity;

public class DeviceMonitorEntity {
	private Integer device_ext_type;
	private Integer device_mon_tpl_idx;
	private Integer logic_obj_idx;
	private Integer library_idx;
	private Integer visiable;
	private Integer alert;
	private Integer color;
	private Integer threshold;
	private String  table_name;
	private Integer meta_log_obj_idx; // metadata_logic_obj
	
	private String logic_obj;
	private String logic_obj_desc;
	
	private Integer plc_logic_obj_idx;
	private String plc_logic_obj;
	private String plc_logic_obj_desc;
	public Integer getDevice_ext_type() {
		return device_ext_type;
	}
	public void setDevice_ext_type(Integer device_ext_type) {
		this.device_ext_type = device_ext_type;
	}
	public Integer getDevice_mon_tpl_idx() {
		return device_mon_tpl_idx;
	}
	public void setDevice_mon_tpl_idx(Integer device_mon_tpl_idx) {
		this.device_mon_tpl_idx = device_mon_tpl_idx;
	}
	public Integer getLogic_obj_idx() {
		return logic_obj_idx;
	}
	public void setLogic_obj_idx(Integer logic_obj_idx) {
		this.logic_obj_idx = logic_obj_idx;
	}
	public Integer getLibrary_idx() {
		return library_idx;
	}
	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}
	public Integer getVisiable() {
		return visiable;
	}
	public void setVisiable(Integer visiable) {
		this.visiable = visiable;
	}
	public Integer getAlert() {
		return alert;
	}
	public void setAlert(Integer alert) {
		this.alert = alert;
	}
	public Integer getColor() {
		return color;
	}
	public void setColor(Integer color) {
		this.color = color;
	}
	public Integer getThreshold() {
		return threshold;
	}
	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public Integer getMeta_log_obj_idx() {
		return meta_log_obj_idx;
	}
	public void setMeta_log_obj_idx(Integer meta_log_obj_idx) {
		this.meta_log_obj_idx = meta_log_obj_idx;
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
	public Integer getPlc_logic_obj_idx() {
		return plc_logic_obj_idx;
	}
	public void setPlc_logic_obj_idx(Integer plc_logic_obj_idx) {
		this.plc_logic_obj_idx = plc_logic_obj_idx;
	}
	public String getPlc_logic_obj() {
		return plc_logic_obj;
	}
	public void setPlc_logic_obj(String plc_logic_obj) {
		this.plc_logic_obj = plc_logic_obj;
	}
	public String getPlc_logic_obj_desc() {
		return plc_logic_obj_desc;
	}
	public void setPlc_logic_obj_desc(String plc_logic_obj_desc) {
		this.plc_logic_obj_desc = plc_logic_obj_desc;
	}
	
}
