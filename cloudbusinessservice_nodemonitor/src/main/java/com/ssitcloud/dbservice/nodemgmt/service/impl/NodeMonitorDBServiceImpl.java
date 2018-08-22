package com.ssitcloud.dbservice.nodemgmt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.util.StringUtil;
import com.ssitcloud.dbservice.nodemgmt.dao.NodeMonitorDBDao;
import com.ssitcloud.dbservice.nodemgmt.service.NodeMonitorDBService;
import com.ssitcloud.node.entity.NodeMonitor;
import com.ssitcloud.node.entity.NodeMonitorPage;
import com.ssitcloud.node.entity.NodeTypeEntity;

@Service
public class NodeMonitorDBServiceImpl implements NodeMonitorDBService {

	@Resource
	private NodeMonitorDBDao nodeMonitorDBDao;

	@Override
	public List<NodeMonitor> queryNodeMonitorByPage(String req) {
		NodeMonitor node = null;
		if (StringUtil.isEmpty(req)) {
			node = new NodeMonitor();
		} else {
			JSONObject json = JSONObject.fromObject(req);
			node = (NodeMonitor) JSONObject.toBean(json, NodeMonitor.class);
		}
		return nodeMonitorDBDao.queryNodeMonitorByPage(node);
	}

	@Override
	public NodeMonitorPage queryNodeMonitorByParam(String req) {
		NodeMonitorPage node = null;
		if (StringUtil.isEmpty(req)) {
			node = new NodeMonitorPage();
		} else {
			JSONObject json = JSONObject.fromObject(req);
			node = (NodeMonitorPage) JSONObject.toBean(json, NodeMonitorPage.class);
		}
		return nodeMonitorDBDao.queryNodeMonitorByParam(node);
	}

	@Override
	public NodeMonitor queryNodeMonitorById(String req) {
		if (StringUtil.isEmpty(req)) {
			return new NodeMonitor();
		}
		JSONObject json = JSONObject.fromObject(req);
		int node_idx = json.optInt("node_idx");
		return nodeMonitorDBDao.queryNodeMonitorById(node_idx);
	}

	@Override
	public List<NodeTypeEntity> getTypeList(String req) {
		NodeTypeEntity entity = null;
		if (StringUtil.isEmpty(req)) {
			entity = new NodeTypeEntity();
		} else {
			JSONObject json = JSONObject.fromObject(req);
			entity = (NodeTypeEntity) JSONObject.toBean(json, NodeTypeEntity.class);
		}
		return nodeMonitorDBDao.getTypeList(entity);
	}

}
