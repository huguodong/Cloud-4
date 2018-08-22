package com.ssitcloud.dbstatistics.dao;

import java.util.List;

import com.ssitcloud.dbstatistics.entity.CirculationYearDataEntity;

public interface CirculationYearDataDao {
	/**
	 * 新增一条记录
	 *
	 * <p>2017年2月8日 上午11:06:24 
	 * <p>create by hjc
	 * @param circulationYearDataEntity
	 * @return
	 */
	public abstract int insertCirculationYearData(CirculationYearDataEntity circulationYearDataEntity);
	/**
	 * 删除一条记录
	 *
	 * <p>2017年2月8日 上午11:06:36 
	 * <p>create by hjc
	 * @param circulationYearDataEntity
	 * @return
	 */
	public abstract int deleteCirculationYearData(CirculationYearDataEntity circulationYearDataEntity);
	/**
	 * 更新一条记录
	 *
	 * <p>2017年2月8日 上午11:06:36 
	 * <p>create by hjc
	 * @param circulationYearDataEntity
	 * @return
	 */
	public abstract int updateCirculationYearData(CirculationYearDataEntity circulationYearDataEntity);
	/**
	 * 查询一条记录
	 *
	 * <p>2017年2月8日 上午11:06:36 
	 * <p>create by hjc
	 * @param circulationYearDataEntity
	 * @return
	 */
	public abstract CirculationYearDataEntity queryCirculationYearData(CirculationYearDataEntity circulationYearDataEntity);
	/**
	 * 查询多条记录
	 *
	 * <p>2017年2月8日 上午11:06:36 
	 * <p>create by hjc
	 * @param circulationYearDataEntity
	 * @return
	 */
	public abstract List<CirculationYearDataEntity> queryCirculationYearDataList(CirculationYearDataEntity circulationYearDataEntity);
	
	/**
	 * 获取所有的年统计数据
	 *
	 * <p>2017年4月5日 下午7:57:57 
	 * <p>create by hjc
	 * @param circulationYearDataEntity
	 * @return
	 */
	public abstract List<CirculationYearDataEntity> getAllCirculationYear();

}
