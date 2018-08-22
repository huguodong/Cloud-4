package com.ssitcloud.business.devregister.service;

import com.ssitcloud.business.devregister.param.DeviceRegisterParam;

public interface FtpBusinessService {
	
	Boolean createFtpUser(DeviceRegisterParam param);
	Boolean delFtpUser(String req);
}
