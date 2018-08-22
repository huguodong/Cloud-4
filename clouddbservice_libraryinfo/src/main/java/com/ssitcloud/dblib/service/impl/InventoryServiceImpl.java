package com.ssitcloud.dblib.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.dao.InventoryDao;
import com.ssitcloud.dblib.entity.InventoryEntity;
import com.ssitcloud.dblib.service.InventoryService;

@Service
public class InventoryServiceImpl implements InventoryService {
	@Resource
	private InventoryDao inventoryDao;

	@Override
	public int insertInventory(
			InventoryEntity inventoryEntity) {
		return inventoryDao.insertInventory(inventoryEntity);
	}

	@Override
	public int updateInventory(
			InventoryEntity inventoryEntity) {
		return inventoryDao.updateInventory(inventoryEntity);
	}

	@Override
	public int deleteInventory(
			InventoryEntity inventoryEntity) {
		return inventoryDao.deleteInventory(inventoryEntity);
	}

	@Override
	public InventoryEntity queryOneInventory(
			InventoryEntity inventoryEntity) {
		return inventoryDao.queryOneInventory(inventoryEntity);
	}

	@Override
	public List<InventoryEntity> queryInventory(
			InventoryEntity inventoryEntity) {
		return inventoryDao.queryInventory(inventoryEntity);
	}

	

}
