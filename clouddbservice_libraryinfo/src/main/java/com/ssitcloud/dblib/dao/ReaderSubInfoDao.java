package com.ssitcloud.dblib.dao;

import java.util.List;

import com.ssitcloud.dblib.entity.ReaderSubInfoEntity;


public interface ReaderSubInfoDao {
	/**
	 * 读者关联基本信息ReaderSubInfoEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:30:27
	 * @param readerSubInfoEntity
	 * @return
	 */
	public abstract int insertReaderSubInfo(ReaderSubInfoEntity readerSubInfoEntity);
	
	/**
	 * 读者关联基本信息ReaderSubInfoEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:30:55
	 * @param readerSubInfoEntity
	 * @return
	 */
	public abstract int updateReaderSubInfo(ReaderSubInfoEntity readerSubInfoEntity);
	
	/**
	 * 读者关联基本信息ReaderSubInfoEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:31:18
	 * @param readerSubInfoEntity
	 * @return
	 */
	public abstract int deleteReaderSubInfo(ReaderSubInfoEntity readerSubInfoEntity);
	
	/**
	 * 读者关联基本信息ReaderSubInfoEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:31:47
	 * @param readerSubInfoEntity
	 * @return
	 */
	public abstract ReaderSubInfoEntity queryOneReaderSubInfo(ReaderSubInfoEntity readerSubInfoEntity);
	
	/**
	 * 读者关联基本信息ReaderSubInfoEntity全表查询
	 * author huanghuang
	 * 2017年2月9日 下午1:32:05
	 * @param readerSubInfoEntity
	 * @return
	 */
	public abstract List<ReaderSubInfoEntity> queryReaderSubInfos(ReaderSubInfoEntity readerSubInfoEntity);

}
