package com.ssitcloud.dbstatistics.dao;

import java.util.List;

import com.ssitcloud.dbstatistics.entity.FineWeekDataEntity;



public interface FineWeekDataDao {
	
	/**
	 * 财经查询按周统计FineWeekDataEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午2:07:00
	 * @param fineWeekDataEntity
	 * @return
	 */
	public abstract int insertFineWeekData(FineWeekDataEntity fineWeekDataEntity);
	
	/**
	 * 财经查询按周统计FineWeekDataEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午2:07:17
	 * @param fineWeekDataEntity
	 * @return
	 */
	public abstract int updateFineWeekData(FineWeekDataEntity fineWeekDataEntity);
	
	/**
	 * 财经查询按周统计FineWeekDataEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午2:07:30
	 * @param fineWeekDataEntity
	 * @return
	 */
	public abstract int deleteFineWeekData(FineWeekDataEntity fineWeekDataEntity);
	
	/**
	 * 财经查询按周统计FineWeekDataEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午2:07:43
	 * @param fineWeekDataEntity
	 * @return
	 */
	public abstract FineWeekDataEntity queryOneFineWeekData(FineWeekDataEntity fineWeekDataEntity);
	
	
	/**
	 * 财经查询按周统计FineWeekDataEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午2:07:58
	 * @param fineWeekDataEntity
	 * @return
	 */
	public abstract List<FineWeekDataEntity> queryFineWeekDatas(FineWeekDataEntity fineWeekDataEntity);
	
	/**
	 * 
	 *
	 * <p>2017年4月5日 下午9:23:47 
	 * <p>create by hjc
	 * @return
	 */
	public abstract List<FineWeekDataEntity> getAllFinanceWeek();

}
