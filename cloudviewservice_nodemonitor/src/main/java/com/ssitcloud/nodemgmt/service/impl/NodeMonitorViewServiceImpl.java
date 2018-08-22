package com.ssitcloud.nodemgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.common.util.HttpClientUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.node.entity.NodeMonitor;
import com.ssitcloud.nodemgmt.service.NodeMonitorViewService;
@Service
public class NodeMonitorViewServiceImpl extends BasicServiceImpl implements NodeMonitorViewService {

	@Override
	public ResultEntity queryNodeMonitorByPage(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(method), map, charset);
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
	public ResultEntity queryNodeMonitorByParam(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(method), map, charset);
		if (result != null&&JSONUtils.mayBeJSON(result)) {
			ResultEntity resultEntity = new ResultEntity();
			resultEntity = JsonUtils.fromJson(result, ResultEntity.class);
			return resultEntity;
		}
		return null;
	}

	@Override
	public ResultEntity queryNodeMonitorById(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(method), map, charset);
		if (result != null&&JSONUtils.mayBeJSON(result)) {
			JSONObject object = JSONObject.fromObject(result);
			JSONObject _result = object.optJSONObject("result");
			
			ResultEntity resultEntity = new ResultEntity();
			if(!_result.isNullObject()){
				NodeMonitor nodeMonitor=(NodeMonitor)JSONObject.toBean(_result, NodeMonitor.class);
				resultEntity.setResult(nodeMonitor);
			}
			resultEntity.setState(object.optBoolean("state"));
			resultEntity.setMessage(object.optString("message"));
			resultEntity.setRetMessage(object.optString("retMessage"));
			return resultEntity;
		}
		return null;
	}

	@Override
	public ResultEntity getTypeList(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println(requestURL.getRequestURL(method));
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(method), map, charset);
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

}
