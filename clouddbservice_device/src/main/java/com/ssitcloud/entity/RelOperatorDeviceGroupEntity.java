package com.ssitcloud.entity;

/**
 * 
 * @comment
 * 
 * @author hwl
 * @data 2016年4月5日
 */
public class RelOperatorDeviceGroupEntity {
	
	/* 自增长ID */
	private Integer rel_oper_dev_idx;
	/* 操作员组ID */
	private Integer operator_group_idx;
	/* 设备组ID */
	private Integer device_group_idx;
	/* 图书馆ID */
	private Integer library_idx;

	public Integer getRel_oper_dev_idx() {
		return rel_oper_dev_idx;
	}

	public void setRel_oper_dev_idx(Integer rel_oper_dev_idx) {
		this.rel_oper_dev_idx = rel_oper_dev_idx;
	}

	public Integer getOperator_group_idx() {
		return operator_group_idx;
	}

	public void setOperator_group_idx(Integer operator_group_idx) {
		this.operator_group_idx = operator_group_idx;
	}

	public Integer getDevice_group_idx() {
		return device_group_idx;
	}

	public void setDevice_group_idx(Integer device_group_idx) {
		this.device_group_idx = device_group_idx;
	}

	public Integer getLibrary_idx() {
		return library_idx;
	}

	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}

}
