package com.ssitcloud.dbstatistics.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dbstatistics.dao.StatisticsMaintypeDao;
import com.ssitcloud.dbstatistics.entity.StatisticsMaintypeEntity;
import com.ssitcloud.dbstatistics.service.StatisticsMaintypeService;

@Service
public class StatisticsMaintypeServiceImpl implements StatisticsMaintypeService {
	
	@Resource
	private StatisticsMaintypeDao statisticsMaintypeDao;
	@Override
	public StatisticsMaintypeEntity queryStatisticsMaintype(
			StatisticsMaintypeEntity statisticsMaintypeEntity) {
		return statisticsMaintypeDao.queryStatisticsMaintype(statisticsMaintypeEntity);
	}

	@Override
	public List<StatisticsMaintypeEntity> queryStatisticsMaintypeList(
			StatisticsMaintypeEntity statisticsMaintypeEntity) {
		return statisticsMaintypeDao.queryStatisticsMaintypeList(statisticsMaintypeEntity);
	}

}
