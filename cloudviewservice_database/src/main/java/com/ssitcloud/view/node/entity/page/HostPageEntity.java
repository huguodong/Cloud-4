package com.ssitcloud.view.node.entity.page;


/**
 * 
 * @comment 节点表，存储节点信息。表名： node
 * 
 * @author xiebf
 * @data 2016年9月5日
 */
public class HostPageEntity extends DatagridPageEntity {
	private static final long serialVersionUID = 4694963221753677158L;
	/** @pdOid 主键 */
	private int host_idx;
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

	public HostPageEntity() {
	}

	public int getHost_idx() {
		return host_idx;
	}

	public void setHost_idx(int host_idx) {
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
		return host_ip1 == null ? "" : host_ip1;
	}

	public java.lang.String getHost_ip2() {
		return host_ip2 == null ? "" : host_ip2;
	}

	public void setHost_ip2(java.lang.String host_ip2) {
		this.host_ip2 = host_ip2;
	}

	public void setHost_ip1(java.lang.String host_ip1) {
		this.host_ip1 = host_ip1;
	}

	public java.lang.String getHost_desc() {
		return host_desc;
	}

	public void setHost_desc(java.lang.String host_desc) {
		this.host_desc = host_desc;
	}

	@Override
	public String toString() {
		return "HostPageEntity [host_idx=" + host_idx + ", host_id=" + host_id + ", host_name=" + host_name + ", host_ip1=" + host_ip1 + ", host_ip2=" + host_ip2 + ", host_desc=" + host_desc + "]";
	}

}
