package com.ssitcloud.dbstatistics.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dbstatistics.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbstatistics.dao.VisitorLogStatisticsDao;
import com.ssitcloud.statistics.entity.StatisticsVisitorEntity;

@Repository
public class VisitorLogStatisticsDaoImpl extends CommonDaoImpl implements VisitorLogStatisticsDao{

	@Override
	public List<StatisticsVisitorEntity> countVisitorLogForDay(Map<String, Object> param) {
		return this.sqlSessionTemplate.selectList("rllcount.countVisitorLogForDay", param);
	}

	@Override
	public List<StatisticsVisitorEntity> countVisitorLogForWeek(Map<String, Object> param) {
		return this.sqlSessionTemplate.selectList("rllcount.countVisitorLogForWeek", param);
	}

	@Override
	public List<StatisticsVisitorEntity> countVisitorLogForMonth(Map<String, Object> param) {
		return this.sqlSessionTemplate.selectList("rllcount.countVisitorLogForMonth", param);
	}

	@Override
	public List<StatisticsVisitorEntity> countVisitorLogForYear(Map<String, Object> param) {
		return this.sqlSessionTemplate.selectList("rllcount.countVisitorLogForYear", param);
	}

	@Override
	public List<StatisticsVisitorEntity> countDayVisitorLogForWeekOrMonth(Map<String, Object> param) {
		return this.sqlSessionTemplate.selectList("rllcount.countDayVisitorLogForWeekOrMonth", param);
	}

	@Override
	public List<StatisticsVisitorEntity> countMonthVisitorLogForYear(Map<String, Object> param) {
		return this.sqlSessionTemplate.selectList("rllcount.countMonthVisitorLogForYear", param);
	}

	@Override
	public List<StatisticsVisitorEntity> countRealTimeVisitor(Map<String, Object> param) {
		return this.sqlSessionTemplate.selectList("rllcount.countRealTimeVisitor", param);
	}

}
