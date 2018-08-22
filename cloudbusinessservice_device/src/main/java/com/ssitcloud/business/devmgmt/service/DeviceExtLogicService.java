package com.ssitcloud.business.devmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;


/** 
 * 设备管理里的硬件与逻辑名模板配置
 * <p>2016年5月19日 上午11:49:37  
 * @author hjc 
 *
 */
public interface DeviceExtLogicService {
	
	/**
	 * 根据参数获取extlist 分页数据
	 *
	 * <p>2016年5月19日 下午1:46:51 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract String SelExtTempListByParam(String json);
	
	/**
	 * 新增硬件与逻辑名配置模板
	 *
	 * <p>2016年6月16日 上午9:00:59 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract ResultEntity addExtTemp(String json);
	
	/**
	 * 更新硬件与逻辑名配置模板
	 *
	 * <p>2016年6月16日 上午9:01:02 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract ResultEntity updateExtTemp(String json);
	
	
	/**
	 * 删除硬件与逻辑名配置模板
	 *
	 * <p>2016年6月16日 上午9:01:02 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract ResultEntity delExtTemp(String json);
	/**
	 * 批量删除硬件与逻辑名配置模板
	 *
	 * <p>2016年6月16日 上午9:01:02 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract ResultEntity delMultiExtTemp(String json);
	

}
