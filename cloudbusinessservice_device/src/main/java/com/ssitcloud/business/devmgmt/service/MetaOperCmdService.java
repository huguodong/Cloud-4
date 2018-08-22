package com.ssitcloud.business.devmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface MetaOperCmdService {

	String SelMetaOperCmd();

	ResultEntity selectCmdType();
	ResultEntity selectCardCmdType();
	
	ResultEntity selectDeviceOperLogCmd();

	ResultEntity queryServgroupByparam(String req);

}
