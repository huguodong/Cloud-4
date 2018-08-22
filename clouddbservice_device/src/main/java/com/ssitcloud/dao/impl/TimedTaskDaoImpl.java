package com.ssitcloud.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.TimedTaskDao;
import com.ssitcloud.entity.TimedTaskEntity;

@Repository
public class TimedTaskDaoImpl extends CommonDaoImpl implements
		TimedTaskDao {

	@Override
	public int insertTimedTask(TimedTaskEntity timedTaskEntity) {
		return this.sqlSessionTemplate.insert("timed_task.insertTimedTask", timedTaskEntity);
	}

	@Override
	public int updateTimedTask(TimedTaskEntity timedTaskEntity) {
		return this.sqlSessionTemplate.update("timed_task.updateTimedTask", timedTaskEntity);
	}

	@Override
	public int deleteTimedTask(TimedTaskEntity timedTaskEntity) {
		return this.sqlSessionTemplate.delete("timed_task.deleteTimedTask", timedTaskEntity);
	}

	@Override
	public TimedTaskEntity queryOneTimedTask(
			TimedTaskEntity timedTaskEntity) {
		return this.select("timed_task.selectTimedTask", timedTaskEntity);
	}

	@Override
	public List<TimedTaskEntity> queryTimedTasks(
			TimedTaskEntity timedTaskEntity) {
		return this.selectAll("timed_task.selectTimedTasks", timedTaskEntity);
	}

}
