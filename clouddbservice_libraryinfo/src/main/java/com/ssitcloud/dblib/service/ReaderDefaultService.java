package com.ssitcloud.dblib.service;

import java.util.List;
import com.ssitcloud.dblib.entity.ReaderDefaultEntity;

public interface ReaderDefaultService {
	
	/**
	 * 读者默认信息ReaderDefaultEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:36:42
	 * @param readerDefaultEntity
	 * @return
	 */
	public abstract int insertReaderDefault(ReaderDefaultEntity readerDefaultEntity);
	
	/**
	 * 读者默认信息ReaderDefaultEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:36:58
	 * @param readerDefaultEntity
	 * @return
	 */
	public abstract int updateReaderDefault(ReaderDefaultEntity readerDefaultEntity);
	
	/**
	 * 读者默认信息ReaderDefaultEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:37:20
	 * @param readerDefaultEntity
	 * @return
	 */
	public abstract int deleteReaderDefault(ReaderDefaultEntity readerDefaultEntity);
	
	/**
	 * 读者默认信息ReaderDefaultEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:36
	 * @param readerDefaultEntity
	 * @return
	 */
	public abstract ReaderDefaultEntity queryOneReaderDefault(ReaderDefaultEntity readerDefaultEntity);
	
	/**
	 * 读者默认信息ReaderDefaultEntity集合查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:49
	 * @param readerDefaultEntity
	 * @return
	 */
	public abstract List<ReaderDefaultEntity> queryReaderDefaults(ReaderDefaultEntity readerDefaultEntity);

}
