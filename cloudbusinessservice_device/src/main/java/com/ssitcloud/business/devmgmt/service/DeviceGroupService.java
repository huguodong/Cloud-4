package com.ssitcloud.business.devmgmt.service;

import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;

/**
 * 
 * @package com.ssitcloud.devmgmt.service
 * @comment
 * @data 2016年4月21日
 * @author hwl
 */
public interface DeviceGroupService {

	
	String SelectGroup(String reqInfo);
	
	/**
	 * 查询设备分组
	 * @methodName 
	 * @returnType String
	 * @param reqInfo
	 * @return
	 * @author hwl
	 */
	String SelDeviceGroup(Map<String, String> map);
	
	/**
	 * 删除设备分组
	 * @methodName 
	 * @returnType String
	 * @param reqInfo
	 * @return
	 * @author hwl
	 */
	String DeleteDeviceGroup(String reqInfo);
	
	/**
	 * 添加设备分组
	 * @methodName 
	 * @returnType String
	 * @param reqInfo
	 * @return
	 * @author hwl
	 */
	String AddDeviceGroup(String reqInfo);
	
	/**
	 * 更新设备分组
	 * @methodName 
	 * @returnType String
	 * @param reqInfo
	 * @return
	 * @author hwl
	 */
	String UpdDeviceGroup(String reqInfo);
	
	/**
	 * 查询图书馆ID
	 * @methodName 
	 * @returnType String
	 * @param reqInfo
	 * @return
	 * @author hwl
	 */
	String SelLibrary(String reqInfo);

	ResultEntity SelectGroupByParam(String req);
	/**
	 * 根据设备IDX查询分组信息
	 * @param req
	 * @return
	 */
	ResultEntity selectGroupByDeviceIdx(String req);
}
