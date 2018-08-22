package com.ssitcloud.business.common.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.OperationLogService;
import com.ssitcloud.business.common.util.HttpClientUtil;

@Service
public class OperationLogServiceImpl extends BasicServiceImpl implements
		OperationLogService {
	
	public static final String ADD_Operationlog = "AddOperationLog";
	@Override
	public String addOperationlog(String operlog) {
		String reqURL=requestURL.getRequestURL(ADD_Operationlog);
		Map<String,String> map=new HashMap<>();
		map.put("json", operlog);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		
		return result;
	}
}
