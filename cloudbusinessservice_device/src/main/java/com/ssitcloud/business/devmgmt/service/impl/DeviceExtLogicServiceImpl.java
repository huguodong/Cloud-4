package com.ssitcloud.business.devmgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.devmgmt.service.DeviceExtLogicService;
import com.ssitcloud.common.entity.ResultEntity;

/** 
 *
 * <p>2016年5月19日 下午1:39:32  
 * @author hjc 
 *
 */
@Service
public class DeviceExtLogicServiceImpl extends BasicServiceImpl implements DeviceExtLogicService {
	private static final String SEL_EXTTEMPLIST_BY_PARAM = "SelExtTempListByParam";
	private static final String URL_addExtTemp = "addExtTemp";
	private static final String URL_updateExtTemp = "updateExtTemp";
	private static final String URL_delExtTemp = "delExtTemp";
	private static final String URL_delMultiExtTemp = "delMultiExtTemp";
	
	@Override
	public String SelExtTempListByParam(String json) {
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String reqURL = requestURL.getRequestURL(SEL_EXTTEMPLIST_BY_PARAM);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());	
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
