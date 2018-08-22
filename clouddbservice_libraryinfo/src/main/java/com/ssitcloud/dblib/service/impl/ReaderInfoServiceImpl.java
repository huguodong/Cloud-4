package com.ssitcloud.dblib.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dblib.dao.ReaderInfoDao;
import com.ssitcloud.dblib.entity.ReaderInfoEntity;
import com.ssitcloud.dblib.service.ReaderInfoService;


@Service
public class ReaderInfoServiceImpl implements ReaderInfoService {
	@Resource
	private ReaderInfoDao readerInfoDao;

	@Override
	public int insertReaderInfo(ReaderInfoEntity readerInfoEntity) {
		return readerInfoDao.insertReaderInfo(readerInfoEntity);
	}

	@Override
	public int deleteReaderInfo(ReaderInfoEntity readerInfoEntity) {
		return readerInfoDao.deleteReaderInfo(readerInfoEntity);
	}

	@Override
	public int updateReaderInfo(ReaderInfoEntity readerInfoEntity) {
		return readerInfoDao.updateReaderInfo(readerInfoEntity);
	}

	@Override
	public ReaderInfoEntity queryReaderInfo(ReaderInfoEntity readerInfoEntity) {
		return readerInfoDao.queryReaderInfo(readerInfoEntity);
	}

	@Override
	public List<ReaderInfoEntity> queryReaderInfoList(
			ReaderInfoEntity readerInfoEntity) {
		return readerInfoDao.queryReaderInfoList(readerInfoEntity);
	}

}
