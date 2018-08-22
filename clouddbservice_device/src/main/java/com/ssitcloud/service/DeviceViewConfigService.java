package com.ssitcloud.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface DeviceViewConfigService {
	public ResultEntity queryDeviceViewData(String fieldSet);
	//public ResultEntity updateDeviceViewConfig(String req);
	public ResultEntity queryDeviceViewConfigSet(String req);
	public ResultEntity deleteDeviceViewConfigSet(String req);
	public ResultEntity insertDeviceViewConfigSet(String req);
	
}
