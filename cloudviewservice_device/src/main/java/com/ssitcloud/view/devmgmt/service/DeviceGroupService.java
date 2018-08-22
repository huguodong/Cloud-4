package com.ssitcloud.view.devmgmt.service;

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

	/**
	 * 查询设备分组信息
	 * @methodName 
	 * @returnType String
	 * @param reqInfo
	 * @return
	 * @author hwl
	 */
	String SelDeviceGroup(Map<String, String> map);
	
	/**
	 * 删除设备分组信息
	 * @methodName 
	 * @returnType String
	 * @param reqInfo
	 * @return
	 * @author hwl
	 */
	String DeleteDeviceGroup(String reqInfo);
	
	/**
	 * 添加设备分组信息
	 * @methodName 
	 * @returnType String
	 * @param reqInfo
	 * @return
	 * @author hwl
	 */
	String AddDeviceGroup(String reqInfo);
	
	/**
	 * 更新设备分组信息
	 * @methodName 
	 * @returnType String
	 * @param reqInfo
	 * @return
	 * @author hwl
	 */
	String UpdDeviceGroup(String reqInfo);
	
	String SelectGroup(String reqInfo);

	ResultEntity SelectGroupByParam(String req);
	/**
	 * 根据设备IDX查询设备所在的分组信息
	 * @param req
	 * @return
	 */
	ResultEntity selectGroupByDeviceIdx(String req);
	
}
