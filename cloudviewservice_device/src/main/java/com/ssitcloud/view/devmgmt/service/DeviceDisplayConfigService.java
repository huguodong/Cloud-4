package com.ssitcloud.view.devmgmt.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Consts;

import com.ssitcloud.devmgmt.entity.DeviceDisplayConfig;
import com.ssitcloud.devmgmt.entity.DeviceDisplayConfigPage;
import com.ssitcloud.view.common.util.HttpClientUtil;

/**
 * 
 * @package com.ssitcloud.view.devmgmt.service
 * @comment
 * @data 2016年4月18日
 */
public interface DeviceDisplayConfigService {

	/**
	 * 查询设备显示类型信息
	 * 
	 * @methodName
	 * @returnType String
	 * @param reqInfo
	 * @return
	 */
	DeviceDisplayConfigPage queryAll(String reqInfo);

	/**
	 * 删除设备显示类型信息
	 * 
	 * @methodName
	 * @returnType String
	 * @param reqInfo
	 * @return
	 */
	String delete(String reqInfo);
	
	/**
	 * 新增设备显示类型信息
	 * 
	 * @methodName
	 * @returnType String
	 * @param reqInfo
	 * @return
	 */
	String insert(String reqInfo);

	/**
	 * 修改设备显示类型信息
	 * 
	 * @methodName
	 * @returnType String
	 * @param reqInfo
	 * @return
	 */
	String update(String reqInfo);

	/**
	 * 根据id来查找设备显示类型信息
	 * 
	 * @methodName
	 * @returnType String
	 * @param reqInfo
	 * @return
	 */
	DeviceDisplayConfig findById(String reqInfo);
	
	/**
	 * 获取设备类型
	 * @param reqInfo
	 * @return
	 */
	String getDeviceTypes(String reqInfo);
	
	/**
	 * 获取全部设备类型
	 * @param reqInfo
	 * @return
	 */
	String getAllDeviceTypes(String string);
}
