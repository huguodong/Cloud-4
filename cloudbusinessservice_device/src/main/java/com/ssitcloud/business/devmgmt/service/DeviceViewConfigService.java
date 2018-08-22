package com.ssitcloud.business.devmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface DeviceViewConfigService {
	
	public ResultEntity queryDeviceViewConfig(String fieldSet);
	public ResultEntity updateDeviceViewConfig(String req);
	public ResultEntity queryDeviceViewConfigSet(String req);
	public ResultEntity queryDeviceViewConfigFieldLabel(String req);
	public ResultEntity deleteDeviceViewConfigSet(String req);
	public ResultEntity insertDeviceViewConfigSet(String req);
	public ResultEntity queryLabelByFieldset(String req);

}
