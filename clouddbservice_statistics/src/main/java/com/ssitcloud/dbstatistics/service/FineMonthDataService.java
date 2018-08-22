package com.ssitcloud.dbstatistics.service;

import java.util.List;

import com.ssitcloud.dbstatistics.entity.FineMonthDataEntity;

public interface FineMonthDataService {
	/**
	 * 新增一条记录
	 *
	 * <p>2017年2月8日 上午11:06:24 
	 * <p>create by hjc
	 * @param fineMonthDataEntity
	 * @return
	 */
	public abstract int insertFineMonthData(FineMonthDataEntity fineMonthDataEntity);
	/**
	 * 删除一条记录
	 *
	 * <p>2017年2月8日 上午11:06:36 
	 * <p>create by hjc
	 * @param fineMonthDataEntity
	 * @return
	 */
	public abstract int deleteFineMonthData(FineMonthDataEntity fineMonthDataEntity);
	/**
	 * 更新一条记录
	 *
	 * <p>2017年2月8日 上午11:06:36 
	 * <p>create by hjc
	 * @param fineMonthDataEntity
	 * @return
	 */
	public abstract int updateFineMonthData(FineMonthDataEntity fineMonthDataEntity);
	/**
	 * 查询一条记录
	 *
	 * <p>2017年2月8日 上午11:06:36 
	 * <p>create by hjc
	 * @param fineMonthDataEntity
	 * @return
	 */
	public abstract FineMonthDataEntity queryFineMonthData(FineMonthDataEntity fineMonthDataEntity);
	/**
	 * 查询多条记录
	 *
	 * <p>2017年2月8日 上午11:06:36 
	 * <p>create by hjc
	 * @param fineMonthDataEntity
	 * @return
	 */
	public abstract List<FineMonthDataEntity> queryFineMonthDataList(FineMonthDataEntity fineMonthDataEntity);
	
	/**
	 * 获取所有的月统计数据
	 *
	 * <p>2017年4月1日 下午9:20:40 
	 * <p>create by hjc
	 * @return
	 */
	public abstract List<FineMonthDataEntity> getAllFinanceMonth();


}
