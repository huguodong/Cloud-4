package com.ssitcloud.business.devmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

/**
 * 
 * @comment 
 * @date 2016年6月14日
 * @author hwl
 */
public interface DeviceRunConfigService {

	ResultEntity SelectRunConfig(String json);
	
	ResultEntity DeleteRunConfig(String json);
	
	ResultEntity InsertRunConfig(String json);
	
	ResultEntity UpdateRunConfig(String json);
	/**
	 * 
	 * @param req
	 * @return
	 */
	ResultEntity SelFunctionByDeviceIdx(String req);
}
