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
import com.ssitcloud.node.entity.ContainerEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.common.util.HttpClientUtil;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.node.entity.page.ContainerPageEntity;
import com.ssitcloud.view.node.service.ContainerViewService;

@Service
public class ContainerViewServiceImpl extends BasicServiceImpl implements ContainerViewService {
	private String url_prefix = "container_";

	@Override
	public ResultEntity queryContainerByPage(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(url_prefix + method), map, charset);
		if (result != null&&JSONUtils.mayBeJSON(result)) {
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return null;
	}

	@Override
	public ContainerPageEntity queryContainerByParam(String req) {
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String reqURL = requestURL.getRequestURL(url_prefix + method);
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		if (result != null) {
			ResultEntityF<ContainerPageEntity> rs = JsonUtils.fromJson(result, new TypeReference<ResultEntityF<ContainerPageEntity>>() {});
			if (rs != null) {
				return rs.getResult();
			}
		}
		return null;
	}

	@Override
	public ContainerEntity queryContainerById(String req) {
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String reqURL = requestURL.getRequestURL(url_prefix + method);
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		if (result != null) {
			ResultEntityF<ContainerEntity> rs = JsonUtils.fromJson(result, new TypeReference<ResultEntityF<ContainerEntity>>() {});
			if (rs != null) {
				return rs.getResult();
			}
		}
		return null;
	}

	@Override
	public ResultEntity deleteContainerById(String req) {
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
	public ResultEntity updateContainer(String req) {
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
	public ResultEntity addContainer(String req) {
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
	public ResultEntity start(String req) {
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
	public ResultEntity stop(String req) {
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
