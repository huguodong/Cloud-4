package com.ssitcloud.view.devmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface SpecialDeviceTypeService {
	
	public ResultEntity querySpecialDeviceTypeByParams(String req);
	
	public ResultEntity querySpecialDeviceTypeByPage(String req);
	
	public ResultEntity addSpecialDeviceType(String req);
	
	public ResultEntity deleteSpecialDeviceType(String req);
	
	public ResultEntity editSpecialDeviceType(String req);
	
}
