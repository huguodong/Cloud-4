package com.ssitcloud.dbstatistics.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dbstatistics.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbstatistics.dao.FineMonthDataDao;
import com.ssitcloud.dbstatistics.entity.FineMonthDataEntity;

@Repository
public class FineMonthDataDaoImpl extends CommonDaoImpl implements FineMonthDataDao{
	@Override
	public int insertFineMonthData(FineMonthDataEntity fineMonthDataEntity) {
		return this.sqlSessionTemplate.insert("finemonth.insertFineMonthData",fineMonthDataEntity);
	}

	@Override
	public int deleteFineMonthData(FineMonthDataEntity fineMonthDataEntity) {
		return this.sqlSessionTemplate.delete("finemonth.deleteFineMonthData",fineMonthDataEntity);
	}

	@Override
	public int updateFineMonthData(FineMonthDataEntity fineMonthDataEntity) {
		return this.sqlSessionTemplate.update("finemonth.updateFineMonthData",fineMonthDataEntity);
	}

	@Override
	public FineMonthDataEntity queryFineMonthData(
			FineMonthDataEntity fineMonthDataEntity) {
		return this.sqlSessionTemplate.selectOne("finemonth.queryFineMonthData",fineMonthDataEntity);
	}

	@Override
	public List<FineMonthDataEntity> queryFineMonthDataList(
			FineMonthDataEntity fineMonthDataEntity) {
		return this.sqlSessionTemplate.selectList("finemonth.queryFineMonthDataList",fineMonthDataEntity);
	}
	
	@Override
	public List<FineMonthDataEntity> getAllFinanceMonth() {
		return this.sqlSessionTemplate.selectList("finemonth.getAllFinanceMonth");
	}
}
