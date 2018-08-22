package com.ssitcloud.mobile.entity;

import com.ssitcloud.authentication.entity.LibraryAcsLogininfoEntity;

/**
 * acs登陆服务实体
 * @author LXP
 * @version 创建时间：2017年3月3日 上午10:01:30
 */
public class AcsLoginEntiy {
	private String username;
	private String pwd;
	private final String operation = "lib_sc_login";
	
	public AcsLoginEntiy() {
	}
	
	public AcsLoginEntiy(LibraryAcsLogininfoEntity libraryConfig) {
		this.username = libraryConfig.getAcs_loginname();
		this.pwd = libraryConfig.getAcs_loginpwd();
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getOperation() {
		return operation;
	}
}
