package com.ssitcloud.view.devmgmt.service;

import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;

/** 
 *
 * <p>2016年5月18日 下午6:45:39  
 * @author hjc 
 *
 */
public interface DeviceExtLogicService {
	
	/**
	 * 获取所有的设备类型
	 *
	 * <p>2016年4月25日 下午4:40:05
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract String SelAllMetadataDeviceType(String json);
	
	/**
	 * 根据参数获取设备硬件与逻辑设备模板的数据，返回分页信息
	 *
	 * <p>2016年5月18日 下午7:44:01 
	 * <p>create by hjc
	 * @param map
	 * @return
	 */
	public abstract String selExtTempListByParam(Map<String, String> map);
	
	/**
	 * 获取所有的logic_obj和ext_model的元数据
	 *
	 * <p>2016年6月14日 下午7:42:35 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract String selExtTempListByParam(String json);
	
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
	 * 删除模板
	 *
	 * <p>2016年6月16日 下午2:55:14 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract ResultEntity delExtTemp(String json);
	/**
	 * 批量删除模板
	 *
	 * <p>2016年6月16日 下午2:55:14 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract ResultEntity delMultiExtTemp(String json);

}
