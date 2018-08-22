package com.ssitcloud.business.statistics.service;

import com.ssitcloud.common.entity.ResultEntity;

/**
 * 
 * author huanghuang
 * 2017年3月16日 下午7:18:14
 */
public interface StatisticsService {
	//查询出统计类型的主类型
	ResultEntity queryStatisticsMaintypeList(String req);
	//查询出统计类型的子类型
	ResultEntity selectStaticsType(String req);
	//查询出一条动态记录
	ResultEntity queryReltype(String req);
	//查询出所有动态记录
	ResultEntity queryReltypeList(String req);
	//取地区码
	String retAreaCode(Integer device_idx);

}
