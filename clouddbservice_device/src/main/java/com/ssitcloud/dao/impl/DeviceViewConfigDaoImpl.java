package com.ssitcloud.dao.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.DeviceViewConfigDao;
import com.ssitcloud.devmgmt.entity.DeviceViewConfigSet;
import com.ssitcloud.devmgmt.entity.DeviceViewDataEntity;
@Repository
public class DeviceViewConfigDaoImpl extends CommonDaoImpl implements DeviceViewConfigDao{
	
	
	public List<DeviceViewDataEntity> queryDeviceViewConfig(String fieldSet) {
		Map<String,String> params = new HashMap<>();
		if(fieldSet != null && fieldSet.length() > 0){
			params = JsonUtils.fromJson(fieldSet, new TypeReference<Map<String,String>>() {});
		}
		if(params == null) params = new HashMap<String, String>();
		return getSqlSession().selectList("deviceViewConfig.queryDeviceViewConfig", params);
	}
	
	public ResultEntity deleteDeviceViewConfigSet(String req){
		Map<String, String> params = new HashMap<>();
		if(req != null && req.length() > 0){
			params = JsonUtils.fromJson(req, new TypeReference<Map<String,String>>() {});
		}
		if(params == null) params = new HashMap<>();
		int result = getSqlSession().delete("deviceViewConfig.deleteConfigViewConfigSet", params);
		if(result > 0){
			ResultEntity resultEntity = new ResultEntity();
			resultEntity.setResult("success");
		}
		return new ResultEntity();
		
	}


	
	public List<DeviceViewConfigSet> queryDeviceViewConfigSet(String req) {
		
		Map<String, String> params = new HashMap<>();
		if(req != null && req.length() > 0){
			params = JsonUtils.fromJson(req, new TypeReference<Map<String,String>>() {});
		}
		if(params == null) params = new HashMap<>();
		return getSqlSession().selectList("deviceViewConfig.queryDeviceViewConfigSet", params);
		
	}
	
	


	@Override
	public ResultEntity insertDeviceViewConfigSet(String req) {
		if(req != null){
			List<DeviceViewConfigSet> configEntities = JsonUtils.fromJson(req, new TypeReference<List<DeviceViewConfigSet>>(){});
			if(configEntities == null || configEntities.isEmpty())return new ResultEntity();
			for(DeviceViewConfigSet resultEntity : configEntities){
				Timestamp operate_time = new Timestamp(System.currentTimeMillis());
				resultEntity.setOperate_time(operate_time);
				if("default".equals(resultEntity.getDevice_type())){
					resultEntity.setDevice_type("");
				}
				getSqlSession().insert("deviceViewConfig.insertDeviceViewConfigSet", resultEntity);
			}
		}
		return new ResultEntity();
	}
	
	
	
	
	

}
