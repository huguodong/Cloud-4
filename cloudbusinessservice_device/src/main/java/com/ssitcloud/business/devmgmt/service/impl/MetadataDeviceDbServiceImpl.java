package com.ssitcloud.business.devmgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.devmgmt.service.MetadataDeviceDbService;
@Service
public class MetadataDeviceDbServiceImpl extends BasicServiceImpl implements MetadataDeviceDbService{

	private static final String SELECT_METADATA_DEVICE_DB="SelectMetadataDeviceDb";
	
	private static final String SELECT_METADATA_DEVICEDBANDTABLEINFO = "SelectMetadataDeviceDbAndTableInfo";
	
	@Override
	public String SelectMetadataDeviceDb(String json) {
		String url=requestURL.getRequestURL(SELECT_METADATA_DEVICE_DB);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		return  HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
	}
	
	@Override
	public String SelectMetadataDeviceDbAndTableInfo(String req) {
		String url=requestURL.getRequestURL(SELECT_METADATA_DEVICEDBANDTABLEINFO);
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		return  HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
	}
	
}
