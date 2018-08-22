package com.ssitcloud.dbstatistics.rllcount.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dbstatistics.rllcount.dao.RllCountDao;
import com.ssitcloud.dbstatistics.rllcount.service.RllCountService;
import com.ssitcloud.devmgmt.entity.RllCountEntity;
import com.ssitcloud.devmgmt.entity.RllCountVO;

/**
 * 
 * 人流量统计数据
 * 
 * @author yeyalin
 * @data 2017年10月10日
 */
@Service
public class RllCountServiceImpl implements RllCountService {
	@Resource
	private RllCountDao rllCountDao;

	/**
	 * 批量插入实时统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	@Override
	public int insertVisitorsEveryDayBatch(List<RllCountEntity> list) {
		return rllCountDao.insertVisitorsEveryDay(list);
	}

	/**
	 * 插入周统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	@Override
	public int insertVisitorsEveryWeek(List<RllCountEntity> list) {
		return rllCountDao.insertVisitorsEveryWeek(list);
	}

	/**
	 * 批量插入月统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	@Override
	public int insertVisitorsEveryMonth(List<RllCountEntity> list) {
		return rllCountDao.insertVisitorsEveryMonth(list);
	}

	/**
	 * 批量插入年统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	@Override
	public int insertVisitorsEveryYear(List<RllCountEntity> list) {
		return rllCountDao.insertVisitorsEveryYear(list);
	}

	/**
	 * 批量更新实时统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	@Override
	public int updateVisitorsEveryDayBatch(List<RllCountEntity> list) {
		int sum = 0;
		if (list != null && list.size() > 0) {
			for (RllCountEntity vo : list) {

				int count = rllCountDao.updateVisitorsEveryDay(vo);
				sum += count;
			}
		}
		return sum;
	}

	/**
	 * 批量更新周统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	@Override
	public int updateVisitorsEveryWeekBatch(List<RllCountEntity> list) {
		int sum = 0;
		if (list != null && list.size() > 0) {
			for (RllCountEntity vo : list) {

				int count = rllCountDao.updateVisitorsEveryWeek(vo);
				sum += count;
			}
		}
		return sum;
	}

	/**
	 * 批量更新月统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	@Override
	public int updateVisitorsEveryMonthBatch(List<RllCountEntity> list) {
		int sum = 0;
		if (list != null && list.size() > 0) {
			for (RllCountEntity vo : list) {

				int count = rllCountDao.updateVisitorsEveryMonth(vo);
				sum += count;
			}
		}
		return sum;
	}

	/**
	 * 批量更新年统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	@Override
	public int updateVisitorsEveryYearBatch(List<RllCountEntity> list) {
		int sum = 0;
		if (list != null && list.size() > 0) {
			for (RllCountEntity vo : list) {

				int count = rllCountDao.updateVisitorsEveryYear(vo);
				sum += count;
			}
		}
		return sum;
	}

	/**
	 * 删除实时统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	@Override
	public int deleteVisitorsEveryDay(RllCountEntity rllCountEntity) {
		return rllCountDao.deleteVisitorsEveryDay(rllCountEntity);
	}

	/**
	 * 删除周统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	@Override
	public int deleteVisitorsEveryWeek(RllCountEntity rllCountEntity) {
		return rllCountDao.deleteVisitorsEveryWeek(rllCountEntity);
	}

	/**
	 * 删除月统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	@Override
	public int deleteVisitorsEveryMonth(RllCountEntity rllCountEntity) {
		return rllCountDao.deleteVisitorsEveryMonth(rllCountEntity);
	}

	/**
	 * 删除年统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	@Override
	public int deleteVisitorsEveryYear(RllCountEntity rllCountEntity) {
		return rllCountDao.deleteVisitorsEveryYear(rllCountEntity);
	}

	/**
	 * 查询实时计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	@Override
	public List<RllCountEntity> selectVisitorsEveryDay(
			RllCountEntity rllCountEntity) {
		return rllCountDao.selectVisitorsEveryDay(rllCountEntity);
	}

	/**
	 * 查询周计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	@Override
	public List<RllCountEntity> selectVisitorsEveryWeek(
			RllCountEntity rllCountEntity) {
		return rllCountDao.selectVisitorsEveryWeek(rllCountEntity);
	}

	/**
	 * 查询月计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	@Override
	public List<RllCountEntity> selectVisitorsEveryMonth(
			RllCountEntity rllCountEntity) {
		return rllCountDao.selectVisitorsEveryMonth(rllCountEntity);
	}

	/**
	 * 查询年计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	@Override
	public List<RllCountEntity> selectVisitorsEveryYear(
			RllCountEntity rllCountEntity) {
		return rllCountDao.selectVisitorsEveryYear(rllCountEntity);
	}

	/**
	 * 获取最后更新时间
	 */
	@Override
	public List<RllCountVO> getLeastUpdateTime() {
		return rllCountDao.getLastUpdateTime();
	}

