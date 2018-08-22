package com.ssitcloud.entity;

import java.util.List;

/**
 * 
 * metadataDeviceType(设备类型) 和 其对应的 metadataLogicObjs (逻辑外设) 对象
 * 
 * @author lbh
 *
 */
public class MetadataDeviceTypeAndLogicObjEntity {
	
	private  MetadataDeviceTypeEntity metadataDeviceType;
	
	private  List<MetadataLogicObjEntity> metadataLogicObjs;

	public MetadataDeviceTypeEntity getMetadataDeviceType() {
		return metadataDeviceType;
	}

	public void setMetadataDeviceType(MetadataDeviceTypeEntity metadataDeviceType) {
		this.metadataDeviceType = metadataDeviceType;
	}

	public List<MetadataLogicObjEntity> getMetadataLogicObjs() {
		return metadataLogicObjs;
	}

	public void setMetadataLogicObjs(List<MetadataLogicObjEntity> metadataLogicObjs) {
		this.metadataLogicObjs = metadataLogicObjs;
	}
	
	
}
