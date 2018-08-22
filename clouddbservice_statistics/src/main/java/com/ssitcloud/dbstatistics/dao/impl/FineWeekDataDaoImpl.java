package com.ssitcloud.dbstatistics.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dbstatistics.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbstatistics.dao.FineWeekDataDao;
import com.ssitcloud.dbstatistics.entity.FineWeekDataEntity;

@Repository
public class FineWeekDataDaoImpl extends CommonDaoImpl implements
		FineWeekDataDao {

	@Override
	public int insertFineWeekData(FineWeekDataEntity fineWeekDataEntity) {
		return this.sqlSessionTemplate.insert("fineWeekData.insertFineWeekData", fineWeekDataEntity);
	}

	@Override
	public int updateFineWeekData(FineWeekDataEntity fineWeekDataEntity) {
		return this.sqlSessionTemplate.update("fineWeekData.updateFineWeekData", fineWeekDataEntity);
	}

	@Override
	public int deleteFineWeekData(FineWeekDataEntity fineWeekDataEntity) {
		return this.sqlSessionTemplate.delete("fineWeekData.deleteFineWeekData", fineWeekDataEntity);
	}

	@Override
	public FineWeekDataEntity queryOneFineWeekData(
			FineWeekDataEntity fineWeekDataEntity) {
		return this.select("fineWeekData.selectFineWeekData", fineWeekDataEntity);
	}

	@Override
	public List<FineWeekDataEntity> queryFineWeekDatas(
			FineWeekDataEntity fineWeekDataEntity) {
		return this.selectAll("fineWeekData.selectFineWeekDatas", fineWeekDataEntity);
	}
	@Override
	public List<FineWeekDataEntity> getAllFinanceWeek() {
		return this.sqlSessionTemplate.selectList("fineWeekData.getAllFinanceWeek");
	}

}
