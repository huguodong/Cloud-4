package com.ssitcloud.view.librarymgmt.service;

import java.util.Map;

public interface MaintenanceCardService {
	
	
	/**
	 * 新增维护卡信息
	 *
	 * <p>2017年3月30日 下午5:00:34 
	 * <p>create by hjc
	 * @param map
	 * @return
	 */
	public abstract String insertMaintenanceCard(Map<String, String> map);
	
	
	/**
	 * 更新维护卡信息
	 *
	 * <p>2017年3月31日 上午10:48:30 
	 * <p>create by hjc
	 * @param map
	 * @return
	 */
	public abstract String updateMaintenanceCard(Map<String, String> map);
	/**
	 * 删除维护卡信息
	 *
	 * <p>2017年3月31日 上午10:48:30 
	 * <p>create by hjc
	 * @param map
	 * @return
	 */
	public abstract String deleteMaintenanceCard(Map<String, String> map);

	/**
	 * 分页模糊查询维护卡信息
	 *
	 * <p>2017年3月30日 下午8:31:18 
	 * <p>create by hjc
	 * @param map
	 * @return
	 */
	public abstract String queryMaintenanceCardByFuzzy(Map<String, String> map);
}
