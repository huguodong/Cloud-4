package com.ssitcloud.node.entity.page;

import com.ssitcloud.common.entity.DatagridPageEntity;

/**
 * 
 * @comment 节点类型表，存储节点类型信息。表名： node_type
 * 
 * @author xiebf
 * @data 2016年9月5日
 */
public class NodeTypePageEntity extends DatagridPageEntity {
	private static final long serialVersionUID = -5664039309281028437L;
	/** 主键ID */
	private int node_type_idx;
	/** 类型编号 */
	private String node_type_id;
	/** 类型名称 */
	private String node_type_name;

	public NodeTypePageEntity() {
	}

	public int getNode_type_idx() {
		return node_type_idx;
	}

	public void setNode_type_idx(int node_type_idx) {
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
		return "NodeTypePageEntity [node_type_idx=" + node_type_idx
				+ ", node_type_id=" + node_type_id + ", node_type_name="
				+ node_type_name + "]";
	}

}
