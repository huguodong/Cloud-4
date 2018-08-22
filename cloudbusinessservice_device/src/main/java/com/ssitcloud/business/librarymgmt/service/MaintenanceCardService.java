package com.ssitcloud.business.librarymgmt.service;

import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;

public interface MaintenanceCardService {
	/**
	 * 新增维护卡信息
	 *
	 * <p>2017年4月7日 上午10:20:20 
	 * <p>create by hjc
	 * @param info
	 * @return
	 */
	public abstract String insertMaintenanceCard(String info);
	/**
	 * 更新维护卡信息
	 *
	 * <p>2017年4月7日 上午10:20:31 
	 * <p>create by hjc
	 * @param info
	 * @return
	 */
	public abstract String updateMaintenanceCard(String info);
	/**
	 * 删除包括批量删除维护卡信息
	 *
	 * <p>2017年4月7日 上午10:20:39 
	 * <p>create by hjc
	 * @param info
	 * @return
	 */
	public abstract String deleteMaintenanceCard(String info);
	
	/**
	 * 模糊分页查询维护卡信息
	 *
	 * <p>2017年4月7日 上午10:20:54 
	 * <p>create by hjc
	 * @param map
	 * @return
	 */
	public abstract String queryMaintenanceCardByFuzzy(Map<String, String> map);
	
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
	 * 修改操作员的维护卡信息
	 *
	 * <p>2016年7月14日 下午4:55:33 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity updateOperatorMaintenanceCard(String req);

}
