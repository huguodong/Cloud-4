package com.ssitcloud.dbstatistics.dao;

import java.util.List;

import com.ssitcloud.dbstatistics.entity.StatisticsMaintypeEntity;

public interface StatisticsMaintypeDao {
	/**
	 * 查询一条记录
	 * author huanghuang
	 * 2017年3月20日 下午1:42:13
	 * @param StatisticsMaintypeEntity
	 * @return
	 */
	StatisticsMaintypeEntity queryStatisticsMaintype(StatisticsMaintypeEntity statisticsMaintypeEntity);
	/**
	 * 查询多条记录
	 * author huanghuang
	 * 2017年3月20日 下午1:42:26
	 * @param StatisticsMaintypeEntity
	 * @return
	 */
	List<StatisticsMaintypeEntity> queryStatisticsMaintypeList(StatisticsMaintypeEntity statisticsMaintypeEntity);
}
