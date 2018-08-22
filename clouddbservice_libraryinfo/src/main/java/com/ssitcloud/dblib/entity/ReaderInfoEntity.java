package com.ssitcloud.dblib.entity;

import java.sql.Timestamp;

public class ReaderInfoEntity {
	private Integer reader_idx;//int(11) NOT NULL读者记录号
	private String reader_name;//varchar(50) NOT NULL姓名
	private String login_name;//varchar(50) not null 账号名
	private String reader_sex;//char(1) NULL性别1男0女
	private String id_card;//varchar(19) NULL身份证号
	private String reader_birthday;//varchar(8) NULL出生日期
	private Integer sox_tpl_id;//int(11) NOT NULLSOX模板ID
	private String reader_pwd;//varchar(50) NULL密码
	private Integer isLock;//tinyint(2) NULL是否锁定
	private Integer isLogged;//tinyint(4) NULL是否已经登录
	private String last_login_ip;//varchar(64) NULL最后登录IP
	private Timestamp last_login_time;//timestamp NOT NULL最后登录时间
	private Timestamp last_lock_time;//timestamp NOT NULL最后锁定时间
	private Integer login_fail_times;//smallint(6) NULL用户登陆失败次数，跟模板表的失败次数一致
	private String mobile;//varchar(12)用户注册时的手机号码
	private String email;//varchar(50)用户注册时的电子邮箱
	private Timestamp createTime;//timestamp NOT NULL创建日期（CURRENT_TIMESTAMP）
	private String updateTime;//varchar(20) NULL修改日期
	
	
	public Integer getReader_idx() {
		return reader_idx;
	}
	public void setReader_idx(Integer reader_idx) {
		this.reader_idx = reader_idx;
	}
	public String getReader_name() {
		return reader_name;
	}
	public void setReader_name(String reader_name) {
		this.reader_name = reader_name;
	}
	public String getReader_sex() {
		return reader_sex;
	}
	public void setReader_sex(String reader_sex) {
		this.reader_sex = reader_sex;
	}
	public String getId_card() {
		return id_card;
	}
	public void setId_card(String id_card) {
		this.id_card = id_card;
	}
	public String getReader_birthday() {
		return reader_birthday;
	}
	public void setReader_birthday(String reader_birthday) {
		this.reader_birthday = reader_birthday;
	}
	public Integer getSox_tpl_id() {
		return sox_tpl_id;
	}
	public void setSox_tpl_id(Integer sox_tpl_id) {
		this.sox_tpl_id = sox_tpl_id;
	}
	public String getReader_pwd() {
		return reader_pwd;
	}
	public void setReader_pwd(String reader_pwd) {
		this.reader_pwd = reader_pwd;
	}
	public Integer getIsLock() {
		return isLock;
	}
	public void setIsLock(Integer isLock) {
		this.isLock = isLock;
	}
	public Integer getIsLogged() {
		return isLogged;
	}
	public void setIsLogged(Integer isLogged) {
		this.isLogged = isLogged;
	}
	public String getLast_login_ip() {
		return last_login_ip;
	}
	public void setLast_login_ip(String last_login_ip) {
		this.last_login_ip = last_login_ip;
	}
	public Timestamp getLast_login_time() {
		return last_login_time;
	}
	public void setLast_login_time(Timestamp last_login_time) {
		this.last_login_time = last_login_time;
	}
	public Timestamp getLast_lock_time() {
		return last_lock_time;
	}
	public void setLast_lock_time(Timestamp last_lock_time) {
		this.last_lock_time = last_lock_time;
	}
	public Integer getLogin_fail_times() {
		return login_fail_times;
	}
	public void setLogin_fail_times(Integer login_fail_times) {
		this.login_fail_times = login_fail_times;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
}
