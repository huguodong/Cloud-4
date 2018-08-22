package com.ssitcloud.view.opermgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface ServGroupService {

	ResultEntity selectCmdType();
	
	ResultEntity selectCardCmdType();
	
	ResultEntity selectDeviceOperLogCmd();

	ResultEntity queryServgroupByparam(String req);

	ResultEntity addservgroup(String req);

	ResultEntity delservgroup(String req);

	ResultEntity updservgroup(String req);

	ResultEntity upadtePermessionCacheForAllDevice();


}
