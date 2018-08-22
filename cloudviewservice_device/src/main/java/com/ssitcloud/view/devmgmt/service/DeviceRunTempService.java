package com.ssitcloud.view.devmgmt.service;

import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;


/** 
 *
 * <p>2016年5月18日 下午6:45:39  
 * @author hjc 
 *
 */
public interface DeviceRunTempService {
	
//	/**
//	 * 获取所有的设备类型
//	 *
//	 * <p>2016年4月25日 下午4:40:05
//	 * <p>create by hjc
//	 * @param json
//	 * @return
//	 */
//	public abstract String selRunTempListByParam(String json);
	
	/**
	 * 根据参数获取设备硬件与逻辑设备模板的数据，返回分页信息
	 *
	 * <p>2016年5月18日 下午7:44:01 
	 * <p>create by hjc
	 * @param map
	 * @return
	 */
	public abstract String selRunTempListByParam(Map<String, String> map);
	
	/**
	 * 新增设备运行模板信息
	 *
	 * <p>2016年6月20日 下午3:19:29 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract ResultEntity addRunTemp(String json);
	
	/**
	 * 更新设备运行模板信息
	 *
	 * <p>2016年6月20日 下午3:19:45 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract ResultEntity updateRunTemp(String json);
	
	/**
	 * 删除模板
	 *
	 * <p>2016年6月16日 下午2:55:14 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract ResultEntity delRunTemp(String json);
	/**
	 * 批量删除模板
	 *
	 * <p>2016年6月16日 下午2:55:14 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract ResultEntity delMultiRunTemp(String json);

}
