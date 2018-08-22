package com.ssitcloud.dbstatistics.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dbstatistics.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbstatistics.dao.CirculationYearDataDao;
import com.ssitcloud.dbstatistics.entity.CirculationYearDataEntity;

@Repository
public class CirculationYearDataDaoImpl extends CommonDaoImpl implements CirculationYearDataDao{
	@Override
	public int insertCirculationYearData(CirculationYearDataEntity circulationYearDataEntity) {
		return this.sqlSessionTemplate.insert("ciryear.insertCirculationYearData",circulationYearDataEntity);
	}

	@Override
	public int deleteCirculationYearData(CirculationYearDataEntity circulationYearDataEntity) {
		return this.sqlSessionTemplate.delete("ciryear.deleteCirculationYearData",circulationYearDataEntity);
	}

	@Override
	public int updateCirculationYearData(CirculationYearDataEntity circulationYearDataEntity) {
		return this.sqlSessionTemplate.update("ciryear.updateCirculationYearData",circulationYearDataEntity);
	}

	@Override
	public CirculationYearDataEntity queryCirculationYearData(
			CirculationYearDataEntity circulationYearDataEntity) {
		return this.sqlSessionTemplate.selectOne("ciryear.queryCirculationYearData",circulationYearDataEntity);
	}

	@Override
	public List<CirculationYearDataEntity> queryCirculationYearDataList(
			CirculationYearDataEntity circulationYearDataEntity) {
		return this.sqlSessionTemplate.selectList("ciryear.queryCirculationYearDataList",circulationYearDataEntity);
	}
	
	@Override
	public List<CirculationYearDataEntity> getAllCirculationYear() {
		return this.sqlSessionTemplate.selectList("ciryear.getAllCirculationYear");
	}
}
