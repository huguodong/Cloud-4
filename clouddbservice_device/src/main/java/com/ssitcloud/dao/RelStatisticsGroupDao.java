package com.ssitcloud.dao;

import java.util.List;

import com.ssitcloud.entity.RelStatisticsGroupEntity;

/**
 * 统计模板与模板组对应关系
 *
 * <p>2017年2月10日 下午2:19:35  
 * @author hjc 
 *
 */
public interface RelStatisticsGroupDao {
	/**
	 * 统计模板与模板组对应关系RelStatisticsGroupEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:30:27
	 * @param relStatisticsGroupEntity
	 * @return
	 */
	public abstract int insertRelStatisticsGroup(RelStatisticsGroupEntity relStatisticsGroupEntity);
	
	/**
	 * 统计模板与模板组对应关系RelStatisticsGroupEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:30:55
	 * @param relStatisticsGroupEntity
	 * @return
	 */
	public abstract int updateRelStatisticsGroup(RelStatisticsGroupEntity relStatisticsGroupEntity);
	
	/**
	 * 邮件服务器配置RelStatisticsGroupEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:31:18
	 * @param relStatisticsGroupEntity
	 * @return
	 */
	public abstract int deleteRelStatisticsGroup(RelStatisticsGroupEntity relStatisticsGroupEntity);
	
	/**
	 * 统计模板与模板组对应关系RelStatisticsGroupEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:31:47
	 * @param relStatisticsGroupEntity
	 * @return
	 */
	public abstract RelStatisticsGroupEntity queryOneRelStatisticsGroup(RelStatisticsGroupEntity relStatisticsGroupEntity);
	
	/**
	 * 统计模板与模板组对应关系RelStatisticsGroupEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:32:05
	 * @param relStatisticsGroupEntity
	 * @return
	 */
	public abstract List<RelStatisticsGroupEntity> queryRelStatisticsGroups(RelStatisticsGroupEntity relStatisticsGroupEntity);
}
