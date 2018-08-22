package com.ssitcloud.view.node.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.common.util.HttpClientUtil;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.node.entity.NodeTypeEntity;
import com.ssitcloud.view.node.entity.page.NodeTypePageEntity;
import com.ssitcloud.view.node.service.NodeTypeViewService;

@Service
public class NodeTypeViewServiceImpl extends BasicServiceImpl implements NodeTypeViewService {
	private String url_prefix = "nodeType_";

	@Override
	public ResultEntity queryNodeTypeByPage(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(url_prefix + method), map, charset);
		if (result != null&&JSONUtils.mayBeJSON(result)) {
			JSONObject object = JSONObject.fromObject(result);
			JSONArray array = object.optJSONArray("result");
			ResultEntity resultEntity = new ResultEntity();
			resultEntity.setResult(array);
			resultEntity.setState(object.optBoolean("state"));
			resultEntity.setMessage(object.optString("message"));
			resultEntity.setRetMessage(object.optString("retMessage"));
			return resultEntity;
		}
		return null;
	}

	@Override
	public NodeTypePageEntity queryNodeTypeByParam(String req) {
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String reqURL = requestURL.getRequestURL(url_prefix + method);
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		if (result != null&&JSONUtils.mayBeJSON(result)) {
			ResultEntityF<NodeTypePageEntity> rs = JsonUtils.fromJson(result, new TypeReference<ResultEntityF<NodeTypePageEntity>>() {});
			if (rs != null) {
				return rs.getResult();
			}
		}
		return null;
	}

	@Override
	public NodeTypeEntity queryNodeTypeById(String req) {
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String reqURL = requestURL.getRequestURL(url_prefix + method);
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		if (result != null) {
			ResultEntityF<NodeTypeEntity> rs = JsonUtils.fromJson(result, new TypeReference<ResultEntityF<NodeTypeEntity>>() {});
			if (rs != null) {
				return rs.getResult();
			}
		}
		return null;
	}

	@Override
	public ResultEntity deleteNodeTypeById(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(url_prefix + method), map, charset);
		if (result != null&&JSONUtils.mayBeJSON(result)) {
			JSONObject object = JSONObject.fromObject(result);
			JSONObject _result = object.optJSONObject("result");
			ResultEntity resultEntity = new ResultEntity();
			resultEntity.setState(object.optBoolean("state"));
			resultEntity.setMessage(object.optString("message"));
			resultEntity.setRetMessage(object.optString("retMessage"));
			resultEntity.setResult(_result);
			return resultEntity;
		}
		return null;
	}

	@Override
	public ResultEntity updateNodeType(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(url_prefix + method), map, charset);
		if (result != null&&JSONUtils.mayBeJSON(result)) {
			JSONObject object = JSONObject.fromObject(result);
			JSONObject _result = object.optJSONObject("result");
			ResultEntity resultEntity = new ResultEntity();
			resultEntity.setState(object.optBoolean("state"));
			resultEntity.setMessage(object.optString("message"));
			resultEntity.setRetMessage(object.optString("retMessage"));
			resultEntity.setResult(_result);
			return resultEntity;
		}
		return null;
	}

	@Override
	public ResultEntity addNodeType(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(url_prefix + method), map, charset);
		if (result != null&&JSONUtils.mayBeJSON(result)) {
			JSONObject object = JSONObject.fromObject(result);
			JSONObject _result = object.optJSONObject("result");
			ResultEntity resultEntity = new ResultEntity();
			resultEntity.setState(object.optBoolean("state"));
			resultEntity.setMessage(object.optString("message"));
			resultEntity.setRetMessage(object.optString("retMessage"));
			resultEntity.setResult(_result);
			return resultEntity;
		}
		return null;
	}
}
