package com.ssitcloud.business.devmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

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
	ResultEntity queryAll(String req);

	/**
	 * 删除设备显示类型信息
	 * 
	 * @methodName
	 * @returnType String
	 * @param reqInfo
	 * @return
	 */
	ResultEntity delete(String req);

	/**
	 * 新增设备显示类型信息
	 * 
	 * @methodName
	 * @returnType String
	 * @param reqInfo
	 * @return
	 */
	ResultEntity insert(String req);
	
	/**
	 * 修改设备显示类型信息
	 * 
	 * @methodName
	 * @returnType String
	 * @param reqInfo
	 * @return
	 */
	ResultEntity update(String req);

	/**
	 * 根据id来查找设备显示类型信息
	 * 
	 * @methodName
	 * @returnType String
	 * @param reqInfo
	 * @return
	 */
	ResultEntity findById(String req);

	/**
	 * 根据设备类型id来查找设备显示类型信息
	 * 
	 * @methodName
	 * @returnType String
	 * @param reqInfo
	 * @return
	 */
	ResultEntity findByTypeId(String req);
	
	/**
	 * 获取设备类型
	 * @return
	 */
	ResultEntity getDeviceTypes(String req);
}
