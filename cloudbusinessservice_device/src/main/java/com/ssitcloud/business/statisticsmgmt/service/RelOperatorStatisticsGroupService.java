package com.ssitcloud.business.statisticsmgmt.service;


import com.ssitcloud.common.entity.ResultEntity;


/**
 * 操作员组与模板组关联表
 *
 * <p>2017年3月13日 
 * @author lqw 
 *
 */
public interface RelOperatorStatisticsGroupService {
	/**
	 * 操作员组与模板组关联RelOperatorStatisticsGroupEntity插入
	 * author lqw
	 * 2017年3月13日
	 * @param relOperatorStatisticsGroupEntity
	 * @return
	 */
	public abstract ResultEntity insertRelOperatorStatisticsGroup(String req);
	
	/**
	 * 操作员组与模板组关联RelOperatorStatisticsGroupEntity修改
	 * author lqw
	 * 2017年3月13日
	 * @param relOperatorStatisticsGroupEntity
	 * @return
	 */
	public abstract ResultEntity updateRelOperatorStatisticsGroup(String req);
	
	/**
	 * 操作员组与模板组关联RelOperatorStatisticsGroupEntity删除
	 * author lqw
	 * 2017年3月13日
	 * @param relOperatorStatisticsGroupEntity
	 * @return
	 */
	public abstract ResultEntity deleteRelOperatorStatisticsGroup(String req);
	
	/**
	 * 操作员组与模板组关联RelOperatorStatisticsGroupEntity单个查询
	 * author lqw
	 * 2017年3月13日
	 * @param relOperatorStatisticsGroupEntity
	 * @return
	 */
	public abstract ResultEntity queryOneRelOperatorStatisticsGroup(String req);
	
	/**
	 * 操作员组与模板组关联RelOperatorStatisticsGroupEntity多个查询
	 * author lqw
	 * 2017年3月13日
	 * @param relOperatorStatisticsGroupEntity
	 * @return
	 */
	public abstract ResultEntity queryRelOperatorStatisticsGroups(String req);
}
