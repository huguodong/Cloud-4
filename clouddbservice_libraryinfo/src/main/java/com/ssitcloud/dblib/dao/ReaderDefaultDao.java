package com.ssitcloud.dblib.dao;

import java.util.List;

import com.ssitcloud.dblib.entity.ReaderDefaultEntity;


public interface ReaderDefaultDao {
	/**
	 * 读者默认信息ReaderDefaultEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:30:27
	 * @param readerDefaultEntity
	 * @return
	 */
	public abstract int insertReaderDefault(ReaderDefaultEntity readerDefaultEntity);
	
	/**
	 * 读者默认信息ReaderDefaultEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:30:55
	 * @param readerDefaultEntity
	 * @return
	 */
	public abstract int updateReaderDefault(ReaderDefaultEntity readerDefaultEntity);
	
	/**
	 * 读者默认信息ReaderDefaultEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:31:18
	 * @param readerDefaultEntity
	 * @return
	 */
	public abstract int deleteReaderDefault(ReaderDefaultEntity readerDefaultEntity);
	
	/**
	 * 读者默认信息ReaderDefaultEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:31:47
	 * @param readerDefaultEntity
	 * @return
	 */
	public abstract ReaderDefaultEntity queryOneReaderDefault(ReaderDefaultEntity readerDefaultEntity);
	
	/**
	 * 读者默认信息ReaderDefaultEntity全表查询
	 * author huanghuang
	 * 2017年2月9日 下午1:32:05
	 * @param readerDefaultEntity
	 * @return
	 */
	public abstract List<ReaderDefaultEntity> queryReaderDefaults(ReaderDefaultEntity readerDefaultEntity);

}
