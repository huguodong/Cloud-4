package com.ssitcloud.service;

import java.util.List;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.entity.RelOperatorGroupEntity;

/**
 * 
 * @comment
 * 
 * @author hwl
 * @data 2016年4月6日
 */
public interface RelOperatorGroupService {

	/**
	 * 添加操作员分组数据
	 * @param relOperatorGroupEntity
	 * @return
	 */
	public abstract int addRelOperatorGroup(
			RelOperatorGroupEntity relOperatorGroupEntity);

	/**
	 * 更新操作员分组数据
	 * @param relOperatorGroupEntity
	 * @return
	 */
	public abstract int updRelOperatorGroup(
			RelOperatorGroupEntity relOperatorGroupEntity);

	/**
	 * 删除操作员分组数据
	 * @param relOperatorGroupEntity
	 * @return
	 */
	public abstract int delRelOperatorGroup(
			RelOperatorGroupEntity relOperatorGroupEntity);

	/**
	 * 根据条件查询操作员分组数据
	 * @param relOperatorGroupEntity
	 * @return
	 */
	public abstract List<RelOperatorGroupEntity> selbyidRelOperatorGroup(
			RelOperatorGroupEntity relOperatorGroupEntity);

	/**
	 * 查询操作员分组关联表所有数据
	 * @return
	 */
	public abstract List<RelOperatorGroupEntity> selallRelOperatorGroup();
	
	/**
	 * 根据操作员idx查询所在的用户组信息
	 *
	 * <p>2016年6月28日 下午6:10:59 
	 * <p>create by hjc
	 * @param groupEntity
	 * @return
	 */
	public abstract RelOperatorGroupEntity queryOperatorGroupByOperIdx(RelOperatorGroupEntity groupEntity);
	
	/**
	 * 更新操作员的idx
	 *
	 * <p>2016年7月14日 下午3:50:14 
	 * <p>create by hjc
	 * @param groupEntity
	 * @return
	 */
	public abstract ResultEntity updateOperatorGroup(String req);

	public abstract ResultEntity deleteRelOperatorGroupByOperatorIdxs(String req);

}
