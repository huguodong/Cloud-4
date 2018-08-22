package com.ssitcloud.dblib.service;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.entity.ReaderCardEntity;
import com.ssitcloud.readermgmt.entity.CollegeInfoEntity;

public interface ReaderCardService {
	
	/**
	 * 读者关联卡信息ReaderCardEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:36:42
	 * @param readerCardEntity
	 * @return
	 */
	public abstract int insertReaderCard(ReaderCardEntity readerCardEntity);
	
	/**
	 * 读者关联卡信息ReaderCardEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:36:58
	 * @param readerCardEntity
	 * @return
	 */
	public abstract int updateReaderCard(ReaderCardEntity readerCardEntity);
	
	/**
	 * 读者关联卡信息ReaderCardEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:37:20
	 * @param readerCardEntity
	 * @return
	 */
	public abstract int deleteReaderCard(ReaderCardEntity readerCardEntity);
	
	/**
	 * 读者关联卡信息ReaderCardEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:36
	 * @param readerCardEntity
	 * @return
	 */
	public abstract ReaderCardEntity queryOneReaderCard(ReaderCardEntity readerCardEntity);
	
	/**
	 * 读者关联卡信息ReaderCardEntity集合查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:49
	 * @param readerCardEntity
	 * @return
	 */
	public abstract List<ReaderCardEntity> queryReaderCards(ReaderCardEntity readerCardEntity);
	
	public List<ReaderCardEntity>selectReaderCardByParams(ReaderCardEntity cardEntity);
	
	public ResultEntity uploadReaderCard(CommonsMultipartFile commonsMultipartFile,String req) throws Exception;
	
	public int countReaderByLibIdxAndCardno(ReaderCardEntity readerCardEntity) ;
}
