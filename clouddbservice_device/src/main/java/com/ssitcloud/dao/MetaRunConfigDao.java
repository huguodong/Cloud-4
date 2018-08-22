package com.ssitcloud.dao;

import java.util.List;


import java.util.Map;

import com.ssitcloud.entity.DeviceRunConfigEntity;
import com.ssitcloud.entity.MetaRunConfigEntity;
import com.ssitcloud.system.entity.MetaRunConfigRemoteEntity;

public interface MetaRunConfigDao {

	List<MetaRunConfigRemoteEntity> selectListByDeviceRunConfig(DeviceRunConfigEntity deviceRunConfig);
	
	/**
	 * 根据run_conf_type查询MetaRunConfigEntity的信息
	 *
	 * <p>2016年6月2日 上午10:40:22 
	 * <p>create by hjc
	 * @param metaRunConfigEntity
	 * @return
	 */
	public abstract MetaRunConfigEntity selMetaRunConfigEntityByConfType(MetaRunConfigEntity metaRunConfigEntity);
	/**
	 * 
	 * @param metaRunConfig
	 * @return
	 */
	MetaRunConfigEntity selOneByIdx(MetaRunConfigEntity metaRunConfig);

	int updateByMap(Map<String, Object> m);

	int insertMapWithIdx(Map<String, Object> m);

}
