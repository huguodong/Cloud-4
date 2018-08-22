package com.ssitcloud.dbstatistics.rllcount.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.util.CommonUtils;
import com.ssitcloud.dbstatistics.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbstatistics.rllcount.dao.RllCountDao;
import com.ssitcloud.devmgmt.entity.RllCountEntity;
import com.ssitcloud.devmgmt.entity.RllCountVO;

/**
 * 人流量统计数据
 * 
 * @author yeyalin
 * @data 2017年10月10日
 */
@Repository
public class RllCountDaoImpl extends CommonDaoImpl implements RllCountDao {

	/**
	 * 插入实时统计数据 分批插入数据每次插入200条数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	@Override
	public int insertVisitorsEveryDay(List<RllCountEntity> rllCountEntitys) {
		int count = 0;
		if (rllCountEntitys != null && rllCountEntitys.size() > 0) {
			// 分批插入数据每次插入200条数据
			List<List<RllCountEntity>> everyPageInsertRllCounts = CommonUtils
					.averageAssign(rllCountEntitys, 200);
			for (List<RllCountEntity> rllCountEntityTemps : everyPageInsertRllCounts) {
				count += this.sqlSessionTemplate.insert("rllcount.insertVisitorsEveryDay",rllCountEntityTemps);
			}
		}
		return count;
	}

	/**
	 * 插入周统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	@Override
	public int insertVisitorsEveryWeek(List<RllCountEntity> rllCountEntitys) {
		int count = 0;
		if (rllCountEntitys != null && rllCountEntitys.size() > 0) {
			// 分批插入数据每次插入200条数据
			List<List<RllCountEntity>> everyPageInsertRllCounts = CommonUtils
					.averageAssign(rllCountEntitys, 200);
			for (List<RllCountEntity> rllCountEntityTemps : everyPageInsertRllCounts) {
				count += this.sqlSessionTemplate.insert("rllcount.insertVisitorsEveryWeek",rllCountEntityTemps);
			}
		}
		return count;
	}

	/**
	 * 插入月统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	@Override
	public int insertVisitorsEveryMonth(List<RllCountEntity> rllCountEntitys) {
		int count = 0;
		if (rllCountEntitys != null && rllCountEntitys.size() > 0) {
			// 分批插入数据每次插入200条数据
			List<List<RllCountEntity>> everyPageInsertRllCounts = CommonUtils
					.averageAssign(rllCountEntitys, 200);
			for (List<RllCountEntity> rllCountEntityTemps : everyPageInsertRllCounts) {
				count += this.sqlSessionTemplate.insert("rllcount.insertVisitorsEveryMonth",rllCountEntityTemps);
			}
		}
		return count;
	}

	/**
	 * 插入年统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	@Override
	public int insertVisitorsEveryYear(List<RllCountEntity> rllCountEntitys) {
		
		int count = 0;
		if (rllCountEntitys != null && rllCountEntitys.size() > 0) {
			// 分批插入数据每次插入200条数据
			List<List<RllCountEntity>> everyPageInsertRllCounts = CommonUtils
					.averageAssign(rllCountEntitys, 200);
			for (List<RllCountEntity> rllCountEntityTemps : everyPageInsertRllCounts) {
				count += this.sqlSessionTemplate.insert("rllcount.insertVisitorsEveryYear",rllCountEntityTemps);
			}
		}
		return count;
	}

	/**
	 * 更新实时统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	@Override
	public int updateVisitorsEveryDay(RllCountEntity rllCountEntity) {
		return this.sqlSessionTemplate.update(
				"rllcount.updateVisitorsEveryDay", rllCountEntity);
	}

	/**
	 * 更新周统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	@Override
	public int updateVisitorsEveryWeek(RllCountEntity rllCountEntity) {
		return this.sqlSessionTemplate.update(
				"rllcount.updateVisitorsEveryWeek", rllCountEntity);
	}

	/**
	 * 更新月统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	@Override
	public int updateVisitorsEveryMonth(RllCountEntity rllCountEntity) {
		return this.sqlSessionTemplate.update(
				"rllcount.updateVisitorsEveryMonth", rllCountEntity);
	}

	/**
	 * 更新年统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	@Override
	public int updateVisitorsEveryYear(RllCountEntity rllCountEntity) {
		return this.sqlSessionTemplate.update(
				"rllcount.updateVisitorsEveryYear", rllCountEntity);
	}

	/**
	 * 删除实时统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	@Override
	public int deleteVisitorsEveryDay(RllCountEntity rllCountEntity) {
		return this.sqlSessionTemplate.delete(
				"rllcount.deleteVisitorsEveryDay", rllCountEntity);
	}

	/**
	 * 删除周统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	@Override
	public int deleteVisitorsEveryWeek(RllCountEntity rllCountEntity) {
		return this.sqlSessionTemplate.delete(
				"rllcount.deleteVisitorsEveryWeek", rllCountEntity);
	}

	/**
	 * 删除据月统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	@Override
	public int deleteVisitorsEveryMonth(RllCountEntity rllCountEntity) {
		return this.sqlSessionTemplate.delete(
				"rllcount.deleteVisitorsEveryMonth", rllCountEntity);
	}

	/**
	 * 删除年统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	@Override
	public int deleteVisitorsEveryYear(RllCountEntity rllCountEntity) {
		return this.sqlSessionTemplate.delete(
				"rllcount.deleteVisitorsEveryYear", rllCountEntity);
	}

	/**
	 * 查询实时统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	@Override
	public List<RllCountEntity> selectVisitorsEveryDay(
			RllCountEntity rllCountEntity) {
		return this.sqlSessionTemplate.selectList(
				"rllcount.selectVisitorsEveryDay", rllCountEntity);
	}

	/**
	 * 查询周统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	@Override
	public List<RllCountEntity> selectVisitorsEveryWeek(
			RllCountEntity rllCountEntity) {
		return this.sqlSessionTemplate.selectList(
				"rllcount.selectVisitorsEveryWeek", rllCountEntity);
	}

	/**
	 * 查询月统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	@Override
	public List<RllCountEntity> selectVisitorsEveryMonth(
			RllCountEntity rllCountEntity) {
		return this.sqlSessionTemplate.selectList(
				"rllcount.selectVisitorsEveryMonth", rllCountEntity);
	}

	/**
	 * 查询年统计数据
	 * 
	 * @param rllCountEntity
	 * @return
	 */
	@Override
	public List<RllCountEntity> selectVisitorsEveryYear(
			RllCountEntity rllCountEntity) {
		return this.sqlSessionTemplate.selectList(
				"rllcount.selectVisitorsEveryYear", rllCountEntity);
	}

