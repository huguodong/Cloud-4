package com.ssitcloud.dbstatistics.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dbstatistics.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbstatistics.dao.CardissueLogStatisticsDao;
import com.ssitcloud.statistics.entity.StatisticsEntity;

@Repository
public class CardissueLogStatisticsDaoImpl extends CommonDaoImpl implements CardissueLogStatisticsDao{

	@Override
	public List<StatisticsEntity> countCardissueLogForDay(Map<String, Object> param) {
		return this.sqlSessionTemplate.selectList("newCardDayData.countCardissueLog", param);
	}

	@Override
	public List<StatisticsEntity> countCardissueLogForWeek(Map<String, Object> param) {
		return this.sqlSessionTemplate.selectList("newCardWeekData.countCardissueLog", param);
	}

	@Override
	public List<StatisticsEntity> countCardissueLogForMonth(Map<String, Object> param) {
		return this.sqlSessionTemplate.selectList("newCardMonthData.countCardissueLog", param);
	}

	@Override
	public List<StatisticsEntity> countCardissueLogForYear(Map<String, Object> param) {
		return this.sqlSessionTemplate.selectList("newCardYearData.countCardissueLog", param);
	}



}
