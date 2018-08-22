package com.ssitcloud.dao;

import java.util.List;
import com.ssitcloud.entity.TimedTaskEntity;

public interface TimedTaskDao {
	/**
	 * 定时任务相关配置TimedTaskEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:30:27
	 * @param timedTaskEntity
	 * @return
	 */
	public abstract int insertTimedTask(TimedTaskEntity timedTaskEntity);
	
	/**
	 * 定时任务相关配置TimedTaskEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:30:55
	 * @param timedTaskEntity
	 * @return
	 */
	public abstract int updateTimedTask(TimedTaskEntity timedTaskEntity);
	
	/**
	 * 定时任务相关配置TimedTaskEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:31:18
	 * @param timedTaskEntity
	 * @return
	 */
	public abstract int deleteTimedTask(TimedTaskEntity timedTaskEntity);
	
	/**
	 * 定时任务相关配置TimedTaskEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:31:47
	 * @param timedTaskEntity
	 * @return
	 */
	public abstract TimedTaskEntity queryOneTimedTask(TimedTaskEntity timedTaskEntity);
	
	/**
	 * 定时任务相关配置TimedTaskEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:32:05
	 * @param timedTaskEntity
	 * @return
	 */
	public abstract List<TimedTaskEntity> queryTimedTasks(TimedTaskEntity timedTaskEntity);

}
