package com.ssitcloud.node.entity;

/**
 * 节点类型
 * @author dell
 *
 */
public class NodeTypeEntity {
	/** @pdOid 主键ID */
	private Integer node_type_idx;
	/** @pdOid 类型编号 */
	private String node_type_id;
	/** @pdOid 类型名称 */
	private String node_type_name;

	public Integer getNode_type_idx() {
		return node_type_idx;
	}

	public void setNode_type_idx(Integer node_type_idx) {
		this.node_type_idx = node_type_idx;
	}

	public String getNode_type_id() {
		return node_type_id;
	}

	public void setNode_type_id(String node_type_id) {
		this.node_type_id = node_type_id;
	}

	public String getNode_type_name() {
		return node_type_name;
	}

	public void setNode_type_name(String node_type_name) {
		this.node_type_name = node_type_name;
	}

	@Override
	public String toString() {
		return "NodeTypeEntity [node_type_idx=" + node_type_idx
				+ ", node_type_id=" + node_type_id + ", node_type_name="
				+ node_type_name + "]";
	}
}
