package com.ssitcloud.view.statistics.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.statistics.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.statistics.service.MainPageService;

@Service
public class MainPageServiceImpl extends BasicServiceImpl implements MainPageService {

	@Override
	public ResultEntity countCardissueLog(String req) {
		return postUrl("countCardissueLog",req);
	}

	@Override
	public ResultEntity countLoanLog(String req) {
		return postUrl("countLoanLog",req);
	}

	@Override
	public ResultEntity countFinanceLog(String req) {
		return postUrl("countFinanceLog",req);
	}

	@Override
	public ResultEntity countVisitorLog(String req) {
		return postUrl("countVisitorLog",req);
	}

}
