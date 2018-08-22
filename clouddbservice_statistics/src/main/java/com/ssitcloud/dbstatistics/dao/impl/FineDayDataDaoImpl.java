package com.ssitcloud.dbstatistics.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dbstatistics.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbstatistics.dao.FineDayDataDao;
import com.ssitcloud.dbstatistics.entity.FineDayDataEntity;

@Repository
public class FineDayDataDaoImpl extends CommonDaoImpl implements FineDayDataDao{

	@Override
	public int insertFineDayData(FineDayDataEntity fineDayDataEntity) {
		return this.sqlSessionTemplate.insert("fineday.insertFineDayData",fineDayDataEntity);
	}

	@Override
	public int deleteFineDayData(FineDayDataEntity fineDayDataEntity) {
		return this.sqlSessionTemplate.delete("fineday.deleteFineDayData",fineDayDataEntity);
	}

	@Override
	public int updateFineDayData(FineDayDataEntity fineDayDataEntity) {
		return this.sqlSessionTemplate.update("fineday.updateFineDayData",fineDayDataEntity);
	}

	@Override
	public FineDayDataEntity queryFineDayData(
			FineDayDataEntity fineDayDataEntity) {
		return this.sqlSessionTemplate.selectOne("fineday.queryFineDayData",fineDayDataEntity);
	}

	@Override
	public List<FineDayDataEntity> queryFineDayDataList(
			FineDayDataEntity fineDayDataEntity) {
		return this.sqlSessionTemplate.selectList("fineday.queryFineDayDataList",fineDayDataEntity);
	}
	
	@Override
	public List<FineDayDataEntity> getAllFinanceDay() {
		return this.sqlSessionTemplate.selectList("fineday.getAllFinanceDay");
	}

}
