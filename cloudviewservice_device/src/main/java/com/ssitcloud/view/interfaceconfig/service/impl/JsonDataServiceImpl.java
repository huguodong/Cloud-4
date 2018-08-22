package com.ssitcloud.view.interfaceconfig.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.interfaceconfig.service.JsonDataService;

@Service
public class JsonDataServiceImpl extends BasicServiceImpl implements JsonDataService {
	private static final String URL_getData = "getData";
	private static final String URL_saveData = "saveData";
	private static final String URL_getInitData = "getInitData";
	private static final String URL_getLibDevData = "getLibDevData";
	private static final String URL_getDevTypeData = "getDevTypeData";

	@Override
	public ResultEntity getDevTypeData(String req) {
		return postUrl(URL_getDevTypeData, req);
	}

	@Override
	public ResultEntity getLibDevData(String req) {
		return postUrl(URL_getLibDevData, req);
	}

	@Override
	public ResultEntity getData(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_getData, req);
	}

	@Override
	public ResultEntity saveData(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_saveData, req);
	}

	@Override
	public ResultEntity getInitData(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_getInitData, req);
	}

}
