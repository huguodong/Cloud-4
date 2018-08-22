package com.ssitcloud.dao;

import java.util.List;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.devmgmt.entity.DeviceViewConfigSet;
import com.ssitcloud.devmgmt.entity.DeviceViewDataEntity;



public interface DeviceViewConfigDao {
	/**查询配置数据*/
	public List<DeviceViewDataEntity> queryDeviceViewConfig(String fieldSet);
	
	//public ResultEntity updateDeviceViewConfig(String req);
	
	public List<DeviceViewConfigSet> queryDeviceViewConfigSet(String req);
	
	public ResultEntity deleteDeviceViewConfigSet(String req);
	
	public ResultEntity insertDeviceViewConfigSet(String req);
	
}
