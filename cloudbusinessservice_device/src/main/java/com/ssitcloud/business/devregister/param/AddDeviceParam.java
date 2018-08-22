package com.ssitcloud.business.devregister.param;

import java.sql.Timestamp;

/** 
 *
 * <p>2016年5月5日 下午2:41:10  
 * @author hjc 
 *
 */
public class AddDeviceParam {
	/** 进行新增操作的用户ip */
	private String admin_idx;
	
	/** 设备IP */
	private String ip;
	/** 设备端口 */
	private String port;
	
	
	/**	自增长ID */
	private String operator_idx;
	/**	用户ID */
	private String operator_id;
	/**	馆ID */
	private String library_idx;
	/**	SOX模板ID */
	private String sox_tpl_id;
	/**	用户名 */
	private String operator_name;
	/**	用户密码 */
	private String operator_pwd;
	/**	用户类型，1-云平台系统管理员，2-海恒维护、3-图书馆系统管理员、4-图书馆用户、5－设备用户*/
	private String operator_type="5";
	/**	是否激活 */
	private String isActive="1";
	/**	是否锁定 */
	private String isLock="0";
	/**	是否已经登录 */
	private String isLogged="0";
	/**	最后登录IP */
	private String last_login_ip;
	/**	最后登录时间 */
	private Timestamp last_login_time;
	/**	最后锁定时间 */
	private Timestamp last_lock_time;
	/**	最后修改密码时间 */
	private Timestamp last_chgpwd_time;
	/**	登录失败次数，满足配置自动锁定用户 */
	private String login_fail_times;
	/** 创建时间 */
	private Timestamp createtime;
	
	
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
	public String getLibrary_idx() {
		return library_idx;
	}
	public void setLibrary_idx(String library_idx) {
		this.library_idx = library_idx;
	}
	public String getSox_tpl_id() {
		return sox_tpl_id;
	}
	public void setSox_tpl_id(String sox_tpl_id) {
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
	public String getLogin_fail_times() {
		return login_fail_times;
	}
	public void setLogin_fail_times(String login_fail_times) {
		this.login_fail_times = login_fail_times;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public String getAdmin_idx() {
		return admin_idx;
	}
	public void setAdmin_idx(String admin_idx) {
		this.admin_idx = admin_idx;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	
}
