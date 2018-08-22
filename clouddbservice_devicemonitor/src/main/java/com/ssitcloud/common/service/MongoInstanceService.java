package com.ssitcloud.common.service;

import com.ssitcloud.common.entity.ResultEntityF;

public interface MongoInstanceService {

	ResultEntityF<Object> queryMongoInstances(String req);

	ResultEntityF<Object> updMongoInstances(String req);

}
