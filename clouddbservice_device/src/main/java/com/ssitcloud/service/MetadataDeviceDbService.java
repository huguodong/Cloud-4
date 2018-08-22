/**
 * 
 */
package com.ssitcloud.service;

import java.util.List;

import com.ssitcloud.entity.MetadataDeviceDbAndTableInfoEntity;
import com.ssitcloud.entity.MetadataDeviceDbEntity;

public interface MetadataDeviceDbService {

	public abstract List<MetadataDeviceDbEntity> selectMetadataDeviceDb(
			MetadataDeviceDbEntity metadataDeviceDbEntity);
	
	public abstract List<MetadataDeviceDbAndTableInfoEntity> SelectMetadataDeviceDbAndTableInfo(
			MetadataDeviceDbEntity metadataDeviceDbEntity);
	

}
