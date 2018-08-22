package com.ssitcloud.dbstatistics.dao;

import java.util.List;

import com.ssitcloud.dbstatistics.entity.NewCardMonthDataEntity;

public interface NewCardMonthDataDao {
	
	/**
	 * 办证查询按月统计NewCardMonthDataEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午2:11:18
	 * @param newCardMonthDataEntity
	 * @return
	 */
	public abstract int insertNewCardMonthData(NewCardMonthDataEntity newCardMonthDataEntity);
	
	/**
	 * 办证查询按月统计NewCardMonthDataEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午2:11:32
	 * @param newCardMonthDataEntity
	 * @return
	 */
	public abstract int updateNewCardMonthData(NewCardMonthDataEntity newCardMonthDataEntity);
	
	/**
	 * 办证查询按月统计NewCardMonthDataEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午2:11:48
	 * @param newCardMonthDataEntity
	 * @return
	 */
	public abstract int deleteNewCardMonthData(NewCardMonthDataEntity newCardMonthDataEntity);
	
	/**
	 * 办证查询按月统计NewCardMonthDataEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午2:12:02
	 * @param newCardMonthDataEntity
	 * @return
	 */
	public abstract NewCardMonthDataEntity queryOneNewCardMonthData(NewCardMonthDataEntity newCardMonthDataEntity);
	
	/**
	 * 办证查询按月统计NewCardMonthDataEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午2:12:15
	 * @param newCardMonthDataEntity
	 * @return
	 */
	public abstract List<NewCardMonthDataEntity> queryNewCardMonthDatas(NewCardMonthDataEntity newCardMonthDataEntity);
	/**
	 * 
	 *
	 * <p>2017年4月5日 下午9:53:23 
	 * <p>create by hjc
	 * @return
	 */
	public abstract List<NewCardMonthDataEntity> getAllNewCardMonth();

}
