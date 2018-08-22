package com.ssitcloud.dbstatistics.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dbstatistics.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbstatistics.dao.StatisticsMaintypeDao;
import com.ssitcloud.dbstatistics.entity.StatisticsMaintypeEntity;

@Repository
public class StatisticsMaintypeDaoImpl extends CommonDaoImpl implements StatisticsMaintypeDao {

	@Override
	public StatisticsMaintypeEntity queryStatisticsMaintype(
			StatisticsMaintypeEntity statisticsMaintypeEntity) {
		return this.sqlSessionTemplate.selectOne("statistics_maintype.queryStatisticsMaintype",statisticsMaintypeEntity);
	}

	@Override
	public List<StatisticsMaintypeEntity> queryStatisticsMaintypeList(
			StatisticsMaintypeEntity statisticsMaintypeEntity) {
		return this.sqlSessionTemplate.selectList("statistics_maintype.queryStatisticsMaintypeList",statisticsMaintypeEntity);
	}

}
