package com.ssitcloud.business.devmgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.devmgmt.service.DeviceExtConfigService;
import com.ssitcloud.common.entity.ResultEntity;

/**
 * 
 * @comment 
 * @date 2016年6月14日
 * @author hwl
 */
@Service
public class DeviceExtConfigServiceImpl extends BasicServiceImpl implements
		DeviceExtConfigService {

	public static final String URL_SelectExt = "SelDeviceExtConfig";
	public static final String URL_DeleteExt = "DelDeviceExtConfig";
	public static final String URL_InsertExt = "AddNewDeviceExtConfig";
	public static final String URL_UpdateExt = "DelAndAddExtConfig";
	private static final String URL_GetDevExtModel = "GetDevExtModel";
		
	@Override
	public ResultEntity SelectExt(String json) {
		
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String reqURL=requestURL.getRequestURL(URL_SelectExt);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());	
		return JsonUtils.fromJson(result, ResultEntity.class);
	}

	@Override
	public ResultEntity UpdateExtdata(String json) {
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String reqURL=requestURL.getRequestURL(URL_UpdateExt);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());	
		return JsonUtils.fromJson(result, ResultEntity.class);
	}

	@Override
	public ResultEntity DeleteExtdata(String json) {
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String reqURL=requestURL.getRequestURL(URL_DeleteExt);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());	
		return JsonUtils.fromJson(result, ResultEntity.class);
	}

	@Override
	public ResultEntity InsertExtdata(String json) {
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String reqURL=requestURL.getRequestURL(URL_InsertExt);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());	
		return JsonUtils.fromJson(result, ResultEntity.class);
	}
	

	@Override
	public ResultEntity GetDevExtModel(String req) {
		return postUrl(URL_GetDevExtModel, req);
	}
}
