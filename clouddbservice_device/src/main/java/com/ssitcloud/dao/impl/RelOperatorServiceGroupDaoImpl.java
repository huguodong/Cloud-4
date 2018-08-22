package com.ssitcloud.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.RelOperatorServiceGroupDao;
import com.ssitcloud.entity.RelOperatorServiceGroupEntity;

/**
 * @comment 实现操作员组和业务组关联表的增删改查。表：rel_operator_service_group
 * @author hwl
 * 
 * @data 2016-3-30上午10:40:52
 * 
 */
@Repository
public class RelOperatorServiceGroupDaoImpl extends CommonDaoImpl implements
		RelOperatorServiceGroupDao {

	@Override
	public int insert(
			RelOperatorServiceGroupEntity relOperatorServiceGroupEntity) {
		return this.sqlSessionTemplate.insert("operatorservicegroup.insert",
				relOperatorServiceGroupEntity);
	}

	@Override
	public int delete(
			RelOperatorServiceGroupEntity relOperatorServiceGroupEntity) {
		return this.sqlSessionTemplate.delete("operatorservicegroup.delete",
				relOperatorServiceGroupEntity);
	}
	@Override
	public int deleteByOperGroupIdx(RelOperatorServiceGroupEntity relOperatorServiceGroupEntity){
		return this.sqlSessionTemplate.delete("operatorservicegroup.deleteByOperGroupIdx",
				relOperatorServiceGroupEntity);
	}
	
	@Override
	public int update(
			RelOperatorServiceGroupEntity relOperatorServiceGroupEntity) {
		return this.sqlSessionTemplate.update("operatorservicegroup.update",
				relOperatorServiceGroupEntity);
	}

	@Override
	public List<RelOperatorServiceGroupEntity> selectByidx(
			RelOperatorServiceGroupEntity relOperatorServiceGroupEntity) {
		return this.sqlSessionTemplate.selectList(
				"operatorservicegroup.selectByidx",
				relOperatorServiceGroupEntity);
	}

	@Override
	public List<RelOperatorServiceGroupEntity> selectAll() {
		return this.sqlSessionTemplate
				.selectList("operatorservicegroup.selectAll");
	}
	@Override
	public List<RelOperatorServiceGroupEntity> selectByServGroupByLibraryIdx(
			Map<String, Object> map) {
		return this.sqlSessionTemplate.selectList("operatorservicegroup.selectByServGroupByLibraryIdx", map);
	}
	@Override
	public List<RelOperatorServiceGroupEntity> selectByOperGroupByLibraryIdx(Map<String, Object> map){
		return this.sqlSessionTemplate.selectList("operatorservicegroup.selectByOperGroupByLibraryIdx", map);

	}

}
