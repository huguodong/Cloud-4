package com.ssitcloud.business.devmgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.devmgmt.service.DeviceRunConfigService;
import com.ssitcloud.common.entity.ResultEntity;

/**
 * 
 * @comment 
 * @date 2016年6月14日
 * @author hwl
 */
@Service
public class DeviceRunConfigServiceImpl extends BasicServiceImpl implements
		DeviceRunConfigService {

	public static final String URL_SelectRunConfig = "SelDeviceRunConf";
	
	public static final String URL_DeleteRunConfig = "DelDeviceRunData";
	
	public static final String URL_UpdateRunConfig = "DelAndAddDeviceRunConf";
	
	public static final String URL_InsertRunConfig = "AddDeviceRunData";

	private static final String URL_SelFunctionByDeviceIdx = "SelFunctionByDeviceIdx";
	
	@Override
	public ResultEntity SelectRunConfig(String json) {
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String reqURL=requestURL.getRequestURL(URL_SelectRunConfig);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());	
		return JsonUtils.fromJson(result, ResultEntity.class);
	}

	@Override
	public ResultEntity DeleteRunConfig(String json) {
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String reqURL=requestURL.getRequestURL(URL_DeleteRunConfig);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());	
		return JsonUtils.fromJson(result, ResultEntity.class);
	}

	@Override
	public ResultEntity InsertRunConfig(String json) {
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String reqURL=requestURL.getRequestURL(URL_InsertRunConfig);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());	
		return JsonUtils.fromJson(result, ResultEntity.class);
	}

	@Override
	public ResultEntity UpdateRunConfig(String json) {
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String reqURL=requestURL.getRequestURL(URL_UpdateRunConfig);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());	
		return JsonUtils.fromJson(result, ResultEntity.class);
	}

	@Override
	public ResultEntity SelFunctionByDeviceIdx(String req) {
		return postUrl(URL_SelFunctionByDeviceIdx, req);
	}

	
}
