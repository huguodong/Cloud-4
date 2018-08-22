package com.ssitcloud.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.DeviceServiceDao;
import com.ssitcloud.dao.DeviceServiceTypeDao;
import com.ssitcloud.devmgmt.entity.DeviceServiceTypeEntity;
import com.ssitcloud.devmgmt.entity.DeviceServiceTypePageEntity;
import com.ssitcloud.devmgmt.entity.SpecialDeviceTypeEntity;
import com.ssitcloud.service.DeviceServiceService;
import com.ssitcloud.service.DeviceServiceTypeService;

@Service
public class DeviceServiceTypeServiceImpl implements DeviceServiceTypeService{
	
	@Autowired
	private DeviceServiceTypeDao serviceTypeDao;
	@Autowired
	private DeviceServiceDao serviceDao;
	@Override
	public ResultEntity queryDeviceServiceTypeByParams(String req) {
		
		Map<String,String> map = new HashMap<>();
		ResultEntity resultEntity = new ResultEntity();
		if(req != null && req.length()>0){
			map = JsonUtils.fromJson(req, Map.class);
		}
		List<DeviceServiceTypeEntity> entities = serviceTypeDao.queryDeviceServiceTypeByParams(map);
		resultEntity.setResult(entities);
		resultEntity.setState(true);
		return resultEntity;
	}

	
	public ResultEntity queryDeviceServiceTypeByPage(String req) {
		DeviceServiceTypePageEntity pageEntity = new DeviceServiceTypePageEntity();
		ResultEntity resultEntity = new ResultEntity();
		if(req != null && req.length() > 0){
			pageEntity = JsonUtils.fromJson(req, DeviceServiceTypePageEntity.class);
		}
		DeviceServiceTypePageEntity entity = serviceTypeDao.queryDeviceServiceTypeByPage(pageEntity);
		resultEntity.setResult(entity);
		resultEntity.setState(true);
		return resultEntity;
	}


	@Override
	public ResultEntity addDeviceServiceType(String req) {
		ResultEntity resultEntity = new ResultEntity();
		if(req == null || req.length()==0){
			return resultEntity;
		}
		DeviceServiceTypeEntity serviceTypeEntity = JsonUtils.fromJson(req, DeviceServiceTypeEntity.class);
		int result = serviceTypeDao.addDeviceServiceType(serviceTypeEntity);
		resultEntity.setResult(result);
		resultEntity.setMessage("添加成功");
		resultEntity.setState(true);
		return resultEntity;
	}
	
	public ResultEntity deleteDeviceServiceType(String req){
		ResultEntity resultEntity = new ResultEntity();
		boolean state = true;
		if(req == null || req.length() <= 0){
			return resultEntity;
		}
		String[] idx = req.split(",");
		Map<String, String> params = new HashMap<>();
		for(String id : idx){
			params.put("device_model_idx", id+"");
			int count = serviceDao.selectCountDeviceService(params);
			if(count > 0){
				state = false;
				resultEntity.setMessage("存在服务类型被引用，无法删除");
				break;
			}
		}
		
		if(!state){
			resultEntity.setState(state);
			return resultEntity;
		}
		
		for(String id : idx){
			params.put("device_model_idx", id+"");
			int count = serviceDao.selectCountDeviceService(params);
			if(count > 0){
				state = false;
				resultEntity.setMessage("服务类型被引用，无法删除");
				break;
			}
			serviceTypeDao.deleteDeviceServiceType(Integer.parseInt(id));
		}
		resultEntity.setState(state);
		return resultEntity;
	}


	@Override
	public ResultEntity editDeviceServiceType(String req) {
		ResultEntity entity = new ResultEntity();
		if(req == null || req.length() <= 0){
			return entity;
		}
		DeviceServiceTypeEntity deviceServiceTypeEntity = JsonUtils.fromJson(req, DeviceServiceTypeEntity.class);
		int result = serviceTypeDao.editDeviceServiceType(deviceServiceTypeEntity);
		entity.setState(true);
		entity.setResult(result);
		return entity;
	}
	
	
	
	
	

}
