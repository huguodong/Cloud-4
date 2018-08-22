package com.ssitcloud.businessauth.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.businessauth.common.service.impl.BasicServiceImpl;
import com.ssitcloud.businessauth.service.LibraryTemplateService;
import com.ssitcloud.businessauth.utils.HttpClientUtil;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class LibraryTemplateServiceImpl extends BasicServiceImpl implements LibraryTemplateService {

	
	private static final String ADD_LIBRARYtemp = "addLibraryTemplate";
	private static final String DEL_LIBRARYtemp = "delLibraryTemplateById";
	private static final String SEL_LIBRARYtemp = "selLibraryTempList";
	private static final String SEL_AllLIBRARYtemp = "selAllLibTemp";
	private static final String UPD_LIBRARYtemp = "updLibraryTemplate";
	private static final String SEL_LIBRARYTEMP_AND_DEVICEIDS = "selLibraryTempAndDeviceIds";
	private static final String URL_selLibraryServiceTemplateByIdx = "selLibraryServiceTemplateByIdx";
	
	
	@Override
	public String selectlibtemp(Map<String, String> map) {
		String reqURL= requestURL.getRequestURL(SEL_LIBRARYtemp);
		//Map<String, String > map = new HashMap<>();
		//map.put("libTempInfo", info);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result ;
	}

	@Override
	public ResultEntity deletelibtemp(String req) {
		return postURL(DEL_LIBRARYtemp, req);
	}

	@Override
	public ResultEntity updatelibtemp(String req) {
		return postURL(UPD_LIBRARYtemp, req);
	}

	@Override
	public String addlibtemp(String info) {
		String reqURL= requestURL.getRequestURL(ADD_LIBRARYtemp);
		Map<String, String > map = new HashMap<>();
		map.put("json", info);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result ;
	}

	@Override
	public String selectAllLibtemp(Map<String, String> map) {
		String reqURL= requestURL.getRequestURL(SEL_AllLIBRARYtemp);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result ;
	}

	@Override
	public String selDeviceUserByLibraryIdx(String json) {
		String reqURL= requestURL.getRequestURL(SEL_LIBRARYTEMP_AND_DEVICEIDS);
		Map<String, String > map = new HashMap<>();
		map.put("json", json);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result ;
	}

	@Override
	public ResultEntity selLibraryServiceTemplateByIdx(String req) {
		return postURL(URL_selLibraryServiceTemplateByIdx, req);
	}

	
	
}
