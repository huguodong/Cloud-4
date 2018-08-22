package com.ssitcloud.auth.service;

import java.util.List;

import com.ssitcloud.auth.entity.AuthEntity;
import com.ssitcloud.auth.entity.page.AuthPageEntity;
import com.ssitcloud.common.entity.ResultEntity;

public interface AuthViewService {
	List<AuthEntity> queryAllAuth(String req);

	AuthPageEntity queryAuthByParam(String req);

	AuthEntity queryAuthByRandomCode(String req);

	ResultEntity updateAuth(String req);

	ResultEntity addAuth(String req);
	
	Boolean appServerResult(String req);
	
	String authenticateResult(String req);
	
	Boolean appServerResult_acs(String req);
	
	String authenticateResult_acs(String req);
	
	Boolean appServerResult_pay(String req);
	
	String authenticateResult_pay(String req);
}
