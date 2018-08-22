package com.ssitcloud.dbstatistics.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.statistics.entity.StatisticsEntity;

public interface FinanceLogStatisticsDao {

	/**
	 * 按天统计数据
	 * 
	 * <p>
	 * 2017年4月1日 下午4:08:07
	 * <p>
	 * 
	 * @return
	 */
	public abstract List<StatisticsEntity> countFinanceLogForDay(Map<String, Object> param);

	/**
	 * 按周统计数据
	 * 
	 * <p>
	 * 2017年4月1日 下午4:08:07
	 * <p>
	 * 
	 * @return
	 */
	public abstract List<StatisticsEntity> countFinanceLogForWeek(Map<String, Object> param);

	/**
	 * 按月统计数据
	 * 
	 * <p>
	 * 2017年4月1日 下午4:08:07
	 * <p>
	 * 
	 * @return
	 */
	public abstract List<StatisticsEntity> countFinanceLogForMonth(Map<String, Object> param);

	/**
	 * 按年统计数据
	 * 
	 * <p>
	 * 2017年4月1日 下午4:08:07
	 * <p>
	 * 
	 * @return
	 */
	public abstract List<StatisticsEntity> countFinanceLogForYear(Map<String, Object> param);

}
