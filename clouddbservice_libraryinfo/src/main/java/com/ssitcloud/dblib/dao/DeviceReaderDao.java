package com.ssitcloud.dblib.dao;

import com.ssitcloud.dblib.entity.DeviceReaderEntity;

public interface DeviceReaderDao {
	
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
	 * <p>2017年4月20日 下午5:54:44 
	 * <p>create by hjc
	 * @param deviceReaderEntity
	 * @return
	 */
	public abstract int updateDeviceReaderInfo(DeviceReaderEntity deviceReaderEntity);
	
	/**
	 * 计算同一个图书馆中相同读者证号的读者
	 *
	 * <p>2017年4月20日 下午5:52:47 
	 * <p>create by hjc
	 * @param deviceReaderEntity
	 * @return
	 */
	public abstract int countReaderByLibIdxAndCardno(DeviceReaderEntity deviceReaderEntity);

}
