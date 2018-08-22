package com.ssitcloud.business.opermgmt.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.opermgmt.service.OperatorService;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class OperatorServiceImpl extends BasicServiceImpl implements OperatorService{

	public static final String URL_queryOperatorByParam="queryOperatorByParam";
	private static final String URL_addOperator = "addOperator";
	private static final String URL_delOperator = "delOperator";
	private static final String URL_delMultiOperator = "delMultiOperator";
	private static final String URL_updOperator = "updOperator";
	private static final String URL_queryOperatorDetailByIdx = "queryOperatorDetailByIdx";
	private static final String URL_queryAllSoxTemp = "queryAllSoxTemp";
	private static final String URL_queryOperatorTypes = "queryOperatorTypes";
	private static final String URL_updateOperator = "updateOperator";
	private static final String URL_queryAllOperatorInfo = "queryAllOperatorInfo";
	private static final String URL_resetPassword = "resetPassword";
	private static final String URL_changePassword = "changePassword";
	private static final String URL_checkPwdFormat = "checkPwdFormat";
	private static final String URL_selOperatorByIdOrIdx = "selOperatorByIdOrIdx";

	@Override
	public ResultEntity queryOperatorByParam(String req) {
		return postUrl(URL_queryOperatorByParam, req);
	}
	@Override
	public ResultEntity addOperator(String req) {
		return postUrl(URL_addOperator, req);
	}
	@Override
	public ResultEntity delOperator(String req) {
		return postUrl(URL_delOperator, req);
	}
	public ResultEntity delMultiOperator(String req) {
		return postUrl(URL_delMultiOperator, req);
	}
	@Override
	public ResultEntity updOperator(String req) {
		return postUrl(URL_updOperator, req);
	}
	@Override
	public ResultEntity queryOperatorDetailByIdx(String req) {
		return postUrl(URL_queryOperatorDetailByIdx, req);
	}
	@Override
	public ResultEntity queryAllSoxTemp(String req) {
		return postUrl(URL_queryAllSoxTemp, req);
	}
	@Override
	public ResultEntity queryOperatorTypes(String req) {
		return postUrl(URL_queryOperatorTypes, req);
	}
	@Override
	public ResultEntity updateOperator(String req) {
		return postUrl(URL_updateOperator, req);
	}
	
	@Override
	public ResultEntity queryAllOperatorInfo(String req) {
		return postUrl(URL_queryAllOperatorInfo, req);
	}
	
	@Override
	public ResultEntity resetPassword(String req) {
		return postUrl(URL_resetPassword, req);
	}
	
	
	@Override
	public ResultEntity changePassword(String req) {
		return postUrl(URL_changePassword, req);
	}
	
	@Override
	public ResultEntity checkPwdFormat(String req) {
		return postUrl(URL_checkPwdFormat, req);
	}
	@Override
	public ResultEntity selOperatorByOperIdOrIdx(String req) {
		
		return postUrl(URL_selOperatorByIdOrIdx, req);
	}
	
	


}
