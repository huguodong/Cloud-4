package com.ssitcloud.service;

import java.util.List;

import com.ssitcloud.entity.RelOperatorDeviceGroupEntity;

/**
 * @comment
 * 
 * @author hwl
 * @data 2016年4月5日
 */
public interface RelOperatorDeviceGroupService {

	/**
	 * 添加操作员组和设备组关联数据
	 * 
	 * @param reloperatordeviceGroupentity
	 * @return
	 */
	public abstract int addRelOperatorDeviceGroup(
			RelOperatorDeviceGroupEntity reloperatordeviceGroupentity);

	/**
	 * 更新操作员组和设备组关联数据
	 * 
	 * @param reloperatordeviceGroupentity
	 * @return
	 */
	public abstract int updRelOperatorDeviceGroup(
			RelOperatorDeviceGroupEntity reloperatordeviceGroupentity);

	/**
	 * 删除操作员组和设备组关联数据
	 * 
	 * @param reloperatordeviceGroupentity
	 * @return
	 */
	public abstract int delRelOperatorDeviceGroup(
			RelOperatorDeviceGroupEntity reloperatordeviceGroupentity);

	/**
	 * 根据条件查询操作员组和设备组关联数据
	 * 
	 * @param reloperatordeviceGroupentity
	 * @return
	 */
	public abstract List<RelOperatorDeviceGroupEntity> selbyidRelOperatorDeviceGroup(
			RelOperatorDeviceGroupEntity reloperatordeviceGroupentity);

	/**
	 * 查询操作员组和设备组关联表所有数据
	 * 
	 * @return
	 */
	public abstract List<RelOperatorDeviceGroupEntity> selallRelOperatorDeviceGroup();

}
