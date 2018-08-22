package com.ssitcloud.view.devmgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.common.util.HttpClientUtil;
import com.ssitcloud.view.devmgmt.service.DeviceExtLogicService;

/** 
 *
 * <p>2016年5月18日 下午6:47:51  
 * @author hjc 
 *
 */
@Service
public class DeviceExtLogicServiceImpl extends BasicServiceImpl implements DeviceExtLogicService{
	private static final String SEL_ALL_METADATA_DEVICETYPE = "SelAllMetadataDeviceType";
	private static final String SEL_EXTTEMP_LIST_BY_PARAM = "SelExtTempListByParam";
	private static final String SEL_ALL_EXTMODEL_AND_LOGICOBJ = "selAllExtModelAndLogicObj";
	private static final String URL_addExtTemp = "addExtTemp";
	private static final String URL_updateExtTemp = "updateExtTemp";
	private static final String URL_delExtTemp = "delExtTemp";
	private static final String URL_delMultiExtTemp = "delMultiExtTemp";

	
	@Override
	public String SelAllMetadataDeviceType(String json) {
		String reqURL=requestURL.getRequestURL(SEL_ALL_METADATA_DEVICETYPE);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	
	@Override
	public String selExtTempListByParam(Map<String, String> map) {
		String reqURL=requestURL.getRequestURL(SEL_EXTTEMP_LIST_BY_PARAM);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}

	@Override
	public String selExtTempListByParam(String json) {
		String reqURL=requestURL.getRequestURL(SEL_ALL_EXTMODEL_AND_LOGICOBJ);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}

	@Override
	public ResultEntity addExtTemp(String req) {
		return postUrl(URL_addExtTemp, req);
	}

	@Override
	public ResultEntity updateExtTemp(String req) {
		return postUrl(URL_updateExtTemp, req);
	}

	@Override
	public ResultEntity delExtTemp(String req) {
		return postUrl(URL_delExtTemp, req);
	}
	@Override
	public ResultEntity delMultiExtTemp(String req) {
		return postUrl(URL_delMultiExtTemp, req);
	}
	
	
	
	

}
