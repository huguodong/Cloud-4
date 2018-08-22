package com.ssitcloud.dbstatistics.service;

import java.util.List;

import com.ssitcloud.dbstatistics.entity.CirculationDayDataEntity;

public interface CirculationDayDataService {
	/**
	 * 新增一条记录
	 *
	 * <p>2017年2月8日 上午11:06:24 
	 * <p>create by hjc
	 * @param circulationDayDataEntity
	 * @return
	 */
	public abstract int insertCirculationDayData(CirculationDayDataEntity circulationDayDataEntity);
	/**
	 * 删除一条记录
	 *
	 * <p>2017年2月8日 上午11:06:36 
	 * <p>create by hjc
	 * @param circulationDayDataEntity
	 * @return
	 */
	public abstract int deleteCirculationDayData(CirculationDayDataEntity circulationDayDataEntity);
	/**
	 * 更新一条记录
	 *
	 * <p>2017年2月8日 上午11:06:36 
	 * <p>create by hjc
	 * @param circulationDayDataEntity
	 * @return
	 */
	public abstract int updateCirculationDayData(CirculationDayDataEntity circulationDayDataEntity);
	/**
	 * 查询一条记录
	 *
	 * <p>2017年2月8日 上午11:06:36 
	 * <p>create by hjc
	 * @param circulationDayDataEntity
	 * @return
	 */
	public abstract CirculationDayDataEntity queryCirculationDayData(CirculationDayDataEntity circulationDayDataEntity);
	/**
	 * 查询多条记录
	 *
	 * <p>2017年2月8日 上午11:06:36 
	 * <p>create by hjc
	 * @param circulationDayDataEntity
	 * @return
	 */
	public abstract List<CirculationDayDataEntity> queryCirculationDayDataList(CirculationDayDataEntity circulationDayDataEntity);
	
	/**
	 * 获取所有的记录
	 *
	 * <p>2017年4月1日 下午4:08:07 
	 * <p>create by hjc
	 * @param circulationDayDataEntity
	 * @return
	 */
	public abstract List<CirculationDayDataEntity> getAllCirculationDay();

}
