package com.ssitcloud.node.entity;

public class NodeRelations {
	/** 主键ID */
	private int node_relations_idx;
	/** 主节点ID */
	private int primary_node_idx;
	/** 备节点id */
	private int secondary_node_idx;

	public int getNode_relations_idx() {
		return node_relations_idx;
	}

	public void setNode_relations_idx(int node_relations_idx) {
		this.node_relations_idx = node_relations_idx;
	}

	public int getPrimary_node_idx() {
		return primary_node_idx;
	}

	public void setPrimary_node_idx(int primary_node_idx) {
		this.primary_node_idx = primary_node_idx;
	}

	public int getSecondary_node_idx() {
		return secondary_node_idx;
	}

	public void setSecondary_node_idx(int secondary_node_idx) {
		this.secondary_node_idx = secondary_node_idx;
	}

	@Override
	public String toString() {
		return "NodeRelations [node_relations_idx=" + node_relations_idx + ", primary_node_idx=" + primary_node_idx + ", secondary_node_idx=" + secondary_node_idx + "]";
	}

}
