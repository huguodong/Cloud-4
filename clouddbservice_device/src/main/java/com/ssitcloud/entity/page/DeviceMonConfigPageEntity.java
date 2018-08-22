package com.ssitcloud.entity.page;

import com.ssitcloud.common.entity.DatagridPageEntity;

public class DeviceMonConfigPageEntity extends DatagridPageEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 FieldTypeComment device_ext_type int(11) NOT NULL模板状态标识 0模板 1数据
	 * device_mon_tpl_idx int(11) NOT NULL显示模板ID或设备ID logic_obj_idx int(11) NOT
	 * NULL逻辑对象ID library_idx int(11) NOT NULL馆ID visiable int(11) NOT NULL是否可见
	 * alert int(11) NOT NULL是否告警 color int(11) NOT NULL显示颜色 threshold int(11)
	 * NOT NULL阈值
	 * 
	 *  d.device_idx,
  		d.device_name,
  		md.device_type,
  		md.device_type_desc,
		mlo.logic_obj,
		mlo.logic_obj_desc,
		dmc.threshold,
		dmc.color,
		dmc.alert 
	 */
	private Integer device_ext_type;//是否是模板 0模板 1数据
	
	private Integer device_mon_tpl_idx;//模板ID 或者是设备IDX
	private String device_mon_tpl_id;
	private String device_mon_tpl_desc;//模板名称
	
	private Integer meta_devicetype_idx;//设备类型IDX
	
	private Integer device_mon_type_idx;//设备类型IDX
	
	private Integer logic_obj_idx;//
	private Integer library_idx;//图书馆IDX
	private Integer visiable;//是否可见
	
	private Integer alert;//是否报警
	private Integer color;//颜色
	private Integer threshold;//阈值
	
	private Integer meta_log_obj_idx;//metadata_logic_obj id meta_log_obj_idx
	private String logic_obj;//逻辑名
	private String logic_obj_desc;//逻辑中文名
	
	
	
	private Integer device_idx;//设备IDx
	private String device_name;//设备名称
	private String device_type;//设备类型
	private String device_type_desc;//设备类型描述
	
	

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

	public Integer getMeta_log_obj_idx() {
		return meta_log_obj_idx;
	}

	public void setMeta_log_obj_idx(Integer meta_log_obj_idx) {
		this.meta_log_obj_idx = meta_log_obj_idx;
	}

	public Integer getDevice_idx() {
		return device_idx;
	}

	public void setDevice_idx(Integer device_idx) {
		this.device_idx = device_idx;
	}

	public String getDevice_name() {
		return device_name;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
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

	public Integer getMeta_devicetype_idx() {
		return meta_devicetype_idx;
	}

	public void setMeta_devicetype_idx(Integer meta_devicetype_idx) {
		this.meta_devicetype_idx = meta_devicetype_idx;
	}

}
