package com.ssitcloud.dblib.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.ssitcloud.dblib.dao.ReaderSubInfoDao;
import com.ssitcloud.dblib.entity.ReaderSubInfoEntity;
import com.ssitcloud.dblib.service.ReaderSubInfoService;

@Service
public class ReaderSubInfoServiceImpl implements ReaderSubInfoService {
	@Resource
	private ReaderSubInfoDao readerSubInfoDao;

	@Override
	public int insertReaderSubInfo(
			ReaderSubInfoEntity readerSubInfoEntity) {
		return readerSubInfoDao.insertReaderSubInfo(readerSubInfoEntity);
	}

	@Override
	public int updateReaderSubInfo(
			ReaderSubInfoEntity readerSubInfoEntity) {
		return readerSubInfoDao.updateReaderSubInfo(readerSubInfoEntity);
	}

	@Override
	public int deleteReaderSubInfo(
			ReaderSubInfoEntity readerSubInfoEntity) {
		return readerSubInfoDao.deleteReaderSubInfo(readerSubInfoEntity);
	}

	@Override
	public ReaderSubInfoEntity queryOneReaderSubInfo(
			ReaderSubInfoEntity readerSubInfoEntity) {
		return readerSubInfoDao.queryOneReaderSubInfo(readerSubInfoEntity);
			
	}

	@Override
	public List<ReaderSubInfoEntity> queryReaderSubInfos(
			ReaderSubInfoEntity readerSubInfoEntity) {
		return readerSubInfoDao.queryReaderSubInfos(readerSubInfoEntity);
		
	}

	

}
