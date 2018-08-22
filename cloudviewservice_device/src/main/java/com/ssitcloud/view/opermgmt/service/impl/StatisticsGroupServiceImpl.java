package com.ssitcloud.view.opermgmt.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.opermgmt.service.StatisticsGroupService;


@Service
public class StatisticsGroupServiceImpl extends BasicServiceImpl implements StatisticsGroupService {
	private static final String URL_addStatisticsGroup = "addStatisticsGroup";
	private static final String URL_delStatisticsGroup = "deleteStatisticsGroup";
	private static final String URL_updStatisticsGroup = "updateStatisticsGroup";
	public static final String URL_queryOneStatisticsGroup="selectStatisticsOneGroup";
	public static final String URL_queryStatisticsGroupByparam="selectStatisticsGroups";
	public static final String URL_queryStatisticsGroupPageByparam="selectStatisticsGroupByPage";

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


}
