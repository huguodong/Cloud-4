package com.ssitcloud.node.entity;

/**
 * 云主机
 * 
 * @author dell
 * 
 */
public class HostEntity {
	/** @pdOid 主键 */
	private Integer host_idx;
	/** @pdOid 云主机编号 */
	private java.lang.String host_id;
	/** @pdOid 云主机名称 */
	private java.lang.String host_name;
	/** @pdOid 云主机IP1 */
	private java.lang.String host_ip1;
	/** @pdOid 云主机IP2 */
	private java.lang.String host_ip2;
	/** @pdOid 云主机描述 */
	private java.lang.String host_desc;

	public Integer getHost_idx() {
		return host_idx;
	}

	public void setHost_idx(Integer host_idx) {
		this.host_idx = host_idx;
	}

	public java.lang.String getHost_id() {
		return host_id;
	}

	public void setHost_id(java.lang.String host_id) {
		this.host_id = host_id;
	}

	public java.lang.String getHost_name() {
		return host_name;
	}

	public void setHost_name(java.lang.String host_name) {
		this.host_name = host_name;
	}

	public java.lang.String getHost_ip1() {
		return host_ip1;
	}

	public void setHost_ip1(java.lang.String host_ip1) {
		this.host_ip1 = host_ip1;
	}

	public java.lang.String getHost_ip2() {
		return host_ip2;
	}

	public void setHost_ip2(java.lang.String host_ip2) {
		this.host_ip2 = host_ip2;
	}

	public java.lang.String getHost_desc() {
		return host_desc;
	}

	public void setHost_desc(java.lang.String host_desc) {
		this.host_desc = host_desc;
	}

	@Override
	public String toString() {
		return "HostEntity [host_idx=" + host_idx + ", host_id=" + host_id + ", host_name=" + host_name + ", host_ip1=" + host_ip1 + ", host_ip2=" + host_ip2 + ", host_desc=" + host_desc + "]";
	}

}
