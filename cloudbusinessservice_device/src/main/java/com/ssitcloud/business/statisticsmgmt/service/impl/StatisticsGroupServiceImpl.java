package com.ssitcloud.business.statisticsmgmt.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.statisticsmgmt.service.StatisticsGroupService;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class StatisticsGroupServiceImpl extends BasicServiceImpl implements StatisticsGroupService {
	private static final String URL_addStatisticsGroup = "addStatisticsGroup";
	private static final String URL_delStatisticsGroup = "deleteStatisticsGroup";
	private static final String URL_updStatisticsGroup = "updateStatisticsGroup";
	public static final String URL_queryOneStatisticsGroup="selectStatisticsOneGroup";
	public static final String URL_queryStatisticsGroupByparam="selectStatisticsGroups";
	public static final String URL_queryStatisticsGroupPageByparam="selectStatisticsGroupByPage";
	
	public static final String URL_INCREASEGROUP="increaseStatisticsGroup";
	public static final String URL_MODIFYGROUP="modifyStatisticsGroup";
	public static final String URL_REMOVEGROUP="removeStatisticsGroup";
	public static final String URL_GROUPPAGE="querysGroupByPageParam";

	@Override
	public ResultEntity addStatisticsGroup(String req) {
		return postUrl(URL_addStatisticsGroup, req);
	}

	@Override
	public ResultEntity delStatisticsGroup(String req) {
		return postUrl(URL_delStatisticsGroup, req);
	}

	@Override
	public ResultEntity updStatisticsGroup(String req) {
		return postUrl(URL_updStatisticsGroup, req);
	}

	@Override
	public ResultEntity queryOneStatisticsGroup(String req) {
		return postUrl(URL_queryOneStatisticsGroup, req);
	}
	
	@Override
	public ResultEntity queryStatisticsGroupByparam(String req) {
		return postUrl(URL_queryStatisticsGroupByparam, req);
	}

	@Override
	public ResultEntity queryStatisticsGroupPageByparam(String req) {
		return postUrl(URL_queryStatisticsGroupPageByparam, req);
	}

	@Override
	public ResultEntity increaseStatisticsGroup(String req) {
		return postUrl(URL_INCREASEGROUP, req);
	}

	@Override
	public ResultEntity modifyStatisticsGroup(String req) {
		return postUrl(URL_MODIFYGROUP, req);
	}

	@Override
	public ResultEntity removeStatisticsGroup(String req) {
		return postUrl(URL_REMOVEGROUP, req);
	}

	@Override
	public ResultEntity querysGroupByPageParam(String req) {
		return postUrl(URL_GROUPPAGE, req);
	}


}
