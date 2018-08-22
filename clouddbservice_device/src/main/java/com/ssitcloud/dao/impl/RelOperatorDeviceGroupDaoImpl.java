package com.ssitcloud.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.RelOperatorDeviceGroupDao;
import com.ssitcloud.entity.RelOperatorDeviceGroupEntity;

/**
 * 
 * @comment 实现操作员组和设备组关联表的增删改查。表：rel_operator_device_group
 * 
 * @author hwl
 * @data 2016年4月5日
 */
@Repository
public class RelOperatorDeviceGroupDaoImpl extends CommonDaoImpl implements
		RelOperatorDeviceGroupDao {

	@Override
	public int insert(RelOperatorDeviceGroupEntity relOperatorDeviceGroupEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("operatordevicegroup.insert",
				relOperatorDeviceGroupEntity);
	}

	@Override
	public int delete(RelOperatorDeviceGroupEntity relOperatorDeviceGroupEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.delete("operatordevicegroup.delete",
				relOperatorDeviceGroupEntity);
	}
	@Override
	public int deleteByOperGroupIdx(RelOperatorDeviceGroupEntity relOperatorDeviceGroupEntity){
		return this.sqlSessionTemplate.delete("operatordevicegroup.deleteByOperGroupIdx",
				relOperatorDeviceGroupEntity);
	}
	@Override
	public int update(RelOperatorDeviceGroupEntity relOperatorDeviceGroupEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("operatordevicegroup.update",
				relOperatorDeviceGroupEntity);
	}

	@Override
	public List<RelOperatorDeviceGroupEntity> selectByidx(
			RelOperatorDeviceGroupEntity relOperatorDeviceGroupEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate
				.selectList("operatordevicegroup.selectByidx",
						relOperatorDeviceGroupEntity);
	}

	@Override
	public List<RelOperatorDeviceGroupEntity> selectAll() {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate
				.selectList("operatordevicegroup.selectAll");
	}

	@Override
	public List<Map<String, Object>> queryLibraryServiceGroup(Map<String, String> param) {
		return this.sqlSessionTemplate.selectList("operatordevicegroup.queryLibraryServiceGroup",param);	
	}

	@Override
	public List<RelOperatorDeviceGroupEntity> selectByIdxArr(
			List<Integer> idxList) {
		return sqlSessionTemplate.selectList("operatordevicegroup.selectByIdxArr", idxList);
	}
	@Override
	public List<RelOperatorDeviceGroupEntity> selectByDeviceGroupLibraryIdx(Map<String,Object> map){
		return sqlSessionTemplate.selectList("operatordevicegroup.selectByDeviceGroupLibraryIdx",map);
	}
}
