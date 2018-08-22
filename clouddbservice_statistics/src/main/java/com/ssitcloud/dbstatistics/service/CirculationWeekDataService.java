package com.ssitcloud.dbstatistics.service;

import java.util.List;

import com.ssitcloud.dbstatistics.entity.CirculationWeekDataEntity;

public interface CirculationWeekDataService {
	/**
	 * 新增一条记录
	 *
	 * <p>2017年2月8日 上午11:06:24 
	 * <p>create by hjc
	 * @param circulationWeekDataEntity
	 * @return
	 */
	public abstract int insertCirculationWeekData(CirculationWeekDataEntity circulationWeekDataEntity);
	/**
	 * 删除一条记录
	 *
	 * <p>2017年2月8日 上午11:06:36 
	 * <p>create by hjc
	 * @param circulationWeekDataEntity
	 * @return
	 */
	public abstract int deleteCirculationWeekData(CirculationWeekDataEntity circulationWeekDataEntity);
	/**
	 * 更新一条记录
	 *
	 * <p>2017年2月8日 上午11:06:36 
	 * <p>create by hjc
	 * @param circulationWeekDataEntity
	 * @return
	 */
	public abstract int updateCirculationWeekData(CirculationWeekDataEntity circulationWeekDataEntity);
	/**
	 * 查询一条记录
	 *
	 * <p>2017年2月8日 上午11:06:36 
	 * <p>create by hjc
	 * @param circulationWeekDataEntity
	 * @return
	 */
	public abstract CirculationWeekDataEntity queryCirculationWeekData(CirculationWeekDataEntity circulationWeekDataEntity);
	/**
	 * 查询多条记录
	 *
	 * <p>2017年2月8日 上午11:06:36 
	 * <p>create by hjc
	 * @param circulationWeekDataEntity
	 * @return
	 */
	public abstract List<CirculationWeekDataEntity> queryCirculationWeekDataList(CirculationWeekDataEntity circulationWeekDataEntity);
	
	/**
	 * 获取所有的记录
	 *
	 * <p>2017年4月1日 下午4:08:07 
	 * <p>create by hjc
	 * @param circulationDayDataEntity
	 * @return
	 */
	public abstract List<CirculationWeekDataEntity> getAllCirculationWeek();

}
