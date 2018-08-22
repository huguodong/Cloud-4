package com.ssitcloud.shelfmgmt.service;

import java.util.List;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.shelfmgmt.entity.ShelfGroupEntity;

public interface ShelfGroupService {

	ResultEntity queryAllShelfGroup(String req);
	ResultEntity queryshelfGroupById(String req);
	ResultEntity addShelfGroup(String req);
	ResultEntity updateShelfGroup(String req);
	ResultEntity deleteShelfGroup(List<ShelfGroupEntity> list);
}
