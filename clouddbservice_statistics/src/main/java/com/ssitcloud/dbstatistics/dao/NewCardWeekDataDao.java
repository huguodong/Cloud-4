package com.ssitcloud.dbstatistics.dao;

import java.util.List;

import com.ssitcloud.dbstatistics.entity.NewCardWeekDataEntity;

public interface NewCardWeekDataDao {

	/**
	 * 办证查询按周统计NewCardWeekDataEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午2:12:45
	 * @param newCardWeekDataEntity
	 * @return
	 */
	public abstract int insertNewCardWeekData(NewCardWeekDataEntity newCardWeekDataEntity);
	
	/**
	 * 办证查询按周统计NewCardWeekDataEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午2:12:58
	 * @param newCardWeekDataEntity
	 * @return
	 */
	public abstract int updateNewCardWeekData(NewCardWeekDataEntity newCardWeekDataEntity);
	
	/**
	 * 办证查询按周统计NewCardWeekDataEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午2:13:10
	 * @param newCardWeekDataEntity
	 * @return
	 */
	public abstract int deleteNewCardWeekData(NewCardWeekDataEntity newCardWeekDataEntity);
	
	/**
	 * 办证查询按周统计NewCardWeekDataEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午2:13:26
	 * @param newCardWeekDataEntity
	 * @return
	 */
	public abstract NewCardWeekDataEntity queryOneNewCardWeekData(NewCardWeekDataEntity newCardWeekDataEntity);
	
	/**
	 * 办证查询按周统计NewCardWeekDataEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午2:13:38
	 * @param newCardWeekDataEntity
	 * @return
	 */
	public abstract List<NewCardWeekDataEntity> queryNewCardWeekDatas(NewCardWeekDataEntity newCardWeekDataEntity);

	/**
	 * 
	 *
	 * <p>2017年4月5日 下午9:55:48 
	 * <p>create by hjc
	 * @return
	 */
	public abstract List<NewCardWeekDataEntity> getAllNewCardWeek( );

}
