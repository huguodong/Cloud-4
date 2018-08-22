package com.ssitcloud.business.devmgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.devmgmt.service.DevcieStateService;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class DeviceStateServiceImpl extends BasicServiceImpl implements DevcieStateService{

	public static final String URL_selectDeviceState="selectDeviceState";
	
	public static final String URL_selectBookrackState="selectBookrackState";

	private static final String URL_selectBinState = "selectBinState";

	private static final String URL_selectDeviceExtState = "selectDeviceExtState";

	private static final String URL_selectSoftState = "selectSoftState";

	private static final String URL_getMongodbNames = "getMongodbNames";
	@Override
	public ResultEntity selectDeviceState(String req){
		ResultEntity result=new ResultEntity();
		Map<String,String> map=new HashMap<>();
		map.put("req",req);
		String res=HttpClientUtil.doPost(requestURL.getRequestURL(URL_selectDeviceState), map, charset);
		if(StringUtils.hasText(res)){
			result=JsonUtils.fromJson(res, ResultEntity.class);
		}
		return result;
	}
	
	@Override
	public ResultEntity selectBookrackState(String req) {
		ResultEntity result=new ResultEntity();
		Map<String,String> map=new HashMap<>();
		map.put("req",req);
		String res=HttpClientUtil.doPost(requestURL.getRequestURL(URL_selectBookrackState), map, charset);
		if(StringUtils.hasText(res)){
			result=JsonUtils.fromJson(res, ResultEntity.class);
		}
		return result;
	}

	@Override
	public ResultEntity selectBinState(String req) {
		ResultEntity result=new ResultEntity();
		Map<String,String> map=new HashMap<>();
		map.put("req",req);
		String res=HttpClientUtil.doPost(requestURL.getRequestURL(URL_selectBinState), map, charset);
		if(StringUtils.hasText(res)){
			result=JsonUtils.fromJson(res, ResultEntity.class);
		}
		return result;
	}
	
	@Override
	public ResultEntity selectDeviceExtState(String req) {
		return postUrl(URL_selectDeviceExtState, req);
	}
	

	@Override
	public ResultEntity selectSoftState(String req) {
		return postUrl(URL_selectSoftState, req);
	}

	@Override
	public ResultEntity getMongodbNames(String req) {
		return postUrl(URL_getMongodbNames, req);
	}
}
