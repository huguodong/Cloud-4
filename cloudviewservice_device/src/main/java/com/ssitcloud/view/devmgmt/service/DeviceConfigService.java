package com.ssitcloud.view.devmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface DeviceConfigService {

	ResultEntity queryDeviceConfigByDeviceIdx(String req);

	ResultEntity queryMongoInstances(String req);

}
