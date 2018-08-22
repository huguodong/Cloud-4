package com.ssitcloud.dbstatistics.dao;

import java.util.List;

import com.ssitcloud.dbstatistics.entity.StatisticsReltypeEntity;

public interface StatisticsReltypeDao {
	/**
	 * 查询一条记录
	 * author lqw
	 * 2017年4月6日 
	 * @param StatisticsReltypeEntity
	 * @return
	 */
	StatisticsReltypeEntity queryReltype(StatisticsReltypeEntity statisticsReltypeEntity);
	/**
	 * 查询多条记录
	 * author lqw
	 * 2017年4月6日 
	 * @param StatisticsReltypeEntity
	 * @return
	 */
	List<StatisticsReltypeEntity> queryReltypeList(StatisticsReltypeEntity statisticsReltypeEntity);
}
