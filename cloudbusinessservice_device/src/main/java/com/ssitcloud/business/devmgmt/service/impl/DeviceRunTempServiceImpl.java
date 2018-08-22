package com.ssitcloud.business.devmgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.devmgmt.service.DeviceRunTempService;
import com.ssitcloud.common.entity.ResultEntity;

/** 
 *	
 * <p>2016年5月19日 下午1:39:32  
 * @author hjc 
 *
 */
@Service
public class DeviceRunTempServiceImpl extends BasicServiceImpl implements DeviceRunTempService {
	private static final String URL_SelRunTempListByParam = "SelRunTempListByParam";
	
	private static final String URL_addRunTemp = "addRunTemp";
	private static final String URL_updateRunTemp = "updateRunTemp";
	private static final String URL_delRunTemp = "delRunTemp";
	private static final String URL_delMultiRunTemp = "delMultiRunTemp";
	
	@Override
	public String SelRunTempListByParam(String json) {
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String reqURL = requestURL.getRequestURL(URL_SelRunTempListByParam);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());	
		return result;
	}
	
	@Override
	public ResultEntity addRunTemp(String req) {
		return postUrl(URL_addRunTemp, req);
	}

	@Override
	public ResultEntity updateRunTemp(String req) {
		return postUrl(URL_updateRunTemp, req);
	}
	
	@Override
	public ResultEntity delRunTemp(String req) {
		return postUrl(URL_delRunTemp, req);
	}
	
	@Override
	public ResultEntity delMultiRunTemp(String req) {
		return postUrl(URL_delMultiRunTemp, req);
	}
	
	
	

}
