package com.ssitcloud.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.entity.DeviceExtConfigEntity;
import com.ssitcloud.entity.MetadataExtModelEntity;
import com.ssitcloud.system.entity.MetadataExtModelRemoteEntity;

public interface MetadataExtModelDao {

	List<MetadataExtModelEntity> selectList(DeviceExtConfigEntity deviceExtConfig);

	List<MetadataExtModelEntity> select(
			MetadataExtModelEntity metadataExtModelEntity);

	/**
	 * 
	 * <p>2016年6月24日 下午5:26:53
	 * <p>create by lbh
	 * @param deviceExtConfig
	 * @return
	 */
	List<MetadataExtModelRemoteEntity> selectListByDeviceExtConfigDown(DeviceExtConfigEntity deviceExtConfig);

	MetadataExtModelEntity selOneByIdx(
			MetadataExtModelEntity metadataExtModelEntity);

	int updateByMap(Map<String, Object> m);

	int insertMapWithIdx(Map<String, Object> m);

}
