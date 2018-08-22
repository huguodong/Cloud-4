package com.ssitcloud.dao;

import java.util.List;

import com.ssitcloud.common.dao.CommonDao;
import com.ssitcloud.entity.RelOperatorGroupEntity;

/**
 * 
 * @comment 
 * 
 * @author hwl
 * @data 2016年4月6日
 */
public interface RelOperatorGroupDao extends CommonDao {

	public abstract int insert(RelOperatorGroupEntity relOperatorGroupEntity);

	public abstract int update(RelOperatorGroupEntity relOperatorGroupEntity);

	public abstract int delete(RelOperatorGroupEntity relOperatorGroupEntity);

	public abstract List<RelOperatorGroupEntity> selectByid(
			RelOperatorGroupEntity relOperatorGroupEntity);

	public abstract List<RelOperatorGroupEntity> selectAll(
			);
	
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
	 * 根据用户idx删除用户组关系
	 *
	 * <p>2016年7月14日 下午4:25:27 
	 * <p>create by hjc
	 * @param relOperatorGroupEntity
	 * @return
	 */
	public abstract int deleteRelByOperatorIdx(RelOperatorGroupEntity relOperatorGroupEntity);

	public abstract int deleteRelOperatorGroupByOperatorIdxs(
			List<Integer> operatorIdxs);
}
