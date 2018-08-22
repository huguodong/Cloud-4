package com.ssitcloud.business.nodemgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface NodeTypeBusinessService {
	ResultEntity queryNodeTypeByPage(String req);

	ResultEntity queryNodeTypeByParam(String req);

	ResultEntity queryNodeTypeById(String req);

	ResultEntity deleteNodeTypeById(String req);

	ResultEntity updateNodeType(String req);

	ResultEntity addNodeType(String req);
}
