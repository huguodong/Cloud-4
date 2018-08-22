package com.ssitcloud.dbstatistics.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dbstatistics.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbstatistics.dao.NewCardWeekDataDao;
import com.ssitcloud.dbstatistics.entity.NewCardWeekDataEntity;

@Repository
public class NewCardWeekDataDaoImpl extends CommonDaoImpl implements
		NewCardWeekDataDao {

	@Override
	public int insertNewCardWeekData(NewCardWeekDataEntity newCardWeekDataEntity) {
		return this.sqlSessionTemplate.insert("newCardWeekData.insertNewCardWeekData", newCardWeekDataEntity);
	}

	@Override
	public int updateNewCardWeekData(NewCardWeekDataEntity newCardWeekDataEntity) {
		return this.sqlSessionTemplate.update("newCardWeekData.updateNewCardWeekData", newCardWeekDataEntity);
	}

	@Override
	public int deleteNewCardWeekData(NewCardWeekDataEntity newCardWeekDataEntity) {
		return this.sqlSessionTemplate.delete("newCardWeekData.deleteNewCardWeekData", newCardWeekDataEntity);
	}

	@Override
	public NewCardWeekDataEntity queryOneNewCardWeekData(
			NewCardWeekDataEntity newCardWeekDataEntity) {
		return this.select("newCardWeekData.selectNewCardWeekData", newCardWeekDataEntity);
	}

	@Override
	public List<NewCardWeekDataEntity> queryNewCardWeekDatas(
			NewCardWeekDataEntity newCardWeekDataEntity) {
		return this.selectAll("newCardWeekData.selectNewCardWeekDatas", newCardWeekDataEntity);
	}
	@Override
	public List<NewCardWeekDataEntity> getAllNewCardWeek() {
		return this.sqlSessionTemplate.selectList("newCardWeekData.getAllNewCardWeek");
	}

}
