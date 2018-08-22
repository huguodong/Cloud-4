package com.ssitcloud.view.devmgmt.service.impl;

import java.util.Map;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.common.util.HttpClientUtil;
import com.ssitcloud.view.devmgmt.service.DeviceRunTempService;

/** 
 *
 * <p>2016年5月18日 下午6:47:51  
 * @author hjc 
 *
 */
@Service
public class DeviceRunTempServiceImpl extends BasicServiceImpl implements DeviceRunTempService{
	private static final String URL_selRunTempListByParam = "selRunTempListByParam";
	private static final String URL_addRunTemp = "addRunTemp";
	private static final String URL_updateRunTemp = "updateRunTemp";
	private static final String URL_delRunTemp = "delRunTemp";
	private static final String URL_delMultiRunTemp = "delMultiRunTemp";

	
//	@Override
//	public String SelAllMetadataDeviceType(String json) {
//		String reqURL=requestURL.getRquestURL(SEL_ALL_METADATA_DEVICETYPE);
//		Map<String,String> map=new HashMap<>();
//		map.put("json", json);
//		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
//		return result;
//	}
//	
	@Override
	public String selRunTempListByParam(Map<String, String> map) {
		String reqURL=requestURL.getRequestURL(URL_selRunTempListByParam);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
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
