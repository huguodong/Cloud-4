package com.ssitcloud.dblib.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dblib.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dblib.dao.ReaderDefaultDao;
import com.ssitcloud.dblib.entity.ReaderDefaultEntity;

@Repository
public class ReaderDefaultDaoImpl extends CommonDaoImpl implements
		ReaderDefaultDao {

	@Override
	public int insertReaderDefault(ReaderDefaultEntity readerDefaultEntity) {
		return this.sqlSessionTemplate.insert("reader_default.insertReaderDefault", readerDefaultEntity);
	}

	@Override
	public int updateReaderDefault(ReaderDefaultEntity readerDefaultEntity) {
		return this.sqlSessionTemplate.update("reader_default.updateReaderDefault", readerDefaultEntity);
	}

	@Override
	public int deleteReaderDefault(ReaderDefaultEntity readerDefaultEntity) {
		return this.sqlSessionTemplate.delete("reader_default.deleteReaderDefault", readerDefaultEntity);
	}

	@Override
	public ReaderDefaultEntity queryOneReaderDefault(
			ReaderDefaultEntity readerDefaultEntity) {
		return this.select("reader_default.selectReaderDefault", readerDefaultEntity);
	}

	@Override
	public List<ReaderDefaultEntity> queryReaderDefaults(
			ReaderDefaultEntity readerDefaultEntity) {
		return this.selectAll("reader_default.selectReaderDefaults", readerDefaultEntity);
	}

}
