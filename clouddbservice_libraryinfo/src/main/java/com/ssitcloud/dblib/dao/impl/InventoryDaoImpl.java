package com.ssitcloud.dblib.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dblib.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dblib.dao.InventoryDao;
import com.ssitcloud.dblib.entity.InventoryEntity;

@Repository
public class InventoryDaoImpl extends CommonDaoImpl implements
		InventoryDao {

	@Override
	public int insertInventory(InventoryEntity inventoryEntity) {
		return this.sqlSessionTemplate.insert("inventory.insertInventory", inventoryEntity);
	}

	@Override
	public int updateInventory(InventoryEntity inventoryEntity) {
		return this.sqlSessionTemplate.update("inventory.updateInventory", inventoryEntity);
	}

	@Override
	public int deleteInventory(InventoryEntity inventoryEntity) {
		return this.sqlSessionTemplate.delete("inventory.deleteInventory", inventoryEntity);
	}

	@Override
	public InventoryEntity queryOneInventory(
			InventoryEntity inventoryEntity) {
		return this.select("inventory.selectInventory", inventoryEntity);
	}

	@Override
	public List<InventoryEntity> queryInventory(
			InventoryEntity inventoryEntity) {
		return this.selectAll("inventory.selectInventories", inventoryEntity);
	}

}
