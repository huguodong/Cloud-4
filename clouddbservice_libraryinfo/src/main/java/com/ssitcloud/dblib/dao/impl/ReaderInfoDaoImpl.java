package com.ssitcloud.dblib.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dblib.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dblib.dao.ReaderInfoDao;
import com.ssitcloud.dblib.entity.ReaderInfoEntity;

@Repository
public class ReaderInfoDaoImpl extends CommonDaoImpl implements	ReaderInfoDao {

	@Override
	public int insertReaderInfo(ReaderInfoEntity readerInfoEntity) {
		return this.sqlSessionTemplate.insert("readerinfo.insertReaderInfo",readerInfoEntity);
	}

	@Override
	public int deleteReaderInfo(ReaderInfoEntity readerInfoEntity) {
		return this.sqlSessionTemplate.delete("readerinfo.deleteReaderInfo",readerInfoEntity);
	}

	@Override
	public int updateReaderInfo(ReaderInfoEntity readerInfoEntity) {
		return this.sqlSessionTemplate.update("readerinfo.updateReaderInfo",readerInfoEntity);
	}

	@Override
	public ReaderInfoEntity queryReaderInfo(ReaderInfoEntity readerInfoEntity) {
		return this.sqlSessionTemplate.selectOne("readerinfo.queryReaderInfo",readerInfoEntity);
	}

	@Override
	public List<ReaderInfoEntity> queryReaderInfoList(
			ReaderInfoEntity readerInfoEntity) {
		return this.sqlSessionTemplate.selectList("readerinfo.queryReaderInfoList",readerInfoEntity);
	}


}
