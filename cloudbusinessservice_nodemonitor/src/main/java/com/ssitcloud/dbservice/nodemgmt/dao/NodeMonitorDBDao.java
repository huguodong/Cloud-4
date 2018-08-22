package com.ssitcloud.dbservice.nodemgmt.dao;

import java.util.List;

import com.ssitcloud.node.entity.NodeMonitor;
import com.ssitcloud.node.entity.NodeMonitorPage;
import com.ssitcloud.node.entity.NodeTypeEntity;

public interface NodeMonitorDBDao {
	public List<NodeMonitor> queryNodeMonitorByPage(NodeMonitor node);

	public NodeMonitorPage queryNodeMonitorByParam(NodeMonitorPage node);

	public NodeMonitor queryNodeMonitorById(int node_idx);

	public List<NodeTypeEntity> getTypeList(NodeTypeEntity entity);
}
