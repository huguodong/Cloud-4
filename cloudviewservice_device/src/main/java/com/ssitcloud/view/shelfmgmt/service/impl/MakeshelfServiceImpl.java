package com.ssitcloud.view.shelfmgmt.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.shelfmgmt.service.MakeshelfService;


@Service
public class MakeshelfServiceImpl extends BasicServiceImpl implements MakeshelfService {

	private static final String URL_queryAllMakeShelfRecord = "queryAllMakeShelfRecord";
	private static final String URL_makeshelf = "makeshelf";
	private static final String URL_deleteMakeShelfRecord = "deleteMakeShelfRecord";
	
	@Override
	public ResultEntity queryAllMakeShelfRecord(String req) {
		return postUrl(URL_queryAllMakeShelfRecord, req);
	}

	@Override
	public  ResultEntity makeshelf(String req){
		return postUrl(URL_makeshelf, req);
	}
	
	@Override
	public  ResultEntity deleteMakeShelfRecord(String req){
		return postUrl(URL_deleteMakeShelfRecord, req);
	}
}
