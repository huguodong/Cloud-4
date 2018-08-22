package com.ssitcloud.business.mobile.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.mobile.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.mobile.service.DemoService;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class DemoServiceImpl extends BasicServiceImpl implements DemoService {
	private static final String URL_DEMO = "";

	@Override
	public ResultEntity demo() {
		ResultEntity resultEntity = new ResultEntity();
//		resultEntity = postURL(URL_DEMO, "");
		return resultEntity;
	}

	
	
}
