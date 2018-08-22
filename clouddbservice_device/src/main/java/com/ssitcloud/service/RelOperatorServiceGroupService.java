package com.ssitcloud.service;

import java.util.List;

import com.ssitcloud.entity.RelOperatorServiceGroupEntity;

/**
 * @comment
 * @author hwl
 * 
 * @data 2016-3-29下午3:35:40
 * 
 */
public interface RelOperatorServiceGroupService {

	/**
	 * 添加操作员组和业务组关联数据
	 * 
	 * @param reloperatorserviceGroupentity
	 * @return
	 */
	public abstract int addRelOperatorServiceGroup(
			RelOperatorServiceGroupEntity reloperatorserviceGroupentity);

	/**
	 * 更新操作员组和业务组关联数据
	 * 
	 * @param reloperatorserviceGroupentity
	 * @return
	 */

	public abstract int updRelOperatorServiceGroup(
			RelOperatorServiceGroupEntity reloperatorserviceGroupentity);

	/**
	 * 删除操作员组和业务组关联数据
	 * 
	 * @param reloperatorserviceGroupentity
	 * @return
	 */
	public abstract int delRelOperatorServiceGroup(
			RelOperatorServiceGroupEntity reloperatorserviceGroupentity);

	/**
	 * 根据条件查询操作员组和业务组关联数据
	 * 
	 * @param reloperatorserviceGroupentity
	 * @return
	 */
	public abstract List<RelOperatorServiceGroupEntity> selbyidRelOperatorServiceGroup(
			RelOperatorServiceGroupEntity reloperatorserviceGroupentity);

	/**
	 * 查询所有数据
	 * 
	 * @return
	 */
	public abstract List<RelOperatorServiceGroupEntity> selallRelOperatorServiceGroup();
}
