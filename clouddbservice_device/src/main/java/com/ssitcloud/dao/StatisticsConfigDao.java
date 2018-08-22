package com.ssitcloud.dao;

import java.util.List;

import com.ssitcloud.entity.StatisticsConfigEntity;

/**
 * 统计查询模板详情
 *
 * <p>2017年2月10日 下午2:20:45  
 * @author hjc 
 *
 */
public interface StatisticsConfigDao {
	/**
	 * 统计查询模板详情StatisticsConfigEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:30:27
	 * @param statisticsConfigEntity
	 * @return
	 */
	public abstract int insertStatisticsConfig(StatisticsConfigEntity statisticsConfigEntity);
	
	/**
	 * 统计查询模板详情StatisticsConfigEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:30:55
	 * @param statisticsConfigEntity
	 * @return
	 */
	public abstract int updateStatisticsConfig(StatisticsConfigEntity statisticsConfigEntity);
	
	/**
	 * 统计查询模板详情StatisticsConfigEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:31:18
	 * @param statisticsConfigEntity
	 * @return
	 */
	public abstract int deleteStatisticsConfig(StatisticsConfigEntity statisticsConfigEntity);
	
	/**
	 * 统计查询模板详情StatisticsConfigEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:31:47
	 * @param statisticsConfigEntity
	 * @return
	 */
	public abstract StatisticsConfigEntity queryOneStatisticsConfig(StatisticsConfigEntity statisticsConfigEntity);
	
	/**
	 * 统计查询模板详情StatisticsConfigEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:32:05
	 * @param statisticsConfigEntity
	 * @return
	 */
	public abstract List<StatisticsConfigEntity> queryStatisticsConfigs(StatisticsConfigEntity statisticsConfigEntity);
}
