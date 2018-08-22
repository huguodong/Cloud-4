package com.ssitcloud.dblib.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dblib.dao.DeviceReaderDao;
import com.ssitcloud.dblib.entity.DeviceReaderEntity;
import com.ssitcloud.dblib.service.DeviceReaderService;

@Service
public class DeviceReaderServiceImpl implements DeviceReaderService{

	@Resource
	private DeviceReaderDao deviceReaderDao;

	@Override
	public int saveDeviceReaderInfo(DeviceReaderEntity deviceReaderEntity) {
		return deviceReaderDao.saveDeviceReaderInfo(deviceReaderEntity);
	}
	@Override
	public int updateDeviceReaderInfo(DeviceReaderEntity deviceReaderEntity) {
		return deviceReaderDao.updateDeviceReaderInfo(deviceReaderEntity);
	}

	@Override
	public int countReaderByLibIdxAndCardno(
			DeviceReaderEntity deviceReaderEntity) {
		return deviceReaderDao.countReaderByLibIdxAndCardno(deviceReaderEntity);
	}
	
	
	
	
}
