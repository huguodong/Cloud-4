package com.ssitcloud.dblib.dao;

import java.util.List;

import com.ssitcloud.dblib.entity.InventoryEntity;


public interface InventoryDao {

	/**
	 * 图书盘点日志InventoryEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:34:26
	 * @param inventoryEntity
	 * @return
	 */
	public abstract int insertInventory(InventoryEntity inventoryEntity);
	
	/**
	 * 图书盘点日志InventoryEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:34:39
	 * @param inventoryEntity
	 * @return
	 */
	public abstract int updateInventory(InventoryEntity inventoryEntity);
	
	/**
	 * 图书盘点日志InventoryEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:34:54
	 * @param inventoryEntity
	 * @return
	 */
	public abstract int deleteInventory(InventoryEntity inventoryEntity);
	
	/**
	 * 图书盘点日志InventoryEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:35:09
	 * @param inventoryEntity
	 * @return
	 */
	public abstract InventoryEntity queryOneInventory(InventoryEntity inventoryEntity);
	
	/**
	 * 图书盘点日志InventoryEntity全表查询
	 * author huanghuang
	 * 2017年2月9日 下午1:35:25
	 * @param inventoryEntity
	 * @return
	 */
	public abstract List<InventoryEntity> queryInventory(InventoryEntity inventoryEntity);

}
