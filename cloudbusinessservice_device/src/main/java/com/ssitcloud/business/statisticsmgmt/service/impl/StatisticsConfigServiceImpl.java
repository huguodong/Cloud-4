package com.ssitcloud.business.statisticsmgmt.service.impl;


import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.statisticsmgmt.service.StatisticsConfigService;
import com.ssitcloud.common.entity.ResultEntity;

/**
 * 统计查询模板详情
 *
 * <p>2017年3月30日  
 * @author lqw 
 *
 */
@Service
public class StatisticsConfigServiceImpl extends BasicServiceImpl implements StatisticsConfigService{
	private static final String URL_INSERTSTATISTICSCONFIG="insertStatisticsConfig";
	private static final String URL_UPDATESTATISTICSCONFIG="updateStatisticsConfig";
	private static final String URL_DELETESTATISTICSCONFIG="deleteStatisticsConfig";
	private static final String URL_SELECTSTATISTICSCONFIG="selectStatisticsConfig";//根据模板ID查询
	private static final String URL_SELECTSTATISTICSCONFIGS="selectStatisticsConfigs";//查询全部

	@Override
	public ResultEntity insertStatisticsConfig(String req) {
		return postUrl(URL_INSERTSTATISTICSCONFIG, req);
	}

	@Override
	public ResultEntity updateStatisticsConfig(String req) {
		return postUrl(URL_UPDATESTATISTICSCONFIG, req);
	}

	@Override
	public ResultEntity deleteStatisticsConfig(String req) {
		return postUrl(URL_DELETESTATISTICSCONFIG, req);
	}

	@Override
	public ResultEntity queryOneStatisticsConfig(String req) {
		return postUrl(URL_SELECTSTATISTICSCONFIG, req);
	}

	@Override
	public ResultEntity queryStatisticsConfigs(String req) {
		return postUrl(URL_SELECTSTATISTICSCONFIGS, req);
	}
	
}
