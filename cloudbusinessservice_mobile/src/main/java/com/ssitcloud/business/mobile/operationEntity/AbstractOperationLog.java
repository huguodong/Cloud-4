package com.ssitcloud.business.mobile.operationEntity;

import java.sql.Timestamp;

/**
 * 日志实体的抽象对象
 * @author LXP
 * @version 创建时间：2017年3月4日 下午4:13:50
 */
public abstract class AbstractOperationLog {
	/** 操作员ID */
	private Integer operator_idx;
	/** 客户端IP */
	private String client_ip;
	/** 客户端端口号 */
	private String client_port;
	
	private boolean  operation_result;
	private Timestamp operation_time;
	public boolean isOperation_result() {
		return operation_result;
	}
	public void setOperation_result(boolean operation_result) {
		this.operation_result = operation_result;
	}
	public Timestamp getOperation_time() {
		if(operation_time == null){
			return new Timestamp(System.currentTimeMillis());
		}
		return operation_time;
	}
	public void setOperation_time(Timestamp operation_time) {
		this.operation_time = operation_time;
	}
	
	public Integer getOperator_idx() {
		if(operator_idx == null){
			return Integer.valueOf(0);
		}
		return operator_idx;
	}
	public void setOperator_idx(Integer operator_idx) {
		this.operator_idx = operator_idx;
	}
	public String getClient_ip() {
		if(client_ip == null){
			return "unKnow";
		}
		return client_ip;
	}
	public void setClient_ip(String client_ip) {
		this.client_ip = client_ip;
	}
	public String getClient_port() {
		if(client_port == null){
			return "unKnow";
		}
		return client_port;
	}
	public void setClient_port(String client_port) {
		this.client_port = client_port;
	}
	/**
	 * 获取操作码
	 * @return
	 */
	public abstract String getOperation_cmd();
	
	/**
	 * 获取操作格式
	 * @return
	 */
	public abstract String getOperation();
}
