package com.ssitcloud.dbstatistics.service;

import java.util.List;

import com.ssitcloud.dbstatistics.entity.NewCardMonthDataEntity;

public interface NewCardMonthDataService {
	
	/**
	 * 办证查询按月统计NewCardMonthDataEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午2:19:43
	 * @param newCardMonthDataEntity
	 * @return
	 */
	public abstract int insertNewCardMonthData(NewCardMonthDataEntity newCardMonthDataEntity);
	
	/**
	 * 办证查询按月统计NewCardMonthDataEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午2:19:57
	 * @param newCardMonthDataEntity
	 * @return
	 */
	public abstract int updateNewCardMonthData(NewCardMonthDataEntity newCardMonthDataEntity);
	
	/**
	 * 办证查询按月统计NewCardMonthDataEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午2:20:09
	 * @param newCardMonthDataEntity
	 * @return
	 */
	public abstract int deleteNewCardMonthData(NewCardMonthDataEntity newCardMonthDataEntity);
	
	/**
	 * 办证查询按月统计NewCardMonthDataEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午2:20:22
	 * @param newCardMonthDataEntity
	 * @return
	 */
	public abstract NewCardMonthDataEntity queryOneNewCardMonthData(NewCardMonthDataEntity newCardMonthDataEntity);
	
	/**
	 * 办证查询按月统计NewCardMonthDataEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午2:20:36
	 * @param newCardMonthDataEntity
	 * @return
	 */
	public abstract List<NewCardMonthDataEntity> queryNewCardMonthDatas(NewCardMonthDataEntity newCardMonthDataEntity);
	
	/**
	 * 
	 *
	 * <p>2017年4月5日 下午9:52:50 
	 * <p>create by hjc
	 * @return
	 */
	public abstract List<NewCardMonthDataEntity> getAllNewCardMonth();

}
