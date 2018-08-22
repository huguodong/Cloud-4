package com.ssitcloud.dbstatistics.service;

import java.util.List;

import com.ssitcloud.dbstatistics.entity.StaticsTypeEntity;

public interface StaticsTypeService {
	/**
	 * 新增一条记录
	 *
	 * <p>2017年3月1日 
	 * <p>create by lqw
	 * @param StaticsTypeEntity
	 * @return
	 */
	public abstract int insertStaticsType(StaticsTypeEntity staticsTypeEntity);
	/**
	 * 删除一条记录
	 *
	 * <p>2017年3月1日 
	 * <p>create by lqw
	 * @param StaticsTypeEntity
	 * @return
	 */
	public abstract int deleteStaticsType(StaticsTypeEntity staticsTypeEntity);
	/**
	 * 更新一条记录
	 *
	 * <p>2017年3月1日 
	 * <p>create by lqw
	 * @param StaticsTypeEntity
	 * @return
	 */
	public abstract int updateStaticsType(StaticsTypeEntity staticsTypeEntity);
	/**
	 * 查询一条记录
	 *
	 * <p>2017年3月1日 
	 * <p>create by lqw
	 * @param StaticsTypeEntity
	 * @return
	 */
	public abstract StaticsTypeEntity queryStaticsType(StaticsTypeEntity staticsTypeEntity);
	/**
	 * 查询多条记录
	 *
	 * <p>2017年3月1日 
	 * <p>create by lqw
	 * @param StaticsTypeEntity
	 * @return
	 */
	public abstract List<StaticsTypeEntity> queryStaticsTypeList(StaticsTypeEntity staticsTypeEntity);

}
