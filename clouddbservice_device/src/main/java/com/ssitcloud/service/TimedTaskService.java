package com.ssitcloud.service;

import java.util.List;
import com.ssitcloud.entity.TimedTaskEntity;

public interface TimedTaskService {
	
	/**
	 * 定时任务相关配置TimedTaskEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:36:42
	 * @param timedTaskEntity
	 * @return
	 */
	public abstract int insertTimedTask(TimedTaskEntity timedTaskEntity);
	
	/**
	 * 定时任务相关配置TimedTaskEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:36:58
	 * @param timedTaskEntity
	 * @return
	 */
	public abstract int updateTimedTask(TimedTaskEntity timedTaskEntity);
	
	/**
	 * 定时任务相关配置TimedTaskEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:37:20
	 * @param timedTaskEntity
	 * @return
	 */
	public abstract int deleteTimedTask(TimedTaskEntity timedTaskEntity);
	
	/**
	 * 定时任务相关配置TimedTaskEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:36
	 * @param timedTaskEntity
	 * @return
	 */
	public abstract TimedTaskEntity queryOneTimedTask(TimedTaskEntity timedTaskEntity);
	
	/**
	 * 定时任务相关配置TimedTaskEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:49
	 * @param timedTaskEntity
	 * @return
	 */
	public abstract List<TimedTaskEntity> queryTimedTasks(TimedTaskEntity timedTaskEntity);

}
