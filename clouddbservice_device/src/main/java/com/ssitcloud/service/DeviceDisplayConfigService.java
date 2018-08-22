package com.ssitcloud.service;

import java.util.List;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.entity.DeviceDisplayConfig;
import com.ssitcloud.entity.MetadataDeviceTypeEntity;
import com.ssitcloud.entity.page.DeviceDisplayConfigPage;


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
	public DeviceDisplayConfigPage queryAll(DeviceDisplayConfigPage page);

	/**
	 * 删除设备显示类型信息
	 * 
	 * @methodName
	 * @returnType String
	 * @param reqInfo
	 * @return
	 */
	public int delete(String req);

	/**
	 * 新增设备显示类型信息
	 * 
	 * @methodName
	 * @returnType String
	 * @param reqInfo
	 * @return
	 */
	public DeviceDisplayConfig insert(String req);
	
	/**
	 * 修改设备显示类型信息
	 * 
	 * @methodName
	 * @returnType String
	 * @param reqInfo
	 * @return
	 */
	public ResultEntity update(String req);

	/**
	 * 根据id来查找设备显示类型信息
	 * 
	 * @methodName
	 * @returnType String
	 * @param reqInfo
	 * @return
	 */
	public DeviceDisplayConfig findById(DeviceDisplayConfig page);

	
	/**
	 * 根据设备类型id来查找设备显示类型信息
	 * 
	 * @methodName
	 * @returnType String
	 * @param reqInfo
	 * @return
	 */
	public DeviceDisplayConfig findByTypeId(DeviceDisplayConfig page);

	/**
	 * 获取设备类型
	 * @param req
	 * @return
	 */
	public List<MetadataDeviceTypeEntity> getDeviceTypes(String req);
}
