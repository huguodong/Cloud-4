package com.ssitcloud.dblib.dao;

import com.ssitcloud.dblib.entity.ReaderDataEntity;



public interface ReaderDataDao {
	
	
	public abstract ReaderDataEntity queryReaderByCardnoAndLibIdx(ReaderDataEntity reader);
	
	public abstract int insertReaderData(ReaderDataEntity reader);
	
	public abstract int updateReaderData(ReaderDataEntity reader);

}
