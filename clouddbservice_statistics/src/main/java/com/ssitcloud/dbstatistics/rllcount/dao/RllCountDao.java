package com.ssitcloud.dbstatistics.rllcount.dao;

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
public interface RllCountDao {
	
	/**
	 * 插入实时统计数据
	 * 分批插入数据每次插入200条数据
	 * @param rllCountEntity
	 * @return
	 */
	public  int insertVisitorsEveryDay(List<RllCountEntity> rllCountEntitys);
	/**
	 * 插入周统计数据
	 * @param rllCountEntity
	 * @return
	 */
	public  int insertVisitorsEveryWeek(List<RllCountEntity> rllCountEntitys);
	/**
	 * 插入月统计数据
	 * @param rllCountEntity
	 * @return
	 */
	public  int insertVisitorsEveryMonth(List<RllCountEntity> rllCountEntitys);
	/**
	 * 插入年统计数据
	 * @param rllCountEntity
	 * @return
	 */
	public  int insertVisitorsEveryYear(List<RllCountEntity> rllCountEntitys);
	
	/**
	 * 更新实时统计数据
	 * @param rllCountEntity
	 * @return
	 */
	public  int updateVisitorsEveryDay(RllCountEntity rllCountEntity);
	/**
	 * 更新周统计数据
	 * @param rllCountEntity
	 * @return
	 */
	public  int updateVisitorsEveryWeek(RllCountEntity rllCountEntity);
	/**
	 * 更新月统计数据
	 * @param rllCountEntity
	 * @return
	 */
	public  int updateVisitorsEveryMonth(RllCountEntity rllCountEntity);
	/**
	 * 更新年统计数据
	 * @param rllCountEntity
	 * @return
	 */
	public  int updateVisitorsEveryYear(RllCountEntity rllCountEntity);
	
	/**
	 * 删除实时统计数据
	 * @param rllCountEntity
	 * @return
	 */
	public  int deleteVisitorsEveryDay(RllCountEntity rllCountEntity);
	/**
	 * 删除周统计数据
	 * @param rllCountEntity
	 * @return
	 */
	public  int deleteVisitorsEveryWeek(RllCountEntity rllCountEntity);
	/**
	 * 删除据月统计数据
	 * @param rllCountEntity
	 * @return
	 */
	public  int deleteVisitorsEveryMonth(RllCountEntity rllCountEntity);
	/**
	 * 删除年统计数据
	 * @param rllCountEntity
	 * @return
	 */
	public  int deleteVisitorsEveryYear(RllCountEntity rllCountEntity);
	
	/**
	 * 查询实时统计数据
	 * @param rllCountEntity
	 * @return
	 */
	public List<RllCountEntity> selectVisitorsEveryDay(RllCountEntity rllCountEntity);
	/**
	 * 查询周统计数据
	 * @param rllCountEntity
	 * @return
	 */
	public List<RllCountEntity> selectVisitorsEveryWeek(RllCountEntity rllCountEntity);
	/**
	 * 查询月统计数据
	 * @param rllCountEntity
	 * @return
	 */
	public List<RllCountEntity> selectVisitorsEveryMonth(RllCountEntity rllCountEntity);
	/**
	 * 查询年统计数据
	 * @param rllCountEntity
	 * @return
	 */
	public List<RllCountEntity> selectVisitorsEveryYear(RllCountEntity rllCountEntity);
	
	/**
	 * 获取最后更新时间
	 */
	public List<RllCountVO> getLastUpdateTime();
	
	/**
	 * 按时间删除上个月的实时数据（每天数据）
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
