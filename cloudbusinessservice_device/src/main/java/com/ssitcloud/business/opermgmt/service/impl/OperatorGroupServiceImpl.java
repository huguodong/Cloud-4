package com.ssitcloud.business.opermgmt.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.opermgmt.service.OperatorGroupService;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class OperatorGroupServiceImpl extends BasicServiceImpl implements OperatorGroupService{

	public static final String URL_queryOperGroupByparam="queryOperGroupByparam";
	private static final String URL_addOperGroup = "addOperGroup";
	private static final String URL_delOperGroup = "delOperGroup";
	private static final String URL_updOperGroup = "updOperGroup";
	private static final String URL_queryAllServiceGroup = "queryAllServiceGroup";
	private static final String URL_queryLibraryServiceGroup = "queryLibraryServiceGroup";
	private static final String URL_queryOperatorGroupByOperIdx = "queryOperatorGroupByOperIdx";
	private static final String URL_updateOperatorGroup = "updateOperatorGroup";
	
	@Override
	public ResultEntity queryOperGroupByparam(String req) {
		return postUrl(URL_queryOperGroupByparam, req);
	}

	@Override
	public ResultEntity addOperGroup(String req) {
		return postUrl(URL_addOperGroup, req);
	}

	@Override
	public ResultEntity delOperGroup(String req) {
		return postUrl(URL_delOperGroup, req);
	}

	@Override
	public ResultEntity updOperGroup(String req) {
		return postUrl(URL_updOperGroup, req);
	}
	
	@Override
	public ResultEntity queryAllServiceGroup(String req) {
		return postUrl(URL_queryAllServiceGroup, req);
	}
	
	@Override
	public ResultEntity queryLibraryServiceGroup(String req) {
		return postUrl(URL_queryLibraryServiceGroup, req);
	}
	
	@Override
	public ResultEntity queryOperatorGroupByOperIdx(String req) {
		return postUrl(URL_queryOperatorGroupByOperIdx, req);
	}
	
	@Override
	public ResultEntity updateOperatorGroup(String req) {
		return postUrl(URL_updateOperatorGroup, req);
	}
	

}
