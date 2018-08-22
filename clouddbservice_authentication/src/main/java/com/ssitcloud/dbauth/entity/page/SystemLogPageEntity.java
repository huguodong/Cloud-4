package com.ssitcloud.dbauth.entity.page;

import java.sql.Timestamp;

import com.ssitcloud.common.entity.DatagridPageEntity;

public class SystemLogPageEntity extends DatagridPageEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
	/**	用户ID */
	private String operator_id;
	/**	用户名 */
	private String operator_name;
	
	private String begin_time;
	
	private String end_time;
	
	private String library_idx;
	/**消息说明**/
	private String log_desc;
	/**消息代码**/
	private String log_code;

	public String getLog_desc() {
		return log_desc;
	}
	public void setLog_desc(String log_desc) {
		this.log_desc = log_desc;
	}
	public String getLibrary_idx() {
		return library_idx;
	}
	public void setLibrary_idx(String library_idx) {
		this.library_idx = library_idx;
	}
	public String getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
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
	public String getLog_code() {
		return log_code;
	}
	public void setLog_code(String log_code) {
		this.log_code = log_code;
	}
	

}
