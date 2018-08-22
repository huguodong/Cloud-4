package com.ssitcloud.shelfmgmt.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.shelfmgmt.entity.ShelfConfigEntity;

public interface ShelfConfigDao {
	public abstract PageEntity queryAllShelfConfig(Map<String, String> map);
	public abstract Integer addShelfConfig(ShelfConfigEntity shelfConfigEntity);
	public abstract Integer updateShelfConfig(ShelfConfigEntity shelfConfigEntity);
	public abstract int deleteShelfConfig(List<ShelfConfigEntity> list);
}