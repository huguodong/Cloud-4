package com.ssitcloud.business.nodemgmt.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.business.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.business.nodemgmt.dao.NodeTypeDao;
import com.ssitcloud.node.entity.NodeTypeEntity;
import com.ssitcloud.node.entity.page.NodeTypePageEntity;

@Repository
public class NodeTypeDaoImpl extends CommonDaoImpl implements NodeTypeDao {

	@Override
	public List<NodeTypeEntity> queryNodeTypeByPage(NodeTypeEntity nodeType) {
		return this.sqlSessionTemplate.selectList("nodeType.queryNodeTypeByPage", nodeType);
	}

	@Override
	public NodeTypePageEntity queryNodeTypeByParam(NodeTypePageEntity nodeType) {
		NodeTypePageEntity total = this.sqlSessionTemplate.selectOne("nodeType.queryNodeTypeByParam", nodeType);
		nodeType.setTotal(total.getTotal());
		nodeType.setDoAount(false);
		nodeType.setRows(this.sqlSessionTemplate.selectList("nodeType.queryNodeTypeByParam", nodeType));
		return nodeType;
	}

	@Override
	public NodeTypeEntity queryNodeTypeById(int node_type_idx) {
		return this.sqlSessionTemplate.selectOne("nodeType.queryNodeTypeById", node_type_idx);
	}

	@Override
	public int deleteNodeTypeById(List<Integer> node_type_idxs) {
		return this.sqlSessionTemplate.delete("nodeType.deleteNodeTypeById", node_type_idxs);
	}

	@Override
	public int updateNodeType(NodeTypeEntity nodeType) {
		return this.sqlSessionTemplate.update("nodeType.updateNodeType", nodeType);
	}

	@Override
	public int addNodeType(NodeTypeEntity nodeType) {
		return this.sqlSessionTemplate.insert("nodeType.addNodeType", nodeType);
	}

}
