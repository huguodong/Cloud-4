package com.ssitcloud.business.devmgmt.service;

import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;

/**
 * 
 * @package com.ssitcloud.devmgmt.service
 * @comment
 * @data 2016年4月18日
 * @author hwl
 */
public interface DeviceTypeService {

	String SelectType(String reqInfo);
	
	/**
	 * 查询设备类型信息
	 * @methodName 
	 * @returnType String
	 * @param reqInfo
	 * @return
	 * @author hwl
	 */
	String SelDeviceType(Map<String, String> map);
	
	/**
	 * 删除设备类型信息
	 * @methodName 
	 * @returnType String
	 * @param reqInfo
	 * @return
	 * @author hwl
	 */
	String DeleteDeviceType(String reqInfo);
	
	/**
	 * 
	 * @methodName 
	 * @returnType String
	 * @param reqInfo
	 * @return
	 * @author hwl
	 */
	String AddDeviceType(String reqInfo);
	
	/**
	 * 
	 * @methodName 
	 * @returnType String
	 * @param reqInfo
	 * @return
	 * @author hwl
	 */
	String UpdDeviceType(String reqInfo);

	ResultEntity selAllDeviceTypeGroupByType();
	/**
	 * 
	 * @param req
	 * @return
	 */
	ResultEntity queryDeviceTypeLogicObj(String req);
	
	/**
	 * 根据名字查询设备类型
	 * @param req
	 * @return
	 */
	ResultEntity queryDeviceTypeByName(String req);
}
