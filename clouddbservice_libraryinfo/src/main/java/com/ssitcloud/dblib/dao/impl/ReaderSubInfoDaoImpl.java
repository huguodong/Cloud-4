package com.ssitcloud.dblib.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dblib.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dblib.dao.ReaderSubInfoDao;
import com.ssitcloud.dblib.entity.ReaderSubInfoEntity;

@Repository
public class ReaderSubInfoDaoImpl extends CommonDaoImpl implements
		ReaderSubInfoDao {

	@Override
	public int insertReaderSubInfo(ReaderSubInfoEntity readerSubInfoEntity) {
		return this.sqlSessionTemplate.insert("readersubInfo.insertReaderSubInfo", readerSubInfoEntity);
	}

	@Override
	public int updateReaderSubInfo(ReaderSubInfoEntity readerSubInfoEntity) {
		return this.sqlSessionTemplate.update("readersubInfo.updateReaderSubInfo", readerSubInfoEntity);
	}

	@Override
	public int deleteReaderSubInfo(ReaderSubInfoEntity readerSubInfoEntity) {
		return this.sqlSessionTemplate.delete("readersubInfo.deleteReaderSubInfo", readerSubInfoEntity);
	}

	@Override
	public ReaderSubInfoEntity queryOneReaderSubInfo(
			ReaderSubInfoEntity readerSubInfoEntity) {
		return this.select("readersubInfo.selectReaderSubInfo", readerSubInfoEntity);
	}

	@Override
	public List<ReaderSubInfoEntity> queryReaderSubInfos(
			ReaderSubInfoEntity readerSubInfoEntity) {
		return this.selectAll("readersubInfo.selectReaderSubInfos", readerSubInfoEntity);
	}

}
