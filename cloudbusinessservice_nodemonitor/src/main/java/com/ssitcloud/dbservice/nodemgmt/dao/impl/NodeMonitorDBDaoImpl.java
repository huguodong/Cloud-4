package com.ssitcloud.dbservice.nodemgmt.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dbservice.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbservice.nodemgmt.dao.NodeMonitorDBDao;
import com.ssitcloud.node.entity.NodeMonitor;
import com.ssitcloud.node.entity.NodeMonitorPage;
import com.ssitcloud.node.entity.NodeTypeEntity;

@Repository
public class NodeMonitorDBDaoImpl extends CommonDaoImpl implements NodeMonitorDBDao {

	@Override
	public List<NodeMonitor> queryNodeMonitorByPage(NodeMonitor node) {
		return this.sqlSessionTemplate.selectList("nodemonitor.queryNodeMonitorByPage", node);
	}

	@Override
	public NodeMonitorPage queryNodeMonitorByParam(NodeMonitorPage node) {
		NodeMonitorPage total = this.sqlSessionTemplate.selectOne("nodemonitor.queryNodeMonitorByParam", node);
		node.setTotal(total.getTotal());
		node.setDoAount(false);
		node.setRows(this.sqlSessionTemplate.selectList("nodemonitor.queryNodeMonitorByParam", node));
		return node;
	}

	@Override
	public NodeMonitor queryNodeMonitorById(int node_idx) {
		return this.sqlSessionTemplate.selectOne("nodemonitor.queryNodeMonitorById", node_idx);
	}

	@Override
	public List<NodeTypeEntity> getTypeList(NodeTypeEntity entity) {
		return this.sqlSessionTemplate.selectList("nodemonitor.getTypeList", entity);
	}

}
