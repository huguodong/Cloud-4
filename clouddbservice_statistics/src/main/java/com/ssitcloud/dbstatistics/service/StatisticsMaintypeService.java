package com.ssitcloud.dbstatistics.service;

import java.util.List;

import com.ssitcloud.dbstatistics.entity.StatisticsMaintypeEntity;

public interface StatisticsMaintypeService {
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
