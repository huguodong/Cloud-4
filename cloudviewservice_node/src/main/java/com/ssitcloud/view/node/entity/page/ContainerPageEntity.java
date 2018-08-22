package com.ssitcloud.view.node.entity.page;


/**
 * 
 * @comment 容量表，存储节点信息。表名： container
 * 
 * @author xiebf
 * @data 2016年9月5日
 */
public class ContainerPageEntity extends DatagridPageEntity{
	private static final long serialVersionUID = -2620275071464449606L;
	/** @pdOid 主键id */
	private int container_idx;
	/** @pdOid 容器编号 */
	private java.lang.String container_id;
	/** @pdOid 容器名称 */
	private java.lang.String container_name;
	/** @pdOid 关联的主机id */
	private int host_idx;
	/** @pdOid 关联的主机名称 */
	private String host_name;
	/** @pdOid 容器端口号 */
	private int container_port;
	/** @pdOid 部署路径 */
	private String save_path;
	/** @pdOid 是否启用 */
	private int is_disable;
	private String protocol_type;

	public ContainerPageEntity() {
	}

	public int getContainer_idx() {
		return container_idx;
	}

	public void setContainer_idx(int container_idx) {
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

	public int getHost_idx() {
		return host_idx;
	}

	public void setHost_idx(int host_idx) {
		this.host_idx = host_idx;
	}

	public String getHost_name() {
		return host_name;
	}

	public void setHost_name(String host_name) {
		this.host_name = host_name;
	}

	public int getContainer_port() {
		return container_port;
	}

	public void setContainer_port(int container_port) {
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

	@Override
	public String toString() {
		return "ContainerPageEntity [container_idx=" + container_idx
				+ ", container_id=" + container_id + ", container_name="
				+ container_name + ", host_idx=" + host_idx + ", host_name="
				+ host_name + ", container_port=" + container_port
				+ ", save_path=" + save_path + ", is_disable=" + is_disable
				+ ", protocol_type=" + protocol_type + "]";
	}


}
