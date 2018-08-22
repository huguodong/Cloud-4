package com.ssitcloud.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.MaintenanceCardDao;
import com.ssitcloud.entity.MaintenanceCardEntity;
import com.ssitcloud.entity.page.MaintenanceCardPageEntity;
import com.ssitcloud.system.entity.MaintenanceInfoRemoteEntity;

@Repository
public class MaintenanceCardDaoImpl extends CommonDaoImpl implements MaintenanceCardDao{

	@Override
	public int countCardByCardIdAndLibIdx(
			MaintenanceCardEntity maintenanceCardEntity) {
		return this.sqlSessionTemplate.selectOne("maintenance.countCardByCardIdAndLibIdx",maintenanceCardEntity);
	}

	@Override
	public int insertMaintenanceCard(MaintenanceCardEntity maintenanceCardEntity) {
		return this.sqlSessionTemplate.insert("maintenance.insertMaintenanceCard",maintenanceCardEntity);
	}
	@Override
	public int updateMaintenanceCard(MaintenanceCardEntity maintenanceCardEntity) {
		return this.sqlSessionTemplate.update("maintenance.updateMaintenanceCard",maintenanceCardEntity);
	}
	@Override
	public int deleteMaintenanceCard(MaintenanceCardEntity maintenanceCardEntity) {
		return this.sqlSessionTemplate.update("maintenance.deleteMaintenanceCard",maintenanceCardEntity);
	}

	@Override
	public MaintenanceCardPageEntity queryMaintenanceCardByFuzzy(
			MaintenanceCardPageEntity pageEntity) {
		return this.queryDatagridPage(pageEntity, "maintenance.queryMaintenanceCardByFuzzy");
	}
	

	@Override
	public List<MaintenanceCardEntity> selMaintenaceCard(Map<String, Object> map) {
		return this.sqlSessionTemplate.selectList("maintenance.selMaintenaceCard",map);
	}
	
	@Override
	public List<MaintenanceInfoRemoteEntity> queryMaintenanceCardInfo(Map<String, Object> map) {
		return this.sqlSessionTemplate.selectList("maintenance.queryMaintenanceCardInfo",map);
	}
	
	

}
