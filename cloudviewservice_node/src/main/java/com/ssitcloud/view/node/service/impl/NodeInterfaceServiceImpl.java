package com.ssitcloud.view.node.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.common.util.HttpClientUtil;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.node.service.NodeInterfaceService;

@Service
public class NodeInterfaceServiceImpl extends BasicServiceImpl implements NodeInterfaceService{
	
	private String url_prefix = "nodeInterface_";
	
	public ResultEntity addNodeInterface(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(url_prefix + method), map, charset);
		if(result != null && result.length() > 0){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}
	

	@Override
	public ResultEntity queryNodeInterfaceByPage(String req) {
		Map<String,String> map = new HashMap<>();
		if(req != null && req.length() > 0){
			map.put("req", req);
		}
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(url_prefix + method), map, charset);
		if(result != null && result.length() > 0){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}

	
	public ResultEntity deleteNodeInterface(String req) {
		
		if(req == null || req.length() <= 0) return new ResultEntity();
		
		Map<String,String> map = new HashMap<>();
		map.put("req", req);
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(url_prefix + method), map, charset);
		if(result != null && result.length() > 0){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}

	
	public String queryInterfaceByNodeName(String req) {
		
		Map<String,String> map = new HashMap<>();
		map.put("req", req);
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(url_prefix + method), map, charset);
		return result;
	}



	
	public ResultEntity editNodeInterface(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(url_prefix + method), map, charset);
		if(result != null && result.length() > 0){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}


	
	public ResultEntity clearNodeCache(String req) {
		
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(url_prefix + method), map, charset);
		if(result != null && result.length() > 0){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}
	
	public ResultEntity queryPreNodesByPage(String req) {
		
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(url_prefix + method), map, charset);
		if(result != null && result.length() > 0){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}
	
	
	
	

}
