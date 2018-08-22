package com.ssitcloud.business.devmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

/**
 * 
 * @comment 
 * @date 2016年6月14日
 * @author hwl
 */
public interface DeviceExtConfigService {

	ResultEntity SelectExt(String json);
	
	ResultEntity UpdateExtdata(String json);
	
	ResultEntity DeleteExtdata(String json);
	
	ResultEntity InsertExtdata(String json);
	/**
	 * req,一个数组device_ids
	 * 获取设备对应的外设信息
	 * @param request
	 * @param req
	 * @return
	 */
	ResultEntity GetDevExtModel(String req);
}
