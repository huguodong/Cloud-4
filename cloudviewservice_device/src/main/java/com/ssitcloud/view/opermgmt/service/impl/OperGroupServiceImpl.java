package com.ssitcloud.view.opermgmt.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.opermgmt.service.OperGroupService;

@Service
public class OperGroupServiceImpl extends BasicServiceImpl implements OperGroupService{

	public static final String URL_queryOperGroupByparam="queryOperGroupByparam";
	private static final String URL_queryServiceGroupAndCmd = "queryServiceGroupAndCmd";
	private static final String URL_addOperGroup = "addOperGroup";
	private static final String URL_delOperGroup = "delOperGroup";
	private static final String URL_updOperGroup = "updOperGroup";
	private static final String URL_queryOperatorNameByoperIdxArr = "queryOperatorNameByoperIdxArr";
	private static final String URL_queryAllServiceGroup = "queryAllServiceGroup";
	private static final String URL_queryLibraryServiceGroup = "queryLibraryServiceGroup";
	
	
	@Override
	public ResultEntity queryOperGroupByparam_view(String req) {
		 return postUrl(URL_queryOperGroupByparam, req);
	}
	@Override
	public ResultEntity queryServiceGroupAndCmd(String req) {
		return postUrl(URL_queryServiceGroupAndCmd, req);
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
	public ResultEntity queryOperatorNameByoperIdxArr(String req) {
		
		return postUrl(URL_queryOperatorNameByoperIdxArr, req);
	}
	@Override
	public ResultEntity queryAllServiceGroup(String req) {
		return postUrl(URL_queryAllServiceGroup, req);
	}
	
	@Override
	public ResultEntity queryLibraryServiceGroup(String req) {
		return postUrl(URL_queryLibraryServiceGroup, req);
	}
	
	
}
