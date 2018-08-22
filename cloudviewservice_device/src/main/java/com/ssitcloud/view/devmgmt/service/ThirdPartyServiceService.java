package com.ssitcloud.view.devmgmt.service;

import java.util.Map;

import net.sf.json.JSONObject;

import com.ssitcloud.common.entity.ResultEntity;

public interface ThirdPartyServiceService {

	public ResultEntity queryThirdPartyServiceByParams(String req);

	public ResultEntity deleteThirdPartyService(String req);

	public ResultEntity queryThirdPartyServiceByPage(String req);

	public ResultEntity editThirdPartyService(String req);

	public JSONObject borrowOrderInfo(Map<String, String> param);

	public JSONObject borrowBackCountInfo(Map<String, String> param);

	public JSONObject cameCountInfo(Map<String, String> param);

	public JSONObject readerRank(Map<String, String> param);

	public JSONObject countCertificate(Map<String, String> param);

	public JSONObject countRealTimeVisitor(Map<String, String> param);

	public JSONObject countLoanForBar(Map<String, String> param);

	public JSONObject loadLibInfo(Map<String, String> param);

	public ResultEntity deleteDisplayInfo(String req);

	public ResultEntity editDisplayInfo(String req);

	public ResultEntity queryDisplayInfo(String req);

	public ResultEntity queryDisplayInfoList(String req);

	public ResultEntity getRecommendListForSMS(String req);
	
	public ResultEntity getRecommendList(String req);

	public ResultEntity getBookListByTopN(String req);

}
