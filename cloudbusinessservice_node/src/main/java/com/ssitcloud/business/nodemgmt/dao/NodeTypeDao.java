package com.ssitcloud.business.nodemgmt.dao;

import java.util.List;

import com.ssitcloud.node.entity.NodeTypeEntity;
import com.ssitcloud.node.entity.page.NodeTypePageEntity;


public interface NodeTypeDao {
	public abstract List<NodeTypeEntity> queryNodeTypeByPage(NodeTypeEntity nodeType);

	public abstract NodeTypePageEntity queryNodeTypeByParam(NodeTypePageEntity nodeType);

	public abstract NodeTypeEntity queryNodeTypeById(int nodeType_idx);

	public abstract int deleteNodeTypeById(List<Integer> nodeType_idxs);

	public abstract int updateNodeType(NodeTypeEntity nodeType);

	public abstract int addNodeType(NodeTypeEntity nodeType);

}