package com.ssitcloud.business.devmgmt.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.devmgmt.service.DeviceConfigService;
import com.ssitcloud.common.entity.ResultEntity;
@Service
public class DeviceConfigServiceImpl extends BasicServiceImpl implements DeviceConfigService{

	private static final String URL_queryDeviceConfigByDeviceIdx = "queryDeviceConfigByDeviceIdx";
	private static final String URL_queryDeviceConfigNewAndOldByDeviceIdx = "queryDeviceConfigNewAndOldByDeviceIdx";
	private static final String URL_queryMongoInstances = "queryMongoInstances";

	@Override
	public ResultEntity queryDeviceConfigByDeviceIdx(String req) {
		return postUrl(URL_queryDeviceConfigByDeviceIdx, req);
	}

	@Override
	public ResultEntity queryDeviceConfigNewAndOldByDeviceIdx(String req) {
		return postUrl(URL_queryDeviceConfigNewAndOldByDeviceIdx, req);
	}

	@Override
	public ResultEntity queryMongoInstances(String req) {
		return postUrl(URL_queryMongoInstances, req);
	}

}
