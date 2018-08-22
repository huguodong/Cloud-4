package com.ssitcloud.dao;

import java.util.List;

import com.ssitcloud.common.dao.CommonDao;
import com.ssitcloud.entity.DeviceDisplayConfig;
import com.ssitcloud.entity.MetadataDeviceTypeEntity;
import com.ssitcloud.entity.page.DeviceDisplayConfigPage;
/**
 * 
 * @comment
 * 
 * @author hwl
 * @data 2016年4月6日
 */
public interface DeviceDisplayConfigDao extends CommonDao {
	/**
	 * 查询设备显示类型信息
	 * 
	 * @methodName
	 * @returnType String
	 * @param reqInfo
	 * @return
	 */
	public DeviceDisplayConfigPage queryAll(DeviceDisplayConfigPage entity);

	/**
	 * 删除设备显示类型信息
	 * 
	 * @methodName
	 * @returnType String
	 * @param reqInfo
	 * @return
	 */
	public int delete(List<Integer> display_type_idxs);

	/**
	 * 新增设备显示类型信息
	 * 
	 * @methodName
	 * @returnType String
	 * @param reqInfo
	 * @return
	 */
	public DeviceDisplayConfig insert(DeviceDisplayConfig entity);
	
	/**
	 * 修改设备显示类型信息
	 * 
	 * @methodName
	 * @returnType String
	 * @param reqInfo
	 * @return
	 */
	public int update(DeviceDisplayConfig entity);

	/**
	 * 根据id来查找设备显示类型信息
	 * 
	 * @methodName
	 * @returnType String
	 * @param reqInfo
	 * @return
	 */
	public DeviceDisplayConfig findById(int display_type_idx);

	
	/**
	 * 根据设备类型id来查找设备显示类型信息
	 * 
	 * @methodName
	 * @returnType String
	 * @param reqInfo
	 * @return
	 */
	public DeviceDisplayConfig findByTypeId(int device_type_idx);
	
	/**
	 * 获取设备类型
	 * @param req
	 * @return
	 */
	public List<MetadataDeviceTypeEntity> getDeviceTypes(String req);
	
}
