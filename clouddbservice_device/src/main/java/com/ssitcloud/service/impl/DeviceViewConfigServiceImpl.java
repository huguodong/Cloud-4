package com.ssitcloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dao.DeviceViewConfigDao;
import com.ssitcloud.devmgmt.entity.DeviceViewConfigSet;
import com.ssitcloud.devmgmt.entity.DeviceViewDataEntity;
import com.ssitcloud.service.DeviceViewConfigService;
@Service
public class DeviceViewConfigServiceImpl implements DeviceViewConfigService {
	@Resource
	private DeviceViewConfigDao viewConfigDao;
	public ResultEntity queryDeviceViewData(String fieldSet) {
		List<DeviceViewDataEntity> deviceViewDataEntities= viewConfigDao.queryDeviceViewConfig(fieldSet);
		ResultEntity resultEntity = new ResultEntity();
		resultEntity.setResult(deviceViewDataEntities);
		return resultEntity;
	}
	
	/*public ResultEntity updateDeviceViewConfig(String req) {
		
		viewConfigDao.updateDeviceViewConfig(req);
		return new ResultEntity();
	}*/

	@Override
	public ResultEntity queryDeviceViewConfigSet(String req) {
		List<DeviceViewConfigSet> configSets = viewConfigDao.queryDeviceViewConfigSet(req);
		ResultEntity resultEntity = new ResultEntity();
		resultEntity.setResult(configSets);
		return resultEntity;
	}
	
	

	@Override
	public ResultEntity deleteDeviceViewConfigSet(String req) {
		return viewConfigDao.deleteDeviceViewConfigSet(req);
	}

	@Override
	public ResultEntity insertDeviceViewConfigSet(String req) {
		return viewConfigDao.insertDeviceViewConfigSet(req);
	}
	
	
	

}
