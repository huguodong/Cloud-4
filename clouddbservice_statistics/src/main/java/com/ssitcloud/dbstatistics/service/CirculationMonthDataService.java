package com.ssitcloud.dbstatistics.service;

import java.util.List;

import com.ssitcloud.dbstatistics.entity.CirculationMonthDataEntity;

public interface CirculationMonthDataService {
	/**
	 * 新增一条记录
	 *
	 * <p>2017年2月8日 上午11:06:24 
	 * <p>create by hjc
	 * @param circulationMonthDataEntity
	 * @return
	 */
	public abstract int insertCirculationMonthData(CirculationMonthDataEntity circulationMonthDataEntity);
	/**
	 * 删除一条记录
	 *
	 * <p>2017年2月8日 上午11:06:36 
	 * <p>create by hjc
	 * @param circulationMonthDataEntity
	 * @return
	 */
	public abstract int deleteCirculationMonthData(CirculationMonthDataEntity circulationMonthDataEntity);
	/**
	 * 更新一条记录
	 *
	 * <p>2017年2月8日 上午11:06:36 
	 * <p>create by hjc
	 * @param circulationMonthDataEntity
	 * @return
	 */
	public abstract int updateCirculationMonthData(CirculationMonthDataEntity circulationMonthDataEntity);
	/**
	 * 查询一条记录
	 *
	 * <p>2017年2月8日 上午11:06:36 
	 * <p>create by hjc
	 * @param circulationMonthDataEntity
	 * @return
	 */
	public abstract CirculationMonthDataEntity queryCirculationMonthData(CirculationMonthDataEntity circulationMonthDataEntity);
	/**
	 * 查询多条记录
	 *
	 * <p>2017年2月8日 上午11:06:36 
	 * <p>create by hjc
	 * @param circulationMonthDataEntity
	 * @return
	 */
	public abstract List<CirculationMonthDataEntity> queryCirculationMonthDataList(CirculationMonthDataEntity circulationMonthDataEntity);
	
	/**
	 * 获取所有的周统计数据
	 *
	 * <p>2017年4月5日 下午7:55:39 
	 * <p>create by hjc
	 * @return
	 */
	public abstract List<CirculationMonthDataEntity> getAllCirculationMonth( );

}
