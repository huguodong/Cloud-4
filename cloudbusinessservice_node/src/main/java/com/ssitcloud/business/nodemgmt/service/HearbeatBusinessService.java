package com.ssitcloud.business.nodemgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface HearbeatBusinessService {
	public String saveData(String req);
	public ResultEntity getDeadNodes(String req);
}
