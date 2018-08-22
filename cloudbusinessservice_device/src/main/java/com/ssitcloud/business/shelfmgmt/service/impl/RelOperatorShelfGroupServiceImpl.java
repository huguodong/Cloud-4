package com.ssitcloud.business.shelfmgmt.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.shelfmgmt.service.RelOperatorShelfGroupService;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class RelOperatorShelfGroupServiceImpl extends BasicServiceImpl implements
		RelOperatorShelfGroupService {

	private static final String URL_queryAllRelOperatorShelfGroup = "queryAllRelOperatorShelfGroup";
	private static final String URL_queryRelOperatorShelfGroupById = "queryRelOperatorShelfGroupById";
	private static final String URL_deleteRelOperatorShelfGroup = "deleteRelOperatorShelfGroup";
	private static final String URL_updateRelOperatorShelfGroup = "updateRelOperatorShelfGroup";
	private static final String URL_addRelOperatorShelfGroup = "addRelOperatorShelfGroup";
	
	@Override
	public ResultEntity queryAllRelOperatorShelfGroup(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_queryAllRelOperatorShelfGroup, req);
	}
	
	@Override
	public ResultEntity queryRelOperatorShelfGroupById(String req){
		return postUrl(URL_queryRelOperatorShelfGroupById, req);
	}
	
	@Override
	public ResultEntity updateRelOperatorShelfGroup(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_deleteRelOperatorShelfGroup, req);
	}

	@Override
	public ResultEntity deleteRelOperatorShelfGroup(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_updateRelOperatorShelfGroup, req);
	}

	@Override
	public ResultEntity addRelOperatorShelfGroup(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_addRelOperatorShelfGroup, req);
	}

}
