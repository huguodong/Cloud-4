package com.ssitcloud.business.nodemgmt.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.nodemgmt.dao.NodeInterfaceDao;
import com.ssitcloud.business.nodemgmt.service.NodeInterfaceService;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.node.entity.NodeInterfaceEntity;
import com.ssitcloud.node.entity.page.NodeInterfacePageEntity;

@Service
public class NodeInterfaceServiceImpl implements NodeInterfaceService {
	
	@Autowired
	private NodeInterfaceDao interfaceDao;
	/**添加节点URL配置信息*/
	public ResultEntity addNodeInterface(String req) {
		
		if(req != null && req.length() > 0){
			NodeInterfaceEntity entity = JsonUtils.fromJson(req, NodeInterfaceEntity.class);
			synchronized (NodeInterfaceServiceImpl.class) {
				if(isHasNodeInterface(entity)){
					return new ResultEntity(false, "接口编号重复！");
				}
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				entity.setCreate_time(timestamp);
				entity.setUpdate_time(timestamp);
				interfaceDao.addNodeInterface(entity);
				return new ResultEntity(true,"添加成功 ");
			}
		}
		return new ResultEntity(false,"添加失败");
		
	}
	
	private boolean isHasNodeInterface(NodeInterfaceEntity interfaceEntity){
		
		NodeInterfacePageEntity nodeInterfaceEntity = new NodeInterfacePageEntity();
		nodeInterfaceEntity.setRequest_id(interfaceEntity.getRequest_id());
		nodeInterfaceEntity.setFrom_node(interfaceEntity.getFrom_node());
		nodeInterfaceEntity.setTo_node(interfaceEntity.getTo_node());
		NodeInterfacePageEntity nodeInterfacePageEntity = interfaceDao.queryNodeInterfaceByPage(nodeInterfaceEntity);
		List<NodeInterfacePageEntity> interfacePageEntities = nodeInterfacePageEntity.getRows();
		if(interfacePageEntities != null && !interfacePageEntities.isEmpty()){
			for(NodeInterfacePageEntity entity : interfacePageEntities){
				if(entity.getRequest_id().equals(interfaceEntity.getRequest_id())
						&&entity.getFrom_node().equals(interfaceEntity.getFrom_node())
						&&entity.getTo_node().equals(interfaceEntity.getTo_node())){
					return true;
				}
			}
		}
		return false;
		
	}
	
	
	/**分页查询*/
	public ResultEntity queryNodeInterfaceByPage(String req) {
		ResultEntity resultEntity = new ResultEntity();
		NodeInterfacePageEntity interfacePageEntity = new NodeInterfacePageEntity();
		if(req != null && req.length() > 0){
			interfacePageEntity = JsonUtils.fromJson(req, NodeInterfacePageEntity.class);
		}
		interfacePageEntity = interfaceDao.queryNodeInterfaceByPage(interfacePageEntity);
		resultEntity.setResult(interfacePageEntity);
		resultEntity.setState(true);
		return resultEntity;
	}
	
	/**删除*/
	public ResultEntity deleteNodeInterface(String req) {
		String[] idxs = req.split(",");
		Map<String, String> params = new HashMap<>();
		for(String idx : idxs){
			params.clear();
			params.put("interface_idx", idx);
			interfaceDao.deleteNodeInterface(params);
		}
		return new ResultEntity(true,"删除成功");
	}

