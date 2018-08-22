package com.ssitcloud.view.node.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ssitcloud.view.node.entity.HostEntity;
import com.ssitcloud.view.node.entity.page.HostPageEntity;
import com.ssitcloud.view.node.service.HostViewService;

@Service
public class HostViewServiceImpl extends BasicServiceImpl implements HostViewService {
	private String url_prefix = "host_";

	@Override
	public List<HostEntity> queryHostByPage(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(url_prefix + method), map, charset);
		if(result!=null){
			ResultEntityF<List<HostEntity>> rs=JsonUtils.fromJson(result, new TypeReference<ResultEntityF<List<HostEntity>>>() {});
			if(rs!=null){
				return rs.getResult();
			}
		}
		return null;
	}

	@Override
	public HostPageEntity queryHostByParam(String req) {
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String reqURL = requestURL.getRequestURL(url_prefix + method);
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		if(result!=null){
			//JSONObject obj=JSONObject.fromObject(result);
			//ResultEntityF rs=(ResultEntityF)JSONObject.toBean(obj,ResultEntityF.class);
			ResultEntityF<HostPageEntity> rs=JsonUtils.fromJson(result, new TypeReference<ResultEntityF<HostPageEntity>>(){});
			if(rs!=null){
				return rs.getResult();
			}
		}
		return null;
	}

	@Override
	public HostEntity queryHostById(String req) {
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String reqURL = requestURL.getRequestURL(url_prefix + method);
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		if(result!=null){
			ResultEntityF<HostEntity> rs=JsonUtils.fromJson(result, new TypeReference<ResultEntityF<HostEntity>>() {});
			if(rs!=null){
				return rs.getResult();
			}
		}
		return null;
	}

	@Override
	public ResultEntity deleteHostById(String req) {
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
	public ResultEntity updateHost(String req) {
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
	public ResultEntity addHost(String req) {
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
