package com.ssitcloud.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.MetadataLogicObjDao;
import com.ssitcloud.entity.DeviceExtConfigEntity;
import com.ssitcloud.entity.MetadataLogicObjEntity;
import com.ssitcloud.system.entity.MetadataLogicObjRemoteEntity;

/**
 * 
 * @comment 实现逻辑对象元数据表的增删改查
 * 
 * @author hwl
 * @data 2016年4月11日
 */
@Repository
public class MetadataLogicObjDaoImpl extends CommonDaoImpl implements MetadataLogicObjDao {

	@Override
	public int insert(MetadataLogicObjEntity metadataLogicObjEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("metalogicobj.insert", metadataLogicObjEntity);
	}

	@Override
	public int delete(MetadataLogicObjEntity metadataLogicObjEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.delete("metalogicobj.delete", metadataLogicObjEntity);
	}

	@Override
	public int update(MetadataLogicObjEntity metadataLogicObjEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("metalogicobj.update", metadataLogicObjEntity);
	}

	@Override
	public List<MetadataLogicObjEntity> select(MetadataLogicObjEntity metadataLogicObjEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("metalogicobj.select", metadataLogicObjEntity);
	}
	@Override
	public List<MetadataLogicObjRemoteEntity> selectListByDeviceExtConfig(DeviceExtConfigEntity deviceExtConfig){
		
		return this.sqlSessionTemplate.selectList("metalogicobj.selectListByDeviceExtConfig", deviceExtConfig);
	}

	@Override
	public List<MetadataLogicObjEntity> selectInlogicObjArr(List<String> logicObjList) {
		
		return this.sqlSessionTemplate.selectList("metalogicobj.selectInlogicObjArr", logicObjList);
	}

	@Override
	public MetadataLogicObjEntity selOneByIdx(
			MetadataLogicObjEntity metadataLogicObjEntity) {
		return this.sqlSessionTemplate.selectOne("metalogicobj.selOneByIdx", metadataLogicObjEntity);
	}

	@Override
	public int insertMapWithIdx(Map<String, Object> m) {
		return this.sqlSessionTemplate.selectOne("metalogicobj.insertMapWithIdx", m);
	}

	@Override
	public int updateByMap(Map<String, Object> m) {
		return this.sqlSessionTemplate.update("metalogicobj.updateByMap", m);
	}
}
