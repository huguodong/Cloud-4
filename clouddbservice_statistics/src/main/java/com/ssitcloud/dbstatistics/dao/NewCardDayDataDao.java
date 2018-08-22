package com.ssitcloud.dbstatistics.dao;

import java.util.List;

import com.ssitcloud.dbstatistics.entity.NewCardDayDataEntity;

public interface NewCardDayDataDao {
	
	/**
	 * 办证查询按天统计NewCardDayDataEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午2:09:51
	 * @param newCardDayDataEntity
	 * @return
	 */
	public abstract int insertNewCardDayData(NewCardDayDataEntity newCardDayDataEntity);
	
	/**
	 * 办证查询按天统计NewCardDayDataEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午2:10:06
	 * @param newCardDayDataEntity
	 * @return
	 */
	public abstract int updateNewCardDayData(NewCardDayDataEntity newCardDayDataEntity);
	
	/**
	 * 办证查询按天统计NewCardDayDataEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午2:10:21
	 * @param newCardDayDataEntity
	 * @return
	 */
	public abstract int deleteNewCardDayData(NewCardDayDataEntity newCardDayDataEntity);
	
	/**
	 * 办证查询按天统计NewCardDayDataEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午2:10:34
	 * @param newCardDayDataEntity
	 * @return
	 */
	public abstract NewCardDayDataEntity queryOneNewCardDayData(NewCardDayDataEntity newCardDayDataEntity);
	
	/**
	 * 办证查询按天统计NewCardDayDataEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午2:10:49
	 * @param newCardDayDataEntity
	 * @return
	 */
	public abstract List<NewCardDayDataEntity> queryNewCardDayDatas(NewCardDayDataEntity newCardDayDataEntity);
	/**
	 * 
	 *
	 * <p>2017年4月5日 下午9:50:14 
	 * <p>create by hjc
	 * @return
	 */
	public abstract List<NewCardDayDataEntity> getAllNewCardDay();

}
