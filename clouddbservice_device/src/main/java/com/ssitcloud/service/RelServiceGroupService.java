package com.ssitcloud.service;

import java.util.List;

import com.ssitcloud.entity.RelServiceGroupEntity;

/**
 * 
 * @comment
 * 
 * @author hwl
 * @data 2016年4月6日
 */
public interface RelServiceGroupService {

	/**
	 * 添加业务分组数据
	 * 
	 * @param relServiceGroupEntity
	 * @return
	 */
	public abstract int addRelServiceGroup(
			RelServiceGroupEntity relServiceGroupEntity);

	/**
	 * 更新业务分组数据
	 * 
	 * @param relServiceGroupEntity
	 * @return
	 */
	public abstract int updRelServiceGroup(
			RelServiceGroupEntity relServiceGroupEntity);

	/**
	 * 删除业务分组数据
	 * 
	 * @param relServiceGroupEntity
	 * @return
	 */
	public abstract int delRelServiceGroup(
			RelServiceGroupEntity relServiceGroupEntity);

	/**
	 * 根据条件查询业务分组数据
	 * 
	 * @param relServiceGroupEntity
	 * @return
	 */
	public abstract List<RelServiceGroupEntity> selbyidRelServiceGroup(
			RelServiceGroupEntity relServiceGroupEntity);

	/**
	 * 查询所有业务分组数据
	 * 
	 * @return
	 */
	public abstract List<RelServiceGroupEntity> selallRelServiceGroup();

}
