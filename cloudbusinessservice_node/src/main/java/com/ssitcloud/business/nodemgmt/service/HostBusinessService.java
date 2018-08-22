package com.ssitcloud.business.nodemgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface HostBusinessService {
	ResultEntity queryHostByPage(String req);

	ResultEntity queryHostByParam(String req);

	ResultEntity queryHostById(String req);

	ResultEntity deleteHostById(String req);

	ResultEntity updateHost(String req);

	ResultEntity addHost(String req);
}
