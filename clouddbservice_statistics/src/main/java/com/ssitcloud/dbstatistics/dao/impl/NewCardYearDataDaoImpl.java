package com.ssitcloud.dbstatistics.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dbstatistics.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbstatistics.dao.NewCardYearDataDao;
import com.ssitcloud.dbstatistics.entity.NewCardYearDataEntity;

@Repository
public class NewCardYearDataDaoImpl extends CommonDaoImpl implements
		NewCardYearDataDao {

	@Override
	public int insertNewCardYearData(NewCardYearDataEntity newCardYearDataEntity) {
		return this.sqlSessionTemplate.insert("newCardYearData.insertNewCardYearData", newCardYearDataEntity);
	}

	@Override
	public int updateNewCardYearData(NewCardYearDataEntity newCardYearDataEntity) {
		return this.sqlSessionTemplate.update("newCardYearData.updateNewCardYearData", newCardYearDataEntity);
	}

	@Override
	public int deleteNewCardYearData(NewCardYearDataEntity newCardYearDataEntity) {
		return this.sqlSessionTemplate.delete("newCardYearData.deleteNewCardYearData", newCardYearDataEntity);
	}

	@Override
	public NewCardYearDataEntity queryOneNewCardYearData(
			NewCardYearDataEntity newCardYearDataEntity) {
		return this.select("newCardYearData.selectNewCardYearData", newCardYearDataEntity);
	}

	@Override
	public List<NewCardYearDataEntity> queryNewCardYearDatas(
			NewCardYearDataEntity newCardYearDataEntity) {
		return this.selectAll("newCardYearData.selectNewCardYearDatas", newCardYearDataEntity);
	}
	
	@Override
	public List<NewCardYearDataEntity> getAllNewCardYear() {
		return this.sqlSessionTemplate.selectList("newCardYearData.getAllNewCardYear");
	}

}
