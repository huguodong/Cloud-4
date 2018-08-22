package com.ssitcloud.entity;

public class DeviceMonConfigEntity {
	/**
	 * 
	 FieldTypeComment device_ext_type int(11) NOT NULL模板状态标识 0模板 1数据
	 * device_mon_tpl_idx int(11) NOT NULL显示模板ID或设备ID logic_obj_idx int(11) NOT
	 * NULL逻辑对象ID library_idx int(11) NOT NULL馆ID visiable int(11) NOT NULL是否可见
	 * alert int(11) NOT NULL是否告警 color int(11) NOT NULL显示颜色 threshold int(11)
	 * NOT NULL阈值
	 */
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
	
	public static final Integer DEVICE_TPL_TYPE_IS_MODEL=0;
	public static final Integer DEVICE_TPL_TYPE_IS_DATA=1;
	
	
	public DeviceMonConfigEntity() {
	}

	public DeviceMonConfigEntity(Integer device_mon_tpl_idx) {
		this.device_mon_tpl_idx = device_mon_tpl_idx;
	}
	public DeviceMonConfigEntity(Integer device_mon_tpl_idx,Integer device_ext_type) {
		this.device_mon_tpl_idx = device_mon_tpl_idx;
		this.device_ext_type=device_ext_type;
	}
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

}
