package com.ssitcloud.mobile.entity;

import org.codehaus.jackson.map.annotate.JsonRootName;

import com.ssitcloud.common.entity.DatagridPageEntity;



@JsonRootName(value="appconnSetting")
public class AppconnSettingEntity extends DatagridPageEntity{
	private static final long serialVersionUID = 1L;
	private Integer conn_id;
	private Integer lib_idx;
	private Integer meta_idx;
	private String meta_value;
	private String lib_idx_str;
	private String sys_name;
	private String meta_name;
	public Integer getConn_id() {
		return conn_id;
	}
	public void setConn_id(Integer conn_id) {
		this.conn_id = conn_id;
	}
	public Integer getLib_idx() {
		return lib_idx;
	}
	public void setLib_idx(Integer lib_idx) {
		this.lib_idx = lib_idx;
	}
	public Integer getMeta_idx() {
		return meta_idx;
	}
	public void setMeta_idx(Integer meta_idx) {
		this.meta_idx = meta_idx;
	}
	public String getMeta_value() {
		return meta_value;
	}
	public void setMeta_value(String meta_value) {
		this.meta_value = meta_value;
	}
	public String getLib_idx_str() {
		return lib_idx_str;
	}
	public void setLib_idx_str(String lib_idx_str) {
		this.lib_idx_str = lib_idx_str;
	}
	public String getSys_name() {
		return sys_name;
	}
	public void setSys_name(String sys_name) {
		this.sys_name = sys_name;
	}
	public String getMeta_name() {
		return meta_name;
	}
	public void setMeta_name(String meta_name) {
		this.meta_name = meta_name;
	}
	
}
