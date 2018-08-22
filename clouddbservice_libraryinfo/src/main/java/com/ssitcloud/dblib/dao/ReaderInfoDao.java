package com.ssitcloud.dblib.dao;

import java.util.List;

import com.ssitcloud.dblib.entity.ReaderInfoEntity;

public interface ReaderInfoDao {

	/**
	 * 插入一条记录
	 *
	 * <p>2017年2月7日 下午4:24:29 
	 * <p>create by hjc
	 * @param readerInfoEntity
	 * @return
	 */
	public abstract int insertReaderInfo(ReaderInfoEntity readerInfoEntity);
	
	/**
	 * 删除一条记录
	 *
	 * <p>2017年2月7日 下午4:24:33 
	 * <p>create by hjc
	 * @param readerInfoEntity
	 * @return
	 */
	public abstract int deleteReaderInfo(ReaderInfoEntity readerInfoEntity);
	
	/**
	 * 更新记录
	 *
	 * <p>2017年2月7日 下午4:24:36 
	 * <p>create by hjc
	 * @param readerInfoEntity
	 * @return
	 */
	public abstract int updateReaderInfo(ReaderInfoEntity readerInfoEntity);
	
	/**
	 * 查询单条记录
	 *
	 * <p>2017年2月7日 下午4:24:40 
	 * <p>create by hjc
	 * @param readerInfoEntity
	 * @return
	 */
	public abstract ReaderInfoEntity queryReaderInfo(ReaderInfoEntity readerInfoEntity);
	
	/**
	 * 查询多条记录
	 *
	 * <p>2017年2月7日 下午4:24:43 
	 * <p>create by hjc
	 * @param readerInfoEntity
	 * @return
	 */
	public abstract List<ReaderInfoEntity> queryReaderInfoList(ReaderInfoEntity readerInfoEntity);
	

	
}
