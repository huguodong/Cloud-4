package com.ssitcloud.datasync.entity;

import java.util.List;

public class DeviceAcsModuleEntity {
	// 协议类型
	private Integer protocol_type;
	// 协议描述
	private String protocol_tpl_desc;
	// 登录信息id
	private Integer logininfo_idx;
	// 模板id
	private Integer protocol_tpl_idx;
	// 设备id
	private Integer device_idx;
	// 图书馆idx
	private Integer library_idx;
	// 登录ip
	private String login_ip;
	// 登录端口
	private int login_port;
	// 登录名
	private String login_username;
	// 登录密码
	private String login_pwd;
	// 协议类型
	private String login_type;
	// 是否允许多次登录
	private String login_check;
	// 输入密码次数
	private String login_count;
	// ACS的字符编码
	private String login_code;
	//协议名
	private String acs_service_name;
	// 指令信息
	private List<ProtocolInfo> protocolInfos;

	public Integer getProtocol_type() {
		return protocol_type;
	}

	public void setProtocol_type(Integer protocol_type) {
		this.protocol_type = protocol_type;
	}

	public String getProtocol_tpl_desc() {
		return protocol_tpl_desc;
	}

	public void setProtocol_tpl_desc(String protocol_tpl_desc) {
		this.protocol_tpl_desc = protocol_tpl_desc;
	}

	public Integer getLogininfo_idx() {
		return logininfo_idx;
	}

	public void setLogininfo_idx(Integer logininfo_idx) {
		this.logininfo_idx = logininfo_idx;
	}

	public Integer getProtocol_tpl_idx() {
		return protocol_tpl_idx;
	}

	public void setProtocol_tpl_idx(Integer protocol_tpl_idx) {
		this.protocol_tpl_idx = protocol_tpl_idx;
	}

	public Integer getDevice_idx() {
		return device_idx;
	}

	public void setDevice_idx(Integer device_idx) {
		this.device_idx = device_idx;
	}

	public Integer getLibrary_idx() {
		return library_idx;
	}

	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}

	public String getLogin_ip() {
		return login_ip;
	}

	public void setLogin_ip(String login_ip) {
		this.login_ip = login_ip;
	}

	public int getLogin_port() {
		return login_port;
	}

	public void setLogin_port(int login_port) {
		this.login_port = login_port;
	}

	public String getLogin_username() {
		return login_username;
	}

	public void setLogin_username(String login_username) {
		this.login_username = login_username;
	}

	public String getLogin_pwd() {
		return login_pwd;
	}

	public void setLogin_pwd(String login_pwd) {
		this.login_pwd = login_pwd;
	}

	public List<ProtocolInfo> getProtocolInfos() {
		return protocolInfos;
	}

	public void setProtocolInfos(List<ProtocolInfo> protocolInfos) {
		this.protocolInfos = protocolInfos;
	}

	public String getLogin_type() {
		return login_type;
	}

	public void setLogin_type(String login_type) {
		this.login_type = login_type;
	}

	public String getLogin_check() {
		return login_check;
	}

	public void setLogin_check(String login_check) {
		this.login_check = login_check;
	}

	public String getLogin_count() {
		return login_count;
	}

	public void setLogin_count(String login_count) {
		this.login_count = login_count;
	}

	public String getLogin_code() {
		return login_code;
	}

	public void setLogin_code(String login_code) {
		this.login_code = login_code;
	}

	public String getAcs_service_name() {
		return acs_service_name;
	}

	public void setAcs_service_name(String acs_service_name) {
		this.acs_service_name = acs_service_name;
	}

	@Override
	public String toString() {
		return "DeviceAcsModuleEntity [protocol_type=" + protocol_type + ", protocol_tpl_desc=" + protocol_tpl_desc + ", logininfo_idx=" + logininfo_idx + ", protocol_tpl_idx=" + protocol_tpl_idx
				+ ", device_idx=" + device_idx + ", library_idx=" + library_idx + ", login_ip=" + login_ip + ", login_port=" + login_port + ", login_username=" + login_username + ", login_pwd="
				+ login_pwd + ", login_type=" + login_type + ", login_check=" + login_check + ", login_count=" + login_count + ", login_code=" + login_code + ", protocolInfos=" + protocolInfos + "]";
	}
}
