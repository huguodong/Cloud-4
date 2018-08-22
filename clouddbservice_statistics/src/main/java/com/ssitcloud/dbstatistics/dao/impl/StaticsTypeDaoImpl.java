package com.ssitcloud.dbstatistics.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dbstatistics.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbstatistics.dao.StaticsTypeDao;
import com.ssitcloud.dbstatistics.entity.StaticsTypeEntity;
@Repository
public class StaticsTypeDaoImpl extends CommonDaoImpl implements StaticsTypeDao {

	@Override
	public int insertStaticsType(StaticsTypeEntity staticsTypeEntity) {
		return this.sqlSessionTemplate.insert("stattype.insertStaticsType",staticsTypeEntity);
	}

	@Override
	public int deleteStaticsType(StaticsTypeEntity staticsTypeEntity) {
		return this.sqlSessionTemplate.delete("stattype.deleteStaticsType",staticsTypeEntity);
	}

	@Override
	public int updateStaticsType(StaticsTypeEntity staticsTypeEntity) {
		return this.sqlSessionTemplate.update("stattype.updateStaticsType",staticsTypeEntity);
	}

	@Override
	public StaticsTypeEntity queryStaticsType(
			StaticsTypeEntity staticsTypeEntity) {
		return this.sqlSessionTemplate.selectOne("stattype.queryStaticsType",staticsTypeEntity);
	}

	@Override
	public List<StaticsTypeEntity> queryStaticsTypeList(
			StaticsTypeEntity staticsTypeEntity) {
		return this.sqlSessionTemplate.selectList("stattype.queryStaticsTypeList",staticsTypeEntity);
	}

}
