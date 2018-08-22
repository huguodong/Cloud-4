package com.ssitcloud.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.DeviceServiceDao;
import com.ssitcloud.devmgmt.entity.DeviceServiceEntity;
import com.ssitcloud.devmgmt.entity.DeviceServicePageEntity;
import com.ssitcloud.devmgmt.entity.DeviceServiceBaseEntity;

@Repository
public class DeviceServiceDaoImpl extends CommonDaoImpl implements DeviceServiceDao {
	
	/**插入服务*/
	public DeviceServiceEntity insertDeviceService(DeviceServiceEntity serviceEntity) {
		getSqlSession().insert("deviceService.insertDeviceService", serviceEntity);
		return serviceEntity;
	}

	/**分页查询服务*/
	public DeviceServicePageEntity queryDeviceServiceByPage(DeviceServicePageEntity pageEntity) {
		
		DeviceServicePageEntity deviceServicePageEntity = getSqlSession().selectOne("deviceService.queryDeviceServiceByPage", pageEntity);
		pageEntity.setTotal(deviceServicePageEntity == null ? 0 :deviceServicePageEntity.getTotal());
		pageEntity.setDoAount(false);
		List<DeviceServiceEntity> entities = getSqlSession().selectList("deviceService.queryDeviceServiceByPage", pageEntity);
		pageEntity.setRows(entities);
		return pageEntity;
	}

	/**根据参数查询服务*/
	public List<DeviceServiceEntity> queryDeviceServiceByParams(Map<String, String> params) {
		return getSqlSession().selectList("deviceService.queryDeviceServiceByParams", params);
	}
	
	
	/**
	 * 根据libidx获取特殊设备（3D导航、人流量、智能家具） 分组
	 * @return Map<library_idx, service_id>
	 */
	@Override
	public Map<Integer, String> queryServiceIdbyLibIdx() {
		return getSqlSession().selectMap("deviceService.queryServiceIdbyLibIdx", "library_idx");
	}
	
	@Override
	public Map<String, String> queryServiceIdbyLibId() {
		return getSqlSession().selectMap("deviceService.queryServiceIdbyLibId", "library_id");
	}
	/**
	 * 根据lServiceId获取特殊设备（3D导航、人流量、智能家具）信息
	 * @return Map<library_idx, service_id>
	 */
	@Override
	public List<DeviceServiceBaseEntity> queryDeviceServiceByServiceId(Map<String, String> params){
		return getSqlSession().selectList("deviceService.queryDeviceServiceByServiceId", params);
	}
	
	
	@Override
	public int editDeviceService(DeviceServiceEntity serviceEntity) {
		return getSqlSession().update("deviceService.editDeviceService", serviceEntity);
	}

	@Override
	public int deleteDeviceService(Map<String, String> map) {
		return getSqlSession().delete("deviceService.deleteDeviceService",map);
	}
	/**
	 * 根据特殊设备（3D导航、人流量、智能家具）服务器serviceidx获取服务名称 分组serviceid
	 * @return Map<library_idx, service_id>
	 */
	public Map<Integer, String> queryServiceIdbyServiceIdx(){
		return getSqlSession().selectMap("deviceService.queryServiceIdbyServiceIdx", "service_idx");
	}

	@Override
	public int updateDeviceServiceOperateTime(DeviceServiceEntity deviceServiceEntity) {
		
		return getSqlSession().update("deviceService.updateDeviceServiceOperateTime", deviceServiceEntity);
	}

	@Override
	public int selectCountDeviceService(Map<String, String> params) {
		
		return getSqlSession().selectOne("deviceService.selectCountDeviceService", params);
	}


}
