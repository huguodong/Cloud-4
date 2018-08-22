package com.ssitcloud.common.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.service.SyncContainerService;
@Service
public class SyncContainerServiceImpl extends BasicServiceImpl implements SyncContainerService{

	private static final String URL_QueryContainerInfo = "QueryContainerInfo";

	@Override
	public ResultEntity QueryContainerInfo(String req) {
		return postUrl(URL_QueryContainerInfo, req);
	}

}
