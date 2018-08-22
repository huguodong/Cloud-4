package com.ssitcloud.view.devmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface DeviceViewConfigService {
	public ResultEntity queryDeviceViewConfig(String req);
	public ResultEntity queryDeviceViewConfigFieldLabel(String req);
	public ResultEntity queryLabelByFieldset(String req);
	public ResultEntity updateDeviceViewConfig(String req);
	public ResultEntity queryDeviceViewConfigSet(String req);

}
