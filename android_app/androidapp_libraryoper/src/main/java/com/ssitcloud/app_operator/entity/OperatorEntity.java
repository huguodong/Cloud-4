package com.ssitcloud.app_operator.entity;

import java.sql.Timestamp;

/**
 * 馆员实体
 *  
 * <p>2017年3月16日 上午9:51
 * @author shuangjunjie
 *
 */
public class OperatorEntity {
	/**	自增长ID */
	private Integer	operator_idx;
	/**	用户ID */
	private String operator_id;
	/**	馆ID */
	private Integer library_idx;
	/**	SOX模板ID */
	private Integer sox_tpl_id;
	/**	用户名 */
	private String operator_name;
	/**	用户密码 */
	private String operator_pwd;
	/**	用户类型，0-海恒管理员，1-设备用户，2-图书馆用户 */
	private Integer operator_type;
	/**	是否激活 */
	private Integer isActive;
	/**	是否锁定 */
	private Integer isLock;
	/**	是否已经登录 */
	private Integer isLogged;
	/**	最后登录IP */
	private String last_login_ip;
	/**	最后登录时间 */
	private Timestamp last_login_time;
	/**	最后锁定时间 */
	private Timestamp last_lock_time;
	/**	最后修改密码时间 */
	private Timestamp last_chgpwd_time;
	/**	登录失败次数，满足配置自动锁定用户 */
	private Integer login_fail_times;
	/** 创建时间 */
	private Timestamp createtime;
	/** 手机号*/
	private String mobile;
	/** 邮箱*/
	private String email;
	/** 身份证号*/
	private String id_card;

	/** 图书馆id*/
	private String lib_id;
	/** 图书馆名*/
	private String lib_name;
	/** 锁定时间*/
	private String lock_time;

	private String passwordSet;

	public static final String SSITCLOUD_ADMIN="1";
	public static final String SSITCLOUD_MANAGER="2";
	public static final String LIBRARY_MANAGER="3";
	public static final String LIBRARY_USER="4";//图书馆用户

	public String getPasswordSet() {
		return passwordSet;
	}

	public void setPasswordSet(String passwordSet) {
		this.passwordSet = passwordSet;
	}

	public Integer getOperator_idx() {
		return operator_idx;
	}
	public void setOperator_idx(Integer operator_idx) {
		this.operator_idx = operator_idx;
	}
	public String getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}
	public Integer getLibrary_idx() {
		return library_idx;
	}
	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}
	public Integer getSox_tpl_id() {
		return sox_tpl_id;
	}
	public void setSox_tpl_id(Integer sox_tpl_id) {
		this.sox_tpl_id = sox_tpl_id;
	}
	public String getOperator_name() {
		return operator_name;
	}
	public void setOperator_name(String operator_name) {
		this.operator_name = operator_name;
	}
	public String getOperator_pwd() {
		return operator_pwd;
	}
	public void setOperator_pwd(String operator_pwd) {
		this.operator_pwd = operator_pwd;
	}
	public Integer getOperator_type() {
		return operator_type;
	}
	public void setOperator_type(Integer operator_type) {
		this.operator_type = operator_type;
	}
	public Integer getIsActive() {
		return isActive;
	}
	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
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
	public Timestamp getLast_chgpwd_time() {
		return last_chgpwd_time;
	}
	public void setLast_chgpwd_time(Timestamp last_chgpwd_time) {
		this.last_chgpwd_time = last_chgpwd_time;
	}
	public Integer getLogin_fail_times() {
		return login_fail_times;
	}
	public void setLogin_fail_times(Integer login_fail_times) {
		this.login_fail_times = login_fail_times;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
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
	public String getId_card() {
		return id_card;
	}
	public void setId_card(String id_card) {
		this.id_card = id_card;
	}

	public String getLib_id() {
		return lib_id;
	}

	public void setLib_id(String lib_id) {
		this.lib_id = lib_id;
	}

	public String getLib_name() {
		return lib_name;
	}

	public void setLib_name(String lib_name) {
		this.lib_name = lib_name;
	}

	public String getLock_time() {
		return lock_time;
	}

	public void setLock_time(String lock_time) {
		this.lock_time = lock_time;
	}
}
