package com.ssitcloud.view.devmgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.common.util.HttpClientUtil;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.devmgmt.service.DeviceViewConfigService;
@Service
public class DeviceViewConfigServiceImpl extends BasicServiceImpl implements DeviceViewConfigService{
	public static final String URL_queryDeviceViewConfig="queryDeviceViewConfig"; 
	public static final String URL_updateDeviceViewConfig="updateDeviceViewConfig"; 
	public static final String URL_queryDeviceViewConfigSet="queryDeviceViewConfigSet";
	public static final String URL_queryDeviceViewConfigFieldLabel="queryDeviceViewConfigFieldLabel";
	public static final String URL_queryLabelByFieldset = "queryLabelByFieldset";
	
	
	/***
	 * 查询设备前端参数
	 * 
	 */
	public ResultEntity queryDeviceViewConfig(String req) {
		Map<String, String> map = new HashMap<String, String>();
		if(req != null && req.length() > 0){
			map.put("req", req);
		}
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_queryDeviceViewConfig), map, charset);
		if(result != null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}
	
	/**
	 * 查询显示设备前端参数
	 */
	public ResultEntity queryDeviceViewConfigFieldLabel(String req) {
		Map<String, String> map = new HashMap<String, String>();
		if(req != null && req.length() > 0){
			map.put("req", req);
		}
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_queryDeviceViewConfigFieldLabel), map, charset);
		if(result != null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}
	
	
	public ResultEntity queryLabelByFieldset(String req){
		
		Map<String, String> map = new HashMap<String, String>();
		if(req != null && req.length() > 0){
			map.put("req", req);
		}
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_queryLabelByFieldset), map, charset);
		if(result != null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
		
		
		
	}
	
	public ResultEntity updateDeviceViewConfig(String req){
		
		Map<String,String> params = new HashMap<>();
		if(req != null && req.length() > 0){
			params.put("req", req);
		}
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_updateDeviceViewConfig), params, charset);
		if(result != null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}

	@Override
	public ResultEntity queryDeviceViewConfigSet(String req) {
		
		Map<String,String> params = new HashMap<>();
		if(req != null && req.length() > 0){
			params.put("req", req);
		}
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_queryDeviceViewConfigSet), params, charset);
		if(result != null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}
	

}
