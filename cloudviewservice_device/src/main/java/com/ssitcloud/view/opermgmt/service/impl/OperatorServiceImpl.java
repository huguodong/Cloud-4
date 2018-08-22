package com.ssitcloud.view.opermgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Consts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.common.util.HttpClientUtil;
import com.ssitcloud.view.opermgmt.service.OperatorService;

@Service
public class OperatorServiceImpl extends BasicServiceImpl implements OperatorService{

	private static final String URL_queryOperatorByParam="queryOperatorByParam";
	private static final String URL_addOperator = "addOperator";
	private static final String URL_delOperator = "delOperator";
	private static final String URL_delMultiOperator = "delMultiOperator";
	private static final String URL_queryOperatorDetailByIdx = "queryOperatorDetailByIdx";
	private static final String URL_queryAllSoxTemp = "queryAllSoxTemp";
	private static final String URL_queryOperatorTypes = "queryOperatorTypes";
	private static final String URL_selLibraryByIdxOrId = "selLibraryByIdxOrId";
	private static final String URL_queryOperatorGroupByOperIdx = "queryOperatorGroupByOperIdx";
	private static final String URL_queryOperatorMaintenanceCard = "queryOperatorMaintenanceCard";
	private static final String URL_updateOperator = "updateOperator";
	private static final String URL_queryAllOperatorInfo = "queryAllOperatorInfo";
	private static final String URL_queryMaintenanceCard = "queryMaintenanceCard";
	private static final String URL_updateOperatorGroup = "updateOperatorGroup";
	private static final String URL_updateOperatorMaintenanceCard = "updateOperatorMaintenanceCard";
	private static final String URL_resetPassword = "resetPassword";
	private static final String URL_changePassword = "changePassword";
	private static final String URL_checkPwdFormat = "checkPwdFormat";
	
	
	@Override
	public ResultEntity queryOperatorByParam(String req) {
		 return postUrl(URL_queryOperatorByParam, req);
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
	public String selLibraryByIdxOrId(String json) {
		String reqURL=requestURL.getRequestURL(URL_selLibraryByIdxOrId);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	
	@Override
	public ResultEntity queryOperatorGroupByOperIdx(String req) {
		return postUrl(URL_queryOperatorGroupByOperIdx, req);
	}
	
	@Override
	public ResultEntity queryOperatorMaintenanceCard(String req) {
		return postUrl(URL_queryOperatorMaintenanceCard, req);
	}
	
	@Override
	public ResultEntity updateOperator(String req) {
		return postUrl(URL_updateOperator, req);
	}
	
	@Override
	public ResultEntity updateOperatorGroup(String req) {
		return postUrl(URL_updateOperatorGroup, req);
	}
	
	@Override
	public ResultEntity updateOperatorMaintenanceCard(String req) {
		return postUrl(URL_updateOperatorMaintenanceCard, req);
	}
	
	@Override
	public ResultEntity addOperator(String req) {
		return postUrl(URL_addOperator, req);
	}
	
	@Override
	public ResultEntity delOperator(String req) {
		return postUrl(URL_delOperator, req);
	}
	@Override
	public ResultEntity delMultiOperator(String req) {
		return postUrl(URL_delMultiOperator, req);
	}
	
	@Override
	public ResultEntity queryAllOperatorInfo(String req) {
		return postUrl(URL_queryAllOperatorInfo, req);
	}
	
	@Override
	public ResultEntity queryMaintenanceCard(String req) {
		return postUrl(URL_queryMaintenanceCard, req);
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
	
	
	
	
}
