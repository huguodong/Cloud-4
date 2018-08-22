package com.ssitcloud.business.devmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface DeviceConfigService {

	ResultEntity queryDeviceConfigByDeviceIdx(String req);

	ResultEntity queryDeviceConfigNewAndOldByDeviceIdx(String req);

	ResultEntity queryMongoInstances(String req);

}
