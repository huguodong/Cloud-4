package com.ssitcloud.business.nodemgmt.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.business.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.business.nodemgmt.dao.NodeDao;
import com.ssitcloud.node.entity.ContainerEntity;
import com.ssitcloud.node.entity.HostEntity;
import com.ssitcloud.node.entity.NodeEntity;
import com.ssitcloud.node.entity.NodeRelations;
import com.ssitcloud.node.entity.NodeTempEntity;
import com.ssitcloud.node.entity.PrimaryNodeEntity;
import com.ssitcloud.node.entity.page.NodePageEntity;


@Repository
public class NodeDaoImpl extends CommonDaoImpl implements NodeDao {

	@Override
	public List<NodeEntity> queryNodeByPage(NodeEntity node) {
		return this.sqlSessionTemplate.selectList("node.queryNodeByPage", node);
	}

	
	@Override
	public NodePageEntity queryNodeByParam(NodePageEntity node) {
		NodePageEntity total = this.sqlSessionTemplate.selectOne("node.queryNodeByParam", node);
		node.setTotal(total.getTotal());
		if (node != null && null != node.getNode_id() && !"".equals(node.getNode_id())) {
			node.setDoAount(false);
			node.setRows(this.sqlSessionTemplate.selectList("node.queryNodeByParam", node));
			return node;
		}
		List<PrimaryNodeEntity> nodes = this.sqlSessionTemplate.selectList("node.loadPrimaryList", node);
		for (PrimaryNodeEntity entity : nodes) {
			List<NodeTempEntity> secondaryList = loadSecondaryList(entity.getNode_idx());
			entity.setSecondaryList(secondaryList == null ? new ArrayList() : secondaryList);
		}
		node.setRows(nodes);
		return node;
	}

	private List<NodeTempEntity> loadSecondaryList(int node_idx) {
		return this.sqlSessionTemplate.selectList("node.loadSecondaryList", node_idx);
	}

	@Override
	public NodeEntity queryNodeById(int node_idx) {
		return this.sqlSessionTemplate.selectOne("node.queryNodeById", node_idx);
	}

	@Override
	public int deleteNodeById(List<Integer> node_idxs) {
		return this.sqlSessionTemplate.delete("node.deleteNodeById", node_idxs);
	}

	@Override
	public NodeEntity updateNode(NodeEntity node) {
		try{
		int count = this.sqlSessionTemplate.update("node.updateNode", node);
		if (count > 0)
			return node;
		}catch(Exception e){
			String  message = e.getMessage();
			System.out.println(message);
		}
		return null;
	}

	@Override
	public NodeEntity addNode(NodeEntity node) {
		try{
		int count = this.sqlSessionTemplate.insert("node.addNode", node);
		if (count > 0)
			return node;
		
		}catch(Exception e){
			String message = e.getMessage();
			System.out.println(message);
		}
		return null;
	}

	@Override
	public int addNodeRelations(NodeRelations node) {
		return this.sqlSessionTemplate.insert("node.addNodeRelations", node);
	}

	@Override
	public ContainerEntity findContainerByNodeId(int node_idx) {
		return this.sqlSessionTemplate.selectOne("node.findContainerByNodeId", node_idx);
	}

	@Override
	public NodeEntity findSecondary(NodeEntity node) {
		String attributes = node.getNode_attributes();
		if ("primary".equals(attributes)) {
			return this.sqlSessionTemplate.selectOne("node.findPrimary", node);
		} else if ("secondary".equals(attributes)) {
			return this.sqlSessionTemplate.selectOne("node.findSecondary", node);
		}
		return null;
	}

	@Override
	public List<NodeEntity> findForwards(int node_idx) {
		return this.sqlSessionTemplate.selectList("node.findForwards", node_idx);
	}

	@Override
	public HostEntity findHostByContainerId(int container_idx) {
		return this.sqlSessionTemplate.selectOne("node.findHostByContainerId", container_idx);
	}


	@Override
	public List<NodeEntity> findAllSecondaryByPrimaryIdx(int node_idx) {
		return this.sqlSessionTemplate.selectList("node.findAllSecondaryByPrimaryIdx", node_idx);
	}
	

	@Override
	public NodeEntity queryNodeByParam(Map<String,Object> param) {
		return this.sqlSessionTemplate.selectOne("node.queryNodeByParamForSSO", param);
	}


	@Override
	public NodeEntity queryNodeByName(String nodeName) {
		return this.sqlSessionTemplate.selectOne("node.queryNodeByName", nodeName);
	}


	@Override
	public List<NodeEntity> queryNodeGroupByName() {
		return this.sqlSessionTemplate.selectList("node.queryNodeGroupByName");
		
	}
	
	
	

}
