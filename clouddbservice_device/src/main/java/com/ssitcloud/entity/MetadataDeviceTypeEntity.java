package com.ssitcloud.entity;



/**
 * 
 * @comment 设备类型描述元数据表，描述设备的类型信息，如自助借还机，SSL
 * 
 * @author hwl
 * @data 2016年4月8日
 */
public class MetadataDeviceTypeEntity {

	// 自增长id
	private Integer meta_devicetype_idx;
	// 设备类型
	private String device_type;
	// 设备类型描述
	private String device_type_desc;
	// 设备类型备注
	private String device_type_mark;
	//创建时间
	private String createTime;
	//设备列表
	private String device_logic_list;
	//设备列表描述
	private String device_logic_list_desc;
	
	//设备类型，其它里面的硬件设备型号， 对应device_ext_model的 ext_model字段
	private String device_logic_other;
	
	private String device_db_list;
	
	private Integer version_stamp;
	
	public String getDevice_logic_other() {
		return device_logic_other;
	}

	public void setDevice_logic_other(String device_logic_other) {
		this.device_logic_other = device_logic_other;
	}

	public Integer getMeta_devicetype_idx() {
		return meta_devicetype_idx;
	}

	public void setMeta_devicetype_idx(Integer meta_devicetype_idx) {
		this.meta_devicetype_idx = meta_devicetype_idx;
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

	public String getDevice_type_mark() {
		return device_type_mark;
	}

	public void setDevice_type_mark(String device_type_mark) {
		this.device_type_mark = device_type_mark;
	}
	
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getDevice_logic_list() {
		return device_logic_list;
	}

	public void setDevice_logic_list(String device_logic_list) {
		this.device_logic_list = device_logic_list;
	}

	public String getDevice_logic_list_desc() {
		return device_logic_list_desc;
	}

	public void setDevice_logic_list_desc(String device_logic_list_desc) {
		this.device_logic_list_desc = device_logic_list_desc;
	}

	public String getDevice_db_list() {
		return device_db_list;
	}

	public void setDevice_db_list(String device_db_list) {
		this.device_db_list = device_db_list;
	}

	public Integer getVersion_stamp() {
		return version_stamp;
	}

	public void setVersion_stamp(Integer version_stamp) {
		this.version_stamp = version_stamp;
	}
}
