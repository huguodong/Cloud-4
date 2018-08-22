package com.ssitcloud.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.entity.DeviceConfigEntity;
import com.ssitcloud.entity.DeviceRunConfigEntity;
import com.ssitcloud.entity.DeviceRunTempEntity;
import com.ssitcloud.entity.MetaRunConfigEntity;
import com.ssitcloud.entity.page.DeviceRunTempPageEntity;
import com.ssitcloud.system.entity.DeviceRunConfigRemoteEntity;

public interface DeviceRunConfDao {

	int insertDeviceRunConfig(DeviceRunConfigEntity deviceRunConfig);

	int insertBatchDeviceRunConfig(List<DeviceRunConfigEntity> deviceRunConfigs);

	int updateDeviceRunConfig(DeviceRunConfigEntity deviceRunConfig);
	
	/**
	 * 运行配置ID==设备IDX
	 * @methodName: updateDeviceRunConfig
	 * @param params
	 * @return
	 * @returnType: int
	 * @author: liuBh
	 */
	int updateDeviceRunConfig(Map<String,Object> params);
	
	

	int deleteDeviceRunConfig(DeviceRunConfigEntity deviceRunConfig);

	List<DeviceRunConfigEntity> selectList(DeviceRunConfigEntity deviceRunConfig);
	

	int insertDeviceRunTemp(DeviceRunTempEntity deviceRunTempEntity);

	int updateDeviceRunTemp(DeviceRunTempEntity deviceRunTempEntity);

	int deleteDeviceRunTemp(DeviceRunTempEntity deviceRunTempEntity);

	List<DeviceRunTempEntity> selectList(DeviceRunTempEntity deviceRunTempEntity);
	/**
	 * 根据device_type获取所有运行模板
	 * <p>2016年5月6日 下午4:56:36 
	 * <p>create by hjc
	 * @param deviceRunTempEntity
	 * @return
	 */
	public abstract List<DeviceRunTempEntity> selectListByDeviceType(DeviceRunTempEntity deviceRunTempEntity);
	

	int insertMetaRunConfig(MetaRunConfigEntity metaRunConfigEntity);

	int deleteMetaRunConfig(MetaRunConfigEntity metaRunConfigEntity);

	int updateMetaRunConfig(MetaRunConfigEntity metaRunConfigEntity);

	List<MetaRunConfigEntity> selectList(MetaRunConfigEntity metaRunConfigEntity);

	
	/**
	 * 根据运行配置模板的idx获取该模板的所有配置信息list
	 *
	 * <p>2016年4月26日 下午6:26:48
	 * <p>create by hjc
	 * @param runTempIdx
	 * @return
	 */
	public abstract List<Map<String, Object>> selRunDetailListByIdx(Integer runTempIdx);
	/**
	 * 查询设备device_run_config 返回给 设备端
	 * @param deviceRunConfig
	 * @return
	 */
	List<DeviceRunConfigRemoteEntity> queryDeviceRunConfigByDown(DeviceRunConfigEntity deviceRunConfig);
	/**
	 * 查询设备 device_run_config 和 metadata_run_config
	 * @param deviceRunConfig
	 * @return
	 */
	List<DeviceRunConfigEntity> queryDeviceRunConfigAndMetadataRunConfig(DeviceRunConfigEntity deviceRunConfig);
	/**
	 * 根据id查询一个记录
	 * @author lbh
	 * @param deviceRunConfigEntity
	 * @return
	 */
	DeviceRunConfigEntity selectOneDeviceRunConfigByExtIdLimitOne(
			DeviceRunConfigEntity deviceRunConfigEntity);

	
	public abstract List<Map<String, Object>> selRunDataListByIdx(Integer runTempIdx);
	
	
	int deleteRunDataByidxAndtype(DeviceRunConfigEntity deviceRunConfigEntity);
	
	/**
	 * 获取运行模板分页信息
	 *
	 * <p>2016年6月18日 下午5:17:30 
	 * <p>create by hjc
	 * @param pageEntity
	 * @return
	 */
	public abstract DeviceRunTempPageEntity selRunTempListByParam(DeviceRunTempPageEntity pageEntity);
	
	/**
	 * 查询外设模板是否被使用
	 *
	 * <p>2016年6月17日 下午9:07:05 
	 * <p>create by hjc
	 * @param deviceConfigEntity
	 * @return
	 */
	public abstract int countUsingRunTempplate(DeviceConfigEntity deviceConfigEntity);
	/**
	 * 查询该设备的数据模板条数
	 * 查询必要条件
	 * device_run_id
	 * device_tpl_type
	 * library_idx
	 * @param deviceRunConfig
	 * @return
	 */
	Integer selectDataTypeCount(DeviceRunConfigEntity deviceRunConfig);
	
	
	
	/**
	 * 根据模板id查询是否有相同的
	 *
	 * <p>2016年7月15日 下午6:15:26 
	 * <p>create by hjc
	 * @param deviceExtTempEntity
	 * @return
	 */
	public abstract int countDeviceRunTempById(DeviceRunTempEntity deviceRunTempEntity);
	/**
	 * 根据模板名查询是否有相同的
	 *
	 * <p>2016年7月15日 下午6:15:39 
	 * <p>create by hjc
	 * @param deviceExtTempEntity
	 * @return
	 */
	public abstract int countDeviceRunTempByDesc(DeviceRunTempEntity deviceRunTempEntity);
	
	/**
	 * 修改run config表中设备的 所属馆id
	 *
	 * <p>2016年9月23日 上午9:49:23 
	 * <p>create by hjc
	 * @param param
	 * @return
	 */
	public abstract int updateDeviceRunLibrary(Map<String, Object> param);

	List<DeviceRunConfigEntity> selectDeviceRunModalByLibraryIdx(
			Map<String, Object> params);

	List<DeviceRunTempEntity> selectDeviceRunTempModalByLibraryIdx(
			Map<String, Object> params);

	int insertDeviceRunTempWithIdx(DeviceRunTempEntity ddJson);
}
