package com.ssitcloud.view.shelfmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface RelOperatorShelfGroupService {

	ResultEntity queryAllRelOperatorShelfGroup(String req);
	
	ResultEntity queryRelOperatorShelfGroupById(String req);
	
	ResultEntity updateRelOperatorShelfGroup(String req);
	
	ResultEntity deleteRelOperatorShelfGroup(String req);
	
	ResultEntity addRelOperatorShelfGroup(String req);
}
