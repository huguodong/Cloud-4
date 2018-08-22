package com.ssitcloud.dbstatistics.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dbstatistics.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbstatistics.dao.CirculationMonthDataDao;
import com.ssitcloud.dbstatistics.entity.CirculationMonthDataEntity;

@Repository
public class CirculationMonthDataDaoImpl extends CommonDaoImpl implements CirculationMonthDataDao{
	
	@Override
	public int insertCirculationMonthData(CirculationMonthDataEntity circulationMonthDataEntity) {
		return this.sqlSessionTemplate.insert("cirmonth.insertCirculationMonthData",circulationMonthDataEntity);
	}

	@Override
	public int deleteCirculationMonthData(CirculationMonthDataEntity circulationMonthDataEntity) {
		return this.sqlSessionTemplate.delete("cirmonth.deleteCirculationMonthData",circulationMonthDataEntity);
	}

	@Override
	public int updateCirculationMonthData(CirculationMonthDataEntity circulationMonthDataEntity) {
		return this.sqlSessionTemplate.update("cirmonth.updateCirculationMonthData",circulationMonthDataEntity);
	}

	@Override
	public CirculationMonthDataEntity queryCirculationMonthData(
			CirculationMonthDataEntity circulationMonthDataEntity) {
		return this.sqlSessionTemplate.selectOne("cirmonth.queryCirculationMonthData",circulationMonthDataEntity);
	}

	@Override
	public List<CirculationMonthDataEntity> queryCirculationMonthDataList(
			CirculationMonthDataEntity circulationMonthDataEntity) {
		return this.sqlSessionTemplate.selectList("cirmonth.queryCirculationMonthDataList",circulationMonthDataEntity);
	}
	@Override
	public List<CirculationMonthDataEntity> getAllCirculationMonth() {
		return this.sqlSessionTemplate.selectList("cirmonth.getAllCirculationMonth");
	}
}
