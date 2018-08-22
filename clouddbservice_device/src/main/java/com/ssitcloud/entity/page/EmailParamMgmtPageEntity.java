package com.ssitcloud.entity.page;

import com.ssitcloud.common.entity.DatagridPageEntity;


/**
 * 邮件服务器配置表
 * author shuangjunjie
 * 2017年2月22日
 */
public class EmailParamMgmtPageEntity extends DatagridPageEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer email_idx;//int(11) NOT NULL 邮件IDX
	private Integer lib_idx;//int(11) NOT NULL 馆idx
	private String lib_idx_str;//馆idx字符串
	private String email_smtp;//varchar(50) NULL 发送邮件的服务地址(SMTP)
	private String email_port;//varchar(10) NULL 服务器端口
	private String email_account;//varchar(50) NULL 邮件发送帐号
	private String email_password;//varchar(50) NULL 密码
	private Integer email_code;//int(11) NULL 邮件编码 1utf8 2简体中文
	private Integer email_use_flg;//int(2) NULL 是否启用Flag 1启用　0未启用
	
	public Integer getEmail_idx() {
		return email_idx;
	}
	public void setEmail_idx(Integer email_idx) {
		this.email_idx = email_idx;
	}
	public Integer getLib_idx() {
		return lib_idx;
	}
	public void setLib_idx(Integer lib_idx) {
		this.lib_idx = lib_idx;
	}
	
	public String getLib_idx_str() {
		return lib_idx_str;
	}
	public void setLib_idx_str(String lib_idx_str) {
		this.lib_idx_str = lib_idx_str;
	}
	public String getEmail_smtp() {
		return email_smtp;
	}
	public void setEmail_smtp(String email_smtp) {
		this.email_smtp = email_smtp;
	}
	public String getEmail_port() {
		return email_port;
	}
	public void setEmail_port(String email_port) {
		this.email_port = email_port;
	}
	public String getEmail_account() {
		return email_account;
	}
	public void setEmail_account(String email_account) {
		this.email_account = email_account;
	}
	public String getEmail_password() {
		return email_password;
	}
	public void setEmail_password(String email_password) {
		this.email_password = email_password;
	}
	public Integer getEmail_code() {
		return email_code;
	}
	public void setEmail_code(Integer email_code) {
		this.email_code = email_code;
	}
	public Integer getEmail_use_flg() {
		return email_use_flg;
	}
	public void setEmail_use_flg(Integer email_use_flg) {
		this.email_use_flg = email_use_flg;
	}
	
}
