package com.ssitcloud.nodemgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface NodeMonitorViewService {
	ResultEntity queryNodeMonitorByPage(String req);

	ResultEntity queryNodeMonitorByParam(String req);

	ResultEntity queryNodeMonitorById(String req);

	ResultEntity getTypeList(String req);

}
