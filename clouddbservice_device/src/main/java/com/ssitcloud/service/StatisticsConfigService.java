package com.ssitcloud.service;

import java.util.List;

import com.ssitcloud.entity.StatisticsConfigEntity;


/**
 * 统计查询模板详情
 *
 * <p>2017年2月10日 下午2:20:45  
 * @author hjc 
 *
 */
public interface StatisticsConfigService {
	/**
	 * 统计查询模板详情StatisticsConfigEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:36:42
	 * @param statisticsConfigEntity
	 * @return
	 */
	public abstract int insertStatisticsConfig(StatisticsConfigEntity statisticsConfigEntity);
	
	/**
	 * 统计查询模板详情StatisticsConfigEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:36:58
	 * @param statisticsConfigEntity
	 * @return
	 */
	public abstract int updateStatisticsConfig(StatisticsConfigEntity statisticsConfigEntity);
	
	/**
	 * 统计查询模板详情StatisticsConfigEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:37:20
	 * @param statisticsConfigEntity
	 * @return
	 */
	public abstract int deleteStatisticsConfig(StatisticsConfigEntity statisticsConfigEntity);
	
	/**
	 * 统计查询模板详情StatisticsConfigEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:36
	 * @param statisticsConfigEntity
	 * @return
	 */
	public abstract StatisticsConfigEntity queryOneStatisticsConfig(StatisticsConfigEntity statisticsConfigEntity);
	
	/**
	 * 统计查询模板详情StatisticsConfigEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:49
	 * @param statisticsConfigEntity
	 * @return
	 */
	public abstract List<StatisticsConfigEntity> queryStatisticsConfigs(StatisticsConfigEntity statisticsConfigEntity);
}
