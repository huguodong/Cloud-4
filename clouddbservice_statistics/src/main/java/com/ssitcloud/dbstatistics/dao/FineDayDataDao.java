package com.ssitcloud.dbstatistics.dao;

import java.util.List;

import com.ssitcloud.dbstatistics.entity.FineDayDataEntity;

public interface FineDayDataDao {
	
	/**
	 * 新增一条记录
	 *
	 * <p>2017年2月8日 上午11:06:24 
	 * <p>create by hjc
	 * @param fineDayDataEntity
	 * @return
	 */
	public abstract int insertFineDayData(FineDayDataEntity fineDayDataEntity);
	/**
	 * 删除一条记录
	 *
	 * <p>2017年2月8日 上午11:06:36 
	 * <p>create by hjc
	 * @param fineDayDataEntity
	 * @return
	 */
	public abstract int deleteFineDayData(FineDayDataEntity fineDayDataEntity);
	/**
	 * 更新一条记录
	 *
	 * <p>2017年2月8日 上午11:06:36 
	 * <p>create by hjc
	 * @param fineDayDataEntity
	 * @return
	 */
	public abstract int updateFineDayData(FineDayDataEntity fineDayDataEntity);
	/**
	 * 查询一条记录
	 *
	 * <p>2017年2月8日 上午11:06:36 
	 * <p>create by hjc
	 * @param fineDayDataEntity
	 * @return
	 */
	public abstract FineDayDataEntity queryFineDayData(FineDayDataEntity fineDayDataEntity);
	/**
	 * 查询多条记录
	 *
	 * <p>2017年2月8日 上午11:06:36 
	 * <p>create by hjc
	 * @param fineDayDataEntity
	 * @return
	 */
	public abstract List<FineDayDataEntity> queryFineDayDataList(FineDayDataEntity fineDayDataEntity);
	
	/**
	 * 获取所有的日统计数据
	 *
	 * <p>2017年4月1日 下午9:18:03 
	 * <p>create by hjc
	 * @param fineDayDataEntity
	 * @return
	 */
	public abstract List<FineDayDataEntity> getAllFinanceDay();


}
