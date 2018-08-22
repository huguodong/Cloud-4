package com.ssitcloud.dbstatistics.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.statistics.entity.StatisticsVisitorEntity;

public interface VisitorLogStatisticsDao {

	/**
	 * 按天统计数据
	 * 
	 * <p>
	 * 2017年4月1日 下午4:08:07
	 * <p>
	 * 
	 * @return
	 */
	public abstract List<StatisticsVisitorEntity> countVisitorLogForDay(Map<String, Object> param);

	/**
	 * 按周统计数据
	 * 
	 * <p>
	 * 2017年4月1日 下午4:08:07
	 * <p>
	 * 
	 * @return
	 */
	public abstract List<StatisticsVisitorEntity> countVisitorLogForWeek(Map<String, Object> param);

	/**
	 * 按月统计数据
	 * 
	 * <p>
	 * 2017年4月1日 下午4:08:07
	 * <p>
	 * 
	 * @return
	 */
	public abstract List<StatisticsVisitorEntity> countVisitorLogForMonth(Map<String, Object> param);

	/**
	 * 按年统计数据
	 * 
	 * <p>
	 * 2017年4月1日 下午4:08:07
	 * <p>
	 * 
	 * @return
	 */
	public abstract List<StatisticsVisitorEntity> countVisitorLogForYear(Map<String, Object> param);
	
	/**
	 * 按周/月统计，需要按天展示
	 * 
	 * <p>
	 * 2017年12月16日 上午11:30
	 * <p>
	 * 
	 * @return
	 */
	public abstract List<StatisticsVisitorEntity> countDayVisitorLogForWeekOrMonth(Map<String, Object> param);
	
	/**
	 * 按年统计，需要按月展示
	 * 
	 * <p>
	 * 2017年12月16日 上午11:30
	 * <p>
	 * 
	 * @return
	 */
	public abstract List<StatisticsVisitorEntity> countMonthVisitorLogForYear(Map<String, Object> param);

	/**
	 * 今天实时数据按时展示
	 * 
	 * <p>
	 * 2017年12月22日 下午14:00
	 * <p>
	 * 
	 * @return
	 */
	public abstract List<StatisticsVisitorEntity> countRealTimeVisitor(Map<String, Object> param);
}
