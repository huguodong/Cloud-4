package com.ssitcloud.dblib.service;

import com.ssitcloud.dblib.entity.DeviceReaderEntity;

public interface DeviceReaderService {
	
	/**
	 * 新增一条读者数据
	 *
	 * <p>2017年4月20日 下午5:12:24 
	 * <p>create by hjc
	 * @param deviceReaderEntity
	 * @return
	 */
	public abstract int saveDeviceReaderInfo(DeviceReaderEntity deviceReaderEntity);
	/**
	 * 更新读者信息
	 *
	 * <p>2017年4月20日 下午5:54:20 
	 * <p>create by hjc
	 * @param deviceReaderEntity
	 * @return
	 */
	public abstract int updateDeviceReaderInfo(DeviceReaderEntity deviceReaderEntity);
	
	/**
	 * 查找是不是有相同的读者
	 *
	 * <p>2017年4月20日 下午5:52:14 
	 * <p>create by hjc
	 * @param deviceReaderEntity
	 * @return
	 */
	public abstract int countReaderByLibIdxAndCardno(DeviceReaderEntity deviceReaderEntity);
	

}
