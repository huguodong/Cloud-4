package com.ssitcloud.entity.page;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonRootName;

import com.ssitcloud.common.entity.DatagridPageEntity;
/**
 * 
 * @comment 设备表，存储设备信息。表名： device
 * 
 * @author hwl
 * @data 2016年4月6日
 */
@JsonRootName(value="device")
public class DevicePageEntity extends DatagridPageEntity{

	
	
	private static final long serialVersionUID = 1L;
	/* 自增id */
	private Integer device_idx;
	/* 图书馆id */
	private Integer library_idx;
	/* 设备类型id */
	private Integer device_model_idx;
	/* 设备id */
	private String device_id;
	/* 设备名 */
	private String device_name;
	/* 设备特征码 */
	private String device_code;
	/* 设备描述 */
	private String device_desc;
	/*设备类型*/
	private String device_type;
	/**
	 * 自助服务登录用户名
	 */
	private String library_login_name;
	/**
	 * 自助服务登录密码
	 */
	private String library_login_pwd;
	/**
	 * 物流编号
	 */
	private String logistics_number;
	/**
	 * device 表中的 设备总数
	 */
	private Integer device_num;
	/**
	 * 图书馆 约束
	 */
	private List<Integer> lib_idx_list;
	/**
	 * 设备 ID 约束
	 */
	private List<String> dev_id_list;
	/**
	 * 约束 用户IDX
	 */
	private Integer operator_idx_Limit;
	
	public DevicePageEntity() {
	}

	public DevicePageEntity(String device_id) {
		this.device_id = device_id;
	}

	public Integer getDevice_idx() {
		return device_idx;
	}

	public void setDevice_idx(Integer device_idx) {
		this.device_idx = device_idx;
	}

	public Integer getLibrary_idx() {
		return library_idx;
	}

	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}

	public Integer getDevice_model_idx() {
		return device_model_idx;
	}

	public void setDevice_model_idx(Integer device_model_idx) {
		this.device_model_idx = device_model_idx;
	}

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public String getDevice_name() {
		return device_name;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}

	public String getDevice_code() {
		return device_code;
	}

	public void setDevice_code(String device_code) {
		this.device_code = device_code;
	}

	public String getDevice_desc() {
		return device_desc;
	}

	public void setDevice_desc(String device_desc) {
		this.device_desc = device_desc;
	}

	public String getLibrary_login_name() {
		return library_login_name;
	}

	public void setLibrary_login_name(String library_login_name) {
		this.library_login_name = library_login_name;
	}

	public String getLibrary_login_pwd() {
		return library_login_pwd;
	}

	public void setLibrary_login_pwd(String library_login_pwd) {
		this.library_login_pwd = library_login_pwd;
	}

	public String getLogistics_number() {
		return logistics_number;
	}

	public void setLogistics_number(String logistics_number) {
		this.logistics_number = logistics_number;
	}

	public String getDevice_type() {
		return device_type;
	}

	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}

	public Integer getDevice_num() {
		return device_num;
	}

	public void setDevice_num(Integer device_num) {
		this.device_num = device_num;
	}

	public List<Integer> getLib_idx_list() {
		return lib_idx_list;
	}

	public void setLib_idx_list(List<Integer> lib_idx_list) {
		this.lib_idx_list = lib_idx_list;
	}

	public List<String> getDev_id_list() {
		return dev_id_list;
	}

	public void setDev_id_list(List<String> dev_id_list) {
		this.dev_id_list = dev_id_list;
	}

	public Integer getOperator_idx_Limit() {
		return operator_idx_Limit;
	}

	public void setOperator_idx_Limit(Integer operator_idx_Limit) {
		this.operator_idx_Limit = operator_idx_Limit;
	}

}
