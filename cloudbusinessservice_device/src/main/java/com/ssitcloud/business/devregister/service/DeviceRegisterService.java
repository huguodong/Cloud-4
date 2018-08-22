package com.ssitcloud.business.devregister.service;

import com.ssitcloud.business.devregister.param.AddDeviceParam;


public interface DeviceRegisterService {
	/**
	 * 向数据层请求信息，注册设备信息
	 * 
	 * 
	 * @methodName: deviceRegister
	 * @param regInfo json 注册信息数据
	 * @return
	 * @returnType: String
	 * @author: liuBh
	 */
	String deviceRegister(String regInfo);
	
	
	/**
	 * 获取所有的设备类型
	 *
	 * <p>2016年4月25日 下午3:59:13
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract String selAllMetadataDeviceType(String json);
	
	/**
	 * 获取所有的硬件与逻辑配置模板
	 *
	 * <p>2016年4月25日 下午8:25:22
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract String selAllExtTempList(String json);
	
	/**
	 * 获取所有运行配置模板信息
	 *
	 * <p>2016年4月25日 下午8:25:22
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract String selAllRunTempList(String json);
	/**
	 * 获取所有的机器监控配置模板
	 *
	 * <p>2016年4月25日 下午8:25:22
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract String selAllMonitorTempList(String json);
	/**
	 * 获取所有数据同步配置模板
	 *
	 * <p>2016年4月25日 下午8:25:22
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract String selAllDBSyncTempList(String json);
	
	/**
	 * 检查系统中是否有deviceCode的设备
	 *
	 * <p>2016年4月28日 下午2:47:47 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract String hasDeviceCode(String json);
	
	/**
	 *  根据设备号查询设备信息，如果有这个设备，返回设备的设备类型
	 *
	 * <p>2016年6月17日 下午4:30:23 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract String queryDeviceByDeviceCode(String json);
	
	/**
	 * 设备注册
	 *
	 * <p>2016年5月5日 上午9:22:12 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract String registerDevice(String json);
	
	/**
	 * 新增一个设备用户
	 *
	 * <p>2016年5月5日 下午3:02:47 
	 * <p>create by hjc
	 * @param deviceParam
	 * @return
	 */
	public abstract String addDevice(AddDeviceParam deviceParam);
	
	/**
	 * 获取所有的logic_obj和ext_model的元数据 
	 *
	 * <p>2016年5月14日 下午2:09:03 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract String selAllExtModelAndLogicObj(String json);
	
	
	/**
	 * 通过图书馆id 或者idx 获取图书馆信息
	 * {lib_id:"1"} or {library_idx:"1"}
	 * <p>2016年6月1日 上午10:28:02 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract String selLibraryByIdxOrId(String json);
	
	/**
	 * 通过id idx 判断设备是否存在
	 * {device_id:"1"} or {device_idx:"1"}
	 * <p>2016年6月1日 下午1:51:58 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract String isExistDeviceWithIdOrIdx(String json);
	
	/**
	 * 查询图书馆的模板信息，以及所有 有效的设备用户的id
	 *
	 * <p>2016年6月2日 下午5:48:49 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract String selDeviceUserByLibraryIdx(String json);
	
	/**
	 * 根据传过来的设备id数组，查询设备库有中有多少设备
	 *
	 * <p>2016年6月2日 下午5:48:49 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract String selDeviceCountByIds(String json);
	

	/**
	 * 获取acs模板数据
	 *
	 * <p>2016年6月30日 下午1:57:36 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract String getAscTempList(String json);
	
	/**
	 * 海洋设备注册
	 *
	 * <p>2018年1月9日 下午15:26:25
	 * <p>create by liuwei
	 * @param json
	 * @return
	 */
	public abstract String registerHYDevice(String json);
}
