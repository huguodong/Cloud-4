package com.ssitcloud.shelfmgmt.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.shelfmgmt.entity.RelShelfConfigEntity;
import com.ssitcloud.shelfmgmt.entity.ShelfGroupEntity;

public interface ShelfGroupDao {
	public abstract PageEntity queryAllShelfGroup(Map<String, String> map);
	public abstract ShelfGroupEntity queryShelGroupfById(ShelfGroupEntity shelfGroupEntity);
	public abstract Integer addShelfGroup(ShelfGroupEntity shelfGroupEntity);
	public abstract Integer updateShelfGroup(ShelfGroupEntity shelfGroupEntity);
	public abstract int deleteShelfGroup(List<ShelfGroupEntity> list);
	public abstract RelShelfConfigEntity queryBookshelfRel(RelShelfConfigEntity relShelfConfig);
	public abstract int addBookshelfRel(RelShelfConfigEntity relShelfConfig);
	public abstract int updateBookshelf(RelShelfConfigEntity relShelfConfig);
}