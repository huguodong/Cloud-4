package com.ssitcloud.mobile.entity;

import java.sql.Timestamp;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 操作日志实体类
 * <p>2016年4月11日 上午11:05:57
 * @author hjc
 * opreation_log_idx int(11) NOT NULLID
	operation_time timestamp NOT NULL操作时间
	operator_idx int(11) NOT NULL操作员ID
	client_ip varchar(64) NOT NULL客户端IP
	client_port varchar(10) NOT NULL客户端端口号
	operation_cmd varchar(10) NOT NULL操作类型
	operation_result varchar(10) NOT NULL操作结果
	operation text NULL详细说明
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OperationLogEntity {
	/** 日志表自增ID */
	private Integer opreation_log_idx;
	/** 操作时间 */
	private Timestamp operation_time;
	/** 操作员ID */
	private Integer operator_idx;
	/** 客户端IP */
	private String client_ip;
	/** 客户端端口号 */
	private String client_port;
	/** 操作类型 */
	private String operation_cmd;
	/** 操作结果 */
	private String operation_result;
	/** 详细说明 */
	private String operation;
	/** 错误码 */
	private String error_code;
	
	
	public Integer getOpreation_log_idx() {
		return opreation_log_idx;
	}
	public void setOpreation_log_idx(Integer opreation_log_idx) {
		this.opreation_log_idx = opreation_log_idx;
	}
	public Timestamp getOperation_time() {
		return operation_time;
	}
	public void setOperation_time(Timestamp operation_time) {
		this.operation_time = operation_time;
	}
	public Integer getOperator_idx() {
		return operator_idx;
	}
	public void setOperator_idx(Integer operator_idx) {
		this.operator_idx = operator_idx;
	}
	public String getClient_ip() {
		return client_ip;
	}
	public void setClient_ip(String client_ip) {
		this.client_ip = client_ip;
	}
	public String getClient_port() {
		return client_port;
	}
	public void setClient_port(String client_port) {
		this.client_port = client_port;
	}
	public String getOperation_cmd() {
		return operation_cmd;
	}
	public void setOperation_cmd(String operation_cmd) {
		this.operation_cmd = operation_cmd;
	}
	public String getOperation_result() {
		return operation_result;
	}
	public void setOperation_result(String operation_result) {
		this.operation_result = operation_result;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	
}
