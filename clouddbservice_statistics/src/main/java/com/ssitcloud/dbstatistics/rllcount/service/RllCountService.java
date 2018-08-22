package com.ssitcloud.dbstatistics.rllcount.service;

import java.util.List;

import com.ssitcloud.devmgmt.entity.RllCountEntity;
import com.ssitcloud.devmgmt.entity.RllCountVO;

/**
 * 
 * 人流量统计数据
 * 
 * @author yeyalin
 * @data 2017年10月10日
 */
public interface RllCountService {
	/**
	 * 批量插入实时统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	public int insertVisitorsEveryDayBatch(List<RllCountEntity> list);
	
	/**
	 * 批量插入周统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	public int insertVisitorsEveryWeek(List<RllCountEntity> list);

	/**
	 * 批量插入月统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	public int insertVisitorsEveryMonth(List<RllCountEntity> list);

	/**
	 * 批量插入年统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	public int insertVisitorsEveryYear(List<RllCountEntity> list);

	/**
	 * 批量更新实时统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	public int updateVisitorsEveryDayBatch(List<RllCountEntity> list);

	/**
	 * 批量更新周统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	public int updateVisitorsEveryWeekBatch(List<RllCountEntity> list);

	/**
	 * 批量更新月统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	public int updateVisitorsEveryMonthBatch(List<RllCountEntity> list);

	/**
	 * 批量更新年统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	public int updateVisitorsEveryYearBatch(List<RllCountEntity> list);

	/**
	 * 删除实时统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	public int deleteVisitorsEveryDay(RllCountEntity rllCountEntity);

	/**
	 * 删除周统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	public int deleteVisitorsEveryWeek(RllCountEntity rllCountEntity);

	/**
	 * 删除据月统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	public int deleteVisitorsEveryMonth(RllCountEntity rllCountEntity);

	/**
	 * 删除年统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	public int deleteVisitorsEveryYear(RllCountEntity rllCountEntity);

	/**
	 * 查询实时统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	public List<RllCountEntity> selectVisitorsEveryDay(
			RllCountEntity rllCountEntity);

	/**
	 * 查询周统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	public List<RllCountEntity> selectVisitorsEveryWeek(
			RllCountEntity rllCountEntity);

	/**
	 * 查询月统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	public List<RllCountEntity> selectVisitorsEveryMonth(
			RllCountEntity rllCountEntity);

	/**
	 * 查询年统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	public List<RllCountEntity> selectVisitorsEveryYear(
			RllCountEntity rllCountEntity);
	/**
	 * 获取最后更新时间
	 */
	public List<RllCountVO> getLeastUpdateTime();
	/**
	 * 按时间删除上个月的数据
	 * @param rllCountEntity
	 */
	public int  deleteVisitorsEveryDayByTime(RllCountEntity rllCountEntity);
	/**
	 * 按时间删除上周以前的数据
	 * @param rllCountEntity
	 */
	public int  deleteVisitorsEveryWeekByTime(RllCountEntity rllCountEntity);
	/**
	 * 按时间删除上个月以前的数据
	 * @param rllCountEntity
	 */
	public int  deleteVisitorsEveryMonthByTime(RllCountEntity rllCountEntity);
	/**
	 * 按时间删除上年以前的数据
	 * @param rllCountEntity
	 */
	public int  deleteVisitorsEveryYearByTime(RllCountEntity rllCountEntity);
	
	/**
	 * 通过ids批量删除数据
	 * @param rllCountEntity
	 */
	public int  deleteVisitorsEveryDayBatchById(List<RllCountEntity> rllCountEntitys);
	/**
	 * 通过ids批量删除数据
	 * @param rllCountEntity
	 */
	public int  deleteVisitorsEveryWeekBatchById(List<RllCountEntity> rllCountEntitys);
	/**
	 * 通过ids批量删除数据
	 * @param rllCountEntity
	 */
	public int  deleteVisitorsEveryMonthBatchById(List<RllCountEntity> rllCountEntitys);
	/**
	 * 通过ids批量删除数据
	 * @param rllCountEntity
	 */
	public int  deleteVisitorsEveryYearBatchById(List<RllCountEntity> rllCountEntitys);
	/**
	 * 根据时间删除周统计数据
	 * @param rllCountEntity
	 * @return
	 */
	public  int deleteVisitorsEveryWeekByUpdateTime(RllCountEntity rllCountEntity);
	/**
	 * 根据时间删除月统计数据
	 * @param rllCountEntity
	 * @return
	 */
	public  int deleteVisitorsEveryMonthByUpdateTime(RllCountEntity rllCountEntity);
	/**
	 * 根据时间删除年统计数据
	 * @param rllCountEntity
	 * @return
	 */
	public  int deleteVisitorsEveryYearByUpdateTime(RllCountEntity rllCountEntity);
}
