package com.ssitcloud.entity.page;

import com.ssitcloud.common.entity.DatagridPageEntity;



public class OperGroupMgmtPageEntity extends DatagridPageEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  operator_group_idx,
	 *  operator_group_id,
	 *  operator_group_name,
 		device_group_idx_str,
 		device_group_name_str,
 		service_group_idx_str,
 		service_group_name_str
 		library_idx
 		operator_idx
 		operator_type
	 * 
	 */
	/**
	 * 操作员分组IDX
	 */
	private Integer operator_group_idx;
	/**
	 * 操作员分组ID
	 */
	private String operator_group_id;
	/**
	 * 操作员分组名称
	 */
	private String operator_group_name;
	/**
	 * 设备分组IDx  
	 */
	private String device_group_idx_str;
	/**
	 * 设备分组名称
	 */
	private String device_group_name_str;
	/**
	 * 权限分组idx
	 */
	private String service_group_idx_str;
	/**
	 * 模板分组idx
	 */
	private String statistics_group_idx_str;
	/**
	 * 全选分组名称
	 */
	private String service_group_name_str;
	/**
	 * 图书馆IDX
	 */
	private String library_idx;
	/**
	 * 创建者的IDX
	 */
	private String operator_idx;
	/**
	 * 操作员类型
	 */
	private String operator_type;
	/**
	 * 备注
	 */
	private String operator_group_desc;
	
	private Integer version_stamp;
	
	public String getOperator_group_desc() {
		return operator_group_desc;
	}
	public void setOperator_group_desc(String operator_group_desc) {
		this.operator_group_desc = operator_group_desc;
	}
	public Integer getOperator_group_idx() {
		return operator_group_idx;
	}
	public void setOperator_group_idx(Integer operator_group_idx) {
		this.operator_group_idx = operator_group_idx;
	}
	public String getOperator_group_name() {
		return operator_group_name;
	}
	public void setOperator_group_name(String operator_group_name) {
		this.operator_group_name = operator_group_name;
	}
	public String getDevice_group_idx_str() {
		return device_group_idx_str;
	}
	public void setDevice_group_idx_str(String device_group_idx_str) {
		this.device_group_idx_str = device_group_idx_str;
	}
	public String getDevice_group_name_str() {
		return device_group_name_str;
	}
	public void setDevice_group_name_str(String device_group_name_str) {
		this.device_group_name_str = device_group_name_str;
	}
	public String getService_group_idx_str() {
		return service_group_idx_str;
	}
	public void setService_group_idx_str(String service_group_idx_str) {
		this.service_group_idx_str = service_group_idx_str;
	}
	public String getService_group_name_str() {
		return service_group_name_str;
	}
	public void setService_group_name_str(String service_group_name_str) {
		this.service_group_name_str = service_group_name_str;
	}
	public String getOperator_group_id() {
		return operator_group_id;
	}
	public void setOperator_group_id(String operator_group_id) {
		this.operator_group_id = operator_group_id;
	}
	public String getLibrary_idx() {
		return library_idx;
	}
	public void setLibrary_idx(String library_idx) {
		this.library_idx = library_idx;
	}
	public String getOperator_idx() {
		return operator_idx;
	}
	public void setOperator_idx(String operator_idx) {
		this.operator_idx = operator_idx;
	}
	public String getOperator_type() {
		return operator_type;
	}
	public void setOperator_type(String operator_type) {
		this.operator_type = operator_type;
	}
	public Integer getVersion_stamp() {
		return version_stamp;
	}
	public void setVersion_stamp(Integer version_stamp) {
		this.version_stamp = version_stamp;
	}
	public String getStatistics_group_idx_str() {
		return statistics_group_idx_str;
	}
	public void setStatistics_group_idx_str(String statistics_group_idx_str) {
		this.statistics_group_idx_str = statistics_group_idx_str;
	}
	
}
