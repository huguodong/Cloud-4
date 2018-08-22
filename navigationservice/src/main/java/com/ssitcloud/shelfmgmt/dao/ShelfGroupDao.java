package com.ssitcloud.shelfmgmt.dao;

import java.util.Map;

import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.shelfmgmt.entity.ShelfGroupEntity;

public interface ShelfGroupDao {
	public abstract PageEntity queryAllShelfGroup(Map<String, String> map);
	public abstract ShelfGroupEntity queryShelGroupfById(ShelfGroupEntity shelfGroupEntity);
	public abstract Integer addShelfGroup(ShelfGroupEntity shelfGroupEntity);
	public abstract Integer updateShelfGroup(ShelfGroupEntity shelfGroupEntity);
	public abstract int deleteShelfGroup(ShelfGroupEntity shelfGroupEntity);
}