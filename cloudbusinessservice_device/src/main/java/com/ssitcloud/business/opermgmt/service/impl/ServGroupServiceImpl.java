package com.ssitcloud.business.opermgmt.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.opermgmt.service.ServGroupService;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class ServGroupServiceImpl extends BasicServiceImpl implements ServGroupService{

	private static final String URL_addservgroup = "addservgroup";
	private static final String URL_delservgroup = "delservgroup";
	private static final String URL_updservgroup = "updservgroup";

	@Override
	public ResultEntity addservgroup(String req) {
		return postUrl(URL_addservgroup, req);
	}

	@Override
	public ResultEntity delservgroup(String req) {
		return postUrl(URL_delservgroup, req);
	}

	@Override
	public ResultEntity updservgroup(String req) {
		return postUrl(URL_updservgroup, req);
	}

}
