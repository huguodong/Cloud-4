package com.ssitcloud.dblib.dao.impl;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dblib.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dblib.dao.ReaderDataDao;
import com.ssitcloud.dblib.entity.ReaderDataEntity;

@Repository
public class ReaderDataDaoImpl extends CommonDaoImpl implements	ReaderDataDao {

	@Override
	public ReaderDataEntity queryReaderByCardnoAndLibIdx(ReaderDataEntity reader) {
		return this.sqlSessionTemplate.selectOne("readerdata.queryReaderByCardnoAndLibIdx", reader);
	}

	@Override
	public int insertReaderData(ReaderDataEntity reader) {
		return this.sqlSessionTemplate.insert("readerdata.insertReaderData", reader);
	}

	@Override
	public int updateReaderData(ReaderDataEntity reader) {
		return this.sqlSessionTemplate.update("readerdata.updateReaderData", reader);
	}

	
}
