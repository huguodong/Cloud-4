package com.ssitcloud.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.entity.DeviceDBSyncConfigEntity;
import com.ssitcloud.entity.DeviceDBSyncTempEntity;
import com.ssitcloud.entity.page.DBSyncTempPageEntity;
import com.ssitcloud.system.entity.DeviceDBSyncConfigRemoteEntity;

public interface DeviceDBSyncConfDao {

	int insertDeviceDBSyncConf(DeviceDBSyncConfigEntity deviceDBSyncConfig);

	int updateDeviceDBSyncConf(DeviceDBSyncConfigEntity deviceDBSyncConfig);

	int deleteDeviceDBSyncConf(DeviceDBSyncConfigEntity deviceDBSyncConfig);

	List<DeviceDBSyncConfigEntity> selectDeviceDBSyncConfig(DeviceDBSyncConfigEntity deviceDBSyncConfig);

	List<DeviceDBSyncConfigEntity> selectList(DeviceDBSyncConfigEntity deviceDBSyncConfig);

	int insertDeviceDBSyncTemp(DeviceDBSyncTempEntity deviceDBSyncTemp);

	int updateDeviceDBSyncTemp(DeviceDBSyncTempEntity deviceDBSyncTemp);

	int deleteDeviceDBSyncTemp(DeviceDBSyncTempEntity deviceDBSyncTemp);

	DeviceDBSyncTempEntity selectDeviceDBSyncTemp(DeviceDBSyncTempEntity deviceDBSyncTemp);

	List<DeviceDBSyncTempEntity> selectList(DeviceDBSyncTempEntity deviceDBSyncTemp);
	
	List<DeviceDBSyncConfigRemoteEntity> selectListByRemoteDeviceSync(DeviceDBSyncConfigEntity deviceDBSyncConfig);
	
	/**
	 * 根据device_type获取模板列表
	 *
	 * <p>2016年5月6日 下午7:09:34 
	 * <p>create by hjc
	 * @param deviceDBSyncTemp
	 * @return
	 */
	public abstract List<DeviceDBSyncTempEntity> selectListByDeviceType(DeviceDBSyncTempEntity deviceDBSyncTemp);
	
	/**
	 * 根据模版获得具体配置信息
	 * @param dbsyncTempId
	 * @return
	 */
	public abstract List<Map<String, Object>> selDbsyncDetailListByIdx(Integer dbsyncTempId);
	
	/**
	 * 分页查询模板id
	 * @comment
	 * @param dbSyncTempPageEntity
	 * @return
	 * @data 2016年6月1日
	 * @author hwl
	 */
	public abstract DBSyncTempPageEntity seldbsyncTemp(DBSyncTempPageEntity dbSyncTempPageEntity);
	
	/**
	 * 根据idx查询数据同步模板配置信息
	 * @comment
	 * @param idx
	 * @return
	 * @data 2016年6月2日
	 * @author hwl
	 */
	public abstract List<DeviceDBSyncConfigEntity> seldbsynctempconfigByidx(int idx);
	
	/**
	 * 根据idx查询数据同步模板描述信息
	 * @comment
	 * @param idx
	 * @return
	 * @data 2016年6月2日
	 * @author hwl
	 */
	public abstract DeviceDBSyncTempEntity seldbsynctempByidx(int idx);
	
	public abstract int deleteDBSyncTemp(Integer idx );

	Integer selectDataTypeCount(DeviceDBSyncConfigEntity deviceDBSyncConfig);
	
	/**
	 * 查询是否有相同id的模板
	 *
	 * <p>2016年7月19日 上午9:54:55 
	 * <p>create by hjc
	 * @param dbSyncTempEntity
	 * @return
	 */
	public abstract int countDeviceDBSyncTempById(DeviceDBSyncTempEntity dbSyncTempEntity);
	
	/**
	 * 查询时否有相同名称的模板
	 *
	 * <p>2016年7月19日 上午9:55:09 
	 * <p>create by hjc
	 * @param dbSyncTempEntity
	 * @return
	 */
	public abstract int countDeviceDBSyncTempByName(DeviceDBSyncTempEntity dbSyncTempEntity);
	
	/**
	 * 查询模板有没有被使用
	 *
	 * <p>2016年7月19日 下午1:44:03 
	 * <p>create by hjc
	 * @param dbSyncTempEntity
	 * @return
	 */
	public abstract int countUsingDBSyncTempplate(DeviceDBSyncTempEntity dbSyncTempEntity);
	
	/**
	 * 修改dbsync config表中设备的 所属馆id
	 *
	 * <p>2016年9月23日 上午9:49:23 
	 * <p>create by hjc
	 * @param param
	 * @return
	 */
	public abstract int updateDeviceDBsyncLibrary(Map<String, Object> param);
	/**
	 * 根据library_idx查询 模板数据
	 * @param params
	 * @return
	 */
	List<DeviceDBSyncConfigEntity> selectDeviceDBSyncModalByLibraryIdx(Map<String, Object> params);
	/**
	 * 根据library_idx查询 模板数据<br>
	 * 参数 {"library_idx":""}
	 * @param params
	 * @return
	 */
	List<DeviceDBSyncTempEntity> seldbsyncTempModalByLibraryIdx(Map<String, Object> params);
	/**
	 * 帶上 主鍵 插入數據 
	 * @param ddJson
	 * @return
	 */
	int insertDeviceDBSyncTempWithIdx(DeviceDBSyncTempEntity ddJson);

}
