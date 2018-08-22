package com.ssitcloud.dbservice.nodemgmt.service;

import java.util.List;

import com.ssitcloud.node.entity.NodeMonitor;
import com.ssitcloud.node.entity.NodeMonitorPage;
import com.ssitcloud.node.entity.NodeTypeEntity;

public interface NodeMonitorDBService {
	public List<NodeMonitor> queryNodeMonitorByPage(String req);

	public NodeMonitorPage queryNodeMonitorByParam(String req);

	public NodeMonitor queryNodeMonitorById(String req);

	public List<NodeTypeEntity> getTypeList(String req);
}