	/**
	 * 按时间删除上个月的每天数据（实时数据）
	 * @param rllCountEntity
	 */
	@Override
	public int deleteVisitorsEveryDayByTime(RllCountEntity rllCountEntity) {
		return rllCountDao.deleteVisitorsEveryDayByTime(rllCountEntity);
	}

	/**
	 * 按时间删除上周以前的数据
	 * @param rllCountEntity
	 */
	@Override
	public int deleteVisitorsEveryWeekByTime(RllCountEntity rllCountEntity) {
		return rllCountDao.deleteVisitorsEveryWeekByTime(rllCountEntity);
	}

	/**
	 * 按时间删除上个月以前的数据
	 * @param rllCountEntity
	 */
	@Override
	public int deleteVisitorsEveryMonthByTime(RllCountEntity rllCountEntity) {
		return rllCountDao.deleteVisitorsEveryMonthByTime(rllCountEntity);
	}

	/**
	 * 按时间删除上年以前的数据
	 * @param rllCountEntity
	 */
	@Override
	public int deleteVisitorsEveryYearByTime(RllCountEntity rllCountEntity) {
		return rllCountDao.deleteVisitorsEveryYearByTime(rllCountEntity);
	}
	/**
	 * 通过ids批量删除数据
	 * @param rllCountEntity
	 */
	public int  deleteVisitorsEveryDayBatchById(List<RllCountEntity> rllCountEntitys){
		return rllCountDao.deleteVisitorsEveryDayBatchById(rllCountEntitys);
	}
	/**
	 * 通过ids批量删除数据
	 * @param rllCountEntity
	 */
	public int  deleteVisitorsEveryWeekBatchById(List<RllCountEntity> rllCountEntitys){
		return rllCountDao.deleteVisitorsEveryWeekBatchById(rllCountEntitys);
	}
	/**
	 * 通过ids批量删除数据
	 * @param rllCountEntity
	 */
	public int  deleteVisitorsEveryMonthBatchById(List<RllCountEntity> rllCountEntitys){
		return rllCountDao.deleteVisitorsEveryMonthBatchById(rllCountEntitys);
	}
	/**
	 * 通过ids批量删除数据
	 * @param rllCountEntity
	 */
	public int  deleteVisitorsEveryYearBatchById(List<RllCountEntity> rllCountEntitys){
		return rllCountDao.deleteVisitorsEveryYearBatchById(rllCountEntitys);
	}
	/**
	 * 根据时间删除周统计数据
	 * @param rllCountEntity
	 * @return
	 */
	public  int deleteVisitorsEveryWeekByUpdateTime(RllCountEntity rllCountEntity){
		return rllCountDao.deleteVisitorsEveryWeekByUpdateTime(rllCountEntity);
	}
	/**
	 * 根据时间删除月统计数据
	 * @param rllCountEntity
	 * @return
	 */
	public  int deleteVisitorsEveryMonthByUpdateTime(RllCountEntity rllCountEntity){
		return rllCountDao.deleteVisitorsEveryMonthByUpdateTime(rllCountEntity);
	}
	/**
	 * 根据时间删除年统计数据
	 * @param rllCountEntity
	 * @return
	 */
	public  int deleteVisitorsEveryYearByUpdateTime(RllCountEntity rllCountEntity){
		return rllCountDao.deleteVisitorsEveryYearByUpdateTime(rllCountEntity);
	}
}
