package com.ssitcloud.dbstatistics.service;

import java.util.List;

import com.ssitcloud.dbstatistics.entity.NewCardDayDataEntity;

public interface NewCardDayDataService {
	
	/**
	 * 办证查询按天统计NewCardDayDataEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午2:18:29
	 * @param newCardDayDataEntity
	 * @return
	 */
	public abstract int insertNewCardDayData(NewCardDayDataEntity newCardDayDataEntity);
	
	/**
	 * 办证查询按天统计NewCardDayDataEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午2:18:40
	 * @param newCardDayDataEntity
	 * @return
	 */
	public abstract int updateNewCardDayData(NewCardDayDataEntity newCardDayDataEntity);
	
	/**
	 * 办证查询按天统计NewCardDayDataEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午2:18:53
	 * @param newCardDayDataEntity
	 * @return
	 */
	public abstract int deleteNewCardDayData(NewCardDayDataEntity newCardDayDataEntity);
	
	/**
	 * NewCardDayDataEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午2:19:06
	 * @param newCardDayDataEntity
	 * @return
	 */
	public abstract NewCardDayDataEntity queryOneNewCardDayData(NewCardDayDataEntity newCardDayDataEntity);
	
	/**
	 * 办证查询按天统计NewCardDayDataEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午2:19:18
	 * @param newCardDayDataEntity
	 * @return
	 */
	public abstract List<NewCardDayDataEntity> queryNewCardDayDatas(NewCardDayDataEntity newCardDayDataEntity);
	
	/**
	 * 
	 *
	 * <p>2017年4月5日 下午9:49:47 
	 * <p>create by hjc
	 * @return
	 */
	public abstract List<NewCardDayDataEntity> getAllNewCardDay();

}
