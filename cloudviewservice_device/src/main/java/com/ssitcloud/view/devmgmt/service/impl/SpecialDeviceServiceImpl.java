package com.ssitcloud.view.devmgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.common.util.HttpClientUtil;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.devmgmt.service.SpecialDeviceService;
@Service
public class SpecialDeviceServiceImpl extends BasicServiceImpl implements SpecialDeviceService {

	public static final String URL_addSpecialDevice="addSpecialDevice";
	public static final String URL_querySpecialDeviceByPage="querySpecialDeviceByPage";
	public static final String URL_querySpecialDeviceByParams="querySpecialDeviceByParams";
	public static final String URL_editSpecialDevice="editSpecialDevice";
	public static final String URL_deleteSpecialDevice="deleteSpecialDevice";
	public ResultEntity addSpecialDevice(String req) {
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_addSpecialDevice), map, charset);
		if(result!=null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
		
	}

	@Override
	public ResultEntity querySpecialDeviceByPage(String req) {
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_querySpecialDeviceByPage), map, charset);
		if(result!=null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}

	@Override
	public ResultEntity querySpecialDeviceByParams(String req) {
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_querySpecialDeviceByParams), map, charset);
		if(result!=null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}

	@Override
	public ResultEntity editSpecialDevice(String req) {
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_editSpecialDevice), map, charset);
		if(result!=null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}

	@Override
	public ResultEntity deleteSpecialDevice(String req) {
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_deleteSpecialDevice), map, charset);
		if(result!=null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}
	
	
	
	

}
