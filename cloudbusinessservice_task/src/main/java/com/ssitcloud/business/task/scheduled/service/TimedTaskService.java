package com.ssitcloud.business.task.scheduled.service;

import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;

public interface TimedTaskService {
	/**
	 * 定时任务service层的添加
	 * author huanghuang
	 * 2017年2月20日 上午9:22:35
	 * @param req
	 * @return
	 */
	ResultEntity addTimedTask(String req);
	
	/**
	 * 定时任务service层的删除
	 * author huanghuang
	 * 2017年2月20日 上午9:28:23
	 * @param req
	 * @return
	 */
	ResultEntity delTimedTask(String req);
	
	/**
	 * 定时任务service层的更新
	 * author huanghuang
	 * 2017年2月20日 上午9:28:29
	 * @param req
	 * @return
	 */
	ResultEntity updTimedTask(String req);
	
	/**
	 * 定时任务service层的单个查询
	 * author huanghuang
	 * 2017年2月20日 上午9:28:29
	 * @param req
	 * @return
	 */
	ResultEntity queryOneTimedTask(String req);
	
	/**
	 * 定时任务service层的所有查询
	 * author huanghuang
	 * 2017年2月20日 上午9:28:37
	 * @param req
	 * @return
	 */
	ResultEntity queryTimedTaskByparam(String req);
	/**
	 * 定时任务触发器service层的删除
	 * author huanghuang
	 * 2017年2月20日 上午9:28:37
	 * @param req
	 * @return
	 */
	ResultEntity deleteTimedTaskTrigger(String req);
	/**
	 * 定时任务触发器service层的所有查询
	 * author huanghuang
	 * 2017年2月20日 上午9:28:37
	 * @param req
	 * @return
	 */
	ResultEntity selectTimedTaskTriggers(String req);
	
	/**
	 * 通过图书馆的索引，查找设备
	 * author huanghuang
	 * 2017年3月1日 下午1:56:06
	 * @param req
	 * @return
	 */
	ResultEntity selectDevices(Map<String, String> param);
	
	ResultEntity selLibraryByIdxOrId(Map<String, String> param);
	
	

}
