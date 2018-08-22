package com.ssitcloud.business.nodemgmt.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.business.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.business.nodemgmt.dao.NodeInterfaceDao;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.node.entity.NodeInterfaceEntity;
import com.ssitcloud.node.entity.page.NodeInterfacePageEntity;

@Repository
public class NodeInterfaceDaoImpl extends CommonDaoImpl implements NodeInterfaceDao {

	public void addNodeInterface(NodeInterfaceEntity entity) {
		this.sqlSessionTemplate.insert("nodeInterface.addNodeInterface", entity);
	}

	
	public NodeInterfacePageEntity queryNodeInterfaceByPage(NodeInterfacePageEntity interfacePageEntity) {
		
		NodeInterfacePageEntity total = this.sqlSessionTemplate.selectOne("nodeInterface.queryNodeInterfaceByPage",interfacePageEntity);
		interfacePageEntity.setTotal(total == null ? 0:total.getTotal());
		interfacePageEntity.setDoAount(false);
		List<NodeInterfacePageEntity> entities = this.sqlSessionTemplate.selectList("nodeInterface.queryNodeInterfaceByPage", interfacePageEntity);
		interfacePageEntity.setRows(entities);
		return interfacePageEntity;
	}

	public void deleteNodeInterface(Map<String,String> map) {
		this.sqlSessionTemplate.delete("nodeInterface.deleteNodeInterface",map);
	}

	public List<NodeInterfaceEntity> queryInterfaceByNodeName(Map<String,String> params) {
		
		return this.sqlSessionTemplate.selectList("nodeInterface.queryInterfaceByNodeName",params);
	}

	public List<NodeInterfaceEntity> queryForwardNodes(Map<String, String> params) {
		return this.sqlSessionTemplate.selectList("nodeInterface.queryForwardNodes",params);
	}
	
	public void editNodeInterface(NodeInterfaceEntity entity) {
		this.sqlSessionTemplate.selectList("nodeInterface.editNodeInterface",entity);
	}


	public NodeInterfacePageEntity queryPreNodesByPage(NodeInterfacePageEntity interfacePageEntity) {
		
		NodeInterfacePageEntity total = this.sqlSessionTemplate.selectOne("nodeInterface.queryPreNodesByPage",interfacePageEntity);
		interfacePageEntity.setTotal(total == null ? 0:total.getTotal());
		interfacePageEntity.setDoAount(false);
		List<NodeInterfacePageEntity> entities = this.sqlSessionTemplate.selectList("nodeInterface.queryPreNodesByPage", interfacePageEntity);
		interfacePageEntity.setRows(entities);
		return interfacePageEntity;
		
	}

	public List<NodeInterfaceEntity> queryPreNodes(Map<String, String> params) {
		
		return this.sqlSessionTemplate.selectList("nodeInterface.queryPreNodes",params);
	}

	
	
	
	
	
	

}
