package com.ssitcloud.service;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.entity.DeviceConfigEntity;
import com.ssitcloud.entity.DeviceRunConfigEntity;
import com.ssitcloud.entity.DeviceRunTempEntity;
import com.ssitcloud.entity.MetaRunConfigEntity;
import com.ssitcloud.entity.page.DeviceRunTempPageEntity;
import com.ssitcloud.system.entity.DeviceRunConfigRemoteEntity;

public interface DeviceRunConfService {

	//设备外设硬件配置
	int insertDeviceRunConfig(DeviceRunConfigEntity DeviceRunConfig);
	
	int insertBatchDeviceRunConfig(List<DeviceRunConfigEntity> deviceRunConfigs);

	int updateDeviceRunConfig(DeviceRunConfigEntity deviceRunConfig);

	int deleteDeviceRunConfig(DeviceRunConfigEntity deviceRunConfig);

	

	List<DeviceRunConfigEntity> selectList(DeviceRunConfigEntity deviceRunConfig);
	
	//设备运行模板
	int insertDeviceRunTemp(DeviceRunTempEntity deviceRunTempEntity);
	
	int updateDeviceRunTemp(DeviceRunTempEntity deviceRunTempEntity);
	
	int deleteDeviceRunTemp(DeviceRunTempEntity deviceRunTempEntity);
	

	
	List<DeviceRunTempEntity> selectList(DeviceRunTempEntity deviceRunTempEntity);

	//设备运行元数据表
	int insertMetaRunConfig(MetaRunConfigEntity metaRunConfigEntity);

	int updateMetaRunConfig(MetaRunConfigEntity metaRunConfigEntity);
	
	int deleteMetaRunConfig(MetaRunConfigEntity metaRunConfigEntity);
	

	
	List<MetaRunConfigEntity> selectList(MetaRunConfigEntity metaRunConfigEntity);
	
	/**
	 * 获取所有的运行配置模板
	 *
	 * <p>2016年4月26日 下午5:40:08
	 * <p>create by hjc
	 * @return
	 */
	public abstract List<Map<String, Object>> selAllRunTempList(Map<String, String> param);
	
	
	public abstract List<Map<String, Object>> selRunDataList(String json);
	
	int deleteRunData(DeviceRunConfigEntity deviceRunConfig);
	
	/**
	 * 根据条件获取运行模板分页信息
	 *
	 * <p>2016年6月18日 下午5:15:53 
	 * <p>create by hjc
	 * @param pageEntity
	 * @return
	 */
	public abstract DeviceRunTempPageEntity selRunTempListByParam(DeviceRunTempPageEntity pageEntity);
	
	/**
	 * 更新运行模板配置
	 *
	 * <p>2016年6月20日 下午3:52:55 
	 * <p>create by hjc
	 * @param extConfStr
	 * @return
	 */
	public abstract ResultEntity addRunTemp(String runConfStr);
	
	/**
	 * 更新运行模板配置
	 *
	 * <p>2016年6月20日 下午3:52:55 
	 * <p>create by hjc
	 * @param extConfStr
	 * @return
	 */
	public abstract ResultEntity updateRunTemp(String runConfStr);
	
	/**
	 * 删除模板数据
	 *
	 * <p>2016年6月16日 上午9:34:44 
	 * <p>create by hjc
	 * @param idx
	 * @return
	 */
	public abstract ResultEntity delRunTemp(String idx);
	
	/**
	 * 删除模板数据
	 *
	 * <p>2016年6月16日 上午9:34:44 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity delMultiRunTemp(String req);
	/**
	 * 模板该自定义数据
	 * 或者自定义数据改自定义数据
	 * @param json
	 * @return
	 */
	ResultEntity DelAndAddDeviceRunConf(String json);
	/**
	 * 查询 DeviceRunConfig 、 MetadataRunConfig
	 * @param req
	 * @return
	 */
	List<DeviceRunConfigEntity> queryDeviceRunConfigAndMetadataRunConfig(String req);
	/**
	 * 
	 * @param req
	 * @return
	 */
	ResultEntity SelFunctionByDeviceIdx(String req);
	
	public List<DeviceRunConfigRemoteEntity> getDeviceRunConfig(DeviceConfigEntity configEntity);
}
