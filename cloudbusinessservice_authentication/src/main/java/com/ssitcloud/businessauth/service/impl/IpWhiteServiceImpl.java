package com.ssitcloud.businessauth.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssitcloud.businessauth.common.service.impl.BasicServiceImpl;
import com.ssitcloud.businessauth.service.IpWhiteService;
import com.ssitcloud.businessauth.utils.HttpClientUtil;

/**
 * <p>2016年4月22日 下午4:09:04
 * @author hjc
 */
@Service
public class IpWhiteServiceImpl extends BasicServiceImpl implements IpWhiteService {
	private static final String CHARSET = "UTF-8";
	private static final String ADD_IPWHITE = "addIpWhite";
	private static final String SEL_IPWHITE_BY_IDX = "selIpWhiteByIdx";
	private static final String DEL_IPWHITE_BY_OPERIDX = "delIpWhiteByOperIdx";
	private static final String UPD_IPWHITE = "updIpWhite";

	@Override
	public String addIpWhite(Map<String, String> param) {
		String response = HttpClientUtil.doPost(requestURL.getRequestURL(ADD_IPWHITE), param, CHARSET);
		return response;
	}

	@Override
	public String selIpWhiteByIdx(Map<String, String> param) {
		String response = HttpClientUtil.doPost(requestURL.getRequestURL(SEL_IPWHITE_BY_IDX), param, CHARSET);
		return response;
	}

	@Override
	public String delIpWhiteByOperIdx(Map<String, String> param) {
		String response = HttpClientUtil.doPost(requestURL.getRequestURL(DEL_IPWHITE_BY_OPERIDX), param, CHARSET);
		return response;
	}

	@Override
	public String updIpWhite(Map<String, String> param) {
		String response = HttpClientUtil.doPost(requestURL.getRequestURL(UPD_IPWHITE), param, CHARSET);
		return response;
	}

	
}
