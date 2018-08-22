package com.ssitcloud.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ssitcloud.common.dao.CommonDao;
import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.devmgmt.entity.DevStatusCode;
import com.ssitcloud.devmgmt.entity.SyncConfigEntity;
import com.ssitcloud.entity.DeivceIdxAndIDEntity;
import com.ssitcloud.entity.DeviceEntity;
import com.ssitcloud.entity.DeviceMgmtEntity;
import com.ssitcloud.entity.DevicePosition;
import com.ssitcloud.entity.page.DeviceMgmtAppPageEntity;
import com.ssitcloud.entity.page.DeviceMgmtPageEntity;
import com.ssitcloud.entity.page.DevicePageEntity;
import com.ssitcloud.entity.sync.DeviceSyncEntity;
/**
 * 
 * @comment
 * 
 * @author hwl
 * @data 2016年4月6日
 */
public interface DeviceDao extends CommonDao {
	
	public abstract int insert(DeviceEntity deviceEntity);
	
	public abstract int update(DeviceEntity deviceEntity);
	
	public abstract int delete(DeviceEntity deviceEntity);
	
	public abstract List<DeviceEntity> selectByid(DeviceEntity deviceEntity);

	public abstract PageEntity selectdevicemgmt(Map<String, String> map);
	
	public abstract int deletedevicemgmt(DeviceMgmtEntity deviceMgmtEntity);
	/*public abstract int adddevicemgmt(DeviceMgmtEntity deviceMgmtEntity);*/

	PageEntity selectPage(PageEntity page);
	
	DevicePageEntity selectPage(DevicePageEntity devicePageEntity,Integer operator_idx,boolean devGroupLimit);
	
	String selectDeviceIdx(DeviceEntity deviceEntity);

	List<DeivceIdxAndIDEntity> selectDeviceIdAndIdx();

	Integer selectDevicTypeByDeviceId(String device_id);
	
	
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
	 * 根据ID查询 设备信息
	 * 返回 List <deviceSyncEntity>
	 * @param deviceSyncEntity
	 * @return
	 */
	List<DeviceSyncEntity> selectByid(DeviceSyncEntity deviceSyncEntity);
	/**
	 * library_idx 和对应的device_id
	 * @return
	 */
	public abstract Map<Integer, String> queryDeviceIdbyLibIdx();
	
	/**
	 * 获取图书馆的设备信息，进行批处理操作， 分页显示， 可以根据参数查询
	 *
	 * <p>2016年9月20日 下午4:19:30 
	 * <p>create by hjc
	 * @param deviceMgmtPageEntity
	 * @return
	 */
	public abstract DeviceMgmtPageEntity getLibraryDevicesByPage(DeviceMgmtPageEntity deviceMgmtPageEntity);
	
	/**
	 * 修改设备的图书馆信息
	 *
	 * <p>2016年9月23日 上午10:15:50 
	 * <p>create by hjc
	 * @param param
	 * @return
	 */
	public abstract int updateDeviceLibrary(Map<String, Object> param);
	/**
	 * 根据设备类型数组 查询 设备信息。
	 * @param devType
	 * @return
	 */
	public abstract List<DeviceEntity> selectByDevTypeList(List<String> devType);
	/**
	 * 根据library_idx 删除 对应得device记录
	 * @param deviceEntity
	 * @return
	 */
	public abstract int deleteDevByLibIdx(DeviceEntity deviceEntity);
	String selectDeviceCode(DeviceEntity deviceEntity);
	
	/**
	 * 根据device_idx查出 device_id
	 * <p>2017年3月6日 下午3:38 
	 * <p>create by shuangjunjie
	 * @param deviceEntity
	 * @return
	 */
	public abstract String selectDevIdByIdx(DeviceEntity deviceEntity);

	
	/**
	 * 根据DevType的名称查询设备
	 * <p>create by LXP
	 * @param map 必须包含devType{List},devType中描述了DevType的名称。可选limitS,limitE
	 * @return
	 */
	List<DeviceEntity> selectByDevTypeNameList(Map<String, Object> map);

	
	/**
	 * 查询图书馆中所有的设备信息
	 *
	 * <p>2017年3月21日 下午8:37:29 
	 * <p>create by hjc
	 * @param param
	 * @return
	 */
	public abstract List<Map<String, Object>> getAllDeviceByLibidx(Map<String, Object> param);
	/**
	 * 根据设备id获取设备信息
	 *
	 * <p>2017年4月12日 上午10:01:29 
	 * <p>create by hjc
	 * @param param
	 * @return
	 */
	public abstract Map<String, Object> selDeviceById(Map<String, Object> param);
	
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
	
