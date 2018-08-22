package com.ssitcloud.dbstatistics.service;

import java.util.List;

import com.ssitcloud.dbstatistics.entity.FineWeekDataEntity;


public interface FineWeekDataService {
	
	/**
	 * 财经查询按周统计FineWeekDateEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午2:15:46
	 * @param fineWeekDataEntity
	 * @return
	 */
	public abstract int insertFineWeekData(FineWeekDataEntity fineWeekDataEntity);
	
	/**
	 * 财经查询按周统计FineWeekDataEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午2:15:57
	 * @param fineWeekDataEntity
	 * @return
	 */
	public abstract int updateFineWeekData(FineWeekDataEntity fineWeekDataEntity);
	
	/**
	 * 财经查询按周统计FineWeekDataEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午2:16:12
	 * @param fineWeekDataEntity
	 * @return
	 */
	public abstract int deleteFineWeekData(FineWeekDataEntity fineWeekDataEntity);
	
	/**
	 * 财经查询按周统计FineWeekDataEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午2:16:25
	 * @param fineWeekDataEntity
	 * @return
	 */
	public abstract FineWeekDataEntity queryOneFineWeekData(FineWeekDataEntity fineWeekDataEntity);
	
	/**
	 * 财经查询按周统计FineWeekDataEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午2:16:38
	 * @param fineWeekDataEntity
	 * @return
	 */
	public abstract List<FineWeekDataEntity> queryFineWeekDatas(FineWeekDataEntity fineWeekDataEntity);
	
	/**
	 * 
	 *
	 * <p>2017年4月5日 下午9:23:05 
	 * <p>create by hjc
	 * @param fineWeekDataEntity
	 * @return
	 */
	public abstract List<FineWeekDataEntity> getAllFinanceWeek( );

}
