package com.ssitcloud.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.datasync.entity.DeviceAcsModuleEntity;
import com.ssitcloud.datasync.entity.ProtocolInfo;
import com.ssitcloud.entity.DeviceAcsLoginInfoEntity;
import com.ssitcloud.system.entity.DeviceAcsLoginInfoRemoteEntity;


public interface DeviceAcsLoginInfoDao {
	
	/**
	 * 新增一条数据
	 *
	 * <p>2016年6月30日 下午4:13:49 
	 * <p>create by hjc
	 * @param infoEntity
	 * @return
	 */
	public abstract int insertAcsInfo(DeviceAcsLoginInfoEntity infoEntity);
	/**
	 * 通过设备IDX查询 AcsInfo信息
	 * @param deviceIdx
	 * @return
	 */
	List<DeviceAcsLoginInfoEntity> queryAcsInfoByDeviceIdx(Integer deviceIdx);
	/**
	 * 通过deviceIdx删除 ACSinfo
	 * @param deviceIdx
	 * @return
	 */
	public abstract int deleteAcsInfoByDeviceIdx(int deviceIdx);
	/**
	 * 批量删除
	 *
	 * <p>2016年9月23日 上午9:59:57 
	 * <p>create by hjc
	 * @param deviceIdx
	 * @return
	 */
	public abstract int deleteAcsInfoByDeviceIdxs(Map<String, Object> param);
	/**
	 * 批量插入
	 * @param infoEntitys
	 * @return
	 */
	public abstract int insertAcsInfoBatch(List<DeviceAcsLoginInfoEntity> infoEntitys);
	
	/**
	 * 根据设备ID查询 ACSinfo ，设备端同步数据用
	 * @param device_id
	 * @return
	 */
	public abstract List<DeviceAcsLoginInfoRemoteEntity> queryAcsInfoByDeviceId(String device_id);
	/**
	 * query acs logininfo by deviceIdx 
	 * @param deviceIdx
	 * @return
	 */
	public abstract List<DeviceAcsLoginInfoEntity> loadAcsLogininfoByDeviceIdx(Integer deviceIdx);
	
	
	public abstract List<DeviceAcsLoginInfoEntity> queryAcsInfoByProtocolTplIdx(Integer protocol_tpl_idx);

	public abstract List<DeviceAcsModuleEntity> queryDeviceAcsModules(Map<String, Object> param);

	public abstract List<ProtocolInfo> queryDeviceAcsDetailModules(Map<String, Object> param);
	
	List<DeviceAcsLoginInfoEntity> queryAcsInfoByParams(Map<String, String> params);
}
