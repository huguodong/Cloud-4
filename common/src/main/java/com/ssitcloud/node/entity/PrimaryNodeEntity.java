package com.ssitcloud.node.entity;

import java.util.List;

/***********************************************************************
 * Module:  PrimaryNodeEntity.java
 * Author:  dell
 * Purpose: Defines the Class PrimaryNodeEntity
 ***********************************************************************/

/** 主节点 */
public class PrimaryNodeEntity extends NodeTempEntity{
	private List<NodeEntity> secondaryList;
	
	public List<NodeEntity> getSecondaryList() {
		return secondaryList;
	}

	public void setSecondaryList(List<NodeEntity> secondaryList) {
		this.secondaryList = secondaryList;
	}

	@Override
	public String toString() {
		return "PrimaryNodeEntity [getNode_type_name()=" + getNode_type_name() + ", getContainer_name()=" + getContainer_name() + ", getNode_idx()=" + getNode_idx() + ", getNode_model()="
				+ getNode_model() + ", getNode_type_idx()=" + getNode_type_idx() + ", getNode_id()=" + getNode_id() + ", getNode_name()=" + getNode_name() + ", getNode_attributes()="
				+ getNode_attributes() + ", getContainer_idx()=" + getContainer_idx() + ", getLibrary_idxs()=" + getLibrary_idxs() + ", getNode_relations()=" + getNode_relations() + "]";
	}
}