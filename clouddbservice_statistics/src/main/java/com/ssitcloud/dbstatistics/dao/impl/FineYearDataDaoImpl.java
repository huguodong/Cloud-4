package com.ssitcloud.dbstatistics.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dbstatistics.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbstatistics.dao.FineYearDataDao;
import com.ssitcloud.dbstatistics.entity.FineYearDataEntity;

@Repository
public class FineYearDataDaoImpl extends CommonDaoImpl implements
		FineYearDataDao {

	@Override
	public int insertFineYearData(FineYearDataEntity fineYearDataEntity) {
		return this.sqlSessionTemplate.insert("fineYearData.insertFineYearData", fineYearDataEntity);
	}

	@Override
	public int updateFineYearData(FineYearDataEntity fineYearDataEntity) {
		return this.sqlSessionTemplate.update("fineYearData.updateFineYearData", fineYearDataEntity);
	}

	@Override
	public int deleteFineYearData(FineYearDataEntity fineYearDataEntity) {
		return this.sqlSessionTemplate.delete("fineYearData.deleteFineYearData", fineYearDataEntity);
	}

	@Override
	public FineYearDataEntity queryOneFineYearData(
			FineYearDataEntity fineYearDataEntity) {
		return this.select("fineYearData.selectFineYearData", fineYearDataEntity);
	}

	@Override
	public List<FineYearDataEntity> queryFineYearDatas(
			FineYearDataEntity fineYearDataEntity) {
		return this.selectAll("fineYearData.selectFineYearDatas", fineYearDataEntity);
	}
	@Override
	public List<FineYearDataEntity> getAllFinanceYear() {
		return this.sqlSessionTemplate.selectList("fineYearData.getAllFinanceYear");
	}

}
