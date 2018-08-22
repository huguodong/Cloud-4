package com.ssitcloud.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.devmgmt.entity.SpecialDeviceTypeEntity;
import com.ssitcloud.devmgmt.entity.SpecialDeviceTypePageEntity;

public interface SpecialDeviceTypeDao {
	
	public List<SpecialDeviceTypeEntity> querySpecialDeviceTypeByParams(Map<String, String> map);
	
	public SpecialDeviceTypePageEntity querySpecialDeviceTypeByPage(SpecialDeviceTypePageEntity pageEntity);
	
	public int addSpecialDeviceType(SpecialDeviceTypeEntity deviceTypeEntity);
	
	public int deleteSpecialDeviceType(Map<String, String> params);
	
	public int editSpecialDeviceType(SpecialDeviceTypeEntity deviceTypeEntity);
	
}
