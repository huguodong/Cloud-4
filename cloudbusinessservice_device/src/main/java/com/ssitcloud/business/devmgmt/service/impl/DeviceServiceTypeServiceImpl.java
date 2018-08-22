package com.ssitcloud.business.devmgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.devmgmt.service.DeviceServiceTypeService;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.utils.HttpClientUtil;


@Service
public class DeviceServiceTypeServiceImpl extends BasicServiceImpl implements DeviceServiceTypeService {

	public static final String URL_queryDeviceServiceTypeByParams="queryDeviceServiceTypeByParams";
	public static final String URL_queryDeviceServiceTypeByPage="queryDeviceServiceTypeByPage";
	public static final String URL_deleteDeviceServiceType="deleteDeviceServiceType";
	public static final String URL_addDeviceServiceType="addDeviceServiceType";
	public static final String URL_editDeviceServiceType="editDeviceServiceType";
	
	@Override
	public ResultEntity queryDeviceServiceTypeByParams(String req) {
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_queryDeviceServiceTypeByParams), map, charset);
		if(result!=null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}
	@Override
	public ResultEntity queryDeviceServiceTypeByPage(String req) {
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_queryDeviceServiceTypeByPage), map, charset);
		if(result!=null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}
	@Override
	public ResultEntity addDeviceServiceType(String req) {
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_addDeviceServiceType), map, charset);
		if(result!=null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}
	
	@Override
	public ResultEntity deleteDeviceServiceType(String req) {
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_deleteDeviceServiceType), map, charset);
		if(result!=null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}
	@Override
	public ResultEntity editDeviceServiceType(String req) {
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_editDeviceServiceType), map, charset);
		if(result!=null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}
	
	
	
	

}
