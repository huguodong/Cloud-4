package com.ssitcloud.dbstatistics.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.statistics.entity.StatisticsEntity;

public interface CardissueLogStatisticsDao {

	/**
	 * 按天统计数据
	 * 
	 * <p>
	 * 2017年4月1日 下午4:08:07
	 * <p>
	 * 
	 * @return
	 */
	public abstract List<StatisticsEntity> countCardissueLogForDay(Map<String, Object> param);

	/**
	 * 按周统计数据
	 * 
	 * <p>
	 * 2017年4月1日 下午4:08:07
	 * <p>
	 * 
	 * @return
	 */
	public abstract List<StatisticsEntity> countCardissueLogForWeek(Map<String, Object> param);

	/**
	 * 按月统计数据
	 * 
	 * <p>
	 * 2017年4月1日 下午4:08:07
	 * <p>
	 * 
	 * @return
	 */
	public abstract List<StatisticsEntity> countCardissueLogForMonth(Map<String, Object> param);

	/**
	 * 按年统计数据
	 * 
	 * <p>
	 * 2017年4月1日 下午4:08:07
	 * <p>
	 * 
	 * @return
	 */
	public abstract List<StatisticsEntity> countCardissueLogForYear(Map<String, Object> param);

}
