package com.ssitcloud.dbstatistics.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.statistics.entity.StatisticsLoanLogEntity;

public interface LoanLogStatisticsDao {

	/**
	 * 按天统计数据
	 * 
	 * <p>
	 * 2017年4月1日 下午4:08:07
	 * <p>
	 * 
	 * @return
	 */
	public abstract List<StatisticsLoanLogEntity> countLoanLogForDay(Map<String, Object> param);

	/**
	 * 按周统计数据
	 * 
	 * <p>
	 * 2017年4月1日 下午4:08:07
	 * <p>
	 * 
	 * @return
	 */
	public abstract List<StatisticsLoanLogEntity> countLoanLogForWeek(Map<String, Object> param);

	/**
	 * 按月统计数据
	 * 
	 * <p>
	 * 2017年4月1日 下午4:08:07
	 * <p>
	 * 
	 * @return
	 */
	public abstract List<StatisticsLoanLogEntity> countLoanLogForMonth(Map<String, Object> param);

	/**
	 * 按年统计数据
	 * 
	 * <p>
	 * 2017年4月1日 下午4:08:07
	 * <p>
	 * 
	 * @return
	 */
	public abstract List<StatisticsLoanLogEntity> countLoanLogForYear(Map<String, Object> param);

}
