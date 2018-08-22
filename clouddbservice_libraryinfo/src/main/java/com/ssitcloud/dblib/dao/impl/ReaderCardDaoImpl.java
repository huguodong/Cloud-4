package com.ssitcloud.dblib.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dblib.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dblib.dao.ReaderCardDao;
import com.ssitcloud.dblib.entity.ReaderCardEntity;
import com.ssitcloud.readermgmt.entity.CollegeInfoEntity;

@Repository
public class ReaderCardDaoImpl extends CommonDaoImpl implements
		ReaderCardDao {

	@Override
	public int insertReaderCard(ReaderCardEntity readerCardEntity) {
		int result = this.sqlSessionTemplate.insert("readercard.insertReaderCard", readerCardEntity);
		return result;
	}

	@Override
	public int updateReaderCard(ReaderCardEntity readerCardEntity) {
		return this.sqlSessionTemplate.update("readercard.updateReaderCard", readerCardEntity);
	}

	@Override
	public int deleteReaderCard(ReaderCardEntity readerCardEntity) {
		return this.sqlSessionTemplate.delete("readercard.deleteReaderCard", readerCardEntity);
	}

	@Override
	public ReaderCardEntity queryOneReaderCard(
			ReaderCardEntity readerCardEntity) {
		return this.select("readercard.selectReaderCard", readerCardEntity);
	}

	@Override
	public List<ReaderCardEntity> queryReaderCards(
			ReaderCardEntity readerCardEntity) {
		return this.selectAll("readercard.selectReaderCards", readerCardEntity);
	}
	
	public List<ReaderCardEntity> selectReaderCardByParams(ReaderCardEntity entity){
		return this.sqlSessionTemplate.selectList("readercard.selectReaderCardByParams",entity);
	}

	public int countReaderByLibIdxAndCardno(ReaderCardEntity readerCardEntity){
		return this.sqlSessionTemplate.selectOne("readercard.countReaderByLibIdxAndCardno",readerCardEntity);
	}
}
