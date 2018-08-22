package com.ssitcloud.dblib.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.dblib.entity.ShelfGroupEntity;

public interface ShelfGroupDao {

	public abstract PageEntity queryAllShelfGroup(Map<String, String> map);
	
	public abstract ShelfGroupEntity queryShelfGroupById(ShelfGroupEntity shelfGroupEntity);
	
	public abstract int updateShelfGroup(ShelfGroupEntity shelfGroupEntity);
	
	public abstract int deleteShelfGroup(ShelfGroupEntity shelfGroupEntity);
	
	public abstract int addShelfGroup(ShelfGroupEntity shelfGroupEntity);
}
