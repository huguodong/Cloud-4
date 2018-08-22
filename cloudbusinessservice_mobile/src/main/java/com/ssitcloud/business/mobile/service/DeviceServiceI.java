package com.ssitcloud.business.mobile.service;

import java.util.List;
import java.util.Map;

import com.ssitcloud.devmgmt.entity.DeviceEntity;
import com.ssitcloud.mobile.entity.AppOPACEntity;

/**
 * 设备服务
 * @author LXP
 * @version 创建时间：2017年2月28日 下午3:13:12
 */
public interface DeviceServiceI {
	/**
	 * 根据图书馆id获取设备信息列表
	 * @param libId
	 * @return 返回list说明正常获取到数据，返回null说明获取数据失败
	 */
	List<Map<String, Object>> findDeviceByLibId(Integer libId);
	
	/**
	 * 根据条件查询设备
	 * @param entity
	 * @return
	 */
	List<Map<String, Object>> findDevice(DeviceEntity entity);
	
	/**
	 * 根据图书馆id，获取opac类型的设备列表
	 * @param libId 图书馆id
	 * @param pageCurrent 第几页，从1开始，当为null时不分页
	 * @param pageSize 每页多少条，当为null时不分页
	 * @param otherPram 需要传递的其他参数
	 * @return 返回null说明获取数据失败
	 */
	List<DeviceEntity> findOpacDevice(Integer libId,Integer pageCurrent,Integer pageSize,Map<String, Object> otherPram);
	
	/**
	 * 
	 * @param libId
	 * @param pageCurrent
	 * @param pageSize 
	 * @param otherPram 需要传递的其他参数
	 * @return 返回null说明获取数据失败
	 */
	List<AppOPACEntity> findOpacDeviceToApp(Integer libId,Integer pageCurrent,Integer pageSize,Map<String, Object> otherPram);
	
	/**
	 * 
	 * @param libId
	 * @param pageCurrent
	 * @param pageSize
	 * @param otherPram 需要传递的其他参数
	 * @return 返回null说明获取数据失败
	 */
	List<AppOPACEntity> findOpacSelfHelpLib(Integer libId,Integer pageCurrent,Integer pageSize,Map<String, Object> otherPram);

    /**
     * 根据idx，查询自助图书馆信息
     * @param lib_idx 图书馆idx
     */
    AppOPACEntity findOpacSelfHelpLib(Integer lib_idx);

    /**
	 * 查询图书馆下设备的idx和地区码
	 * @param lib_idx
	 * @return
	 */
	List<Map<String, Object>> selectDeviceIdxAndRegion(Integer lib_idx);
}
