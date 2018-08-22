package com.ssitcloud.service;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.entity.DeviceConfigEntity;
/**
 * 设备配置
 * @package: com.ssitcloud.service
 * @classFile: DeviceConfigService
 * @author: liuBh
 * @description: TODO
 */
public interface DeviceConfigService {

	int insertDeviceConfig(DeviceConfigEntity deviceConfig);

	int updateDeviceConfig(DeviceConfigEntity deviceConfig);

	List<DeviceConfigEntity> selectList(DeviceConfigEntity deviceConfig);
	
	int deleteDeviceConfig(DeviceConfigEntity deviceConfig);

	List<DeviceConfigEntity> queryDeviceConfigByDevIdAndLibIdx(Map<String, Object> params);

	ResultEntity downloadCfgSync(String req);
	/**
	 * 根据device_id查询实体
	 * req={
	 * 	"device_idx":"xxxx"
	 * }
	 * @author lbh
	 * @param req
	 * @return
	 */
	ResultEntity queryDeviceConfigByDeviceIdx(String req);

	ResultEntity queryDeviceConfigNewAndOldByDeviceIdx(String req);
	/**
	 * 根据device_id查询实体
	 * req={
	 * 	"device_idx":"xxxx"
	 * }
	 * @author lbh
	 * @param req
	 * @return
	 */
	DeviceConfigEntity queryDeviceConfigByDeviceIdx(DeviceConfigEntity deviceConfigEntity);
	/**
	 * req={
	 * 	table_name:"",model_idx:""
	 * }
	 * @param req
	 * @return
	 */
	ResultEntity queryDevIdsByModelAndModelIdx(String req);
	
}
