package com.ssitcloud.dbstatistics.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dbstatistics.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbstatistics.dao.NewCardDayDataDao;
import com.ssitcloud.dbstatistics.entity.NewCardDayDataEntity;

@Repository
public class NewCardDayDataDaoImpl extends CommonDaoImpl implements
		NewCardDayDataDao {

	@Override
	public int insertNewCardDayData(NewCardDayDataEntity newCardDayDataEntity) {
		return this.sqlSessionTemplate.insert("newCardDayData.insertNewCardDayData", newCardDayDataEntity);
	}

	@Override
	public int updateNewCardDayData(NewCardDayDataEntity newCardDayDataEntity) {
		return this.sqlSessionTemplate.update("newCardDayData.updateNewCardDayData", newCardDayDataEntity);
	}

	@Override
	public int deleteNewCardDayData(NewCardDayDataEntity newCardDayDataEntity) {
		return this.sqlSessionTemplate.delete("newCardDayData.deleteNewCardDayData", newCardDayDataEntity);
	}

	@Override
	public NewCardDayDataEntity queryOneNewCardDayData(
			NewCardDayDataEntity newCardDayDataEntity) {
		return this.select("newCardDayData.selectNewCardDayData", newCardDayDataEntity);
	}

	@Override
	public List<NewCardDayDataEntity> queryNewCardDayDatas(
			NewCardDayDataEntity newCardDayDataEntity) {
		return this.selectAll("newCardDayData.selectNewCardDayDatas", newCardDayDataEntity);
	}
	@Override
	public List<NewCardDayDataEntity> getAllNewCardDay() {
		return this.sqlSessionTemplate.selectList("newCardDayData.getAllNewCardDay");
	}

}
