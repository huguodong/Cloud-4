package com.ssitcloud.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.entity.OperatorMaintenanceInfoEntity;

/** 
 *
 * <p>2016年7月14日 下午3:01:11  
 * @author hjc 
 *
 */
public interface OperatorMaintenanceInfoDao {
	
	/**
	 * 根据操作员idx查找操作员的维护卡信息
	 *
	 * <p>2016年7月14日 下午3:04:47 
	 * <p>create by hjc
	 * @param param
	 * @return
	 */
	public abstract OperatorMaintenanceInfoEntity queryOperatorMaintenanceCard(Map<String, Object> param);
	
	/**
	 * 根据操作员IDX删除原来的维护卡
	 *
	 * <p>2016年7月14日 下午5:02:17 
	 * <p>create by hjc
	 * @param maintenanceInfoEntity
	 * @return
	 */
	public abstract int deleteMaintenanceByOperatorIdx(OperatorMaintenanceInfoEntity maintenanceInfoEntity);
	
	/**
	 * 新增一条维护卡关联信息
	 *
	 * <p>2016年7月14日 下午5:03:00 
	 * <p>create by hjc
	 * @param maintenanceInfoEntity
	 * @return
	 */
	public abstract int addMaintenance(OperatorMaintenanceInfoEntity maintenanceInfoEntity);

	public abstract List<OperatorMaintenanceInfoEntity> queryOperatorMaintenanceCardByTypeIdxs(
			List<Integer> typeIdxList);

}
