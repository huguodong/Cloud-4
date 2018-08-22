package com.ssitcloud.service;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.exception.DeleteDeviceErrorExeception;
import com.ssitcloud.devmgmt.entity.SyncConfigEntity;
import com.ssitcloud.entity.DeivceIdxAndIDEntity;
import com.ssitcloud.entity.DeviceConfigEntity;
import com.ssitcloud.entity.DeviceEntity;
import com.ssitcloud.entity.DeviceMgmtEntity;
import com.ssitcloud.entity.page.DeviceMgmtAppPageEntity;
import com.ssitcloud.entity.page.DevicePageEntity;
import com.ssitcloud.param.DeviceRegisterParam;


/**
 * 
 * @comment
 * 
 * @author hwl
 * @data 2016年4月7日
 */
public interface DeviceService {

	
	/**
	 * 添加设备数据
	 * @param deviceEntity
	 * @return
	 */
	public abstract int addDevice(DeviceEntity deviceEntity);
	
	/**
	 * 更新设备数据
	 * @param deviceEntity
	 * @return
	 */
	public abstract int updDevice(DeviceEntity deviceEntity);
	
	/**
	 * 删除设备数据
	 * @param deviceEntity
	 * @return
	 */
	public abstract int delDevice(DeviceEntity deviceEntity);
	
	/**
	 * 根据条件查询设备数据
	 * @param deviceEntity
	 * @return
	 */
	public abstract List<DeviceEntity> selbyidDevice(DeviceEntity deviceEntity);
	
	
	
	public abstract  PageEntity SelectDeviceMgmt(Map<String, String> map);

	
	public abstract int DeleteDeviceMgmt(DeviceMgmtEntity deviceMgmtEntity) throws DeleteDeviceErrorExeception;
	//public abstract int AddDeviceMgmt(DeviceMgmtEntity deviceMgmtEntity);

	List<DeivceIdxAndIDEntity> selectDeviceIdAndIdx();

	public abstract PageEntity selbyPage(PageEntity page);

	public abstract DevicePageEntity selbyDevicePage(DevicePageEntity page,Integer operator_idx,boolean devGroupLimit);
	
	/**
	 * 检测数据库中是否存在deviceCode的设备
	 *
	 * <p>2016年4月28日 下午3:03:09 
	 * <p>create by hjc
	 * @param deviceCode
	 * @return
	 */
	public abstract Integer hasDeviceCode(String deviceCode);
	
	/**
	 * 根据设备deviceCode查询设备信息，包括设备类型
	 *
	 * <p>2016年6月17日 下午4:00:19 
	 * <p>create by hjc
	 * @param deviceCode
	 * @return
	 */
	public abstract Map<String, Object> queryDeviceByDeviceCode(String deviceCode);
	
	/**
	 * 
	 *
	 * <p>2016年4月28日 下午5:25:40 
	 * <p>create by hjc
	 * @return
	 */
	public abstract ResultEntity deviceRegister(DeviceRegisterParam registerParam);

	public abstract ResultEntity queryDeviceByParam(String req);
	
	/**
	 * 判断id或者idx的设备是否存在
	 *
	 * <p>2016年6月1日 下午2:22:22 
	 * <p>create by hjc
	 * @param deviceEntity
	 * @return 返回符合条件的设备数
	 */
	public abstract int isExistDeviceWithIdOrIdx(DeviceEntity deviceEntity);
	
	
	/**
	 * 根据传过来的设备id数组，查询设备库有中有多少设备
	 *
	 * <p>2016年6月2日 下午7:24:12 
	 * <p>create by hjc
	 * @param param
	 * @return
	 */
	public abstract int selDeviceCountByIds(Map<String, Object> param);
	/**
	 * 设备管理点击保存按钮 保存数据
	 * 
	 * 需要修改到的表有
		device_config[变更模板（不是模板改数据或者数据该模板）]
		device[设备类型/图书馆ID/设备物流编号/设备流通地点/设备描述/设备名称/设备ID]
		device_group[设备分组]
	 * @param req
	 * @return
	 */
	public abstract ResultEntity UpdDeviceMgmtPage(String req);
	
	public abstract ResultEntity UpdHyDeviceMgmtPage(String req);
	
	/**
	 * 查询设备有没有自定义参数配置
	 * @param req={"device_idx":deviceIdx,"configName":configName}
	 * @return
	 */
	public abstract ResultEntity checkAllConfigDataByDevIdx(String req);

	public abstract ResultEntity compareMonitorConfig(String req);

	public abstract ResultEntity queryDeviceIdbyLibIdx(String req);
	
	/**
	 * 获取图书馆的设备信息，进行批处理操作， 分页显示， 可以根据参数查询
	 *
	 * <p>2016年9月20日 下午1:55:20 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity getLibraryDevicesByPage(String req);
	/**
	 * 发送请求，修改设备层的设备所属馆信息，并且删除设备的设备组关联
	 *
	 * <p>2016年9月21日 下午6:33:29 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity devTransferToLibrary(String req);
	public abstract ResultEntity selectDeviceCode(String req);
	
	/**
	 * 通过device_id 查出设备类型
	 * 
	 * <p>2017年3月6日 上午11:47
	 * <p>create by shuangjunjie
	 * @param req
	 * @return
	 */
	public abstract ResultEntity selectDevicTypeByDeviceId(String req);
	
