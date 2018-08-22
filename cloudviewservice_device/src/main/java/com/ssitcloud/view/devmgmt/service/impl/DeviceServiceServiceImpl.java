package com.ssitcloud.view.devmgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.common.util.HttpClientUtil;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.devmgmt.service.DeviceServiceService;

@Service
public class DeviceServiceServiceImpl extends BasicServiceImpl implements DeviceServiceService{
	
	public static final String URL_deviceServiceRegister="deviceServiceRegister";
	public static final String URL_queryDeviceServiceByPage="queryDeviceServiceByPage";
	public static final String URL_queryDeviceServiceRegister="queryDeviceServiceRegister";
	public static final String URL_editDeviceServiceRegister="editDeviceServiceRegister";
	public static final String URL_deleteDeviceService="deleteDeviceService";
	private static final String URL_updateDeviceServiceOperateTime = "updateDeviceServiceOperateTime";
	public static final String URL_queryDeviceServiceByParams="queryDeviceServiceByParams";
	
	public static final String URL_addCloudDbSyncConfig="addCloudDbSyncConfig"; 
	
	public static final String URL_selectCloudDbSyncConfig = "selectCloudDbSyncConfig";
	
	/**服务注册*/
	public ResultEntity deviceServiceRegister(String req) {
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_deviceServiceRegister), map, charset);
		if(result!=null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}

	
	public ResultEntity queryDeviceServiceByPage(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_queryDeviceServiceByPage), map, charset);
		if(result!=null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}


	@Override
	public ResultEntity queryDeviceServiceRegister(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_queryDeviceServiceRegister), map, charset);
		if(result!=null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}


	@Override
	public ResultEntity editDeviceServiceRegister(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_editDeviceServiceRegister), map, charset);
		if(result!=null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}


	@Override
	public ResultEntity deleteDeviceService(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_deleteDeviceService), map, charset);
		if(result!=null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}


	@Override
	public ResultEntity queryDeviceServiceByParams(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_queryDeviceServiceByParams), map, charset);
		if(result!=null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}


	@Override
	public ResultEntity addCloudDbSyncConfig(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_addCloudDbSyncConfig), map, charset);
		if(result!=null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}


	@Override
	public ResultEntity selectCloudDbSyncConfig(String req) {
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_selectCloudDbSyncConfig), map, charset);
		if(result!=null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}


	@Override
	public ResultEntity updateDeviceServiceOperateTime(String req) {
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_updateDeviceServiceOperateTime), map, charset);
		return JsonUtils.fromJson(result, ResultEntity.class);
	}
	
	
	
	
	
	

}
