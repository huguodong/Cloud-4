package com.ssitcloud.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.SpecialDeviceDao;
import com.ssitcloud.dao.SpecialDeviceTypeDao;
import com.ssitcloud.devmgmt.entity.SpecialDeviceTypeEntity;
import com.ssitcloud.devmgmt.entity.SpecialDeviceTypePageEntity;
import com.ssitcloud.service.SpecialDeviceTypeService;
@Service
public class SpecialDeviceTypeServiceImpl implements SpecialDeviceTypeService {
	@Autowired
	private SpecialDeviceTypeDao deviceTypeDao;
	@Autowired
	private SpecialDeviceDao deviceDao;
	public ResultEntity querySpecialDeviceTypeByParams(String req) {
		Map<String, String> map = new HashMap<>();
		ResultEntity resultEntity = new ResultEntity();
		if(req != null && req.length() > 0){
			map = JsonUtils.fromJson(req, Map.class);
		}
		List<SpecialDeviceTypeEntity> deviceTypeEntities = deviceTypeDao.querySpecialDeviceTypeByParams(map);
		if(deviceTypeEntities != null && !deviceTypeEntities.isEmpty()){
			resultEntity.setResult(deviceTypeEntities);
		}
		resultEntity.setState(true);
		return resultEntity;
	}
	@Override
	public ResultEntity querySpecialDeviceTypeByPage(String req) {
		SpecialDeviceTypePageEntity pageEntity = new SpecialDeviceTypePageEntity();
		ResultEntity resultEntity = new ResultEntity();
		if(req != null && req.length() > 0){
			pageEntity = JsonUtils.fromJson(req, SpecialDeviceTypePageEntity.class);
		}
		SpecialDeviceTypePageEntity result = deviceTypeDao.querySpecialDeviceTypeByPage(pageEntity);
		resultEntity.setResult(result);
		resultEntity.setState(true);
		return resultEntity;
	}
	
	@Override
	public ResultEntity addSpecialDeviceType(String req) {
		ResultEntity resultEntity = new ResultEntity();
		if(req == null || req.length() <=0){
			return resultEntity;
		}
		SpecialDeviceTypeEntity deviceTypeEntity = JsonUtils.fromJson(req, SpecialDeviceTypeEntity.class);
		int result = deviceTypeDao.addSpecialDeviceType(deviceTypeEntity);
		resultEntity.setResult(result);
		resultEntity.setState(true);
		return resultEntity;
	}
	
	@Override
	public ResultEntity deleteSpecialDeviceType(String req) {
		ResultEntity resultEntity = new ResultEntity();
		boolean state = true;
		if(req == null || req.length() <=0){
			return resultEntity;
		}
		String[] tempArrays = req.split(",");
		Map<String, String> params = new HashMap<>(); 
		for(String device_type_id : tempArrays){
			params.put("device_type_id", device_type_id);
			int count = deviceDao.selectCountSpecialDevice(params);
			if(count > 0){
				state = false;
				resultEntity.setMessage("存在设备类型被引用，无法删除");
				break;
			}
		}
		if(!state){
			return resultEntity;
		}
		for(String device_type_id : tempArrays){
			params.put("device_type_id", device_type_id);
			deviceTypeDao.deleteSpecialDeviceType(params);
		}
		return new ResultEntity(true,"删除成功");
	}
	
	@Override
	public ResultEntity editSpecialDeviceType(String req) {
		ResultEntity entity = new ResultEntity();
		if(req == null || req.length() <= 0){
			return entity;
		}
		SpecialDeviceTypeEntity deviceTypeEntity = JsonUtils.fromJson(req, SpecialDeviceTypeEntity.class);
		int result = deviceTypeDao.editSpecialDeviceType(deviceTypeEntity);
		entity.setState(true);
		entity.setResult(result);
		return entity;
	}
	
	
	
	
	

}
