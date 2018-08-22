package com.ssitcloud.businessauth.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.businessauth.common.service.impl.BasicServiceImpl;
import com.ssitcloud.businessauth.service.SoxTemplateService;
import com.ssitcloud.common.entity.ResultEntity;

/**
 * <p>2016年4月15日 下午1:33:02
 * @author hjc
 */
@Service
public class SoxTemplateServiceImpl extends BasicServiceImpl implements SoxTemplateService{
	private static final String URL_queryAllSoxTemp = "queryAllSoxTemp";
	private static final String URL_getSoxTempListByParam = "getSoxTempListByParam";
	private static final String URL_addSoxTemp = "addSoxTemp";
	private static final String URL_updateSoxTemp = "updateSoxTemp";
	private static final String URL_delSoxTemp = "delSoxTemp";
	private static final String URL_delMultiSoxTemp = "delMultiSoxTemp";

	@Override
	public ResultEntity queryAllSoxTemp(String req) {
		return postURL(URL_queryAllSoxTemp, req);
	}

	@Override
	public ResultEntity getSoxTempListByParam(String req) {
		return postURL(URL_getSoxTempListByParam, req);
	}
	
	@Override
	public ResultEntity addSoxTemp(String req) {
		return postURL(URL_addSoxTemp, req);
	}

	@Override
	public ResultEntity updateSoxTemp(String req) {
		return postURL(URL_updateSoxTemp, req);
	}
	
	@Override
	public ResultEntity delSoxTemp(String req) {
		return postURL(URL_delSoxTemp, req);
	}

	@Override
	public ResultEntity delMultiSoxTemp(String req) {
		return postURL(URL_delMultiSoxTemp, req);
	}
	
}
