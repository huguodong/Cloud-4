package com.ssitcloud.operlog.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 系统日志
 * 
 * @author Administrator
 * 
 */
@Document(collection = "sys_log")
public class SysLogEntity {
	@Id
	private String id;
	@Field(value = "opertime")
	private String opertime;

	@Field(value = "operator")
	private String operator;

	@Field(value = "opercmd")
	private String opercmd;

	@Field(value = "operresult")
	private String operresult;

	@Field(value = "AuthCardno")
	private String authCardno;

	@Field(value = "AuthType")
	private String authType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOpertime() {
		return opertime;
	}

	public void setOpertime(String opertime) {
		this.opertime = opertime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOpercmd() {
		return opercmd;
	}

	public void setOpercmd(String opercmd) {
		this.opercmd = opercmd;
	}

	public String getOperresult() {
		return operresult;
	}

	public void setOperresult(String operresult) {
		this.operresult = operresult;
	}

	public String getAuthCardno() {
		return authCardno;
	}

	public void setAuthCardno(String authCardno) {
		this.authCardno = authCardno;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

}
