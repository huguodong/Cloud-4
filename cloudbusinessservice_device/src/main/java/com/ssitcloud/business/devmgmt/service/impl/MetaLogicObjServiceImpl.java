package com.ssitcloud.business.devmgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.devmgmt.service.MetaLogicObjService;

@Service
public class MetaLogicObjServiceImpl extends BasicServiceImpl implements MetaLogicObjService{
	
	private static final String SELECT_METADATA_LOGICOBJ="SelectMetadataLogicObj";
	
	@Override
	public String SelectMetadataLogicObj(String json) {
		String url=requestURL.getRequestURL(SELECT_METADATA_LOGICOBJ);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		return  HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
	}
	
	
}