	/**更加节点名称查询它拥有的URL*/
	public String queryInterfaceByNodeName(String req) {
		JSONObject resultJson = new JSONObject();
		resultJson.put("status", 1);
		resultJson.put("from_node", "");
		resultJson.put("result","");
		resultJson.put("msg", "success");
		if(req == null || req.length() <= 0){
			resultJson.put("msg", "params is null");
			resultJson.put("status", 0);
			return resultJson.toString();
		}
		
		Map<String, String> params = new HashMap<>();
		params = JsonUtils.fromJson(req, new TypeReference<Map<String,String>>(){});
		List<NodeInterfaceEntity> entities = interfaceDao.queryInterfaceByNodeName(params);
		if(entities == null || entities.isEmpty()){
			resultJson.put("msg", "result is null");
			resultJson.put("status", 0);
			return resultJson.toString();
		}
		
		String fromNode = "";
		JSONArray jsonArray = new JSONArray();
		for(NodeInterfaceEntity entity : entities){
			if(fromNode.equals("")){
				fromNode = entity.getFrom_node();
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("to_node",entity.getTo_node());
			jsonObject.put("ip", entity.getIp());
			jsonObject.put("port",entity.getPort());
			jsonObject.put("request_id", entity.getRequest_id());
			jsonObject.put("request_url", entity.getRequest_url());
			jsonObject.put("protocol", entity.getProtocol());
			jsonArray.add(jsonObject);
		}
		resultJson.put("from_node", fromNode);
		resultJson.put("result", jsonArray.toString());
		return resultJson.toString();
	}


	/**修改*/
	public ResultEntity editNodeInterface(String req) {
		if(req != null && req.length() > 0){
			NodeInterfaceEntity entity = JsonUtils.fromJson(req, NodeInterfaceEntity.class);
			NodeInterfacePageEntity nodeInterfaceEntity = new NodeInterfacePageEntity();
			nodeInterfaceEntity.setRequest_id(entity.getRequest_id());
			nodeInterfaceEntity.setFrom_node(entity.getFrom_node());
			nodeInterfaceEntity.setTo_node(entity.getTo_node());
			synchronized (NodeInterfaceServiceImpl.class) {
				NodeInterfacePageEntity nodeInterfacePageEntity = interfaceDao.queryNodeInterfaceByPage(nodeInterfaceEntity);
				List<NodeInterfacePageEntity> interfacePageEntities = nodeInterfacePageEntity.getRows();
				for(NodeInterfacePageEntity interfacePageEntity : interfacePageEntities){
					if(!interfacePageEntity.getInterface_idx().equals(entity.getInterface_idx())
							&&(entity.getInterface_idx().equals(interfacePageEntity.getInterface_idx())
							&&entity.getFrom_node().equals(interfacePageEntity.getFrom_node())
							&&entity.getTo_node().equals(interfacePageEntity.getTo_node()))
							){
						return new ResultEntity(false,"节点编号重复!");
					}
				}
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				entity.setUpdate_time(timestamp);
				interfaceDao.editNodeInterface(entity);
				return new ResultEntity(true,"修改成功 ");
			}
		}
		return new ResultEntity(false,"修改失败");
		
	}


	/**查询当前节点的上一节点*/
	public ResultEntity queryPreNodesByPage(String req) {
		
		ResultEntity resultEntity = new ResultEntity();
		NodeInterfacePageEntity interfacePageEntity = new NodeInterfacePageEntity();
		
		if(req != null && req.length() > 0){
			interfacePageEntity = JsonUtils.fromJson(req, NodeInterfacePageEntity.class);
		}
		
		interfacePageEntity = interfaceDao.queryPreNodesByPage(interfacePageEntity);
		resultEntity.setResult(interfacePageEntity);
		resultEntity.setState(true);
		return resultEntity;
	}


	/**清除节点的缓存*/
	public ResultEntity clearNodeCache(String req) {
		List<NodeInterfaceEntity> entities = null;
		if(req == null || req.length() <= 0){//如果没有参数传过来，就清除所有的节点缓存
			entities = interfaceDao.queryPreNodes(new HashMap<String,String>());//清除所有的缓存
		}else{
			entities = JsonUtils.fromJson(req, new TypeReference<List<NodeInterfaceEntity>>() {});//清除指定的缓存
		}
		ResultEntity resultE = new ResultEntity();
		
		for(NodeInterfaceEntity entity : entities){
			String protocol = entity.getProtocol();
			String ip = entity.getIp();
			String port = entity.getPort();
			String node_name = entity.getFrom_node();
			String request_url = protocol+"://"+ip+":"+port+"/"+node_name+"/appConfig/clearAppConfig";
			String result = HttpClientUtil.doPost(request_url, new HashMap<String,String>(), "UTF-8");
			if(result != null && result.length() > 0){
				ResultEntity resultEntity = JsonUtils.fromJson(result, ResultEntity.class);
				if(!resultEntity.getState()){
					return resultEntity;
				}
			}
		}
		resultE.setState(true);	
		return resultE;
	}
	
	
	/*public List<NodeInterfaceEntity> queryForwardNodes(String req){
		
		if(req == null || req.length() <= 0)
			return new ArrayList<>();
		Map<String, String> params = new HashMap<>();
		params = JsonUtils.fromJson(req, new TypeReference<Map<String,String>>(){});	
		return interfaceDao.queryForwardNodes(params);
	}*/
	
	
	
	
}