	/**
	 * 通过device_idx 查出device_id
	 * 
	 * <p>2017年3月6日 下午3:41
	 * <p>create by shuangjunjie
	 * @param req
	 * @return
	 */
	public abstract ResultEntity selectDevIdByIdx(String req);

	
	/**
	 * 根据DevType的名称查询设备
	 * <p>create by LXP
	 * @param map 必须包含devType{List},devType中描述了DevType的名称。可选library_idx , limitS,limitE
	 * @return
	 */
	ResultEntity selectByDevTypeNameList(Map<String,Object> map);

	
	/**
	 * 查询图书馆中所有的设备信息
	 *
	 * <p>2017年3月21日 下午8:20:05 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity getAllDeviceByLibidx(String req);
	/**
	 * 根据设备id获取设备信息
	 *
	 * <p>2017年4月12日 上午10:00:57 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity selDeviceById(String req);
	
	/**
	 * 通过 图书馆idxs查出对应设备信息
	 * add by shuangjunjie
	 * 2017年4月11日
	 * @param map
	 * @return
	 */
	public abstract DeviceMgmtAppPageEntity SelectDeviceMgmtByLibraryIdxs(DeviceMgmtAppPageEntity pageEntity);
	
	/**
	 * 根据图书馆idx查询设备idx和地区码，若没有设置地区码则地区码为null
	 * @param library_idx
	 */
	List<Map<String, Object>> selectDeviceRegionByLibidx(Integer library_idx);
	
	public abstract ResultEntity selDeviceIdx(String req);
	
	/**
	 * 获取设备的地点信息
	 *
	 * <p>2017年9月8日 下午3:49:02 
	 * <p>create by hjc
	 * @param devIdx
	 * @return
	 */
	public abstract ResultEntity queryDeviceRegion(String devIdx);
	
	/**
	 * 获取设备地理位置信息
	 * <p>2017年10月14日 下午16:09:02
	 * <p>create by liuwei
	 */
	public abstract ResultEntity getLibPosition(Map<String,Object> map);
	
	/**
	 * 获取设备地理位置信息
	 * <p>2017年10月14日 下午16:09:02
	 * <p>create by liuwei
	 */
	public abstract ResultEntity getDevicePosition(Map<String,Object> map);
	
	/**
	 * 获取服务设备信息
	 * <p>2017年10月14日 下午16:09:02
	 * <p>create by liuwei
	 */
	public ResultEntity queryServiceDeviceByParam(String req);
	
	/**
	 * 查询设备的位置信息
	 * <p>2017年10月26日 下午05:26:02
	 * <p>create by liuwei
	 */
	public abstract int queryDeviceById(String device_id);
	/**
	 * 保存设备的位置信息
	 * <p>2017年10月26日 上午10:26:02
	 * <p>create by liuwei
	 */
	public abstract ResultEntity saveDevicePosition(Map<String,Object> map);
	/**
	 * 更新设备的位置信息
	 * @param map
	 * @return
	 */
	public abstract ResultEntity updateDevicePosition(Map<String,Object> map);
	
	/**
	 * 查询图书馆的位置信息
	 * <p>2017年10月26日 下午05:26:02
	 * <p>create by liuwei
	 */
	public abstract int queryLibById(String lib_id);
	
	/**
	 * 保存图书馆的位置信息
	 * <p>2017年10月26日 下午05:26:02
	 * <p>create by liuwei
	 */
	public abstract ResultEntity saveLibPosition(Map<String,Object> map);
	
	/**
	 * 更新图书馆的位置信息
	 * @param map
	 * @return
	 */
	public ResultEntity updateLibPosition(Map<String,Object> map);
	
	/**
	 * 删除图书馆位置信息
	 * @param map
	 * @return
	 */
	public ResultEntity deleteLibraryPosition(String req);
	/**
	 * 查询文件更新信息是否存在
	 * <p>
	 * 2017年11月23日 下午05:26:02
	 * <p>
	 * create by liuwei
	 */
	public abstract int queryFileUploadFlag(Map<String, Object> map);

	/**
	 * 保存文件更新信息
	 * <p>
	 * 2017年11月23日 下午05:26:02
	 * <p>
	 * create by liuwei
	 */
	public abstract ResultEntity saveFileUploadFlag(Map<String, Object> map);

	/**
	 * 保存文件更新信息
	 * <p>
	 * 2017年11月23日 下午05:26:02
	 * <p>
	 * create by liuwei
	 */
	public abstract ResultEntity updateFileUploadFlag(Map<String, Object> map);

	public List<SyncConfigEntity> SelSyncConfig();
	/**
	 * 
	 *
	 * <p>2018年1月9日 下午15:18:29 
	 * <p>create by liuwei
	 * @return
	 */
	public abstract ResultEntity hydeviceRegister(DeviceRegisterParam registerParam);
	
	public ResultEntity selectDeviceCountByLibraryIdx(String req);
	
	/**
	 * 获取设备部件监控状态错误码
	 */
	public ResultEntity queryDevStatusCode(); 
	/**加载设备到redis缓存中*/
	public void loadDeviceToRedis();
	
	public void pushMessage(DeviceConfigEntity configEntity,int library_idx,int device_idx);
}

