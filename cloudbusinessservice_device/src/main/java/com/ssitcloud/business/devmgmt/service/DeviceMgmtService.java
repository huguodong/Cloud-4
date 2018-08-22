package com.ssitcloud.business.devmgmt.service;
/**
 * 
 * @package com.ssitcloud.devmgmt.service
 * @comment
 * @data 2016年4月14日
 * @author hwl
 */
public interface DeviceMgmtService {

	/**
	 * 向数据层请求信息，查询设备信息
	 * @methodName SelDeviceMgmt
	 * @returnType String
	 * @param reqInfo
	 * @return
	 * @author hwl
	 */
	String SelectDeviceMgmt(String reqInfo);
	
	
	/**
	 * 向数据层发起请求，删除设备相关信息
	 * @methodName AddDeviceMgmt
	 * @returnType String
	 * @param reqInfo
	 * @return
	 * @author hwl
	 */
	String DeleteDeviceMgmt(String reqInfo);
	
}
