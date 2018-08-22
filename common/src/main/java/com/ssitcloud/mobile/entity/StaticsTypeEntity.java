package com.ssitcloud.mobile.entity;

public class StaticsTypeEntity {
	private Integer statictype_idx;  //主键ID
	private Integer data_type;  //1中图法
	private String data_key;  //关键词
	private String  data_desc;  //说明
	public Integer getStatictype_idx() {
		return statictype_idx;
	}
	public void setStatictype_idx(Integer statictype_idx) {
		this.statictype_idx = statictype_idx;
	}
	public Integer getData_type() {
		return data_type;
	}
	public void setData_type(Integer data_type) {
		this.data_type = data_type;
	}
	public String getData_key() {
		return data_key;
	}
	public void setData_key(String data_key) {
		this.data_key = data_key;
	}
	public String getData_desc() {
		return data_desc;
	}
	public void setData_desc(String data_desc) {
		this.data_desc = data_desc;
	}
	public StaticsTypeEntity() {
		super();
	}
	

}
