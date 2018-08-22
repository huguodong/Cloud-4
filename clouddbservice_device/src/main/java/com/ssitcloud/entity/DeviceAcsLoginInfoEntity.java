package com.ssitcloud.entity;

import org.mybatis.generator.internal.util.EqualsUtil;

public class DeviceAcsLoginInfoEntity {
	/**
	 * 
	 logininfo_idx int(11) NOT NULLID号 protocol_tpl_idx int(11) NOT NULLACS模板号
	 * device_idx int(11) NOT NULL设备IDX library_idx int(11) NOT NULL图书馆IDX
	 * login_ip varchar(20) NOT NULL登录IP login_port int(11) NOT NULL登录端口号
	 * login_username varchar(50) NULL登录用户名 login_pwd varchar(50) NULL登录密码
	 * acs_service_name varchar(50) NOT NULLACS后台服务名
	 */
	private Integer logininfo_idx;
	private Integer protocol_tpl_idx;
	private Integer device_idx;
	private Integer library_idx;
	private String login_ip;
	private Integer login_port;
	private String login_username;
	private String login_pwd;
	private String login_type;
	private String login_check;
	private String login_count;
	private String login_code;
	private String acs_service_name;

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

	public Integer getLogin_port() {
		return login_port;
	}

	public void setLogin_port(Integer login_port) {
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof DeviceAcsLoginInfoEntity))
			return false;
		DeviceAcsLoginInfoEntity deviceAcsLoginInfo = (DeviceAcsLoginInfoEntity) obj;
		if (EqualsUtil.areEqual(this.acs_service_name, deviceAcsLoginInfo.getAcs_service_name()) && EqualsUtil.areEqual(this.device_idx, deviceAcsLoginInfo.getDevice_idx())
				&& EqualsUtil.areEqual(this.library_idx, deviceAcsLoginInfo.getLibrary_idx()) && EqualsUtil.areEqual(this.login_ip, deviceAcsLoginInfo.getLogin_ip())
				&& EqualsUtil.areEqual(this.login_port, deviceAcsLoginInfo.getLogin_port()) && EqualsUtil.areEqual(this.login_pwd, deviceAcsLoginInfo.getLogin_pwd())
				&& EqualsUtil.areEqual(this.login_username, deviceAcsLoginInfo.getLogin_username()) && EqualsUtil.areEqual(this.protocol_tpl_idx, deviceAcsLoginInfo.getProtocol_tpl_idx())
				&& EqualsUtil.areEqual(this.login_type, deviceAcsLoginInfo.getLogin_type()) && EqualsUtil.areEqual(this.login_check, deviceAcsLoginInfo.getLogin_check())
				&& EqualsUtil.areEqual(this.login_count, deviceAcsLoginInfo.getLogin_count()) && EqualsUtil.areEqual(this.login_code, deviceAcsLoginInfo.getLogin_code())
		// EqualsUtil.areEqual(this.logininfo_idx,
		// deviceAcsLoginInfo.getAcs_service_name());
		) {
			return true;
		}
		return false;
	}

}
