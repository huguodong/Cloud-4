package com.ssitcloud.dblib.service;

import java.util.List;
import com.ssitcloud.dblib.entity.ReaderSubInfoEntity;

public interface ReaderSubInfoService {
	
	/**
	 * 读者关联基本信息ReaderSubInfoEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:36:42
	 * @param readerSubInfoEntity
	 * @return
	 */
	public abstract int insertReaderSubInfo(ReaderSubInfoEntity readerSubInfoEntity);
	
	/**
	 * 读者关联基本信息ReaderSubInfoEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:36:58
	 * @param readerSubInfoEntity
	 * @return
	 */
	public abstract int updateReaderSubInfo(ReaderSubInfoEntity readerSubInfoEntity);
	
	/**
	 * 读者关联基本信息ReaderSubInfoEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:37:20
	 * @param readerSubInfoEntity
	 * @return
	 */
	public abstract int deleteReaderSubInfo(ReaderSubInfoEntity readerSubInfoEntity);
	
	/**
	 * 读者关联基本信息ReaderSubInfoEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:36
	 * @param readerSubInfoEntity
	 * @return
	 */
	public abstract ReaderSubInfoEntity queryOneReaderSubInfo(ReaderSubInfoEntity readerSubInfoEntity);
	
	/**
	 * 读者关联基本信息ReaderSubInfoEntity集合查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:49
	 * @param readerSubInfoEntity
	 * @return
	 */
	public abstract List<ReaderSubInfoEntity> queryReaderSubInfos(ReaderSubInfoEntity readerSubInfoEntity);

}
