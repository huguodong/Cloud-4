package com.ssitcloud.dbstatistics.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dbstatistics.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbstatistics.dao.FinanceLogStatisticsDao;
import com.ssitcloud.statistics.entity.StatisticsEntity;

@Repository
public class FinanceLogStatisticsDaoImpl extends CommonDaoImpl implements FinanceLogStatisticsDao{

	@Override
	public List<StatisticsEntity> countFinanceLogForDay(Map<String, Object> param) {
		return this.sqlSessionTemplate.selectList("fineday.countFinanceLog", param);
	}

	@Override
	public List<StatisticsEntity> countFinanceLogForWeek(Map<String, Object> param) {
		return this.sqlSessionTemplate.selectList("fineweek.countFinanceLog", param);
	}

	@Override
	public List<StatisticsEntity> countFinanceLogForMonth(Map<String, Object> param) {
		return this.sqlSessionTemplate.selectList("fineWeekData.countFinanceLog", param);
	}

	@Override
	public List<StatisticsEntity> countFinanceLogForYear(Map<String, Object> param) {
		return this.sqlSessionTemplate.selectList("fineYearData.countFinanceLog", param);
	}

}
