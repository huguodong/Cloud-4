package com.ssitcloud.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.MetadataOrderDao;
import com.ssitcloud.entity.MetadataOrderEntity;
import com.ssitcloud.system.entity.MetadataOrderRemoteEntity;
/**
 * 
 * @comment 实现监控指令元数据表的增删改查
 * 
 * @author hwl
 * @data 2016年4月9日
 */
@Repository
public class MetadataOrderDaoImpl extends CommonDaoImpl implements
		MetadataOrderDao {

	@Override
	public int insert(MetadataOrderEntity metadataOrderEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("metadataorder.insert", metadataOrderEntity);
	}

	@Override
	public int delete(MetadataOrderEntity metadataOrderEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.delete("metadataorder.delete", metadataOrderEntity);
	}

	@Override
	public int update(MetadataOrderEntity metadataOrderEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("metadataorder.update", metadataOrderEntity);
	}

	@Override
	public List<MetadataOrderEntity> select(
			MetadataOrderEntity metadataOrderEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("metadataorder.select", metadataOrderEntity);
	}
	@Override
	public List<MetadataOrderRemoteEntity> selectByDown(
			MetadataOrderEntity metadataOrderEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("metadataorder.selectByDown", metadataOrderEntity);
	}
	
	

}
