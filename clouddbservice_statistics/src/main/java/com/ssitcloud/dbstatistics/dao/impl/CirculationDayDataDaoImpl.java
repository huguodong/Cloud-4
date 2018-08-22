package com.ssitcloud.dbstatistics.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dbstatistics.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbstatistics.dao.CirculationDayDataDao;
import com.ssitcloud.dbstatistics.entity.CirculationDayDataEntity;

@Repository
public class CirculationDayDataDaoImpl extends CommonDaoImpl implements CirculationDayDataDao{

	@Override
	public int insertCirculationDayData(CirculationDayDataEntity circulationDayDataEntity) {
		return this.sqlSessionTemplate.insert("cirday.insertCirculationDayData",circulationDayDataEntity);
	}

	@Override
	public int deleteCirculationDayData(CirculationDayDataEntity circulationDayDataEntity) {
		return this.sqlSessionTemplate.delete("cirday.deleteCirculationDayData",circulationDayDataEntity);
	}

	@Override
	public int updateCirculationDayData(CirculationDayDataEntity circulationDayDataEntity) {
		return this.sqlSessionTemplate.update("cirday.updateCirculationDayData",circulationDayDataEntity);
	}

	@Override
	public CirculationDayDataEntity queryCirculationDayData(
			CirculationDayDataEntity circulationDayDataEntity) {
		return this.sqlSessionTemplate.selectOne("cirday.queryCirculationDayData",circulationDayDataEntity);
	}

	@Override
	public List<CirculationDayDataEntity> queryCirculationDayDataList(
			CirculationDayDataEntity circulationDayDataEntity) {
		return this.sqlSessionTemplate.selectList("cirday.queryCirculationDayDataList",circulationDayDataEntity);
	}
	
	public List<CirculationDayDataEntity> getAllCirculationDay() {
		return this.sqlSessionTemplate.selectList("cirday.getAllCirculationDay");
	}

}
