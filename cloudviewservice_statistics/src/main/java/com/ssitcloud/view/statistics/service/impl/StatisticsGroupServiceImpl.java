package com.ssitcloud.view.statistics.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.statistics.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.statistics.service.StatisticsGroupService;


@Service
public class StatisticsGroupServiceImpl extends BasicServiceImpl implements StatisticsGroupService {
	public static final String URL_INCREASEGROUP="increaseStatisticsGroup";
	public static final String URL_MODIFYGROUP="modifyStatisticsGroup";
	public static final String URL_REMOVEGROUP="removeStatisticsGroup";
	public static final String URL_GROUPPAGE="querysGroupByPageParam";

	
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
