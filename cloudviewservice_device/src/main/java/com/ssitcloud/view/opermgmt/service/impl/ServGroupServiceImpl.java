package com.ssitcloud.view.opermgmt.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.opermgmt.service.ServGroupService;
@Service
public class ServGroupServiceImpl extends BasicServiceImpl implements ServGroupService {

	public static final String URL_selectCmdType="selectCmdType";
	
	public static final String URL_selectCardCmdType="selectCardCmdType";
	
	public static final String URL_selectDeviceOperLogCmd="selectDeviceOperLogCmd";
	
	private static final String URL_queryServgroupByparam = "queryServgroupByparam";

	private static final String URL_addservgroup = "addservgroup";

	private static final String URL_delservgroup = "delservgroup";

	private static final String URL_updservgroup = "updservgroup";

	private static final String URL_upadtePermessionCacheForAllDevice = "upadtePermessionCacheForAllDevice";

	
	@Override
	public ResultEntity selectCmdType() {
		return postUrl(URL_selectCmdType, "");
	}
	
	@Override
	public ResultEntity selectCardCmdType() {
		return postUrl(URL_selectCardCmdType, "");
	}
	
	@Override
	public ResultEntity selectDeviceOperLogCmd() {
		return postUrl(URL_selectDeviceOperLogCmd, "");
	}


	@Override
	public ResultEntity queryServgroupByparam(String req) {
		return postUrl(URL_queryServgroupByparam,req);
	}

	@Override
	public ResultEntity addservgroup(String req) {
		return postUrl(URL_addservgroup,req);
	}

	@Override
	public ResultEntity delservgroup(String req) {
		return postUrl(URL_delservgroup,req);
	}

	@Override
	public ResultEntity updservgroup(String req) {
		return postUrl(URL_updservgroup,req);
	}

	@Override
	public ResultEntity upadtePermessionCacheForAllDevice() {
		return postUrl(URL_upadtePermessionCacheForAllDevice, null);
	}


}
