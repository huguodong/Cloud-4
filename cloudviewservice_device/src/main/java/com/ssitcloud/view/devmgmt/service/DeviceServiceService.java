package com.ssitcloud.view.devmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface DeviceServiceService {
	
	public ResultEntity deviceServiceRegister(String req);
	
	public ResultEntity queryDeviceServiceByPage(String req);
	
	public ResultEntity queryDeviceServiceRegister(String req);
	
	public ResultEntity editDeviceServiceRegister(String req);
	
	public ResultEntity deleteDeviceService(String req);
	public ResultEntity queryDeviceServiceByParams(String req);
	
	public ResultEntity addCloudDbSyncConfig(String req);
	
	public ResultEntity selectCloudDbSyncConfig(String req);

	public ResultEntity updateDeviceServiceOperateTime(String req);

}
