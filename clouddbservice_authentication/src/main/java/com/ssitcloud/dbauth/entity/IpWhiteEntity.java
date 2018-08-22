package com.ssitcloud.dbauth.entity;

/**
 * IP白名单
 * <p>2016年4月5日 上午11:06:18
 * @author hjc
 *
 */
public class IpWhiteEntity {
	/** 用户自增ID */
	private Integer operator_idx;
	/** IP地址 */
	private String ipaddr;
	public Integer getOperator_idx() {
		return operator_idx;
	}
	public void setOperator_idx(Integer operator_idx) {
		this.operator_idx = operator_idx;
	}
	public String getIpaddr() {
		return ipaddr;
	}
	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}
	
	
}
