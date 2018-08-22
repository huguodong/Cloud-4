package com.ssitcloud.dblib.service;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.dblib.entity.ShelfGroupEntity;

public interface ShelfGroupService {

	public abstract PageEntity queryAllShelfGroup(Map<String, String> map);
	
	public abstract int updateShelfGroup(ShelfGroupEntity shelfGroupEntity);
	
	public abstract int deleteShelfGroup(ShelfGroupEntity shelfGroupEntity);
	
	public abstract int addShelfGroup(ShelfGroupEntity shelfGroupEntity);
}
