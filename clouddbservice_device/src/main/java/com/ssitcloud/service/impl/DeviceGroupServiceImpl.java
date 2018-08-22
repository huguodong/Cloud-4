package com.ssitcloud.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.DeviceGroupDao;
import com.ssitcloud.entity.DeviceEntity;
import com.ssitcloud.entity.DeviceGroupEntity;
import com.ssitcloud.service.DeviceGroupService;
/**
 * 
 * @comment 设备组表Service
 * 
 * @author hwl
 * @data 2016年4月6日
 */
@Service
public class DeviceGroupServiceImpl implements DeviceGroupService {

	@Resource
	DeviceGroupDao deviceGroupDao;
	
	
	/***
	 * 根据要求 一个图书馆IDX下的分组ID不能重复
	 */
	@Override
	public int addDeviceGroup(DeviceGroupEntity deviceGroupEntity) {
		// TODO Auto-generated method stub
		return deviceGroupDao.insert(deviceGroupEntity);
	}

	@Override
	public int updDeviceGroup(DeviceGroupEntity deviceGroupEntity) {
		// TODO Auto-generated method stub
		return deviceGroupDao.update(deviceGroupEntity);
	}

	@Override
	public int delDeviceGroup(DeviceGroupEntity deviceGroupEntity) {
		// TODO Auto-generated method stub
		return deviceGroupDao.delete(deviceGroupEntity);
	}

	@Override
	public List<DeviceGroupEntity> selbyidDeviceGroup(
			DeviceGroupEntity deviceGroupEntity) {
		// TODO Auto-generated method stub
		return deviceGroupDao.selectByid(deviceGroupEntity);
	}

	@Override
	public DeviceGroupEntity seloneDeviceGroup(DeviceGroupEntity deviceGroupEntity) {
		// TODO Auto-generated method stub
		return deviceGroupDao.selectOne(deviceGroupEntity);
	}

	@Override
	public PageEntity selPageDeviceGroup(Map<String, String> map) {
		// TODO Auto-generated method stub
		return deviceGroupDao.selectPage(map);
	}

	@Override
	public List<DeviceGroupEntity> selectgroup() {
		// TODO Auto-generated method stub
		return deviceGroupDao.selectgroup();
	}

	@Override
	public List<DeviceGroupEntity> selectgroupBylib_idx(int library_idx) {
		
		return deviceGroupDao.selectgroupBylib_idx(library_idx);
	}

	@Override
	public List<DeviceGroupEntity> selectgroupBylib_idxs(
			List<Integer> libraryIdxs) {
		return deviceGroupDao.selectgroupBylib_idxs(libraryIdxs);
	}

	@Override
	public ResultEntity selectGroupByDeviceIdx(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			DeviceEntity device=JsonUtils.fromJson(req, DeviceEntity.class);
			Map<String, Object> resultMap=deviceGroupDao.selectGroupByDeviceIdx(device);
			result.setResult(resultMap);
			result.setState(true);
		}
		return result;
	}
	@Override
	public DeviceGroupEntity selectByDeviceGroupIdx(DeviceGroupEntity d){
		return deviceGroupDao.selectByDeviceGroupIdx(d);
	}

	@Override
	public List<DeviceGroupEntity> selectgroupBylibIdxAndDeviceGroupId(
			DeviceGroupEntity d) {
		return deviceGroupDao.selectgroupBylibIdxAndDeviceGroupId(d);
	}

	

}
