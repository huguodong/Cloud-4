package com.ssitcloud.view.devmgmt.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.devmgmt.service.DeviceConfigService;
@Service
public class DeviceConfigServiceImpl extends BasicServiceImpl implements DeviceConfigService{

	private static final String URL_queryDeviceConfigByDeviceIdx = "queryDeviceConfigByDeviceIdx";
	private static final String URL_queryMongoInstances = "queryMongoInstances";

	@Override
	public ResultEntity queryDeviceConfigByDeviceIdx(String req) {
		return postUrl(URL_queryDeviceConfigByDeviceIdx, req);
	}

	@Override
	public ResultEntity queryMongoInstances(String req) {
		return postUrl(URL_queryMongoInstances, req);
	}

}
