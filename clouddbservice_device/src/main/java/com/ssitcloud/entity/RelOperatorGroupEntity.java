package com.ssitcloud.entity;

/**
 * 
 * @comment 操作员分组关联表。表名：rel_operator_group
 * 
 * @author hwl
 * @data 2016年4月6日
 */
public class RelOperatorGroupEntity {

	/* 自增id */
	private Integer rel_operator_idx;
	/* 操作员组id */
	private Integer operator_group_idx;
	/* 操作员id */
	private Integer operator_idx;
	/* 图书馆id */
	private Integer library_idx;

	public Integer getRel_operator_idx() {
		return rel_operator_idx;
	}

	public void setRel_operator_idx(Integer rel_operator_idx) {
		this.rel_operator_idx = rel_operator_idx;
	}

	public Integer getOperator_group_idx() {
		return operator_group_idx;
	}

	public void setOperator_group_idx(Integer operator_group_idx) {
		this.operator_group_idx = operator_group_idx;
	}

	public Integer getOperator_idx() {
		return operator_idx;
	}

	public void setOperator_idx(Integer operator_idx) {
		this.operator_idx = operator_idx;
	}

	public Integer getLibrary_idx() {
		return library_idx;
	}

	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}

}
