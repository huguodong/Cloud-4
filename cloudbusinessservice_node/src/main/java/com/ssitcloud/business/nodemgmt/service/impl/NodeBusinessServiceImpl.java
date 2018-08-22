package com.ssitcloud.business.nodemgmt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.nodemgmt.dao.NodeDao;
import com.ssitcloud.business.nodemgmt.service.NodeBusinessService;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.StringUtil;
import com.ssitcloud.node.entity.ContainerEntity;
import com.ssitcloud.node.entity.HostEntity;
import com.ssitcloud.node.entity.NodeEntity;
import com.ssitcloud.node.entity.NodeRelations;
import com.ssitcloud.node.entity.page.NodePageEntity;


@Service
public class NodeBusinessServiceImpl extends BasicServiceImpl implements NodeBusinessService {
	//private String url_prefix = "node_";
	@Resource
	private NodeDao nodeDao;
	
	@Override
	public ResultEntity queryNodeByPage(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			List<NodeEntity> list = _queryNodeByPage(req);
			if (list != null) {
				resultEntity.setValue(true, "success", "", list);
			} else {
				resultEntity.setValue(false, "无记录");
			}
		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName + "() 发生异常--" + e.getMessage(), "");
		}
		return resultEntity;
	}
	
	private List<NodeEntity> _queryNodeByPage(String req) {
		NodeEntity node = null;
		if (StringUtil.isEmpty(req)) {
			node = new NodeEntity();
		} else {
			JSONObject json = JSONObject.fromObject(req);
			node = (NodeEntity) JSONObject.toBean(json, NodeEntity.class);
		}
		return nodeDao.queryNodeByPage(node);
	}

	
	@Override
	public ResultEntity queryNodeByParam(String req) {
		
		ResultEntity resultEntity = new ResultEntity();
		NodePageEntity page = new NodePageEntity();

		if (StringUtils.hasText(req)) {
			page = JsonUtils.fromJson(req, NodePageEntity.class);
		}
		page.setDoAount(true);
		page = nodeDao.queryNodeByParam(page);
		resultEntity.setResult(page);
		resultEntity.setState(true);
		return resultEntity;
		
	}

	@Override
	public ResultEntity queryNodeById(String req) {
		
		ResultEntity resultEntity = new ResultEntity();
		NodeEntity entity=new NodeEntity();
		if(StringUtils.hasText(req)){
			entity=JsonUtils.fromJson(req, NodeEntity.class);
		}
		NodeEntity nodeEntity = queryNodeById(entity);
		if (nodeEntity != null) {
			resultEntity.setState(true);
			resultEntity.setResult(nodeEntity);
		}else{
			resultEntity.setState(false);
		}
		return resultEntity;
	}
	
	private NodeEntity queryNodeById(NodeEntity node) {
		int node_idx = node.getNode_idx();
		return nodeDao.queryNodeById(node_idx);
	}
	
	
	@Override
	public ResultEntity deleteNodeById(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			int count =_deleteNodeById(req);
			if (count > 0) {
				Map<String, String> result = new HashMap<String, String>();
				result.put("result", String.valueOf(count));
				resultEntity.setValue(true, "success", "", result);
			} else {
				resultEntity.setValue(false, "删除失败");
			}
		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName + "() 发生异常--" + e.getMessage(), "");
		}
		return resultEntity;
	}
	
	public int _deleteNodeById(String req) {
		if (StringUtil.isEmpty(req)) {
			return 0;
		}
		List<Integer> list = new ArrayList<Integer>();
		if (req.indexOf(",") != -1) {
			String[] ss = req.split("\\,");
			for (String s : ss) {
				list.add(Integer.parseInt(s));
			}
		} else {
			list.add(Integer.parseInt(req));
		}
		return nodeDao.deleteNodeById(list);
	}
	

	@Override
	public ResultEntity updateNode(String req) {
		
		ResultEntity resultEntity = new ResultEntity();
		try {
			NodeEntity node = _updateNode(req);
			if (node != null) {
				resultEntity.setValue(true, "success", "", node);
			} else {
				resultEntity.setValue(false, "更新失败");
			}
		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName + "() 发生异常--" + e.getMessage(), "");
		}
		return resultEntity;
	}
	
	private NodeEntity _updateNode(String req) {
		if (StringUtil.isEmpty(req)) {
			return null;
		}
		JSONObject json = JSONObject.fromObject(req);
		NodeEntity node = (NodeEntity) JSONObject.toBean(json, NodeEntity.class);
		String attributes = node.getNode_attributes();
		NodeEntity nodeNewPri = nodeDao.updateNode(node);

		if ("primary".equals(attributes) && nodeNewPri != null) {// 从节点
			List<NodeEntity> nodeEntitys = nodeDao.findAllSecondaryByPrimaryIdx(nodeNewPri.getNode_idx());
			if (nodeEntitys != null) {// 主备节点拥有相同的几个属性
				for (NodeEntity nodeSec : nodeEntitys) {
					nodeSec.setLibrary_idxs(nodeNewPri.getLibrary_idxs());
					nodeSec.setNode_relations(nodeNewPri.getNode_relations());
					nodeDao.updateNode(nodeSec);
				}
			}
		}

		return nodeNewPri;
	}
	

	@Override
	public ResultEntity addNode(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			NodeEntity node = _addNode(req);
			if (node != null) {
				resultEntity.setValue(true, "success", "", node);
			} else {
				resultEntity.setValue(false, "新增失败");
			}
		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName + "() 发生异常--" + e.getMessage(), "");
		}
		return resultEntity;
	}
	
	
	private NodeEntity _addNode(String req) {
		if (StringUtil.isEmpty(req)) {
			return null;
		}
		JSONObject json = JSONObject.fromObject(req);
		NodeEntity node = (NodeEntity) JSONObject.toBean(json, NodeEntity.class);
		String attributes = node.getNode_attributes();
		if ("secondary".equals(attributes)) {// 从节点
			int primary_node_idx = json.optInt("primary_node_idx");
			NodeEntity nodeEntity = nodeDao.queryNodeById(primary_node_idx);
			if (nodeEntity != null) {// 主备节点拥有相同的几个属性
				node.setNode_model(nodeEntity.getNode_model());
				node.setNode_type_idx(nodeEntity.getNode_type_idx());
				node.setLibrary_idxs(nodeEntity.getLibrary_idxs());
				node.setNode_relations(nodeEntity.getNode_relations());
				node.setEnable(0);
				NodeEntity node1 = nodeDao.addNode(node);
				if (node1 != null) {// 添加主备关系
					System.out.println("---添加备节点成功---");
					int secondary_node_idx = node1.getNode_idx();
					String jsonStr = "{\"primary_node_idx\":" + primary_node_idx + ",\"secondary_node_idx\":" + secondary_node_idx + "}";
					json = JSONObject.fromObject(jsonStr);
					NodeRelations nodeRelations = (NodeRelations) JSONObject.toBean(json, NodeRelations.class);
					int count = nodeDao.addNodeRelations(nodeRelations);
					if (count > 0) {
						System.out.println("---主备关系添加成功---");
					}
				}
				return node1;
			}
		} else {
			return nodeDao.addNode(node);
		}
		return null;
	}
	
	
	

	@Override
	public ResultEntity findAddressByNodeName(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			Map<String, String> map = _findAddressByNodeName(req);
			if (map != null && map.containsKey("address")) {
				resultEntity.setValue(true, "success", "", map);
			} else {
				resultEntity.setValue(false, "无记录");
			}
		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName + "(): 发生异常--" + e.getMessage(), "");
		}
		return resultEntity;
	}
	
	public Map<String, String> _findAddressByNodeName(String req) {
		if (StringUtil.isEmpty(req)) {
			return new HashMap<String, String>();
		}
		JSONObject json = JSONObject.fromObject(req);
		String node_name = json.optString("node_name");
		NodeEntity node = nodeDao.queryNodeByName(node_name);
		if (node == null)
			return new HashMap<String, String>();
		int node_idx = node.getNode_idx();
		ContainerEntity container = nodeDao.findContainerByNodeId(node_idx);
		if (container == null)
			return new HashMap<String, String>();
		int port = container.getContainer_port();
		int container_idx = container.getContainer_idx();
		String protocol_type=container.getProtocol_type()==null?"http":container.getProtocol_type();
		HostEntity host = nodeDao.findHostByContainerId(container_idx);
		if (host == null)
			return new HashMap<String, String>();
		String ip = host.getHost_ip2();
		if (ip == null || "".equals(ip) || ip.trim().length() == 0) {
			ip = host.getHost_ip1();
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("address", protocol_type+"://"+ip + ":" + port + "/" + node_name);
		map.put("libs", node.getLibrary_idxs());
		return map;
	}
	
	public ResultEntity queryNodeGroupByName() {
		
		ResultEntity resultEntity = new ResultEntity();
		List<NodeEntity> entities = nodeDao.queryNodeGroupByName();
		if(entities != null && !entities.isEmpty()){
			resultEntity.setValue(true, "success","",entities);
			
		}else{
			resultEntity.setValue(false, "无记录");
		}
		return resultEntity;
		
	}

	@Override
	public ResultEntity findAddressForSSO(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			Map<String, String> map = _findAddressForSSO(req);
			if (map != null && map.containsKey("address")) {
				resultEntity.setValue(true, "success", "", map);
			} else {
				resultEntity.setValue(false, "内部错误，请联系系统管理员！");
			}
		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName + "(): 发生异常--" + e.getMessage(), "");
		}
		return resultEntity;
	}
	
	private Map<String, String> _findAddressForSSO(String req) {
		if (StringUtil.isEmpty(req)) {
			return new HashMap<String, String>();
		}
		JSONObject json = JSONObject.fromObject(req);
		String node_name = json.optString("node_name");
		int lib_idx = json.optInt("lib_idx");
		//根据传入的图书馆id、节点名称查询匹配的节点
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("node_name", node_name);
		param.put("lib_idx", "%"+lib_idx+"%");
		NodeEntity node = nodeDao.queryNodeByParam(param);
		if (node == null){//如果没有则查询相同节点名的节点，忽略图书馆
			node = nodeDao.queryNodeByName(node_name);
		}
		if (node == null)
			return new HashMap<String, String>();
		int node_idx = node.getNode_idx();
		ContainerEntity container = nodeDao.findContainerByNodeId(node_idx);
		if (container == null)
			return new HashMap<String, String>();
		int port = container.getContainer_port();
		int container_idx = container.getContainer_idx();
		String protocol_type=container.getProtocol_type()==null?"http":container.getProtocol_type();
		HostEntity host = nodeDao.findHostByContainerId(container_idx);
		if (host == null)
			return new HashMap<String, String>();
		String ip = host.getHost_ip2();
		if (ip == null || "".equals(ip) || ip.trim().length() == 0) {
			ip = host.getHost_ip1();
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("address", protocol_type+"://"+ip + ":" + port + "/" + node_name);
		return map;
	}
	
	
	
	
	
	
}
