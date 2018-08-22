package com.ssitcloud.dbauth.param;


/**
 * 获取操作员使用期限信息接口的返回参数类
 * <p>2016年4月8日 下午3:06:56
 * @author hjc
 *
 */
public class GetVaildTimeParam {
	/**	用户ID */
	private String operator_id;
	/**	用户名 */
	private String operator_name;
	/**	SOX模板ID */
	private String sox_tpl_id;
	/** 模板名称 */
	private String sox_tpl_name;
	/** 密码长度 */
	private String password_length;
	/** 密码字符集 1大写，2小写，3数字，4特殊字符^%&',;=?$/*/
	private String password_charset;
	/** 登录失败次数 */
	private String login_fail_times;
	/** 是否锁定*/
	private String isLock;
	/** 锁定时长 */
	private String lock_time;
	/** 首次登陆修改密码 */
	private String first_login_chgpwd;
	/** 验证历史密码个数 */
	private String count_history_password;
	/** 密码有效天数 */
	private String password_validdays;
	/** 用户可用时间段,如8：00-9:00 */
	private String vaild_time;
	/** 图书馆名称 */
	private String lib_name;
	/** 用户服务描述 */
	private String lib_service_tpl_desc;
	/** 服务开始时间 */
	private String service_start_date;
	/** 服务结束时间 */
	private String service_expire_date;
	/** 最大设备数 */
	private String max_device_count;
	/** 最大用户数 */
	private String max_operator_count;
	/** 图书馆规模数 */
	private String max_sublib_count;
	public String getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}
	public String getOperator_name() {
		return operator_name;
	}
	public void setOperator_name(String operator_name) {
		this.operator_name = operator_name;
	}
	public String getSox_tpl_id() {
		return sox_tpl_id;
	}
	public void setSox_tpl_id(String sox_tpl_id) {
		this.sox_tpl_id = sox_tpl_id;
	}
	public String getSox_tpl_name() {
		return sox_tpl_name;
	}
	public void setSox_tpl_name(String sox_tpl_name) {
		this.sox_tpl_name = sox_tpl_name;
	}
	public String getPassword_length() {
		return password_length;
	}
	public void setPassword_length(String password_length) {
		this.password_length = password_length;
	}
	public String getPassword_charset() {
		return password_charset;
	}
	public void setPassword_charset(String password_charset) {
		this.password_charset = password_charset;
	}
	public String getLogin_fail_times() {
		return login_fail_times;
	}
	public void setLogin_fail_times(String login_fail_times) {
		this.login_fail_times = login_fail_times;
	}
	public String getLock_time() {
		return lock_time;
	}
	public void setLock_time(String lock_time) {
		this.lock_time = lock_time;
	}
	public String getFirst_login_chgpwd() {
		return first_login_chgpwd;
	}
	public void setFirst_login_chgpwd(String first_login_chgpwd) {
		this.first_login_chgpwd = first_login_chgpwd;
	}
	public String getCount_history_password() {
		return count_history_password;
	}
	public void setCount_history_password(String count_history_password) {
		this.count_history_password = count_history_password;
	}
	public String getPassword_validdays() {
		return password_validdays;
	}
	public void setPassword_validdays(String password_validdays) {
		this.password_validdays = password_validdays;
	}
	public String getVaild_time() {
		return vaild_time;
	}
	public void setVaild_time(String vaild_time) {
		this.vaild_time = vaild_time;
	}
	public String getLib_name() {
		return lib_name;
	}
	public void setLib_name(String lib_name) {
		this.lib_name = lib_name;
	}
	public String getLib_service_tpl_desc() {
		return lib_service_tpl_desc;
	}
	public void setLib_service_tpl_desc(String lib_service_tpl_desc) {
		this.lib_service_tpl_desc = lib_service_tpl_desc;
	}
	public String getService_start_date() {
		return service_start_date;
	}
	public void setService_start_date(String service_start_date) {
		this.service_start_date = service_start_date;
	}
	public String getService_expire_date() {
		return service_expire_date;
	}
	public void setService_expire_date(String service_expire_date) {
		this.service_expire_date = service_expire_date;
	}
	public String getMax_device_count() {
		return max_device_count;
	}
	public void setMax_device_count(String max_device_count) {
		this.max_device_count = max_device_count;
	}
	public String getMax_operator_count() {
		return max_operator_count;
	}
	public void setMax_operator_count(String max_operator_count) {
		this.max_operator_count = max_operator_count;
	}
	public String getMax_sublib_count() {
		return max_sublib_count;
	}
	public void setMax_sublib_count(String max_sublib_count) {
		this.max_sublib_count = max_sublib_count;
	}
	public String getIsLock() {
		return isLock;
	}
	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}
	
	
}
