package com.ssitcloud.business.opermgmt.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.opermgmt.service.SoxTempService;
import com.ssitcloud.common.entity.ResultEntity;

/** 
 *
 * <p>2016年6月21日 下午4:42:59  
 * @author hjc 
 *
 */
@Service
public class SoxTempServiceImpl extends BasicServiceImpl implements SoxTempService {
	private static final String URL_getSoxTempListByParam = "getSoxTempListByParam";
	private static final String URL_addSoxTemp = "addSoxTemp";
	private static final String URL_updateSoxTemp = "updateSoxTemp";
	private static final String URL_delSoxTemp = "delSoxTemp";
	private static final String URL_delMultiSoxTemp = "delMultiSoxTemp";

	@Override
	public ResultEntity getSoxTempListByParam(String req) {
		return postUrl(URL_getSoxTempListByParam, req);
	}

	@Override
	public ResultEntity addSoxTemp(String req) {
		return postUrl(URL_addSoxTemp, req);
	}

	@Override
	public ResultEntity updateSoxTemp(String req) {
		return postUrl(URL_updateSoxTemp, req);
	}
	
	@Override
	public ResultEntity delSoxTemp(String req) {
		return postUrl(URL_delSoxTemp, req);
	}

	@Override
	public ResultEntity delMultiSoxTemp(String req) {
		return postUrl(URL_delMultiSoxTemp, req);
	}
	
	
}
