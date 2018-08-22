package com.ssitcloud.business.shelfmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface ShelfGroupService {

	ResultEntity queryAllShelfGroup(String req);
	
	ResultEntity updateShelfGroup(String req);
	
	ResultEntity deleteShelfGroup(String req);
	
	ResultEntity addShelfGroup(String req);
}
