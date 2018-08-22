package com.ssitcloud.dblib.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.ssitcloud.dblib.dao.ReaderDefaultDao;
import com.ssitcloud.dblib.entity.ReaderDefaultEntity;
import com.ssitcloud.dblib.service.ReaderDefaultService;

@Service
public class ReaderDefaultServiceImpl implements ReaderDefaultService {
	@Resource
	private ReaderDefaultDao readerDefaultDao;

	@Override
	public int insertReaderDefault(
			ReaderDefaultEntity readerDefaultEntity) {
		return readerDefaultDao.insertReaderDefault(readerDefaultEntity);
	}

	@Override
	public int updateReaderDefault(
			ReaderDefaultEntity readerDefaultEntity) {
		return readerDefaultDao.updateReaderDefault(readerDefaultEntity);
	}

	@Override
	public int deleteReaderDefault(
			ReaderDefaultEntity readerDefaultEntity) {
		return readerDefaultDao.deleteReaderDefault(readerDefaultEntity);
	}

	@Override
	public ReaderDefaultEntity queryOneReaderDefault(
			ReaderDefaultEntity readerDefaultEntity) {
		return readerDefaultDao.queryOneReaderDefault(readerDefaultEntity);
			
	}

	@Override
	public List<ReaderDefaultEntity> queryReaderDefaults(
			ReaderDefaultEntity readerDefaultEntity) {
		return readerDefaultDao.queryReaderDefaults(readerDefaultEntity);
		
	}

	

}
