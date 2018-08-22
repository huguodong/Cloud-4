package com.ssitcloud.business.mobile.service;

import java.util.List;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.ReaderSubInfoEntity;

/**
 * ReaderSubInfo服务类
 * @author LXP
 * @version 创建时间：2017年2月27日 下午2:39:07
 */
public interface ReaderSubInfoServiceI {
	
	/**
	 * 采用删除-->插入形式，请保证数据是整块数据
	 * 
	 * @param readerSubInfoList 数组中reader_idx务必一致，否则插入失败
	 * @throws IllegalArgumentException readerSubInfoList为null或为空List
	 * @return
	 */
	ResultEntity deleteAndInsert(List<ReaderSubInfoEntity> readerSubInfoList);

	/**
	 * 获取读者的readerSubInfo
	 * @param reader_idx 读者主键
	 * @return
	 */
	ResultEntity selectReaderSubInfo(Integer reader_idx);
}
