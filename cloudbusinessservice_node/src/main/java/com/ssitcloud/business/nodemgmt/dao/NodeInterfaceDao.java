package com.ssitcloud.business.nodemgmt.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.node.entity.NodeInterfaceEntity;
import com.ssitcloud.node.entity.page.NodeInterfacePageEntity;


public interface NodeInterfaceDao {
	
	public void addNodeInterface(NodeInterfaceEntity entity);

	public NodeInterfacePageEntity queryNodeInterfaceByPage(NodeInterfacePageEntity interfacePageEntity);

	public void deleteNodeInterface(Map<String, String> params);

	public List<NodeInterfaceEntity> queryInterfaceByNodeName(Map<String,String> params);
	
	public List<NodeInterfaceEntity> queryForwardNodes(Map<String,String> params);

	public void editNodeInterface(NodeInterfaceEntity entity);
	
	public NodeInterfacePageEntity queryPreNodesByPage(NodeInterfacePageEntity interfacePageEntity);
	
	public List<NodeInterfaceEntity> queryPreNodes(Map<String,String> params);
	
	
}
