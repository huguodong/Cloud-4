package com.ssitcloud.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.entity.MaintenanceCardEntity;
import com.ssitcloud.entity.page.MaintenanceCardPageEntity;
import com.ssitcloud.system.entity.MaintenanceInfoRemoteEntity;

public interface MaintenanceCardDao {
	
	
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
	 * 更新维护卡
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
	 *根据与图书馆ID获取图书馆的维护卡信息
	 *
	 * <p>2016年7月14日 下午2:21:17 
	 * <p>create by hjc
	 * @param map
	 * @return
	 */
	public List<MaintenanceCardEntity> selMaintenaceCard(Map<String, Object> map);


	/**
	 * 数据同步下发维护卡信息，根据图书馆id查询
	 *
	 * <p>2017年4月7日 下午5:52:51 
	 * <p>create by hjc
	 * @param param
	 * @return
	 */
	public abstract List<MaintenanceInfoRemoteEntity> queryMaintenanceCardInfo(Map<String, Object> param);

}
