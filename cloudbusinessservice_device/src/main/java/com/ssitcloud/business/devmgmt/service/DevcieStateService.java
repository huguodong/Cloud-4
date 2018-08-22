package com.ssitcloud.business.devmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface DevcieStateService {

	ResultEntity selectDeviceState(String req);

	ResultEntity selectBookrackState(String req);

	ResultEntity selectBinState(String req);

	ResultEntity selectDeviceExtState(String req);

	ResultEntity selectSoftState(String req);

	ResultEntity getMongodbNames(String req);

}
