package com.ssitcloud.view.opermgmt.service;

import com.ssitcloud.common.entity.ResultEntity;


public interface StatisticsGroupService {
	/**
	 * 模板组service层的添加
	 * author huanghuang
	 * 2017年2月20日 上午9:22:35
	 * @param req
	 * @return
	 */
	ResultEntity addStatisticsGroup(String req);
	
	/**
	 * 模板组service层的删除
	 * author huanghuang
	 * 2017年2月20日 上午9:28:23
	 * @param req
	 * @return
	 */
	ResultEntity delStatisticsGroup(String req);
	
	/**
	 * 模板组service层的更新
	 * author huanghuang
	 * 2017年2月20日 上午9:28:29
	 * @param req
	 * @return
	 */
	ResultEntity updStatisticsGroup(String req);
	
	/**
	 * 模板组service层的单个查询
	 * author huanghuang
	 * 2017年2月20日 上午9:28:29
	 * @param req
	 * @return
	 */
	ResultEntity queryOneStatisticsGroup(String req);
	
	/**
	 * 模板组service层的所有查询
	 * author huanghuang
	 * 2017年2月20日 上午9:28:37
	 * @param req
	 * @return
	 */
	ResultEntity queryStatisticsGroupByparam(String req);
	
	/**
	 * 模板组service层的分页查询
	 * author huanghuang
	 * 2017年2月20日 上午9:28:43
	 * @param req
	 * @return
	 */
	ResultEntity queryStatisticsGroupPageByparam(String req);

}
