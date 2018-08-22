package com.ssitcloud.view.opermgmt.service.impl;


import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.opermgmt.service.RelOperatorStatisticsGroupService;

/**
 * 操作员组与模板组关联表
 *
 * <p>2017年3月13日  
 * @author lqw 
 *
 */
@Service
public class RelOperatorStatisticsGroupServiceImpl extends BasicServiceImpl implements RelOperatorStatisticsGroupService{
	private static final String URL_addRelOperatorStatisticsGroup = "addRelOperatorStatisticsGroup";
	private static final String URL_delRelOperatorStatisticsGroup = "delRelOperatorStatisticsGroup";
	private static final String URL_updRelOperatorStatisticsGroup = "updRelOperatorStatisticsGroup";
	private static final String URL_queryOneRelOperatorStatisticsGroup = "queryOneRelOperatorStatisticsGroup";
	private static final String URL_queryRelOperatorStatisticsGroups = "queryRelOperatorStatisticsGroups";

	@Override
	public ResultEntity insertRelOperatorStatisticsGroup(String req) {
		return postUrl(URL_addRelOperatorStatisticsGroup, req);
	}

	@Override
	public ResultEntity updateRelOperatorStatisticsGroup(String req) {
		return postUrl(URL_updRelOperatorStatisticsGroup, req);
	}

	@Override
	public ResultEntity deleteRelOperatorStatisticsGroup(String req) {
		return postUrl(URL_delRelOperatorStatisticsGroup, req);
	}

	@Override
	public ResultEntity queryOneRelOperatorStatisticsGroup(String req) {
		return postUrl(URL_queryOneRelOperatorStatisticsGroup, req);
	}

	@Override
	public ResultEntity queryRelOperatorStatisticsGroups(String req) {
		return postUrl(URL_queryRelOperatorStatisticsGroups, req);
	}
	
}
