package com.ssitcloud.dbstatistics.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dbstatistics.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbstatistics.dao.NewCardMonthDataDao;
import com.ssitcloud.dbstatistics.entity.NewCardMonthDataEntity;

@Repository
public class NewCardMonthDataDaoImpl extends CommonDaoImpl implements
		NewCardMonthDataDao {

	@Override
	public int insertNewCardMonthData(NewCardMonthDataEntity newCardMonthDataEntity) {
		return this.sqlSessionTemplate.insert("newCardMonthData.insertNewCardMonthData", newCardMonthDataEntity);
	}

	@Override
	public int updateNewCardMonthData(NewCardMonthDataEntity newCardMonthDataEntity) {
		return this.sqlSessionTemplate.update("newCardMonthData.updateNewCardMonthData", newCardMonthDataEntity);
	}

	@Override
	public int deleteNewCardMonthData(NewCardMonthDataEntity newCardMonthDataEntity) {
		return this.sqlSessionTemplate.delete("newCardMonthData.deleteNewCardMonthData", newCardMonthDataEntity);
	}

	@Override
	public NewCardMonthDataEntity queryOneNewCardMonthData(
			NewCardMonthDataEntity newCardMonthDataEntity) {
		return this.select("newCardMonthData.selectNewCardMonthData", newCardMonthDataEntity);
	}

	@Override
	public List<NewCardMonthDataEntity> queryNewCardMonthDatas(
			NewCardMonthDataEntity newCardMonthDataEntity) {
		return this.selectAll("newCardMonthData.selectNewCardMonthDatas", newCardMonthDataEntity);
	}
	@Override
	public List<NewCardMonthDataEntity> getAllNewCardMonth() {
		return this.sqlSessionTemplate.selectList("newCardMonthData.getAllNewCardMonth");
	}

}
