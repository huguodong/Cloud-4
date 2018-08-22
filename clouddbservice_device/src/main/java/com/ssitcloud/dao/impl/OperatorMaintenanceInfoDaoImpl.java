package com.ssitcloud.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.OperatorMaintenanceInfoDao;
import com.ssitcloud.entity.OperatorMaintenanceInfoEntity;

/** 
 *
 * <p>2016年7月14日 下午3:01:32  
 * @author hjc 
 *
 */
@Repository
public class OperatorMaintenanceInfoDaoImpl extends CommonDaoImpl implements
		OperatorMaintenanceInfoDao {

	@Override
	public OperatorMaintenanceInfoEntity queryOperatorMaintenanceCard(
			Map<String, Object> param) {
		return this.sqlSessionTemplate.selectOne("maintenance.queryOperatorMaintenanceCard",param);
	}

	@Override
	public int deleteMaintenanceByOperatorIdx(
			OperatorMaintenanceInfoEntity maintenanceInfoEntity) {
		return this.sqlSessionTemplate.delete("maintenance.deleteMaintenanceByOperatorIdx", maintenanceInfoEntity);
	}

	@Override
	public int addMaintenance(
			OperatorMaintenanceInfoEntity maintenanceInfoEntity) {
		return this.sqlSessionTemplate.update("maintenance.addMaintenance",maintenanceInfoEntity);
	}

	@Override
	public List<OperatorMaintenanceInfoEntity> queryOperatorMaintenanceCardByTypeIdxs(
			List<Integer> typeIdxList) {
		return this.sqlSessionTemplate.selectList("maintenance.queryOperatorMaintenanceCardByTypeIdxs",typeIdxList);

	}

	
	
}
