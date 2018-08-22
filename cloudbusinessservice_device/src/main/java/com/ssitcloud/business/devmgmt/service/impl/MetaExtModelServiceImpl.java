package com.ssitcloud.business.devmgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.devmgmt.service.MetaExtModelService;

/** 
 *
 * <p>2016年5月11日 上午9:47:01  
 * @author hjc 
 *
 */
@Service
public class MetaExtModelServiceImpl extends BasicServiceImpl implements MetaExtModelService {
	
	private static final String SELECT_METADATA_EXTMODEL="SelMetadataExtModel";
	
	@Override
	public String SelectMetaExtModel(String json) {
		String url=requestURL.getRequestURL(SELECT_METADATA_EXTMODEL);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		return  HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
	}

	
	
}
