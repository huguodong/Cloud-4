package com.ssitcloud.business.mobile.fatory;

import java.util.HashMap;
import java.util.Map;

import com.ssitcloud.authentication.entity.LibraryInfoEntity;
import com.ssitcloud.authentication.entity.LibraryUnionEntity;
import com.ssitcloud.devmgmt.entity.DeviceEntity;
import com.ssitcloud.mobile.entity.AppOPACEntity;

public class AppOPACEntityFatory {
	public static AppOPACEntity createEntity(DeviceEntity obj) {
		AppOPACEntity opacDev = new AppOPACEntity();

		Map<String, Object> idData = new HashMap<>(3);
		Map<String, Object> otherData = new HashMap<>(10);

		idData.put("device_idx", obj.getDevice_idx());
		idData.put("device_id", obj.getDevice_id());
		idData.put("logistics_number", obj.getLogistics_number());
		
		otherData.put("device_id", obj.getDevice_id());
		otherData.put("device_idx", obj.getDevice_idx());
		otherData.put("device_code", obj.getDevice_code());
		otherData.put("device_name", obj.getDevice_name());

		opacDev.setType(1);
		opacDev.setIdData(idData);
		opacDev.setOtherData(otherData);
		opacDev.setDevName(obj.getDevice_name());
		opacDev.setLocation(obj.getCircule_location());

		return opacDev;
	}

	public static AppOPACEntity createEntity(LibraryUnionEntity obj) {
		AppOPACEntity opacDev = new AppOPACEntity();

		Map<String, Object> idData = new HashMap<>(3);
		Map<String, Object> otherData = new HashMap<>(10);

		idData.put("nowlib_idx", obj.getLibrary_idx());
		idData.put("lib_id", obj.getLib_id());
		
		otherData.put("nowlib_idx", obj.getLibrary_idx());
		otherData.put("lib_id", obj.getLib_id());
		otherData.put("device_name", obj.getLib_name());

		opacDev.setType(2);
		opacDev.setIdData(idData);
		opacDev.setOtherData(otherData);
		opacDev.setDevName(obj.getLib_name());

		// 提取地址
		Integer addressId = Integer.valueOf(16);
		for (LibraryInfoEntity info : obj.getLibraryInfoEntitys()) {
			if (addressId.equals(info.getInfotype_idx())) {
				opacDev.setLocation(info.getInfo_value());
				break;
			}
		}

		return opacDev;
	}

}
