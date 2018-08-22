package com.ssitcloud.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dao.DeviceTroubleDao;
import com.ssitcloud.entity.DeviceTroubleEntity;
import com.ssitcloud.entity.page.DeviceTroublePageEntity;
import com.ssitcloud.service.DeviceTroubleService;


@Service
public class DeviceTroubleServiceImpl implements DeviceTroubleService {
	@Resource
	private DeviceTroubleDao deviceTroubleDao;

	@Override
	public int insertDeviceTrouble(
			DeviceTroubleEntity deviceTroubleEntity) {
		return deviceTroubleDao.insertDeviceTrouble(deviceTroubleEntity);
	}

	@Override
	public int updateDeviceTrouble(
			DeviceTroubleEntity deviceTroubleEntity) {
		return deviceTroubleDao.updateDeviceTrouble(deviceTroubleEntity);
	}

	@Override
	public int deleteDeviceTrouble(
			DeviceTroubleEntity deviceTroubleEntity) {
		return deviceTroubleDao.deleteDeviceTrouble(deviceTroubleEntity);
	}

	@Override
	public DeviceTroubleEntity queryOneDeviceTrouble(
			DeviceTroubleEntity deviceTroubleEntity) {
		return deviceTroubleDao.queryOneDeviceTrouble(deviceTroubleEntity);
			
	}

	@Override
	public List<DeviceTroubleEntity> queryDeviceTroubles(
			DeviceTroubleEntity deviceTroubleEntity) {
		return deviceTroubleDao.queryDeviceTroubles(deviceTroubleEntity);
		
	}

	@Override
	public DeviceTroublePageEntity selectDeviceTroublesByLibIdxs(
			DeviceTroublePageEntity pageEntity) {
		DeviceTroublePageEntity deviceTroublePageEntity = new DeviceTroublePageEntity();
		deviceTroublePageEntity = deviceTroubleDao.selectDeviceTroublesByLibIdxs(pageEntity);
		return deviceTroublePageEntity;
	}

	@Override
	public int updateDeviceTroubles(Map map) {
		
		return deviceTroubleDao.updateDeviceTroubles(map);
	}


	

}
