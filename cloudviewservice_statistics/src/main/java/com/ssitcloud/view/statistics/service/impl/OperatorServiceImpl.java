package com.ssitcloud.view.statistics.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.statistics.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.statistics.service.OperatorService;

@Service
public class OperatorServiceImpl extends BasicServiceImpl implements OperatorService{

	private static final String URL_changePassword = "changePassword";
	private static final String URL_checkPwdFormat = "checkPwdFormat";
	
	
	
	@Override
	public ResultEntity changePassword(String req) {
		return postUrl(URL_changePassword, req);
	}
	
	@Override
	public ResultEntity checkPwdFormat(String req) {
		return postUrl(URL_checkPwdFormat, req);
	}
	
	
	
	
}
