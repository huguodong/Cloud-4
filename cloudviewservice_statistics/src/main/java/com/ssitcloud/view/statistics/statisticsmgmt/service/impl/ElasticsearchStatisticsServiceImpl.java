package com.ssitcloud.view.statistics.statisticsmgmt.service.impl;


import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.statistics.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.statistics.statisticsmgmt.service.ElasticsearchStatisticsService;
@Service
public class ElasticsearchStatisticsServiceImpl extends BasicServiceImpl implements
		ElasticsearchStatisticsService {
	private static final String URL_STATISTICSELASTICSEARCH="statisticselasticsearch";
	private static final String URL_QUERYELASTICSEARCH="queryelasticsearch";
	private static final String URL_GETPACKETTREE="getpackettree";
	private static final String URL_EXPORTSELECT="exportSelect";
	private static final String URL_LIBARR="libArr";
	private static final String URL_DEVARR="devArr";
	@Override
	public ResultEntity statistics(String req) {
		return postUrl(URL_STATISTICSELASTICSEARCH, req);
	}

	@Override
	public ResultEntity query(String req) {
		return postUrl(URL_QUERYELASTICSEARCH, req);
	}

	@Override
	public ResultEntity gtree(String req) {
		return postUrl(URL_GETPACKETTREE, req);
	}

	@Override
	public ResultEntity exportSelect(String req) {
		return postUrl(URL_EXPORTSELECT, req);
	}

	@Override
	public ResultEntity libArr(String req) {
		return postUrl(URL_LIBARR, req);
	}

	@Override
	public ResultEntity devArr(String req) {
		return postUrl(URL_DEVARR, req);
	}
}
