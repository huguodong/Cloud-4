package com.ssitcloud.business.nodemgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface NodeBusinessService {
	ResultEntity queryNodeByPage(String req);

	ResultEntity queryNodeByParam(String req);

	ResultEntity queryNodeById(String req);

	ResultEntity deleteNodeById(String req);

	ResultEntity updateNode(String req);

	ResultEntity addNode(String req);
	
	ResultEntity findAddressByNodeName(String req);

	ResultEntity findAddressForSSO(String req);

	ResultEntity queryNodeGroupByName();
}