	/**
	 * 根据地区码查询地区位置信息
	 *
	 * <p>2017年9月7日 下午5:05:23 
	 * <p>create by hjc
	 * @param param
	 * @return
	 */
	public abstract Map<String, Object> queryRegionByCode(Map<String, Object> param);
	
	/**
	 * 新增设备地理位置信息
	 *
	 * <p>2017年9月7日 下午5:09:01 
	 * <p>create by hjc
	 * @param param
	 * @return
	 */
	public abstract int insertDeviceExtendInfo(Map<String, Object> param);
	
	/**
	 * 修改设备的地理位置信息
	 *
	 * <p>2017年9月7日 下午5:09:01 
	 * <p>create by hjc
	 * @param param
	 * @return
	 */
	public abstract int updateDeviceExtendInfoByDeviceIdx(Map<String, Object> param);
	
	/**
	 * 查询设备的地理位置信息
	 *
	 * <p>2017年9月7日 下午5:10:24 
	 * <p>create by hjc
	 * @param param
	 * @return
	 */
	public abstract Map<String, Object> queryDeviceExtendInfoByDeviceIdx(Map<String, Object> param);
	/**
	 * 通过设备idx查询地点代码
	 *
	 * <p>2017年9月8日 下午3:55:36 
	 * <p>create by hjc
	 * @param param
	 * @return
	 */
	public abstract Map<String, Object> queryRegionByDeviceIdx(Map<String, Object> param);
	
	/**
	 * 通过查询图书馆经纬度信息
	 */
	public List<DevicePosition> getLibPosition(Map<String,Object> map);

	/**
	 * 通过图书馆id查询设备经纬度信息
	 */
	public List<DevicePosition> getDevicePosition(Map<String,Object> map);
	
	/**
	 * 查询服务设备设备信息
	 */
	public com.ssitcloud.entity.ServiceDevicePageEntity selectServiceDevicePage(com.ssitcloud.entity.ServiceDevicePageEntity devicePageEntity);

	/**
	 * 查询图书馆位置是否存在
	 *
	 */
	public int queryDeviceById(String device_id);
	
	/**
	 * 保存设备的位置信息
	 * 
	 */
	public int saveDevicePosition(Map<String,Object> map);
	
	/**
	 * 更新图书馆的位置信息
	 */
	public int updateDevicePosition(Map<String,Object> map);
	
	/**
	 * 查询图书馆位置是否存在
	 *
	 */
	public int queryLibById(String lib_id);
	
	/**
	 * 保存图书馆的位置信息
	 * 
	 */
	public int saveLibPosition(Map<String,Object> map);
	
	/**
	 * 更新图书馆的位置信息
	 */
	public int updateLibPosition(Map<String,Object> map);
	
	public int deleteLibraryPosition(ArrayList list);
	/**
	 * 查询文件上传更新信息是否存在
	 * 
	 */
	public int queryFileUploadFlag(Map<String, Object> map);
	/**
	 * 保存文件上传更新信息
	 */
	public int saveFileUploadFlag(Map<String, Object> map);
	/**
	 * 更新文件上传更新信息
	 * 
	 */
	public int updateFileUploadFlag(Map<String, Object> map);
	public List<SyncConfigEntity> SelSyncConfig(SyncConfigEntity syncConfig);

	public abstract int selectDeviceCountByLibraryIdx(Map<String, String> params);
	
	public List<DevStatusCode> queryDevStatusCode();
	public List<DeviceEntity> selectAllDevice();
}
