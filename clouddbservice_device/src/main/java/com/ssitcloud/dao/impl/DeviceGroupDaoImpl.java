package com.ssitcloud.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.DeviceGroupDao;
import com.ssitcloud.entity.DeviceEntity;
import com.ssitcloud.entity.DeviceGroupEntity;
/**
 * 
 * @comment 实现设备组表的增删改查。表名：device_group
 * 
 * @author hwl
 * @data 2016年4月6日
 */
@Repository
public class DeviceGroupDaoImpl extends CommonDaoImpl implements DeviceGroupDao {

	@Override
	public int insert(DeviceGroupEntity deviceGroupEntity) {

		return this.sqlSessionTemplate.insert("devicegroup.insert",deviceGroupEntity);
	}

	@Override
	public int update(DeviceGroupEntity deviceGroupEntity) {

		return this.sqlSessionTemplate.update("devicegroup.update", deviceGroupEntity);
	}

	@Override
	public int delete(DeviceGroupEntity deviceGroupEntity) {

		return this.sqlSessionTemplate.delete("devicegroup.delete", deviceGroupEntity);
	}

	@Override
	public List<DeviceGroupEntity> selectByid(
			DeviceGroupEntity deviceGroupEntity) {

		return this.sqlSessionTemplate.selectList("devicegroup.selectByidx", deviceGroupEntity);
	}

	@Override
	public DeviceGroupEntity selectOne(DeviceGroupEntity deviceGroupEntity) {

		return this.sqlSessionTemplate.selectOne("devicegroup.selectOne", deviceGroupEntity);
	}
	@Override
	public Map<String,Object> selectGroupByDeviceIdx(DeviceEntity device){
		if(device==null||device.getDevice_idx()==null) return null;
		return this.sqlSessionTemplate.selectOne("devicegroup.selectGroupByDeviceIdx", device.getDevice_idx());
	}
	
	@Override
	public PageEntity selectPage(Map<String, String> map) {
		DeviceGroupEntity dGroupEntity = JsonUtils.fromJson(map.get("json"), DeviceGroupEntity.class);
		PageEntity pageEntity = JsonUtils.fromJson(map.get("page"), PageEntity.class);
		int total=Integer.parseInt(this.sqlSessionTemplate.selectOne("devicegroup.count", dGroupEntity).toString());
		pageEntity.setTotal(total);
		RowBounds rowBounds = new RowBounds(pageEntity.getBeginIndex(), pageEntity.getPageSize());
		pageEntity.setRows(this.sqlSessionTemplate.selectList("devicegroup.selectByidx", dGroupEntity, rowBounds));
		return pageEntity;
	}

	@Override
	public List<DeviceGroupEntity> selectgroup() {
	
		return this.sqlSessionTemplate.selectList("devicegroup.selectgroup");
	}
	@Override
	public List<DeviceGroupEntity> selectgroupAll(){
		
		return this.sqlSessionTemplate.selectList("devicegroup.selectgroupAll");

	}

	@Override
	public List<DeviceGroupEntity> selectgroupBylib_idx(int library_idx) {
	
		return this.sqlSessionTemplate.selectList("devicegroup.selectgroupBylib_idx", library_idx);
	}
	
	@Override
	public List<DeviceGroupEntity> selectgroupBylib_idxs(
			List<Integer> libraryIdxs) {
		return this.sqlSessionTemplate.selectList("devicegroup.selectgroupBylib_idxs", libraryIdxs);
	}

	@Override
	public DeviceGroupEntity selectByDeviceGroupIdx(
			DeviceGroupEntity deviceGroupEntity) {
		return this.sqlSessionTemplate.selectOne("devicegroup.selectByDeviceGroupIdx",deviceGroupEntity);
	}

	@Override
	public List<DeviceGroupEntity> selectgroupBylibIdxAndDeviceGroupId(DeviceGroupEntity deviceGroupEntity) {
		return  this.sqlSessionTemplate.selectList("devicegroup.selectgroupBylibIdxAndDeviceGroupId", deviceGroupEntity);
	}
	
}
