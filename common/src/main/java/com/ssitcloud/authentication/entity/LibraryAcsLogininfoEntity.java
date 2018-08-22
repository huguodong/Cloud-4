package com.ssitcloud.authentication.entity;
public class LibraryAcsLogininfoEntity {
	private Integer lib_acs_idx;
	private Integer lib_idx;
	private Integer protocol_tpl_idx;
	private String acs_key;
	private String lib_acs_ip;
	private Integer lib_acs_port;
	private String acs_target;
	private String acs_loginname;
	private String acs_loginpwd;
	
	public Integer getLib_acs_idx() {
		return lib_acs_idx;
	}
	public void setLib_acs_idx(Integer lib_acs_idx) {
		this.lib_acs_idx = lib_acs_idx;
	}
	public Integer getLib_idx() {
		return lib_idx;
	}
	public void setLib_idx(Integer lib_idx) {
		this.lib_idx = lib_idx;
	}
	public String getLib_acs_ip() {
		return lib_acs_ip;
	}
	public void setLib_acs_ip(String lib_acs_ip) {
		this.lib_acs_ip = lib_acs_ip;
	}
	public Integer getLib_acs_port() {
		return lib_acs_port;
	}
	public void setLib_acs_port(Integer lib_acs_port) {
		this.lib_acs_port = lib_acs_port;
	}
	public String getAcs_target() {
		return acs_target;
	}
	public void setAcs_target(String acs_target) {
		this.acs_target = acs_target;
	}
	public String getAcs_loginname() {
		return acs_loginname;
	}
	public void setAcs_loginname(String acs_loginname) {
		this.acs_loginname = acs_loginname;
	}
	public String getAcs_loginpwd() {
		return acs_loginpwd;
	}
	public void setAcs_loginpwd(String acs_loginpwd) {
		this.acs_loginpwd = acs_loginpwd;
	}
	public Integer getProtocol_tpl_idx() {
		return protocol_tpl_idx;
	}
	public void setProtocol_tpl_idx(Integer protocol_tpl_idx) {
		this.protocol_tpl_idx = protocol_tpl_idx;
	}
	public String getAcs_key() {
		return acs_key;
	}
	public void setAcs_key(String acs_key) {
		this.acs_key = acs_key;
	}
	
	
}
