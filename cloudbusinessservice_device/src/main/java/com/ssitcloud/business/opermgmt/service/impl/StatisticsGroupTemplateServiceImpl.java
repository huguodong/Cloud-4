package com.ssitcloud.business.opermgmt.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.opermgmt.service.StatisticsGroupTemplateService;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class StatisticsGroupTemplateServiceImpl extends BasicServiceImpl implements
		StatisticsGroupTemplateService {
	public static final String URL_FINDSTATISTICSGROUPTEMPLATESERVICEBYID="findStatisticsGroupTemplateServiceById";
	public static final String URL_FINDSTATISTICSGROUPTEMPLATESERVICEALL="findStatisticsGroupTemplateServiceAll";

	@Override
	public ResultEntity findById(String req) {
		return postUrl(URL_FINDSTATISTICSGROUPTEMPLATESERVICEBYID, req);
	}

	@Override
	public ResultEntity findAll(String req) {
		return postUrl(URL_FINDSTATISTICSGROUPTEMPLATESERVICEALL, req);
	}

}