	/**
	 * 获取最后更新时间
	 * 
	 */
	@Override
	public List<RllCountVO> getLastUpdateTime() {
		return this.sqlSessionTemplate.selectList("rllcount.getLastUpdateTime");
	}

	/**
	 * 按时间删除上个月的实时数据（每天数据）
	 * 
	 * @param rllCountEntity
	 */
	@Override
	public int deleteVisitorsEveryDayByTime(RllCountEntity rllCountEntity) {
		return this.sqlSessionTemplate.delete(
				"rllcount.deleteVisitorsEveryDayByTime", rllCountEntity);
	}

	/**
	 * 按时间删除上周以前的数据
	 * 
	 * @param rllCountEntity
	 */
	@Override
	public int deleteVisitorsEveryWeekByTime(RllCountEntity rllCountEntity) {
		return this.sqlSessionTemplate.delete(
				"rllcount.deleteVisitorsEveryWeekByTime", rllCountEntity);
	}

	/**
	 * 按时间删除上个月的以前数据
	 * 
	 * @param rllCountEntity
	 */
	@Override
	public int deleteVisitorsEveryMonthByTime(RllCountEntity rllCountEntity) {
		return this.sqlSessionTemplate.delete(
				"rllcount.deleteVisitorsEveryMonthByTime", rllCountEntity);
	}

	/**
	 * 按时间删除上年以前的数据
	 * 
	 * @param rllCountEntity
	 */
	@Override
	public int deleteVisitorsEveryYearByTime(RllCountEntity rllCountEntity) {
		return this.sqlSessionTemplate.delete(
				"rllcount.deleteVisitorsEveryYearByTime", rllCountEntity);
	}
	/**
	 * 通过ids批量删除数据
	 * @param rllCountEntity
	 */
	public int  deleteVisitorsEveryDayBatchById(List<RllCountEntity> rllCountEntitys){
		if (rllCountEntitys != null && rllCountEntitys.size() > 0) {
			
			return this.sqlSessionTemplate.delete(
					"rllcount.deleteVisitorsEveryDayBatchById", rllCountEntitys);
		}else {
			return 0;
		}
	}

	/**
	 * 通过ids批量删除数据
	 * @param rllCountEntity
	 */
	public int deleteVisitorsEveryWeekBatchById(
			List<RllCountEntity> rllCountEntitys) {
		if (rllCountEntitys != null && rllCountEntitys.size() > 0) {
			
			return this.sqlSessionTemplate.delete(
					"rllcount.deleteVisitorsEveryWeekBatchById", rllCountEntitys);
		}else {
			return 0;
		}
	}

	/**
	 * 通过ids批量删除数据
	 * @param rllCountEntity
	 */
	public int deleteVisitorsEveryMonthBatchById(
			List<RllCountEntity> rllCountEntitys) {
		if (rllCountEntitys != null && rllCountEntitys.size() > 0) {
			
			return this.sqlSessionTemplate.delete(
					"rllcount.deleteVisitorsEveryMonthBatchById", rllCountEntitys);
		}else {
			return 0;
		}
	}

	/**
	 * 通过ids批量删除数据
	 * @param rllCountEntity
	 */
	public int deleteVisitorsEveryYearBatchById(
			List<RllCountEntity> rllCountEntitys) {
		if (rllCountEntitys != null && rllCountEntitys.size() > 0) {
			
			return this.sqlSessionTemplate.delete(
					"rllcount.deleteVisitorsEveryYearBatchById", rllCountEntitys);
		}else {
			return 0;
		}
	}
	/**
	 * 根据时间删除周统计数据
	 * @param rllCountEntity
	 * @return
	 */
	public  int deleteVisitorsEveryWeekByUpdateTime(RllCountEntity rllCountEntity){
		return this.sqlSessionTemplate.delete("rllcount.deleteVisitorsEveryWeekByUpdateTime", rllCountEntity);
	}
	/**
	 * 根据时间删除月统计数据
	 * @param rllCountEntity
	 * @return
	 */
	public  int deleteVisitorsEveryMonthByUpdateTime(RllCountEntity rllCountEntity){
		return this.sqlSessionTemplate.delete("rllcount.deleteVisitorsEveryMonthByUpdateTime", rllCountEntity);
	}
	/**
	 * 根据时间删除年统计数据
	 * @param rllCountEntity
	 * @return
	 */
	public  int deleteVisitorsEveryYearByUpdateTime(RllCountEntity rllCountEntity){
		return this.sqlSessionTemplate.delete("rllcount.deleteVisitorsEveryYearByUpdateTime", rllCountEntity);
	}
}
