package com.ssitcloud.business.nodemgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface ContainerBusinessService {
	ResultEntity queryContainerByPage(String req);

	ResultEntity queryContainerByParam(String req);

	ResultEntity queryContainerById(String req);

	ResultEntity deleteContainerById(String req);

	ResultEntity updateContainer(String req);

	ResultEntity addContainer(String req);

	ResultEntity start(String req);

	ResultEntity stop(String req);

}
