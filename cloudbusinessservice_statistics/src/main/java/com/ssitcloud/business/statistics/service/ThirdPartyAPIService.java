package com.ssitcloud.business.statistics.service;

import java.util.Map;

import net.sf.json.JSONObject;

public interface ThirdPartyAPIService {

	JSONObject borrowOrderInfo(Map<String, String> param);

	JSONObject borrowBackCountInfo(Map<String, String> param);

	JSONObject cameCountInfo(Map<String, String> param);

	JSONObject readerRank(Map<String, String> param);

	JSONObject countCertificate(Map<String, String> param);

	JSONObject countLoanForBar(Map<String, String> param);

	JSONObject countRealTimeVisitor(Map<String, String> param);
	
	JSONObject loadLibInfo(Map<String, String> param);
}
