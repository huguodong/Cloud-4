package com.ssitcloud.interfaceconfig.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssitcloud.devmgmt.entity.ConfigDataEntity;
import com.ssitcloud.devmgmt.entity.ConfigDataEntityF;
import com.ssitcloud.devmgmt.entity.DataEntity;
import com.ssitcloud.devmgmt.entity.DeviceType;
import com.ssitcloud.devmgmt.entity.QueryDevice;

public interface JsonDataDao {
	public List<DataEntity> getJsonData(HashMap<String, String> map);

	public int saveJsonData(ConfigDataEntity entity);

	public String getInitData(Map<String, Object> map);

	public int queryExsit(Map<String, Object> map);

	public int updateJsonData(ConfigDataEntity entity);

	public List<QueryDevice> queryAllLibDev(HashMap<String, Object> map);

	public List<DeviceType> getDevTypeData(String libId);

	public int queryLibExsit(Map<String, String> map);

	public List<DataEntity> getViewConfigData();

	public List<ConfigDataEntity> getConfigDataEntity(HashMap<String, Object> map);
	
	public List<DataEntity> queryViewConfigData();
	
	public List<ConfigDataEntityF> queryConfigDataByDevIdAndLibIdx(Map<String, Object> params);
	
	public List<ConfigDataEntity> selectDeviceIdxByLibId(Map<String, Object> params); 
}
