package com.ssitcloud.common.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
/**
 * 登录对象
 * @package: com.ssitcloud.common.entity
 * @classFile: UserEntity
 * @author: liuBh
 * @description: TODO
 */
@JsonInclude(value=Include.NON_NULL)
public class UserEntity {
	//用户名
	private String operator_id;
	//明文密码
	private String operator_pwd;
	private Boolean rememberme;
	
	private String ip;
	private String port;
	

	
	
	public UserEntity(){}
	public UserEntity(String operator_id,String operator_pwd,String ip,String port){
		this.operator_id=operator_id;
		this.operator_pwd=operator_pwd;
		this.ip=ip;
		this.port=port;
	}
	
	public String getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}
	public String getOperator_pwd() {
		return operator_pwd;
	}
	public void setOperator_pwd(String operator_pwd) {
		this.operator_pwd = operator_pwd;
	}
	public Boolean getRememberme() {
		return rememberme;
	}
	public void setRememberme(Boolean rememberme) {
		this.rememberme = rememberme;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

	
	
}
