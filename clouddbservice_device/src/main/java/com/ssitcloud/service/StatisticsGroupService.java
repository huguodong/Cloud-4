package com.ssitcloud.service;

import java.util.List;

import com.ssitcloud.common.entity.ResultEntity;
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
public interface StatisticsGroupService {
	/**
	 * 模板组StatisticsGroupEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:36:42
	 * @param statisticsGroupEntity
	 * @return
	 */
	int insertStatisticsGroup(StatisticsGroupEntity statisticsGroupEntity);
	
	/**
	 * 模板组StatisticsGroupEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:36:58
	 * @param statisticsGroupEntity
	 * @return
	 */
	int updateStatisticsGroup(StatisticsGroupEntity statisticsGroupEntity);
	
	/**
	 * 模板组StatisticsGroupEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:37:20
	 * @param statisticsGroupEntity
	 * @return
	 */
	int deleteStatisticsGroup(StatisticsGroupEntity statisticsGroupEntity);
	
	/**
	 * 模板组StatisticsGroupEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:36
	 * @param statisticsGroupEntity
	 * @return
	 */
	StatisticsGroupEntity queryOneStatisticsGroup(StatisticsGroupEntity statisticsGroupEntity);
	
	/**
	 * 模板组StatisticsGroupEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:49
	 * @param statisticsGroupEntity
	 * @return
	 */
	List<StatisticsGroupEntity> queryStatisticsGroups(StatisticsGroupEntity statisticsGroupEntity);
	
	/**
	 * 模板组StatisticsGroupEntity多个分页查询
	 * author huanghuang
	 * 2017年2月20日 下午1:42:41
	 * @param sGroupMgmtPageEntity
	 * @return
	 */
	StatisticsGroupMgmtPageEntity selectStatisticsGroupByPage(StatisticsGroupMgmtPageEntity sGroupMgmtPageEntity);
	/**
	 * 插入模板组
	 * author huanghuang
	 * 2017年4月27日 下午8:08:09
	 * @param req
	 * @return
	 */
	ResultEntity addStatisticsGroup(String req);
	/**
	 * 修改模板组
	 * author huanghuang
	 * 2017年4月27日 下午8:08:09
	 * @param req
	 * @return
	 */
	ResultEntity updStatisticsGroup(String req);
	/**
	 * 删除模板组
	 * author huanghuang
	 * 2017年4月27日 下午8:08:09
	 * @param req
	 * @return
	 */
	ResultEntity delStatisticsGroup(String req);
	/**
	 * 模板组StatisticsGroupEntity多个分页查询
	 * author huanghuang
	 * 2017年2月20日 下午1:42:41
	 * @param sGroupMgmtPageEntity
	 * @return
	 */
	StatisticsGroupMgmtPageEntity querysGroupByPage(StatisticsGroupMgmtPageEntity sGroupMgmtPageEntity);
}
