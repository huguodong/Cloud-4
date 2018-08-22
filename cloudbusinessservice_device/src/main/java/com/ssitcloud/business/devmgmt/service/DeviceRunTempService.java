package com.ssitcloud.business.devmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;


/**
 * 设备运行模板配置
 *
 * <p>2016年6月18日 下午3:44:13  
 * @author hjc 
 *
 */
public interface DeviceRunTempService {
	/**
	 * 根据参数获取Runlist 分页数据
	 *
	 * <p>2016年5月19日 下午1:46:51 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract String SelRunTempListByParam(String json);
	
	/**
	 * 新增硬件与逻辑名配置模板
	 *
	 * <p>2016年6月16日 上午9:00:59 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract ResultEntity addRunTemp(String json);
	
	/**
	 * 更新硬件与逻辑名配置模板
	 *
	 * <p>2016年6月16日 上午9:01:02 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract ResultEntity updateRunTemp(String json);
	
	
	/**
	 * 删除硬件与逻辑名配置模板
	 *
	 * <p>2016年6月16日 上午9:01:02 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract ResultEntity delRunTemp(String json);
	/**
	 * 批量删除硬件与逻辑名配置模板
	 *
	 * <p>2016年6月16日 上午9:01:02 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract ResultEntity delMultiRunTemp(String json);
}
