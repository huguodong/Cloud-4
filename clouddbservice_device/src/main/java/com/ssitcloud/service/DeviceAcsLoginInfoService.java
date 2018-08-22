package com.ssitcloud.service;

import java.util.List;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.datasync.entity.DeviceAcsModuleEntity;


/**
 * acs表操作
 *
 * <p>2016年6月30日 下午4:10:13  
 * @author hjc 
 *
 */
public interface DeviceAcsLoginInfoService {
	/**
	 * 根据device_idx 获取 acs logininfo
	 * @param req
	 * @return
	 */
	ResultEntity loadAcsLogininfo(String req);

	/**
	 * 根据图书馆libId和设备id查询
	 */
	List<DeviceAcsModuleEntity> loadAcsXml(String libraryId, String device_id);
}
