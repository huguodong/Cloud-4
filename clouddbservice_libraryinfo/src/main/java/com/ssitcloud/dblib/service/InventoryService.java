package com.ssitcloud.dblib.service;

import java.util.List;
import com.ssitcloud.dblib.entity.InventoryEntity;

public interface InventoryService {
	
	/**
	 * 图书盘点日志InventoryEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:39:52
	 * @param inventoryEntity
	 * @return
	 */
	public abstract int insertInventory(InventoryEntity inventoryEntity);
	
	/**
	 * 图书盘点日志InventoryEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:40:03
	 * @param inventoryEntity
	 * @return
	 */
	public abstract int updateInventory(InventoryEntity inventoryEntity);
	
	/**
	 * 图书盘点日志InventoryEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:40:18
	 * @param inventoryEntity
	 * @return
	 */
	public abstract int deleteInventory(InventoryEntity inventoryEntity);
	
	/**
	 * 图书盘点日志InventoryEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:40:37
	 * @param inventoryEntity
	 * @return
	 */
	public abstract InventoryEntity queryOneInventory(InventoryEntity inventoryEntity);
	
	/**
	 * 图书盘点日志InventoryEntity集合查询
	 * author huanghuang
	 * 2017年2月9日 下午1:40:50
	 * @param inventoryEntity
	 * @return
	 */
	public abstract List<InventoryEntity> queryInventory(InventoryEntity inventoryEntity);

}
