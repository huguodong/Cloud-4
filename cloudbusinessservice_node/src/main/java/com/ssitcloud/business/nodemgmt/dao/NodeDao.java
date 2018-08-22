package com.ssitcloud.business.nodemgmt.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.node.entity.ContainerEntity;
import com.ssitcloud.node.entity.HostEntity;
import com.ssitcloud.node.entity.NodeEntity;
import com.ssitcloud.node.entity.NodeRelations;
import com.ssitcloud.node.entity.page.NodePageEntity;

public interface NodeDao {
	public abstract List<NodeEntity> queryNodeByPage(NodeEntity node);

	public abstract NodePageEntity queryNodeByParam(NodePageEntity node);

	public abstract NodeEntity queryNodeById(int node_idx);

	public abstract int deleteNodeById(List<Integer> node_idxs);

	public abstract NodeEntity updateNode(NodeEntity node);

	public abstract NodeEntity addNode(NodeEntity node);

	public abstract int addNodeRelations(NodeRelations node);

	public abstract ContainerEntity findContainerByNodeId(int node_idx);

	public abstract NodeEntity findSecondary(NodeEntity node);

	public abstract List<NodeEntity> findForwards(int node_idx);

	public abstract HostEntity findHostByContainerId(int container_idx);
	
	public abstract List<NodeEntity> findAllSecondaryByPrimaryIdx(int node_idx);
	
	public abstract NodeEntity queryNodeByName(String nodeName);
	
	public abstract NodeEntity queryNodeByParam(Map<String,Object> param);
	
	public abstract List<NodeEntity>queryNodeGroupByName();
	
	//public abstract     
}