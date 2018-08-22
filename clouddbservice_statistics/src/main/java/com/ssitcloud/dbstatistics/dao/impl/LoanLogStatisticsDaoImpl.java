package com.ssitcloud.dbstatistics.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dbstatistics.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbstatistics.dao.LoanLogStatisticsDao;
import com.ssitcloud.statistics.entity.StatisticsLoanLogEntity;

@Repository
public class LoanLogStatisticsDaoImpl extends CommonDaoImpl implements LoanLogStatisticsDao{

	@Override
	public List<StatisticsLoanLogEntity> countLoanLogForDay(Map<String, Object> param) {
		return this.sqlSessionTemplate.selectList("cirday.countLoanLog", param);
	}

	@Override
	public List<StatisticsLoanLogEntity> countLoanLogForWeek(Map<String, Object> param) {
		return this.sqlSessionTemplate.selectList("cirweek.countLoanLog", param);
	}

	@Override
	public List<StatisticsLoanLogEntity> countLoanLogForMonth(Map<String, Object> param) {
		return this.sqlSessionTemplate.selectList("cirmonth.countLoanLog", param);
	}

	@Override
	public List<StatisticsLoanLogEntity> countLoanLogForYear(Map<String, Object> param) {
		return this.sqlSessionTemplate.selectList("ciryear.countLoanLog", param);
	}

}
