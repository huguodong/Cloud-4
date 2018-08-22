package com.ssitcloud.service;

import java.util.List;
import java.util.Map;



import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.entity.DeviceGroupEntity;

/**
 * 
 * @comment
 * 
 * @author hwl
 * @data 2016年4月6日
 */
public interface DeviceGroupService {

	/**
	 * 增加设备组数据
	 * 
	 * @param deviceGroupEntity
	 * @return
	 */
	public abstract int addDeviceGroup(DeviceGroupEntity deviceGroupEntity);

	/**
	 * 更新设备组数据
	 * 
	 * @param deviceGroupEntity
	 * @return
	 */
	public abstract int updDeviceGroup(DeviceGroupEntity deviceGroupEntity);

	/**
	 * 删除设备组数据
	 * 
	 * @param deviceGroupEntity
	 * @return
	 */
	public abstract int delDeviceGroup(DeviceGroupEntity deviceGroupEntity);

	/**
	 * 条件查询设备组数据
	 * 
	 * @param deviceGroupEntity
	 * @return
	 */
	public abstract List<DeviceGroupEntity> selbyidDeviceGroup(
			DeviceGroupEntity deviceGroupEntity);

	/**
	 * 查询单个设备组数据
	 * 
	 * @return
	 */
	public abstract DeviceGroupEntity seloneDeviceGroup(DeviceGroupEntity deviceGroupEntity);


	public abstract PageEntity selPageDeviceGroup(Map<String, String> map);
	
	public abstract List<DeviceGroupEntity> selectgroup();
	
	public abstract List<DeviceGroupEntity> selectgroupBylib_idx(int library_idx);
	public abstract List<DeviceGroupEntity> selectgroupBylib_idxs(List<Integer> libraryIdxs);

	public abstract ResultEntity selectGroupByDeviceIdx(String req);
	/**
	 * 根据libIdx和 DeviceGroupId查询设备分组信息
	 * @param d
	 * @return
	 */
	public abstract List<DeviceGroupEntity> selectgroupBylibIdxAndDeviceGroupId(
			DeviceGroupEntity d);

	DeviceGroupEntity selectByDeviceGroupIdx(DeviceGroupEntity d);

}
