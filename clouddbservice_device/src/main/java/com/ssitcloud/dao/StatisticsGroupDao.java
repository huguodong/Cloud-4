package com.ssitcloud.dao;

import java.util.List;

import com.ssitcloud.entity.StatisticsGroupEntity;
import com.ssitcloud.entity.page.OperGroupMgmtPageEntity;
import com.ssitcloud.entity.page.StatisticsGroupMgmtPageEntity;

/**
 * 模板组
 *
 * <p>2017年2月10日 下午2:21:49  
 * @author hjc 
 *
 */
public interface StatisticsGroupDao {
	/**
	 * 模板组StatisticsGroupEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:30:27
	 * @param statisticsGroupEntity
	 * @return
	 */
	public abstract int insertStatisticsGroup(StatisticsGroupEntity statisticsGroupEntity);
	
	/**
	 * 模板组StatisticsGroupEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:30:55
	 * @param statisticsGroupEntity
	 * @return
	 */
	public abstract int updateStatisticsGroup(StatisticsGroupEntity statisticsGroupEntity);
	
	/**
	 * 模板组StatisticsGroupEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:31:18
	 * @param statisticsGroupEntity
	 * @return
	 */
	public abstract int deleteStatisticsGroup(StatisticsGroupEntity statisticsGroupEntity);
	
	/**
	 * 模板组StatisticsGroupEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:31:47
	 * @param statisticsGroupEntity
	 * @return
	 */
	public abstract StatisticsGroupEntity queryOneStatisticsGroup(StatisticsGroupEntity statisticsGroupEntity);
	
	/**
	 * 模板组StatisticsGroupEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:32:05
	 * @param statisticsGroupEntity
	 * @return
	 */
	public abstract List<StatisticsGroupEntity> queryStatisticsGroups(StatisticsGroupEntity statisticsGroupEntity);
	
	/**
	 * 模板组StatisticsGroupEntity多个分页查询
	 * author huanghuang
	 * 2017年2月20日 下午1:46:53
	 * @param sGroupMgmtPageEntity
	 * @return
	 */
	public abstract StatisticsGroupMgmtPageEntity queryStatisticsGroupByPage(StatisticsGroupMgmtPageEntity sGroupMgmtPageEntity);
	/**
	 * 模板组StatisticsGroupEntity多个分页查询
	 * author huanghuang
	 * 2017年2月20日 下午1:46:53
	 * @param sGroupMgmtPageEntity
	 * @return
	 */
	public abstract StatisticsGroupMgmtPageEntity querysGroupByPage(StatisticsGroupMgmtPageEntity sGroupMgmtPageEntity);
}
