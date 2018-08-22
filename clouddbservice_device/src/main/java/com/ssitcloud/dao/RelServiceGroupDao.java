package com.ssitcloud.dao;

import java.util.List;

import com.ssitcloud.common.dao.CommonDao;
import com.ssitcloud.entity.RelServiceGroupEntity;

/**
 * 
 * @comment
 * 
 * @author hwl
 * @data 2016年4月6日
 */
public interface RelServiceGroupDao extends CommonDao {

	public abstract int insert(RelServiceGroupEntity relServiceGroupEntity);

	public abstract int update(RelServiceGroupEntity relServiceGroupEntity);

	public abstract int delete(RelServiceGroupEntity relServiceGroupEntity);

	public abstract List<RelServiceGroupEntity> selectByidx(
			RelServiceGroupEntity relServiceGroupEntity);

	public abstract List<RelServiceGroupEntity> selectAll(
			);

	public abstract int deleteByServiceGroupIdxAndLibIdx(RelServiceGroupEntity relServiceGroupEntity);
	/**
	 * 查询记录数
	 * 限定条件
	 * service_group_idx
	 * library_idx 
	 * 
	 * @param relServiceGroupEntity
	 * @return
	 */
	public abstract int selectNumByServiceGroupIdxAndLibIdx(
			RelServiceGroupEntity relServiceGroupEntity);
}
