package com.ssitcloud.business.nodemgmt.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.business.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.business.nodemgmt.dao.HearbeatDao;
import com.ssitcloud.node.entity.FlowInfo;
import com.ssitcloud.node.entity.NodeEntity;
import com.ssitcloud.node.entity.PrimaryNodeEntity;

@Repository
public class HearbeatDaoImpl extends CommonDaoImpl implements HearbeatDao {

	@Override
	public int saveData(FlowInfo info) {
		return this.sqlSessionTemplate.insert("hearbeat.addData", info);
	}

	@Override
	public List<PrimaryNodeEntity> getDeadNodes(NodeEntity node) {
		return this.sqlSessionTemplate.selectList("hearbeat.getDeadNodes", node);
	}
}
