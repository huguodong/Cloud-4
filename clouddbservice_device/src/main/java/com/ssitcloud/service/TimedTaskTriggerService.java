package com.ssitcloud.service;

import java.util.List;
import com.ssitcloud.entity.TimedTaskTriggerEntity;

public interface TimedTaskTriggerService {
	
	/**
	 * 定时任务相关配置TimedTaskTriggerEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:36:42
	 * @param timedTaskTriggerEntity
	 * @return
	 */
	public abstract int insertTimedTaskTrigger(TimedTaskTriggerEntity timedTaskTriggerEntity);
	
	/**
	 * 定时任务相关配置TimedTaskTriggerEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:36:58
	 * @param timedTaskTriggerEntity
	 * @return
	 */
	public abstract int updateTimedTaskTrigger(TimedTaskTriggerEntity timedTaskTriggerEntity);
	
	/**
	 * 定时任务相关配置TimedTaskTriggerEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:37:20
	 * @param timedTaskTriggerEntity
	 * @return
	 */
	public abstract int deleteTimedTaskTrigger(TimedTaskTriggerEntity timedTaskTriggerEntity);
	
	/**
	 * 定时任务相关配置TimedTaskTriggerEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:36
	 * @param timedTaskTriggerEntity
	 * @return
	 */
	public abstract TimedTaskTriggerEntity queryOneTimedTaskTrigger(TimedTaskTriggerEntity timedTaskTriggerEntity);
	
	/**
	 * 定时任务相关配置TimedTaskTriggerEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:49
	 * @param timedTaskTriggerEntity
	 * @return
	 */
	public abstract List<TimedTaskTriggerEntity> queryTimedTaskTriggers(TimedTaskTriggerEntity timedTaskTriggerEntity);

}
