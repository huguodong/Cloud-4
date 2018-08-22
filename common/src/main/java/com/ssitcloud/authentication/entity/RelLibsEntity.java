package com.ssitcloud.authentication.entity;

/**
 * 总分馆关系表
 * <p>2016年4月5日 上午11:06:02
 * @author hjc
 *
 */
public class RelLibsEntity {
	/** 总馆ID，为library的自增id */
	private Integer master_lib_id;
	/** 分馆ID，为library的自增id */
	private Integer slave_lib_id;
	/** 关系类型  */
	private String rel_type;
	public Integer getMaster_lib_id() {
		return master_lib_id;
	}
	public void setMaster_lib_id(Integer master_lib_id) {
		this.master_lib_id = master_lib_id;
	}
	public Integer getSlave_lib_id() {
		return slave_lib_id;
	}
	public void setSlave_lib_id(Integer slave_lib_id) {
		this.slave_lib_id = slave_lib_id;
	}
	public String getRel_type() {
		return rel_type;
	}
	public void setRel_type(String rel_type) {
		this.rel_type = rel_type;
	}
	
	
}
