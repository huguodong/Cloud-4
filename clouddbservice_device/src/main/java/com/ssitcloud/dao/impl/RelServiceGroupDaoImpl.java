package com.ssitcloud.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.RelServiceGroupDao;
import com.ssitcloud.entity.RelServiceGroupEntity;

/**
 * 
 * @comment 实现业务分组关联表的增删改查。表：rel_service_group
 * 
 * @author hwl
 * @data 2016年4月6日
 */
@Repository
public class RelServiceGroupDaoImpl extends CommonDaoImpl implements
		RelServiceGroupDao {

	@Override
	public int insert(RelServiceGroupEntity relServiceGroupEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("relservicegroup.insert",
				relServiceGroupEntity);
	}

	@Override
	public int update(RelServiceGroupEntity relServiceGroupEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("relservicegroup.update",
				relServiceGroupEntity);
	}

	@Override
	public int delete(RelServiceGroupEntity relServiceGroupEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.delete("relservicegroup.delete",
				relServiceGroupEntity);
	}

	@Override
	public List<RelServiceGroupEntity> selectByidx(
			RelServiceGroupEntity relServiceGroupEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList(
				"relservicegroup.selectByidx", relServiceGroupEntity);
	}

	@Override
	public List<RelServiceGroupEntity> selectAll() {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate
				.selectList("relservicegroup.selectAll");
	}

	@Override
	public int deleteByServiceGroupIdxAndLibIdx(RelServiceGroupEntity relServiceGroupEntity) {
		return getSqlSession().delete("relservicegroup.deleteByServiceGroupIdxAndLibIdx", relServiceGroupEntity);
	}

	@Override
	public int selectNumByServiceGroupIdxAndLibIdx(
			RelServiceGroupEntity relServiceGroupEntity) {
		return getSqlSession().selectOne("relservicegroup.selectNumByServiceGroupIdxAndLibIdx", relServiceGroupEntity);
	}

}
