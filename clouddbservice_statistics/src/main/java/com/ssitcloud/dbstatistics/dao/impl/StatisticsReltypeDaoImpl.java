package com.ssitcloud.dbstatistics.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dbstatistics.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbstatistics.dao.StatisticsReltypeDao;
import com.ssitcloud.dbstatistics.entity.StatisticsReltypeEntity;
@Repository
public class StatisticsReltypeDaoImpl extends CommonDaoImpl implements StatisticsReltypeDao {

	@Override
	public StatisticsReltypeEntity queryReltype(
			StatisticsReltypeEntity statisticsReltypeEntity) {
		return this.sqlSessionTemplate.selectOne("reltype.queryReltype",statisticsReltypeEntity);
	}

	@Override
	public List<StatisticsReltypeEntity> queryReltypeList(
			StatisticsReltypeEntity statisticsReltypeEntity) {
		return this.sqlSessionTemplate.selectList("reltype.queryReltypeList",statisticsReltypeEntity);
	}

}
