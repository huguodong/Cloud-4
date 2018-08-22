package com.ssitcloud.node.entity;

/**
 * 容器
 * 
 * @author dell
 * 
 */
public class ContainerEntity {
	/** @pdOid 主键id */
	private Integer container_idx;
	/** @pdOid 容器编号 */
	private java.lang.String container_id;
	/** @pdOid 容器名称 */
	private java.lang.String container_name;
	/** @pdOid 关联的主机id */
	private Integer host_idx;
	/** @pdOid 容器端口号 */
	private Integer container_port;
	/** @pdOid 部署路径 */
	private String save_path;
	/** @pdOid 是否启用 */
	private int is_disable = 1;
	/**协议*/
	private String protocol_type;
	/**ip地址*/
	private String host_ip1;
	public Integer getContainer_idx() {
		return container_idx;
	}

	public void setContainer_idx(Integer container_idx) {
		this.container_idx = container_idx;
	}

	public java.lang.String getContainer_id() {
		return container_id;
	}

	public void setContainer_id(java.lang.String container_id) {
		this.container_id = container_id;
	}

	public java.lang.String getContainer_name() {
		return container_name;
	}

	public void setContainer_name(java.lang.String container_name) {
		this.container_name = container_name;
	}

	public Integer getHost_idx() {
		return host_idx;
	}

	public void setHost_idx(Integer host_idx) {
		this.host_idx = host_idx;
	}

	public Integer getContainer_port() {
		return container_port;
	}

	public void setContainer_port(Integer container_port) {
		this.container_port = container_port;
	}

	public String getSave_path() {
		return save_path;
	}

	public void setSave_path(String save_path) {
		this.save_path = save_path;
	}

	public int getIs_disable() {
		return is_disable;
	}

	public void setIs_disable(int is_disable) {
		this.is_disable = is_disable;
	}

	public String getProtocol_type() {
		return protocol_type;
	}

	public void setProtocol_type(String protocol_type) {
		this.protocol_type = protocol_type;
	}
	

	public String getHost_ip1() {
		return host_ip1;
	}

	public void setHost_ip1(String host_ip1) {
		this.host_ip1 = host_ip1;
	}

	@Override
	public String toString() {
		return "ContainerEntity [container_idx=" + container_idx
				+ ", container_id=" + container_id + ", container_name="
				+ container_name + ", host_idx=" + host_idx
				+ ", container_port=" + container_port + ", save_path="
				+ save_path + ", is_disable=" + is_disable + ", protocol_type="
				+ protocol_type + "]";
	}

}
