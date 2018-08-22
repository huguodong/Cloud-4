package com.ssitcloud.shelfmgmt.service;

import java.util.List;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.shelfmgmt.entity.ShelfConfigEntity;

public interface ShelfConfigService {

	ResultEntity queryAllShelfConfig(String req);
	ResultEntity addShelfConfig(ShelfConfigEntity shelfConfigEntity);
	ResultEntity updateShelfConfig(ShelfConfigEntity shelfConfigEntity);
	ResultEntity deleteShelfConfig(List<ShelfConfigEntity> list);
}
