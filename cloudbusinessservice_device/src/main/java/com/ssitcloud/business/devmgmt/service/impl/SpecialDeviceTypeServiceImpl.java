package com.ssitcloud.business.devmgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.devmgmt.service.SpecialDeviceTypeService;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.utils.HttpClientUtil;

@Service
public class SpecialDeviceTypeServiceImpl extends BasicServiceImpl implements SpecialDeviceTypeService{
	
	public static final String URL_querySpecialDeviceTypeByParams="querySpecialDeviceTypeByParams";
	public static final String URL_querySpecialDeviceTypeByPage="querySpecialDeviceTypeByPage";
	public static final String URL_addSpecialDeviceType="addSpecialDeviceType";
	public static final String URL_deleteSpecialDeviceType="deleteSpecialDeviceType";
	
	public static final String URL_editSpecialDeviceType="editSpecialDeviceType";
	@Override
	public ResultEntity querySpecialDeviceTypeByParams(String req) {
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_querySpecialDeviceTypeByParams), map, charset);
		if(result!=null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}
	
	@Override
	public ResultEntity querySpecialDeviceTypeByPage(String req) {
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_querySpecialDeviceTypeByPage), map, charset);
		if(result!=null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}
	
	public ResultEntity addSpecialDeviceType(String req) {
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_addSpecialDeviceType), map, charset);
		if(result!=null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}

	@Override
	public ResultEntity deleteSpecialDeviceType(String req) {
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_deleteSpecialDeviceType), map, charset);
		if(result!=null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}

	@Override
	public ResultEntity editSpecialDeviceType(String req) {
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_editSpecialDeviceType), map, charset);
		if(result!=null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}

	
}
