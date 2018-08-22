package com.ssitcloud.entity;

/**
 * @comment 操作员组表。表名：operator_group
 * @author hwl
 * 
 * @data 2016年4月5日
 * 
 */
public class OperatorGroupEntity {

	/* 操作员组自增长ID */
	private Integer operator_group_idx;
	 /* 操作员组自ID */
	private String operator_group_id;
	/* 馆id */
	private Integer library_idx;
	/* 业务组名称 */
	private String operator_group_name;
	/* 业务组描述 */
	private String operator_group_desc;
	/*创建者IDX*/
	private Integer operator_idx;
	private Integer version_stamp;

	public Integer getOperator_group_idx() {
		return operator_group_idx;
	}

	public void setOperator_group_idx(Integer operator_group_idx) {
		this.operator_group_idx = operator_group_idx;
	}

	public Integer getLibrary_idx() {
		return library_idx;
	}

	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}

	public String getOperator_group_name() {
		return operator_group_name;
	}

	public void setOperator_group_name(String operator_group_name) {
		this.operator_group_name = operator_group_name;
	}

	public String getOperator_group_desc() {
		return operator_group_desc;
	}

	public void setOperator_group_desc(String operator_group_desc) {
		this.operator_group_desc = operator_group_desc;
	}

	public String getOperator_group_id() {
		return operator_group_id;
	}

	public void setOperator_group_id(String operator_group_id) {
		this.operator_group_id = operator_group_id;
	}

	public Integer getOperator_idx() {
		return operator_idx;
	}

	public void setOperator_idx(Integer operator_idx) {
		this.operator_idx = operator_idx;
	}

	public Integer getVersion_stamp() {
		return version_stamp;
	}

	public void setVersion_stamp(Integer version_stamp) {
		this.version_stamp = version_stamp;
	}
	
}
