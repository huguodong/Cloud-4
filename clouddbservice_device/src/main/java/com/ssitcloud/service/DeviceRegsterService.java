package com.ssitcloud.service;

import javax.servlet.http.HttpServletRequest;

import com.ssitcloud.common.entity.ResultEntity;

public interface DeviceRegsterService {
	/**
	 * 注册信息
	 * 
	 *     |馆ID[library_idx] 
	 *     |设备ID[device_id]
	 *  1--|设备名[device_name]
	 *     |设备类型[device_model_idx]
	 *     |设备特征码[device_code]
	 * 
	 *  2--硬件配置模板[DeviceExtTempEntity类]
	 * 
	 *  3--设备监控模板[DeviceMonTempEntity类]
	 * 
	 *  4--运行模板[DeviceRunTempEntity类]
	 * 
	 *  5--数据同步模板[DeviceDBSyncTempEntity类]
	 *  
	 *  
	 * @methodName: doDeviceRegister
	 * @param req
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	ResultEntity doDeviceRegister(HttpServletRequest req);

}
