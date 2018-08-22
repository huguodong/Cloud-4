package com.ssitcloud.common.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.ssitcloud.common.entity.UserMenuPermessionEntity;
import com.ssitcloud.common.entity.UserRolePermessionEntity;

/**
 * 用户，有鉴权loginCheck接口返回数据
 * 
 * @package: com.ssitcloud.common.entity
 * @classFile: Operator
 * @author: liuBh
 * @description: TODO
 */

public class Operator implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String operator_idx;

	private String operator_id;

	private String operator_name;

	private String lib_id;

	private String lib_name;

	private String sox_tpl_id;

	private String operator_pwd;

	private String operator_type;

	private String isActive;

	private String isLock;

	private String lock_time;

	private String isLogged;

	private String last_login_ip;

	private String last_login_time;

	private String last_lock_time;

	private String last_chgpwd_time;

	private String login_fail_times;

	private String service_expire_date;

	private String service_start_date;

	private String vaild_time;
	
	private String faild_times;
	
	private Set<String> roleName;
	
	private String library_idx;
		
	private List<UserRolePermessionEntity> userRolePermessions;
	
	private List<UserMenuPermessionEntity> userMenuPermessions;
	
	private String firstChange;//首次登录修改密码
	
	private String pwdInvalid;//密码失效，也强制修改密码
	
	private String first_login_chgpwd;//第一次登陆是否修改密码，1是  0否
	
	private String createtime;//创建时间
	
	private Integer password_validdays; //密码有效天数
	
	private Integer password_remind; //密码提醒天数
	
	private String cloud_admin;//管理员id，前端显示用
	
	public static final String SSITCLOUD_ADMIN="1";
	public static final String SSITCLOUD_MANAGER="2";
	public static final String LIBRARY_MANAGER="3";
	public static final String LIBRARY_USER="4";//图书馆用户
	
	public String getLibrary_idx() {
		return library_idx;
	}

	public void setLibrary_idx(String library_idx) {
		this.library_idx = library_idx;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getOperator_id() {
		return this.operator_id;
	}

	public void setOperator_name(String operator_name) {
		this.operator_name = operator_name;
	}

	public String getOperator_name() {
		return this.operator_name;
	}

	public void setLib_id(String lib_id) {
		this.lib_id = lib_id;
	}

	public String getLib_id() {
		return this.lib_id;
	}

	public void setLib_name(String lib_name) {
		this.lib_name = lib_name;
	}

	public String getLib_name() {
		return this.lib_name;
	}

	public void setSox_tpl_id(String sox_tpl_id) {
		this.sox_tpl_id = sox_tpl_id;
	}

	public String getSox_tpl_id() {
		return this.sox_tpl_id;
	}

	public void setOperator_pwd(String operator_pwd) {
		this.operator_pwd = operator_pwd;
	}

	public String getOperator_pwd() {
		return this.operator_pwd;
	}

	public void setOperator_type(String operator_type) {
		this.operator_type = operator_type;
	}

	public String getOperator_type() {
		return this.operator_type;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}

	public String getIsLock() {
		return this.isLock;
	}

	public void setLock_time(String lock_time) {
		this.lock_time = lock_time;
	}

	public String getLock_time() {
		return this.lock_time;
	}

	public void setIsLogged(String isLogged) {
		this.isLogged = isLogged;
	}

	public String getIsLogged() {
		return this.isLogged;
	}

	public void setLast_login_ip(String last_login_ip) {
		this.last_login_ip = last_login_ip;
	}

	public String getLast_login_ip() {
		return this.last_login_ip;
	}

	public void setLast_login_time(String last_login_time) {
		this.last_login_time = last_login_time;
	}

	public String getLast_login_time() {
		return this.last_login_time;
	}

	public void setLast_lock_time(String last_lock_time) {
		this.last_lock_time = last_lock_time;
	}

	public String getLast_lock_time() {
		return this.last_lock_time;
	}

	public void setLast_chgpwd_time(String last_chgpwd_time) {
		this.last_chgpwd_time = last_chgpwd_time;
	}

	public String getLast_chgpwd_time() {
		return this.last_chgpwd_time;
	}

	public void setLogin_fail_times(String login_fail_times) {
		this.login_fail_times = login_fail_times;
	}

	public String getLogin_fail_times() {
		return this.login_fail_times;
	}

	public void setService_expire_date(String service_expire_date) {
		this.service_expire_date = service_expire_date;
	}

	public String getService_expire_date() {
		return this.service_expire_date;
	}

	public void setService_start_date(String service_start_date) {
		this.service_start_date = service_start_date;
	}

	public String getService_start_date() {
		return this.service_start_date;
	}

	public void setVaild_time(String vaild_time) {
		this.vaild_time = vaild_time;
	}

	public String getVaild_time() {
		return this.vaild_time;
	}



	public Set<String> getRoleName() {
		return roleName;
	}

	public void setRoleName(Set<String> roleName) {
		this.roleName = roleName;
	}

	public String getOperator_idx() {
		return operator_idx;
	}

	public void setOperator_idx(String operator_idx) {
		this.operator_idx = operator_idx;
	}

	public String getFaild_times() {
		return faild_times;
	}

	public void setFaild_times(String faild_times) {
		this.faild_times = faild_times;
	}

	public List<UserRolePermessionEntity> getUserRolePermessions() {
		return userRolePermessions;
	}

	public void setUserRolePermessions(List<UserRolePermessionEntity> userRolePermessions) {
		this.userRolePermessions = userRolePermessions;
	}
	
	public List<UserMenuPermessionEntity> getUserMenuPermessions() {
		return userMenuPermessions;
	}

	public void setUserMenuPermessions(
			List<UserMenuPermessionEntity> userMenuPermessions) {
		this.userMenuPermessions = userMenuPermessions;
	}

	public String getFirstChange() {
		return firstChange;
	}

	public void setFirstChange(String firstChange) {
		this.firstChange = firstChange;
	}

	public String getPwdInvalid() {
		return pwdInvalid;
	}

	public void setPwdInvalid(String pwdInvalid) {
		this.pwdInvalid = pwdInvalid;
	}

	public String getFirst_login_chgpwd() {
		return first_login_chgpwd;
	}

	public void setFirst_login_chgpwd(String first_login_chgpwd) {
		this.first_login_chgpwd = first_login_chgpwd;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public Integer getPassword_validdays() {
		return password_validdays;
	}

	public void setPassword_validdays(Integer password_validdays) {
		this.password_validdays = password_validdays;
	}

	public Integer getPassword_remind() {
		return password_remind;
	}

	public void setPassword_remind(Integer password_remind) {
		this.password_remind = password_remind;
	}

	public String getCloud_admin() {
		cloud_admin = SSITCLOUD_ADMIN;
		return cloud_admin;
	}
	

}