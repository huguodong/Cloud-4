package com.ssitcloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dao.StatisticsConfigDao;
import com.ssitcloud.entity.StatisticsConfigEntity;
import com.ssitcloud.service.StatisticsConfigService;

/**
 * 统计查询模板详情
 *
 * <p>2017年2月10日 下午2:20:45  
 * @author hjc 
 *
 */
@Service
public class StatisticsConfigServiceImpl implements StatisticsConfigService{
	@Resource
	private StatisticsConfigDao statisticsConfigDao;

	@Override
	public int insertStatisticsConfig(
			StatisticsConfigEntity statisticsConfigEntity) {
		return statisticsConfigDao.insertStatisticsConfig(statisticsConfigEntity);
	}

	@Override
	public int updateStatisticsConfig(
			StatisticsConfigEntity statisticsConfigEntity) {
		return statisticsConfigDao.updateStatisticsConfig(statisticsConfigEntity);
	}

	@Override
	public int deleteStatisticsConfig(
			StatisticsConfigEntity statisticsConfigEntity) {
		return statisticsConfigDao.deleteStatisticsConfig(statisticsConfigEntity);
	}

	@Override
	public StatisticsConfigEntity queryOneStatisticsConfig(
			StatisticsConfigEntity statisticsConfigEntity) {
		return statisticsConfigDao.queryOneStatisticsConfig(statisticsConfigEntity);
			
	}

	@Override
	public List<StatisticsConfigEntity> queryStatisticsConfigs(
			StatisticsConfigEntity statisticsConfigEntity) {
		return statisticsConfigDao.queryStatisticsConfigs(statisticsConfigEntity);
		
	}

	
}
