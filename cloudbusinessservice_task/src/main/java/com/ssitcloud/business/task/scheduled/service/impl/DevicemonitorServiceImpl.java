package com.ssitcloud.business.task.scheduled.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.task.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.task.scheduled.service.DevicemonitorService;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class DevicemonitorServiceImpl extends BasicServiceImpl implements DevicemonitorService {
	private static final String URL_QUERYLOANLOG = "selectLoanlog";
	private static final String URL_QUERYFINLOG = "selectFinlog";
	private static final String URL_QUERYCARDLOG = "selectCardlog";

	@Override
	public ResultEntity queryLoanLog(String req) {
		return postURL(URL_QUERYLOANLOG, req);
	}

	@Override
	public ResultEntity queryFinLog(String req) {
		return postURL(URL_QUERYFINLOG, req);
	}
	@Override
	public ResultEntity queryCardLog(String req) {
		return postURL(URL_QUERYCARDLOG, req);
	}

}
