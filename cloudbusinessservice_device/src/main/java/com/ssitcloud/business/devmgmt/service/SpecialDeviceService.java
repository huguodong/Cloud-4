package com.ssitcloud.business.devmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface SpecialDeviceService {
	
	public ResultEntity addSpecialDevice(String req);

	public ResultEntity querySpecialDeviceByPage(String req);

	public ResultEntity querySpecialDeviceByParams(String req);

	public ResultEntity editSpecialDevice(String req);
	
	public ResultEntity deleteSpecialDevice(String req);
	
}
