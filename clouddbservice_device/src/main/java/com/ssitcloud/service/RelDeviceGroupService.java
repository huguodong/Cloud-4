package com.ssitcloud.service;

import java.util.List;

import com.ssitcloud.entity.RelDeviceGroupEntity;

/**
 * 
 * @comment
 * 
 * @author hwl
 * @data 2016年4月6日
 */
public interface RelDeviceGroupService {

	/**
	 * 增加设备分组关联数据
	 * 
	 * @param relDeviceGroupEntity
	 * @return
	 */
	public abstract int addRelDeviceGroup(
			RelDeviceGroupEntity relDeviceGroupEntity);

	/**
	 * 更新设备分组关联数据
	 * 
	 * @param relDeviceGroupEntity
	 * @return
	 */
	public abstract int updRelDeviceGroup(
			RelDeviceGroupEntity relDeviceGroupEntity);

	/**
	 * 删除设备分组关联数据
	 * 
	 * @param relDeviceGroupEntity
	 * @return
	 */
	public abstract int delRelDeviceGroup(
			RelDeviceGroupEntity relDeviceGroupEntity);

	/**
	 * 根据条件查询设备分组关联数据
	 * 
	 * @param relDeviceGroupEntity
	 * @return
	 */
	public abstract List<RelDeviceGroupEntity> selbyidRelDeviceGroup(
			RelDeviceGroupEntity relDeviceGroupEntity);

	/**
	 * 查询设备分组所有关联数据
	 * 
	 * @return
	 */
	public abstract List<RelDeviceGroupEntity> selallRelDeviceGroup();

}
