package com.ssitcloud.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.dao.CommonDao;
import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.entity.DeviceEntity;
import com.ssitcloud.entity.DeviceGroupEntity;

/**
 * 
 * @comment
 * 
 * @author hwl
 * @data 2016年4月6日
 */
public interface DeviceGroupDao extends CommonDao {

	public abstract int insert(DeviceGroupEntity deviceGroupEntity);

	public abstract int update(DeviceGroupEntity deviceGroupEntity);

	public abstract int delete(DeviceGroupEntity deviceGroupEntity);

	public abstract List<DeviceGroupEntity> selectByid(
			DeviceGroupEntity deviceGroupEntity);

	public abstract DeviceGroupEntity selectOne(DeviceGroupEntity deviceGroupEntity);

	public abstract PageEntity selectPage(Map<String, String> map);
	
	public abstract List<DeviceGroupEntity> selectgroup();

	public abstract List<DeviceGroupEntity> selectgroupBylib_idx(int library_idx);
	public abstract List<DeviceGroupEntity> selectgroupBylib_idxs(List<Integer> libraryIdxs);
	
	List<DeviceGroupEntity> selectgroupAll();
	/**
	 * 通过设备IDX查询出设备分组信息
	 * @param device
	 * @return
	 */
	Map<String, Object> selectGroupByDeviceIdx(DeviceEntity device);
	/**
	 * 根据device_group_idx（主键） 查询
	 * @param deviceGroupEntity
	 * @return
	 */
	public abstract DeviceGroupEntity selectByDeviceGroupIdx(
			DeviceGroupEntity deviceGroupEntity);
	/**
	 * 根据图书馆IDX和设备分组ID查询 分组数据
	 * @param deviceGroupEntity
	 * @return
	 */
	public abstract List<DeviceGroupEntity> selectgroupBylibIdxAndDeviceGroupId(
			DeviceGroupEntity deviceGroupEntity);
	
	

}
