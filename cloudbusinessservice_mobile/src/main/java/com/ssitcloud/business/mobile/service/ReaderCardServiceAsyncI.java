package com.ssitcloud.business.mobile.service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.ReaderCardEntity;
/**
 * 异步执行插入和更新读者信息类
 * @author LXP
 * @version 创建时间：2017年3月3日 下午5:52:13
 */
public interface ReaderCardServiceAsyncI {
	/**
	 * 异步执行更新读者卡任务
	 * @param readerCard
	 * @return
	 */
    void updateReaderCardAsync(ReaderCardEntity readerCard);
	
	/**
	 * 通过acs服务异步更新读者卡
	 * @param readerCard
	 * @return
	 */
    void updateReaderCardOnAcsAsync(ReaderCardEntity readerCard);
	
	/**
	 * 异步执行插入读者卡任务
	 * @param readerCard
	 * @return
	 */
    void insertReaderCardAsync(ReaderCardEntity readerCard);
}
