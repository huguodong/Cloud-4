package com.ssitcloud.businessauth.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.businessauth.common.service.impl.BasicServiceImpl;
import com.ssitcloud.businessauth.service.OperatorService;
import com.ssitcloud.businessauth.utils.HttpClientUtil;
import com.ssitcloud.businessauth.utils.JsonUtils;
import com.ssitcloud.common.entity.ResultEntity;

/**
 * <p>2016年4月15日 下午1:33:02
 * @author hjc
 */
@Service
public class OperatorServiceImpl extends BasicServiceImpl implements OperatorService{
	private static final String CHARSET = "UTF-8";
	private static final String GETVAILDTIME = "getVaildTime";
	private static final String LOGINCHECK = "loginCheck";
	private static final String ADDOPERATOR = "addOperator";
	private static final String DELOPERATORBYIDX = "delOperatorByIdx";
	private static final String UPDOPERATORBYIDX = "updOperatorByIdx";
	private static final String ADD_DEVICE = "addDevice";
	private static final String URL_selOperatorByOperIdOrIdx = "selOperatorByOperIdOrIdx";
	private static final String URL_queryOperatorNameByoperIdxArr = "queryOperatorNameByoperIdxArr";
	private static final String URL_queryOperatorByParam="queryOperatorByParam";
	private static final String URL_addOperator = "addOperator";
	private static final String URL_delOperator = "delOperator";
	private static final String URL_delMultiOperator = "delMultiOperator";
	private static final String URL_updOperator = "updOperator";
	private static final String URL_queryOperatorDetailByIdx = "queryOperatorDetailByIdx";
	private static final String URL_updateOperator = "updateOperator";
	private static final String URL_queryOperatorTypes = "queryOperatorTypes";
	private static final String URL_queryAllOperatorInfo = "queryAllOperatorInfo";
	private static final String URL_deleteDevOperatorInfoByOperIds = "deleteDevOperatorInfoByOperIds";
	private static final String URL_resetPassword = "resetPassword";
	private static final String URL_changePassword = "changePassword";
	private static final String URL_queryDeviceIps = "queryDeviceIps";
	private static final String URL_authTransferToLibrary = "authTransferToLibrary";
	private static final String URL_devTransferToLibraryLog = "devTransferToLibraryLog";
	private static final String URL_checkPwdFormat = "checkPwdFormat";
	
	


	
	//private static final String ADD_OPERATIONLOG = "AddOperationLog";
	@Override
	public String getVaildTime(Map<String, String> param) {
		String response = HttpClientUtil.doPost(requestURL.getRequestURL(GETVAILDTIME), param, CHARSET);
		return response;
	}

	@Override
	public String loginCheck(Map<String, String> param) {
		String response = HttpClientUtil.doPost(requestURL.getRequestURL(LOGINCHECK), param, CHARSET);
		return response;
	}

	@Override
	public String addOperator(Map<String, String> param) {
		String response = HttpClientUtil.doPost(requestURL.getRequestURL(ADDOPERATOR), param, CHARSET);
		return response;
	}

	@Override
	public String delOperatorByIdx(Map<String, String> param) {
		String response = HttpClientUtil.doPost(requestURL.getRequestURL(DELOPERATORBYIDX), param, CHARSET);
		return response;
	}

	@Override
	public String updOperatorByIdx(Map<String, String> param) {
		String response = HttpClientUtil.doPost(requestURL.getRequestURL(UPDOPERATORBYIDX), param, CHARSET);
		return response;
	}

	@Override
	public String addDevice(String json) {
		String reqURL=requestURL.getRequestURL(ADD_DEVICE);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}

	@Override
	public ResultEntity selOperatorByOperIdOrIdx(String json) {
		ResultEntity resultEntity=new ResultEntity();
		String reqURL=requestURL.getRequestURL(URL_selOperatorByOperIdOrIdx);
		Map<String,String> map=new HashMap<>();
		map.put("operInfo", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		if(result!=null){
			resultEntity=JsonUtils.fromJson(result, ResultEntity.class);
		}
		return resultEntity;
	}


	@Override
	public ResultEntity queryOperatorNameByoperIdxArr(String req) {
		// TODO Auto-generated method stub
		return postURL(URL_queryOperatorNameByoperIdxArr, req);
	}

	/*@Override
	public String addOperationLog(String json) {
		String reqURL=requestURL.getRequestURL(ADD_OPERATIONLOG);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}*/
	
	
	@Override
	public ResultEntity queryOperatorByParam(String req) {
		 return postURL(URL_queryOperatorByParam, req);
	}
	public ResultEntity addOperator(String req) {
		return postURL(URL_addOperator, req);
	}
	@Override
	public ResultEntity delOperator(String req) {
		return postURL(URL_delOperator, req);
	}
	@Override
	public ResultEntity delMultiOperator(String req) {
		return postURL(URL_delMultiOperator, req);
	}
	@Override
	public ResultEntity updOperator(String req) {
		return postURL(URL_updOperator, req);
	}
	@Override
	public ResultEntity queryOperatorDetailByIdx(String req) {
		return postURL(URL_queryOperatorDetailByIdx, req);
	}
	@Override
	public ResultEntity updateOperator(String req) {
		return postURL(URL_updateOperator, req);
	}
	
	@Override
	public ResultEntity queryOperatorTypes(String req) {
		return postURL(URL_queryOperatorTypes, req);
	}
	
	@Override
	public ResultEntity queryAllOperatorInfo(String req) {
		return postURL(URL_queryAllOperatorInfo, req);
	}

	@Override
	public ResultEntity deleteDevOperatorInfoByOperIds(String req) {
		return postURL(URL_deleteDevOperatorInfoByOperIds, req);
	}
	
	@Override
	public ResultEntity resetPassword(String req) {
		return postURL(URL_resetPassword, req);
	}
	

	@Override
	public ResultEntity changePassword(String req) {
		return postURL(URL_changePassword, req);
	}
	@Override
	public ResultEntity queryDeviceIps(String req) {
		return postURL(URL_queryDeviceIps, req);
	}
	@Override
	public ResultEntity authTransferToLibrary(String req) {
		return postURL(URL_authTransferToLibrary, req);
	}

	@Override
	public ResultEntity devTransferToLibraryLog(String req) {
		return postURL(URL_devTransferToLibraryLog, req);
	}
	
	@Override
	public ResultEntity checkPwdFormat(String req) {
		return postURL(URL_checkPwdFormat, req);
	}
	
}
