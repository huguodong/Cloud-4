package com.ssitcloud.business.nodemgmt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.nodemgmt.dao.NodeTypeDao;
import com.ssitcloud.business.nodemgmt.service.NodeTypeBusinessService;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.StringUtil;
import com.ssitcloud.node.entity.NodeTypeEntity;
import com.ssitcloud.node.entity.page.NodeTypePageEntity;

@Service
public class NodeTypeBusinessServiceImpl extends BasicServiceImpl implements NodeTypeBusinessService {
	private String url_prefix="nodeType_";
	@Resource
	private NodeTypeDao nodeTypeDao;
	@Override
	public ResultEntity queryNodeTypeByPage(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			List<NodeTypeEntity> list =_queryNodeTypeByPage(req);
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
	
	public List<NodeTypeEntity> _queryNodeTypeByPage(String req) {
		NodeTypeEntity nodeType = null;
		if (StringUtil.isEmpty(req)) {
			nodeType = new NodeTypeEntity();
		} else {
			JSONObject json = JSONObject.fromObject(req);
			nodeType = (NodeTypeEntity) JSONObject.toBean(json, NodeTypeEntity.class);
		}
		return nodeTypeDao.queryNodeTypeByPage(nodeType);
	}
	@Override
	public ResultEntity queryNodeTypeByParam(String req) {
		ResultEntity resultEntity = new ResultEntity();
		NodeTypePageEntity page = new NodeTypePageEntity();

		if (StringUtils.hasText(req)) {
			page = JsonUtils.fromJson(req, NodeTypePageEntity.class);
		}
		page.setDoAount(true);
		page = nodeTypeDao.queryNodeTypeByParam(page);
		resultEntity.setResult(page);
		resultEntity.setState(true);
		return resultEntity;
		
	}

	@Override
	public ResultEntity queryNodeTypeById(String req) {
		
		ResultEntity resultEntity = new ResultEntity();
		NodeTypeEntity entity=new NodeTypeEntity();
		if(StringUtils.hasText(req)){
			entity=JsonUtils.fromJson(req, NodeTypeEntity.class);
		}
		NodeTypeEntity nodeTypeEntity = _queryNodeTypeById(entity);
		if (nodeTypeEntity != null) {
			resultEntity.setState(true);
			resultEntity.setResult(nodeTypeEntity);
		}else{
			resultEntity.setState(false);
		}
		return resultEntity;
		
	}

	
	private NodeTypeEntity _queryNodeTypeById(NodeTypeEntity entity) {
		int nodeType_idx = entity.getNode_type_idx();
		return nodeTypeDao.queryNodeTypeById(nodeType_idx);
	}
	
	
	@Override
	public ResultEntity deleteNodeTypeById(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			int count = _deleteNodeTypeById(req);
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
	
	private int _deleteNodeTypeById(String req) {
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
		return nodeTypeDao.deleteNodeTypeById(list);
	}

	@Override
	public ResultEntity updateNodeType(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			int count = _updateNodeType(req);
			if (count > 0) {
				Map<String, String> result = new HashMap<String, String>();
				result.put("result", String.valueOf(count));
				resultEntity.setValue(true, "success", "", result);
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
	
	private int _updateNodeType(String req) {
		if (StringUtil.isEmpty(req)) {
			return 0;
		}
		JSONObject json = JSONObject.fromObject(req);
		NodeTypeEntity nodeType = (NodeTypeEntity) JSONObject.toBean(json, NodeTypeEntity.class);
		return nodeTypeDao.updateNodeType(nodeType);
	}
	@Override
	public ResultEntity addNodeType(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			int count = _addNodeType(req);
			if (count > 0) {
				Map<String, String> result = new HashMap<String, String>();
				result.put("result", String.valueOf(count));
				resultEntity.setValue(true, "success", "", result);
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
	
	public int _addNodeType(String req) {
		if (StringUtil.isEmpty(req)) {
			return 0;
		}
		JSONObject json = JSONObject.fromObject(req);
		NodeTypeEntity nodeType = (NodeTypeEntity) JSONObject.toBean(json, NodeTypeEntity.class);
		return nodeTypeDao.addNodeType(nodeType);
	}

}
