package com.ssitcloud.business.devmgmt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.devmgmt.service.DeviceViewConfigService;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.devmgmt.entity.ConfigEntity;
import com.ssitcloud.devmgmt.entity.DeviceViewConfigSet;
import com.ssitcloud.devmgmt.entity.DeviceViewDataEntity;
@Service
public class DeviceViewConfigServiceImpl extends BasicServiceImpl implements DeviceViewConfigService{
	private static final String URL_queryDeviceViewConfig = "queryDeviceViewConfig";
	private static final String URL_updateDeviceViewConfig = "updateDeviceViewConfig";
	private static final String URL_queryDeviceViewConfigSet = "queryDeviceViewConfigSet";
	private static final String URL_deleteDeviceViewConfigSet = "deleteDeviceViewConfigSet";
	private static final String URL_insertDeviceViewConfigSet = "insertDeviceViewConfigSet";
	
	/***查询设备总的配置数据*/
	public ResultEntity queryDeviceViewConfig(String req) {
		String reqURL = requestURL.getRequestURL(URL_queryDeviceViewConfig);
		Map<String, String> map = new HashMap<>();
		if(req != null && req.length() > 0){
			map.put("req", req);
		}
		String result = HttpClientUtil.doPost(reqURL, map, "utf-8");
		if(result != null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}
	
	/**更新设置的配置数据*/
	public ResultEntity updateDeviceViewConfig(String req){
		ResultEntity entity = new ResultEntity();
		if(req != null && req.length() > 0){
			List<DeviceViewConfigSet> configEntities = JsonUtils.fromJson(req, new TypeReference<List<DeviceViewConfigSet>>(){});
			if(configEntities == null || configEntities.isEmpty()) return new ResultEntity();
			deleteDeviceViewConfigByFieldset(configEntities);
			insertDeviceViewConfigSet(req);
			entity.setState(true);
		}
		return entity;
	}
	
	/***
	 * 根据fieldset,library,devicetype删除配置数据
	 * @param deviceViewConfigSet
	 */
	private void deleteDeviceViewConfigByFieldset(List<DeviceViewConfigSet> configEntities){	
		JSONObject jsonObject = new JSONObject();
		int library_idx = -1;
		for(DeviceViewConfigSet configSet : configEntities){
			if(configSet.getLibrary_idx() != library_idx){
				jsonObject.clear();
				jsonObject.put("library_idx", configSet.getLibrary_idx());
				deleteDeviceViewConfigSet(jsonObject.toString());
				library_idx = configSet.getLibrary_idx();
			}
		}	
	}
	
	/**删除数据*/
	public ResultEntity deleteDeviceViewConfigSet(String req){
		String reqURL = requestURL.getRequestURL(URL_deleteDeviceViewConfigSet);
		Map<String, String> map = new HashMap<>();
		if(req != null && req.length() > 0){
			map.put("req", req);
		}
		String result = HttpClientUtil.doPost(reqURL, map, "utf-8");
		if(result != null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}
	

	/**查询设置的配置数据*/
	public ResultEntity queryDeviceViewConfigSet(String req) {
		
		String reqURL = requestURL.getRequestURL(URL_queryDeviceViewConfigSet);
		Map<String, String> map = new HashMap<>();
		if(req != null && req.length() > 0){
			map.put("req", req);
		}
		String result = HttpClientUtil.doPost(reqURL, map, "utf-8");
		
		return JsonUtils.fromJson(result, ResultEntity.class);
	}
	
	/***归类总的配置数据，根据fieldset归类*/
	public ResultEntity queryDeviceViewConfigFieldLabel(String req) {
		ResultEntity resultEntity = queryDeviceViewConfig(req);
		Object resultData = resultEntity.getResult();
		ResultEntity entity = new ResultEntity();
		Map<String, List<DeviceViewDataEntity>> resultMap = new HashMap<>();
		if(resultData != null){
			List<DeviceViewDataEntity> dataEntities = JsonUtils.convertMap(resultData,new TypeReference<List<DeviceViewDataEntity>>() {});
			
			for(DeviceViewDataEntity dataEntity : dataEntities){
				
				ConfigEntity configEntity = JsonUtils.fromJson(dataEntity.getOperation(), ConfigEntity.class);
				if(configEntity.getFieldset() == null || configEntity.getFieldset().length() <= 0){
					configEntity.setFieldset(configEntity.getLabel());
				}
				
				if(resultMap.containsKey(configEntity.getFieldset())){
					List<DeviceViewDataEntity> entities = resultMap.get(configEntity.getFieldset());
					entities.add(dataEntity);
				}else{
					List<DeviceViewDataEntity> entities = new ArrayList<>();
					entities.add(dataEntity);
					resultMap.put(configEntity.getFieldset(), entities);
				}
				
			}
		}
		
		JSONArray resultArray = new JSONArray();
		for(List<DeviceViewDataEntity> dataEntities: resultMap.values()){
			JSONArray jsonArray = new JSONArray();
			for(DeviceViewDataEntity dataEntity : dataEntities){
				jsonArray.add(JsonUtils.toJson(dataEntity));
			}
			resultArray.add(jsonArray);
		}
		entity.setResult(resultArray);
		return entity;
	}


	/**插入设置信息*/
	public ResultEntity insertDeviceViewConfigSet(String req) {
		String reqURL = requestURL.getRequestURL(URL_insertDeviceViewConfigSet);
		Map<String, String> map = new HashMap<>();
		if(req != null && req.length() > 0){
			map.put("req", req);
		}
		String result = HttpClientUtil.doPost(reqURL, map, "utf-8");
		if(result != null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
	}
	
	/**根据fieldset查询出设置的配置数据*/
	public ResultEntity queryLabelByFieldset(String req){
		
		ResultEntity resultEntity = queryDeviceViewConfig(req);
		Object resultData = resultEntity.getResult();
		ConfigEntity params = JsonUtils.fromJson(req, ConfigEntity.class);
		ResultEntity entity = new ResultEntity();
		if(resultData != null){
			List<ConfigEntity> configEntities = new ArrayList<>();
			List<DeviceViewDataEntity> dataEntities = JsonUtils.convertMap(resultData,new TypeReference<List<DeviceViewDataEntity>>() {});
			for(DeviceViewDataEntity dataEntity : dataEntities){
				String data = dataEntity.getOperation();
				ConfigEntity configEntity = JsonUtils.fromJson(data, ConfigEntity.class);
				String fieldset = configEntity.getFieldset();
				if(params != null && params.getFieldset() != null && params.getFieldset().length() > 0){
					if(fieldset == null || fieldset.length() <= 0) continue;
					if(!params.getFieldset().equals(fieldset)){
						continue;
					}
				}
				configEntity.setId(dataEntity.getId());
				configEntities.add(configEntity);
			}
			
			entity.setResult(configEntities);
			entity.setState(true);
			return entity;
		}
		return entity;
		
	}
	
	
	
	

}
