package com.ssitcloud.mobile.entity;

import org.codehaus.jackson.map.annotate.JsonRootName;

@JsonRootName(value="appconnMetadata")
public class AppconnMetadata {
	private Integer meta_idx;
	private String meta_name;
	private String sys_name;
	private String sys_type;
	
	public Integer getMeta_idx() {
		return meta_idx;
	}
	public void setMeta_idx(Integer meta_idx) {
		this.meta_idx = meta_idx;
	}
	public String getMeta_name() {
		return meta_name;
	}
	public void setMeta_name(String meta_name) {
		this.meta_name = meta_name;
	}
	public String getSys_name() {
		return sys_name;
	}
	public void setSys_name(String sys_name) {
		this.sys_name = sys_name;
	}
	public String getSys_type() {
		return sys_type;
	}
	public void setSys_type(String sys_type) {
		this.sys_type = sys_type;
	}
	
}
