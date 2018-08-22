package com.ssitcloud.entity;

/**
 * @comment 操作员组和业务组关联表。表名:rel_operator_service_group
 * @author hwl
 * 
 * @data 2016-3-29下午1:15:42
 * 
 */
public class RelOperatorServiceGroupEntity {

	/* 自增长ID */
	private Integer rel_oper_serv_idx;
	/* 操作员组ID */
	private Integer operator_group_idx;
	/* 业务组ID */
	private Integer service_group_idx;
	/* 图书馆ID */
	private Integer library_idx;

	public Integer getRel_oper_serv_idx() {
		return rel_oper_serv_idx;
	}

	public void setRel_oper_serv_idx(Integer rel_oper_serv_idx) {
		this.rel_oper_serv_idx = rel_oper_serv_idx;
	}

	public Integer getOperator_group_idx() {
		return operator_group_idx;
	}

	public void setOperator_group_idx(Integer operator_group_idx) {
		this.operator_group_idx = operator_group_idx;
	}

	public Integer getService_group_idx() {
		return service_group_idx;
	}

	public void setService_group_idx(Integer service_group_idx) {
		this.service_group_idx = service_group_idx;
	}

	public Integer getLibrary_idx() {
		return library_idx;
	}

	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}

}
