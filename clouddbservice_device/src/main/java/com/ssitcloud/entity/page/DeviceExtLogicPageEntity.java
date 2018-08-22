package com.ssitcloud.entity.page;


import com.ssitcloud.common.entity.DatagridPageEntity;

/**
 * 硬件与逻辑配置分页参数entity
 * 
 * <p>
 * 2016年5月19日 下午2:50:02
 * 
 * @author hjc
 * 
 */
public class DeviceExtLogicPageEntity extends DatagridPageEntity<DeviceExtLogicPageEntity> {

	private static final long serialVersionUID = 1L;

	/**
	 * ec.`device_tpl_type`, ec.`ext_comm_conf`, ec.`ext_extend_conf`,
	 * ec.`ext_model_idx`, ec.`logic_obj_idx`, ec.`library_idx`,
	 * ec.`device_ext_id`,
	 * 
	 * et.`device_tpl_ID`, et.`device_tpl_desc`, et.`device_type`,
	 * 
	 * em.`ext_model`, em.`ext_model_desc`, em.`ext_model_version`,
	 * em.`ext_type`,
	 * 
	 * lo.`logic_obj`, lo.`logic_obj_desc`
	 */

	/** 模板idx **/
	private String device_tpl_idx;
	/** 模板id **/
	private String device_tpl_ID;
	/** 模板描述 **/
	private String device_tpl_desc;
	/** 设备类型 **/
	private String device_type;
	/** 设备类型描述 */
	private String device_type_desc;

	private String device_tpl_type;
	private String ext_comm_conf;
	private String ext_extend_conf;
	private String ext_model_idx;
	private String logic_obj_idx;
	private String library_idx;
	private String device_ext_id;
	
	private String ext_model;
	private String ext_model_desc;
	private String ext_model_version;
	private String ext_type;

	private String logic_obj;
	private String logic_obj_desc;
	
	
	
	public String getDevice_type_desc() {
		return device_type_desc;
	}
	public void setDevice_type_desc(String device_type_desc) {
		this.device_type_desc = device_type_desc;
	}
	public String getDevice_tpl_idx() {
		return device_tpl_idx;
	}
	public void setDevice_tpl_idx(String device_tpl_idx) {
		this.device_tpl_idx = device_tpl_idx;
	}
	public String getDevice_tpl_type() {
		return device_tpl_type;
	}
	public void setDevice_tpl_type(String device_tpl_type) {
		this.device_tpl_type = device_tpl_type;
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
	public String getExt_model_idx() {
		return ext_model_idx;
	}
	public void setExt_model_idx(String ext_model_idx) {
		this.ext_model_idx = ext_model_idx;
	}
	public String getLogic_obj_idx() {
		return logic_obj_idx;
	}
	public void setLogic_obj_idx(String logic_obj_idx) {
		this.logic_obj_idx = logic_obj_idx;
	}
	public String getLibrary_idx() {
		return library_idx;
	}
	public void setLibrary_idx(String library_idx) {
		this.library_idx = library_idx;
	}
	public String getDevice_ext_id() {
		return device_ext_id;
	}
	public void setDevice_ext_id(String device_ext_id) {
		this.device_ext_id = device_ext_id;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
