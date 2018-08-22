package com.ssitcloud.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.devmgmt.entity.DeviceServiceTypeEntity;
import com.ssitcloud.devmgmt.entity.DeviceServiceTypePageEntity;

public interface DeviceServiceTypeDao {
	
	public List<DeviceServiceTypeEntity> queryDeviceServiceTypeByParams(Map<String, String> map);
	public DeviceServiceTypePageEntity queryDeviceServiceTypeByPage(DeviceServiceTypePageEntity entity);
	public int addDeviceServiceType(DeviceServiceTypeEntity serviceTypeEntity);
	public int deleteDeviceServiceType(Integer service_type_idx);
	public int editDeviceServiceType(DeviceServiceTypeEntity deviceServiceTypeEntity);
}
