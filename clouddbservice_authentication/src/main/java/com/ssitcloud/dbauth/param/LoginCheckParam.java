package com.ssitcloud.dbauth.param;

/**
 * 用户登录用到的相关信息类
 * <p>2016年4月9日 下午3:45:02
 * @author hjc
 *
 */
public class LoginCheckParam {
	/** 主键 */
	private String operator_idx;
	/** 用户ID */
	private String operator_id;
	/** 用户名 */
	private String operator_name;
	/** 图书馆id */
	private String lib_id;
	/** 图书馆名称 */
	private String lib_name;
	/** SOX模板ID */
	private String sox_tpl_id;
	/** 用户密码 */
	private String operator_pwd;
	/** 用户类型 */
	private String operator_type;
	/** 是否激活 */
	private String isActive;
	/** 是否锁定 */
	private String isLock;
	/** 锁定时长 */
	private String lock_time;
	/** 是否登录 */
	private String isLogged;
	/** 上次登录ip */
	private String last_login_ip;
	/** 上次登录时间 */
	private String last_login_time;
	/** 上次锁定时间 */
	private String last_lock_time;
	/** 上次修改密码时间 */
	private String last_chgpwd_time;
	/** 登录失败锁定次数 */
	private String login_fail_times;
	/** 服务到期时间 */
	private String service_expire_date;
	/** 服务开始时间 */
	private String service_start_date;
	/** 登录失败次数,用于网页传递 */
	private String faild_times;
	/** 登录时间段 */
	private String vaild_time;
	
	private String firstChange;//首次登录修改密码
	
	private String pwdInvalid;//密码失效，也强制修改密码
	
	private String first_login_chgpwd;//第一次登陆是否修改密码，1是  0否
	
	private String createtime;//创建时间
	
	private Integer password_validdays; //密码有效天数
	
	private Integer password_remind; //密码提醒天数
	
	//图书馆自增id
	private int library_idx;
	
	public int getLibrary_idx() {
		return library_idx;
	}
	public void setLibrary_idx(int library_idx) {
		this.library_idx = library_idx;
	}
	
	public String getOperator_idx() {
		return operator_idx;
	}
	public void setOperator_idx(String operator_idx) {
		this.operator_idx = operator_idx;
	}
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
	public String getSox_tpl_id() {
		return sox_tpl_id;
	}
	public void setSox_tpl_id(String sox_tpl_id) {
		this.sox_tpl_id = sox_tpl_id;
	}
	public String getOperator_pwd() {
		return operator_pwd;
	}
	public void setOperator_pwd(String operator_pwd) {
		this.operator_pwd = operator_pwd;
	}
	public String getOperator_type() {
		return operator_type;
	}
	public void setOperator_type(String operator_type) {
		this.operator_type = operator_type;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getIsLock() {
		return isLock;
	}
	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}
	public String getIsLogged() {
		return isLogged;
	}
	public void setIsLogged(String isLogged) {
		this.isLogged = isLogged;
	}
	public String getLast_login_ip() {
		return last_login_ip;
	}
	public void setLast_login_ip(String last_login_ip) {
		this.last_login_ip = last_login_ip;
	}
	public String getLast_login_time() {
		return last_login_time;
	}
	public void setLast_login_time(String last_login_time) {
		this.last_login_time = last_login_time;
	}
	public String getLast_lock_time() {
		return last_lock_time;
	}
	public void setLast_lock_time(String last_lock_time) {
		this.last_lock_time = last_lock_time;
	}
	public String getLast_chgpwd_time() {
		return last_chgpwd_time;
	}
	public void setLast_chgpwd_time(String last_chgpwd_time) {
		this.last_chgpwd_time = last_chgpwd_time;
	}
	public String getLogin_fail_times() {
		return login_fail_times;
	}
	public void setLogin_fail_times(String login_fail_times) {
		this.login_fail_times = login_fail_times;
	}
	public String getService_expire_date() {
		return service_expire_date;
	}
	public void setService_expire_date(String service_expire_date) {
		this.service_expire_date = service_expire_date;
	}
	public String getService_start_date() {
		return service_start_date;
	}
	public void setService_start_date(String service_start_date) {
		this.service_start_date = service_start_date;
	}
	public String getLock_time() {
		return lock_time;
	}
	public void setLock_time(String lock_time) {
		this.lock_time = lock_time;
	}
	public String getFaild_times() {
		return faild_times;
	}
	public void setFaild_times(String faild_times) {
		this.faild_times = faild_times;
	}
	public String getVaild_time() {
		return vaild_time;
	}
	public void setVaild_time(String vaild_time) {
		this.vaild_time = vaild_time;
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
	
	
	
	
}
