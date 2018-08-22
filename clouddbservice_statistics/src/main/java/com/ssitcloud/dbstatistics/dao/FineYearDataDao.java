package com.ssitcloud.dbstatistics.dao;

import java.util.List;

import com.ssitcloud.dbstatistics.entity.FineYearDataEntity;



public interface FineYearDataDao {
	/**
	 * 财经查询按年统计FineYearDataEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午2:08:31
	 * @param fineYearDataEntity
	 * @return
	 */
	public abstract int insertFineYearData(FineYearDataEntity fineYearDataEntity);
	
	/**
	 * 财经查询按年统计FineYearDataEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午2:08:46
	 * @param fineYearDataEntity
	 * @return
	 */
	public abstract int updateFineYearData(FineYearDataEntity fineYearDataEntity);
	
	/**
	 * 财经查询按年统计FineYearDataEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午2:09:01
	 * @param fineYearDataEntity
	 * @return
	 */
	public abstract int deleteFineYearData(FineYearDataEntity fineYearDataEntity);
	
	/**
	 * 财经查询按年统计FineYearDataEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午2:09:14
	 * @param fineYearDataEntity
	 * @return
	 */
	public abstract FineYearDataEntity queryOneFineYearData(FineYearDataEntity fineYearDataEntity);
	
	/**
	 * 财经查询按年统计FineYearDataEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午2:09:28
	 * @param fineYearDataEntity
	 * @return
	 */
	public abstract List<FineYearDataEntity> queryFineYearDatas(FineYearDataEntity fineYearDataEntity);
	/**
	 * 
	 *
	 * <p>2017年4月5日 下午9:26:58 
	 * <p>create by hjc
	 * @return
	 */
	public abstract List<FineYearDataEntity> getAllFinanceYear();

}
