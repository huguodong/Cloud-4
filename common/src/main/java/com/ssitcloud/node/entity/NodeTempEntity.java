package com.ssitcloud.node.entity;

/***********************************************************************
 * Module:  Node.java
 * Author:  dell
 * Purpose: Defines the Class Node
 ***********************************************************************/

/** 节点 */
public class NodeTempEntity extends NodeEntity{
	/** 节点类型 */
	private String node_type_name;
	/** 容器名称 */
	private String container_name;

    private Integer node_status;
    
	public String getNode_type_name() {
		return node_type_name;
	}

	public void setNode_type_name(String node_type_name) {
		this.node_type_name = node_type_name;
	}

	public String getContainer_name() {
		return container_name;
	}

	public void setContainer_name(String container_name) {
		this.container_name = container_name;
	}

	public Integer getNode_status() {
		return node_status;
	}

	public void setNode_status(Integer node_status) {
		this.node_status = node_status;
	}

	@Override
	public String toString() {
		return "NodeTempEntity [node_type_name=" + node_type_name + ", container_name=" + container_name + ", node_status=" + node_status + ", getNode_idx()=" + getNode_idx() + ", getNode_model()=" + getNode_model()
				+ ", getNode_type_idx()=" + getNode_type_idx() + ", getNode_id()=" + getNode_id() + ", getNode_name()=" + getNode_name() + ", getNode_attributes()=" + getNode_attributes()
				+ ", getContainer_idx()=" + getContainer_idx() + ", getLibrary_idxs()=" + getLibrary_idxs() + ", getNode_relations()=" + getNode_relations() + "]";
	}
	
}