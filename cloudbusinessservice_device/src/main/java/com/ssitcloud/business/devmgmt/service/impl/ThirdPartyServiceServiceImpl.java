package com.ssitcloud.business.devmgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.devmgmt.service.ThirdPartyServiceService;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class ThirdPartyServiceServiceImpl extends BasicServiceImpl implements ThirdPartyServiceService {

	private String URL_queryThirdPartyService = "queryThirdPartyService";
	private String URL_deleteThirdPartyService = "deleteThirdPartyService";
	private String URL_queryThirdPartyServiceByPage = "queryThirdPartyServiceByPage";
	private String URL_editThirdPartyService = "editThirdPartyService";
	
	private String URL_editDisplayInfo = "editDisplayInfo";
	private String URL_deleteDisplayInfo = "deleteDisplayInfo";
	private String URL_queryDisplayInfo = "queryDisplayInfo";
	private String URL_queryDisplayInfoList = "queryDisplayInfoList";

	public ResultEntity queryThirdPartyServiceByParams(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_queryThirdPartyService), map, charset);
		if(JSONUtils.mayBeJSON(result)){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity(false,"查询失败");
	}

	@Override
	public ResultEntity deleteThirdPartyService(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_deleteThirdPartyService), map, charset);
		if(JSONUtils.mayBeJSON(result)){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity(false,"操作失败");
	}

	@Override
	public ResultEntity queryThirdPartyServiceByPage(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_queryThirdPartyServiceByPage), map, charset);
		if(JSONUtils.mayBeJSON(result)){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity(false,"查询失败");
	}

	@Override
	public ResultEntity editThirdPartyService(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_editThirdPartyService), map, charset);
		if(JSONUtils.mayBeJSON(result)){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity(false,"操作失败");
	}

	@Override
	public ResultEntity editDisplayInfo(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_editDisplayInfo), map, charset);
		if(JSONUtils.mayBeJSON(result)){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity(false,"操作失败");
	}

	@Override
	public ResultEntity queryDisplayInfo(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_queryDisplayInfo), map, charset);
		if(JSONUtils.mayBeJSON(result)){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity(false,"查询失败");
	}

	@Override
	public ResultEntity queryDisplayInfoList(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_queryDisplayInfoList), map, charset);
		if(JSONUtils.mayBeJSON(result)){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity(false,"查询失败");
	}

	@Override
	public ResultEntity deleteDisplayInfo(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_deleteDisplayInfo), map, charset);
		if(JSONUtils.mayBeJSON(result)){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity(false,"操作失败");
	}

}
