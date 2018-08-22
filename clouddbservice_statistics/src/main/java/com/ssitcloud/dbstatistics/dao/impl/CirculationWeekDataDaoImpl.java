package com.ssitcloud.dbstatistics.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dbstatistics.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbstatistics.dao.CirculationWeekDataDao;
import com.ssitcloud.dbstatistics.entity.CirculationWeekDataEntity;

@Repository
public class CirculationWeekDataDaoImpl extends CommonDaoImpl implements CirculationWeekDataDao{
	@Override
	public int insertCirculationWeekData(CirculationWeekDataEntity circulationWeekDataEntity) {
		return this.sqlSessionTemplate.insert("cirweek.insertCirculationWeekData",circulationWeekDataEntity);
	}

	@Override
	public int deleteCirculationWeekData(CirculationWeekDataEntity circulationWeekDataEntity) {
		return this.sqlSessionTemplate.delete("cirweek.deleteCirculationWeekData",circulationWeekDataEntity);
	}

	@Override
	public int updateCirculationWeekData(CirculationWeekDataEntity circulationWeekDataEntity) {
		return this.sqlSessionTemplate.update("cirweek.updateCirculationWeekData",circulationWeekDataEntity);
	}

	@Override
	public CirculationWeekDataEntity queryCirculationWeekData(
			CirculationWeekDataEntity circulationWeekDataEntity) {
		return this.sqlSessionTemplate.selectOne("cirweek.queryCirculationWeekData",circulationWeekDataEntity);
	}

	@Override
	public List<CirculationWeekDataEntity> queryCirculationWeekDataList(
			CirculationWeekDataEntity circulationWeekDataEntity) {
		return this.sqlSessionTemplate.selectList("cirweek.queryCirculationWeekDataList",circulationWeekDataEntity);
	}
	
	@Override
	public List<CirculationWeekDataEntity> getAllCirculationWeek() {
		return this.sqlSessionTemplate.selectList("cirweek.getAllCirculationWeek");
	}
}
