package com.ssitcloud.entity;

/**
 * 
 * @comment 业务分组表，表名:rel_service_group。
 * 
 * @author hwl
 * @data 2016年4月6日
 */
public class RelServiceGroupEntity {

	/* 自增长id */
	private Integer rel_service_idx;
	/* 业务组id */
	private Integer service_group_idx;
	/* 业务命令id */
	private Integer meta_opercmd_idx;
	/* 图书馆id */
	private Integer library_idx;

	public Integer getRel_service_idx() {
		return rel_service_idx;
	}

	public void setRel_service_idx(Integer rel_service_idx) {
		this.rel_service_idx = rel_service_idx;
	}

	public Integer getService_group_idx() {
		return service_group_idx;
	}

	public void setService_group_idx(Integer service_group_idx) {
		this.service_group_idx = service_group_idx;
	}

	public Integer getMeta_opercmd_idx() {
		return meta_opercmd_idx;
	}

	public void setMeta_opercmd_idx(Integer meta_opercmd_idx) {
		this.meta_opercmd_idx = meta_opercmd_idx;
	}

	public Integer getLibrary_idx() {
		return library_idx;
	}

	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}

}
