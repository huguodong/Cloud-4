package com.ssitcloud.businessauth.service.impl;

import java.util.Map;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.businessauth.common.service.impl.BasicServiceImpl;
import com.ssitcloud.businessauth.service.MetadataInfotypeService;
import com.ssitcloud.businessauth.utils.HttpClientUtil;
/**
 * 图书馆信息元数据表Service
 * @comment 
 * @date 2016年5月26日
 * @author hwl
 */
@Service
public class MetadataInfotypeServiceImpl extends BasicServiceImpl implements
		MetadataInfotypeService {
	public static final String	URL_GETLIBALLTYPE = "selLibInfotype";
	
	@Override
	public String getLibInfotype(Map<String, String> map) {
		String reqURL = requestURL.getRequestURL(URL_GETLIBALLTYPE);
		return HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
	}
	
}
