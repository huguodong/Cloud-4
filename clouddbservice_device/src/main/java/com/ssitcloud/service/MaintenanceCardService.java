package com.ssitcloud.service;


import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.entity.MaintenanceCardEntity;
import com.ssitcloud.entity.page.MaintenanceCardPageEntity;

public interface MaintenanceCardService {
	
	/**
	 * 查询同一个图书馆下是否有相同的维护卡号
	 *
	 * <p>2017年3月30日 下午6:36:50 
	 * <p>create by hjc
	 * @param maintenanceCardEntity
	 * @return
	 */
	public abstract int countCardByCardIdAndLibIdx(MaintenanceCardEntity maintenanceCardEntity);
	
	
	
	/**
	 * 新增维护卡
	 *
	 * <p>2017年3月30日 下午6:37:09 
	 * <p>create by hjc
	 * @param maintenanceCardEntity
	 * @return
	 */
	public abstract int insertMaintenanceCard(MaintenanceCardEntity maintenanceCardEntity);
	
	/**
	 * 更新维护卡
	 *
	 * <p>2017年3月30日 下午6:37:09 
	 * <p>create by hjc
	 * @param maintenanceCardEntity
	 * @return
	 */
	public abstract int updateMaintenanceCard(MaintenanceCardEntity maintenanceCardEntity);
	/**
	 * 删除维护卡
	 *
	 * <p>2017年3月30日 下午6:37:09 
	 * <p>create by hjc
	 * @param maintenanceCardEntity
	 * @return
	 */
	public abstract int deleteMaintenanceCard(MaintenanceCardEntity maintenanceCardEntity);
	
	/**
	 * 
	 *
	 * <p>2017年3月30日 下午8:42:13 
	 * <p>create by hjc
	 * @param pageEntity
	 * @return
	 */
	public abstract MaintenanceCardPageEntity queryMaintenanceCardByFuzzy(MaintenanceCardPageEntity pageEntity);
	
	/**
	 * 根据图书馆ID查询图书馆维护卡信息
	 *
	 * <p>2016年7月14日 下午2:01:43 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	public abstract ResultEntity queryMaintenanceCard(String req);
	
	/**
	 * 修改操作员维护卡信息
	 *
	 * <p>2016年7月14日 下午4:57:38 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity updateOperatorMaintenanceCard(String req);
	

}
