package com.ssitcloud.business.nodemgmt.dao;


import java.util.List;

import com.ssitcloud.node.entity.FlowInfo;
import com.ssitcloud.node.entity.NodeEntity;
import com.ssitcloud.node.entity.PrimaryNodeEntity;


public interface HearbeatDao {
	public int saveData(FlowInfo info);
	public List<PrimaryNodeEntity> getDeadNodes(NodeEntity node);
	
}
