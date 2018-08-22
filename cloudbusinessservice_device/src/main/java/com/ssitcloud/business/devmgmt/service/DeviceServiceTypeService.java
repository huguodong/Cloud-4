package com.ssitcloud.business.devmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface DeviceServiceTypeService {
	
	public ResultEntity queryDeviceServiceTypeByParams(String req);
	
	public ResultEntity queryDeviceServiceTypeByPage(String req);
	
	public ResultEntity addDeviceServiceType(String req);

	public ResultEntity deleteDeviceServiceType(String req);

	public ResultEntity editDeviceServiceType(String req);
	
}
