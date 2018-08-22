package com.ssitcloud.dbstatistics.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dbstatistics.dao.StatisticsReltypeDao;
import com.ssitcloud.dbstatistics.entity.StatisticsReltypeEntity;
import com.ssitcloud.dbstatistics.service.StatisticsReltypeService;
@Service
public class StatisticsReltypeServiceImpl implements StatisticsReltypeService {
	@Resource
	StatisticsReltypeDao statisticsReltypeDao;

	@Override
	public StatisticsReltypeEntity queryReltype(
			StatisticsReltypeEntity statisticsReltypeEntity) {
		return statisticsReltypeDao.queryReltype(statisticsReltypeEntity);
	}

	@Override
	public List<StatisticsReltypeEntity> queryReltypeList(
			StatisticsReltypeEntity statisticsReltypeEntity) {
		return statisticsReltypeDao.queryReltypeList(statisticsReltypeEntity);
	}

}
