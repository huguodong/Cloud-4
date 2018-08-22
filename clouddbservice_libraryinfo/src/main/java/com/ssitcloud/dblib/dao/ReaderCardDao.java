package com.ssitcloud.dblib.dao;

import java.util.List;

import com.ssitcloud.dblib.entity.ReaderCardEntity;
import com.ssitcloud.readermgmt.entity.CollegeInfoEntity;


public interface ReaderCardDao {
	/**
	 * 读者关联卡信息ReaderCardEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:30:27
	 * @param readerCardEntity
	 * @return
	 */
	public abstract int insertReaderCard(ReaderCardEntity readerCardEntity);
	
	/**
	 * 读者关联卡信息ReaderCardEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:30:55
	 * @param readerCardEntity
	 * @return
	 */
	public abstract int updateReaderCard(ReaderCardEntity readerCardEntity);
	
	/**
	 * 读者关联卡信息ReaderCardEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:31:18
	 * @param readerCardEntity
	 * @return
	 */
	public abstract int deleteReaderCard(ReaderCardEntity readerCardEntity);
	
	/**
	 * 读者关联卡信息ReaderCardEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:31:47
	 * @param readerCardEntity
	 * @return
	 */
	public abstract ReaderCardEntity queryOneReaderCard(ReaderCardEntity readerCardEntity);
	
	/**
	 * 读者关联卡信息ReaderCardEntity全表查询
	 * author huanghuang
	 * 2017年2月9日 下午1:32:05
	 * @param readerCardEntity
	 * @return
	 */
	public abstract List<ReaderCardEntity> queryReaderCards(ReaderCardEntity readerCardEntity);
	
	public List<ReaderCardEntity> selectReaderCardByParams(ReaderCardEntity entity);

	public int countReaderByLibIdxAndCardno(ReaderCardEntity readerCardEntity);
}
