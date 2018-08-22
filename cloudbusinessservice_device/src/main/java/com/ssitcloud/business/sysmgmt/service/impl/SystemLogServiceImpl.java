package com.ssitcloud.business.sysmgmt.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.sysmgmt.service.SystemLogService;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class SystemLogServiceImpl extends BasicServiceImpl implements SystemLogService{

	private static final String URL_getOperationLogType = "getOperationLogType";

	@Override
	public ResultEntity getOperationLogType(String req) {
		return postUrl(URL_getOperationLogType, req);
	}

}
