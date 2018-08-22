package com.ssitcloud.service;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.entity.DeviceConfigEntity;
import com.ssitcloud.entity.DeviceDBSyncConfigEntity;
import com.ssitcloud.entity.DeviceDBSyncTempEntity;
import com.ssitcloud.entity.page.DBSyncTempPageEntity;
import com.ssitcloud.system.entity.DeviceDBSyncConfigRemoteEntity;

public interface DeviceDBSyncConfService {
	/**
	 * 同步设置
	 * @methodName: insertDeviceDBSyncConf
	 * @param deviceDBSyncConfig
	 * @return
	 * @returnType: int
	 * @author: liuBh
	 */
	int insertDeviceDBSyncConf(DeviceDBSyncConfigEntity deviceDBSyncConfig);

	int updateDeviceDBSyncConf(DeviceDBSyncConfigEntity deviceDBSyncConfig);
	
	int InsertDbsyncAndUpdDevCfg(DeviceDBSyncConfigEntity deviceDBSyncConfig);
	/**
	 * 删除数据同步配置
	 * 
	 * 删除条件
	 * 	AND device_dbsync_id
		AND library_idx
		AND device_tpl_type
		AND db_name
		AND table_name
	 * @methodName: deleteDeviceDBSyncConf
	 * @param deviceDBSyncConfig
	 * @return
	 * @returnType: int
	 * @author: liuBh
	 */
	int deleteDeviceDBSyncConf(DeviceDBSyncConfigEntity deviceDBSyncConfig);

	List<DeviceDBSyncConfigEntity> selectDeviceDBSyncConfig(DeviceDBSyncConfigEntity deviceDBSyncConfig);

	List<DeviceDBSyncConfigEntity> selectList(DeviceDBSyncConfigEntity deviceDBSyncConfig);

	/**
	 * 同步模板设置
	 * @methodName: insertDeviceDBSyncTemp
	 * @param deviceDBSyncTemp
	 * @return
	 * @returnType: int
	 * @author: liuBh
	 */
	int insertDeviceDBSyncTemp(DeviceDBSyncTempEntity deviceDBSyncTemp);

	int updateDeviceDBSyncTemp(DeviceDBSyncTempEntity deviceDBSyncTemp);

	DeviceDBSyncTempEntity selectDeviceDBSyncTemp(DeviceDBSyncTempEntity deviceDBSyncTemp);

	List<DeviceDBSyncTempEntity> selectList(DeviceDBSyncTempEntity deviceDBSyncTemp);

	int deleteDeviceDBSyncTemp(DeviceDBSyncTempEntity deviceDBSyncTemp);
	
	/**
	 * 获取所有数据同步配置模板
	 *
	 * <p>2016年4月27日 上午11:38:00
	 * <p>create by hjc
	 * @return
	 */
	public abstract List<Map<String, Object>> selAllDBSyncTempList(Map<String, String> param );
	
	/**
	 * 分页查询同步模板信息
	 * @comment
	 * @param json
	 * @return
	 * @data 2016年6月2日
	 * @author hwl
	 */
	public abstract DBSyncTempPageEntity selDbsynctemp(String json);
	
	/**
	 * 删除数据同步模板信息
	 * @comment
	 * @param json
	 * @return
	 * @data 2016年6月2日
	 * @author hwl
	 */
	public abstract ResultEntity DelDbsynctemp(String json);
	
	/**
	 * 更新数据同步模板信息
	 * @comment
	 * @param json
	 * @return
	 * @data 2016年6月2日
	 * @author hwl
	 */
	public abstract ResultEntity UpdDbsynctemp(String json);
	
	/**
	 * 新增数据同步模板信息
	 * @comment
	 * @param json
	 * @return
	 * @data 2016年6月2日
	 * @author hwl
	 */
	public abstract ResultEntity AddDbsynctemp(String json);
	
	public List<DeviceDBSyncConfigRemoteEntity> getDeviceDBSyncConfig(DeviceConfigEntity configEntity);

}
