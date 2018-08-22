package com.ssitcloud.dbauth.entity.page;

import com.ssitcloud.common.entity.DatagridPageEntity;

public class SoxTempPageEntity extends DatagridPageEntity<SoxTempPageEntity> {
	private static final long serialVersionUID = 1L;
	
	
	/** 模板ID */
	private Integer sox_tpl_id;
	/** 模板名称 */
	private String sox_tpl_name;
	/** 密码长度 */
	private Integer password_length;
	/** 密码字符集 1大写，2小写，3数字，4特殊字符^%&',;=?$/*/
	private String password_charset;
	/** 登录失败次数 */
	private Integer login_fail_times;
	/** 锁定时长 */
	private Integer lock_time;
	/** 首次登陆修改密码 */
	private Integer first_login_chgpwd;
	/** 历史密码保留个数 */
	private Integer count_history_password;
	/** 密码有效天数 */
	private Integer password_validdays;
	/** 到期提醒天数 */
	private Integer password_remind;
	/** 用户可用时间段,如8:00-9:00 */
	private String vaild_time;
	
	private String operType;
	
	private Integer version_stamp;
	
	
	
	public Integer getSox_tpl_id() {
		return sox_tpl_id;
	}
	public void setSox_tpl_id(Integer sox_tpl_id) {
		this.sox_tpl_id = sox_tpl_id;
	}
	public String getSox_tpl_name() {
		return sox_tpl_name;
	}
	public void setSox_tpl_name(String sox_tpl_name) {
		this.sox_tpl_name = sox_tpl_name;
	}
	public Integer getPassword_length() {
		return password_length;
	}
	public void setPassword_length(Integer password_length) {
		this.password_length = password_length;
	}
	public String getPassword_charset() {
		return password_charset;
	}
	public void setPassword_charset(String password_charset) {
		this.password_charset = password_charset;
	}
	public Integer getLogin_fail_times() {
		return login_fail_times;
	}
	public void setLogin_fail_times(Integer login_fail_times) {
		this.login_fail_times = login_fail_times;
	}
	public Integer getLock_time() {
		return lock_time;
	}
	public void setLock_time(Integer lock_time) {
		this.lock_time = lock_time;
	}
	public Integer getFirst_login_chgpwd() {
		return first_login_chgpwd;
	}
	public void setFirst_login_chgpwd(Integer first_login_chgpwd) {
		this.first_login_chgpwd = first_login_chgpwd;
	}
	public Integer getCount_history_password() {
		return count_history_password;
	}
	public void setCount_history_password(Integer count_history_password) {
		this.count_history_password = count_history_password;
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
	public String getVaild_time() {
		return vaild_time;
	}
	public void setVaild_time(String vaild_time) {
		this.vaild_time = vaild_time;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public Integer getVersion_stamp() {
		return version_stamp;
	}
	public void setVersion_stamp(Integer version_stamp) {
		this.version_stamp = version_stamp;
	}
	
}
