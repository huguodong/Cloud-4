package com.ssitcloud.view.devmgmt.service;

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

	/**
	 * 查询设备类型信息
	 * @methodName 
	 * @returnType String
	 * @param reqInfo
	 * @return
	 * @author hwl
	 */
	String SelDeviceType(Map<String, String> map);
	
	String QueryType(String reqInfo);
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
	 * 新增设备类型信息
	 * @methodName 
	 * @returnType String
	 * @param reqInfo
	 * @return
	 * @author hwl
	 */
	String AddDeviceType(String reqInfo);
	
	/**
	 * 修改设备类型信息
	 * @methodName 
	 * @returnType String
	 * @param reqInfo
	 * @return
	 * @author hwl
	 */
	String UpdDeviceType(String reqInfo);

	ResultEntity selAllDeviceTypeGroupByType();

	ResultEntity queryDeviceTypeLogicObj(String req);

	ResultEntity selectMetadataLogicObj(String req);
	
	ResultEntity selectMetadataDeviceDb(String req);
	
	ResultEntity selectMetadataDeviceDbAndTableInfo(String req);
	
	ResultEntity qyeryDeviceExtList(String req);
}
