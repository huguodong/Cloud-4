package com.ssitcloud.view.database.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.database.service.ServerService;

@Service
public class ServerServiceImpl extends BasicServiceImpl implements ServerService {

	private static final String URL_getUserMenus="getUserMenus";
	private static final String URL_getServerConfig="getServerConfig";
	private static final String URL_addDBConnect="addDBConnect";
	private static final String URL_removeDBConnect="removeDBConnect";

	@Override
	public ResultEntity getUserMenus() {
		return postUrl(URL_getUserMenus, null);
	}
	
	@Override
	public ResultEntity getServerConfig() {
		return postUrl(URL_getServerConfig, null);
	}

	@Override
	public ResultEntity addDBConnect(String req) {
		return postUrl(URL_addDBConnect, req);
	}

	@Override
	public ResultEntity removeDBConnect(String req) {
		return postUrl(URL_removeDBConnect, req);
	}
}
