package com.ssitcloud.view.service;

import java.util.HashMap;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.devmgmt.entity.DeviceDisplayConfig;
import com.ssitcloud.devmgmt.entity.MetadataDeviceTypeEntity;
import com.ssitcloud.devregister.entity.DeviceRegisterParam;

/**
 * <p>2016年4月25日 下午4:36:11
 * @author hjc
 */
public interface DeviceRegisteService {

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
	 * 获取所有的硬件模板信息
	 *
	 * <p>2016年4月25日 下午6:43:54
	 * <p>create by hjc
	 * @return
	 */
	public abstract String getAllExtTempList(String json);
	
	/**
	 * 获取所有的硬件配置模板信息
	 *
	 * <p>2016年4月26日 下午6:48:35
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract String getAllRunTempList(String json);
	
	/**
	 * 获取所有的硬件配置模板信息
	 *
	 * <p>2016年4月26日 下午6:48:35
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract String getAllMonitorTempList(String json);
	
	/**
	 * 获取所有数据同步配置模板
	 *
	 * <p>2016年4月26日 下午6:48:35
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract String getAllDbSyncTempList(String json);
	
	/**
	 * 设备注册
	 *
	 * <p>2016年4月27日 下午6:14:01
	 * <p>create by hjc
	 * @param registerParam
	 * @return
	 */
	public abstract String deviceRegister(DeviceRegisterParam registerParam);
	
	/**
	 * 检查系统中是否有deviceCode的设备
	 *
	 * <p>2016年4月28日 下午2:37:43 
	 * <p>create by hjc
	 * @param deviceCode
	 * @return
	 */
	public abstract String hasDeviceCode(String deviceCode);
	
	/**
	 * 根据设备特征码查询设备是否存在，如果存在返回设备类型
	 *
	 * <p>2016年6月17日 下午4:42:50 
	 * <p>create by hjc
	 * @param deviceCode
	 * @return
	 */
	public abstract String queryDeviceByDeviceCode(String deviceCode);
	
	/**
	 * 获取所有的外设逻辑部件
	 *
	 * <p>2016年5月7日 下午3:06:26 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract String SelectMetadataLogicObj(String json);
	
	/**
	 * 获取所有的外设extmodel
	 *
	 * <p>2016年5月11日 上午9:56:40 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract String SelMetadataExtModel(String json);
	
	/**
	 *  获取所有的logic_obj和ext_model的元数据
	 *
	 * <p>2016年5月14日 下午2:17:06 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract String selAllExtModelAndLogicObj(String json);
	
	/**
	 * 根据图书馆id或者idx获取图书馆信息
	 *
	 * <p>2016年6月1日 上午10:26:24 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract String selLibraryByIdxOrId(String json);
	
	/**
	 * 根据设备id或者idx查询是否存在这一个台机器
	 * 如果存在返回result为1  否则result为0
	 * <p>2016年6月1日 下午2:14:31 
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
	 * 根据设备类型id来获取显示风格
	 *
	 * <p>2016年6月30日 下午1:57:36 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract DeviceDisplayConfig getDisplayByTypeId(String json);
	
	/**
	 * 根据设备类型id来获取显示风格
	 *
	 * <p>2016年6月30日 下午1:57:36 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract MetadataDeviceTypeEntity queryDeviceTypeByName(String json);
	
	/**
	 * 根据设备id 查询设备ip
	 *
	 * <p>2016年9月21日 下午6:52:56 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract String queryDeviceIps(String json);
	
	/**
	 * 将图书馆的设备转移到另一个图书馆
	 *
	 * <p>2016年9月21日 上午10:37:55 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity transferToNewLib(String req);
	
	/**
	 * 根据馆idx查询父馆
	 * @param req
	 * @return
	 */
	public abstract boolean isHHMaster(String req);
	
	/**
	 * 查询所有的地区信息
	 *
	 * <p>2017年9月7日 上午10:20:34 
	 * <p>create by hjc
	 * @return
	 */
	public abstract ResultEntity queryAllRegion(String req);
	/**
	 * 海洋设备注册
	 *
	 * <p>2018年1月9日 下午15:30:14 
	 * <p>create by liuwei
	 * @return
	 */
	public abstract String hydeviceRegister(DeviceRegisterParam registerParam);
}
