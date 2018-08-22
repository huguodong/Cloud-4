/**
 * 
 */
package com.ssitcloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dao.DeviceLogConfigDao;
import com.ssitcloud.entity.DeviceLogConfigEntity;
import com.ssitcloud.service.DeviceLogConfigService;

/**
 * @comment 设备运行日志配置Service
 * 
 * @author hwl
 * @data 2016年4月11日
 */
@Service
public class DeviceLogConfigServiceImpl implements DeviceLogConfigService {

	@Resource
	DeviceLogConfigDao deviceLogConfigDao;
	@Override
	public int addDeviceLogConfig(DeviceLogConfigEntity deviceLogConfigEntity) {
		// TODO Auto-generated method stub
		return deviceLogConfigDao.insert(deviceLogConfigEntity);
	}

	@Override
	public int updateDeviceLogConfig(DeviceLogConfigEntity deviceLogConfigEntity) {
		// TODO Auto-generated method stub
		return deviceLogConfigDao.update(deviceLogConfigEntity);
	}

	@Override
	public int deleteDeviceLogConfig(DeviceLogConfigEntity deviceLogConfigEntity) {
		// TODO Auto-generated method stub
		return deviceLogConfigDao.delete(deviceLogConfigEntity);
	}

	@Override
	public List<DeviceLogConfigEntity> selectDeviceLogConfig(
			DeviceLogConfigEntity deviceLogConfigEntity) {
		// TODO Auto-generated method stub
		return deviceLogConfigDao.select(deviceLogConfigEntity);
	}

}
