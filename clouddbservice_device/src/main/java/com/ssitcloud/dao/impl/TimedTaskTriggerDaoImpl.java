package com.ssitcloud.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.TimedTaskTriggerDao;
import com.ssitcloud.entity.TimedTaskTriggerEntity;

@Repository
public class TimedTaskTriggerDaoImpl extends CommonDaoImpl implements
TimedTaskTriggerDao {

	@Override
	public int insertTimedTaskTrigger(TimedTaskTriggerEntity timedTaskTriggerEntity) {
		return this.sqlSessionTemplate.insert("timed_task_tri.insertTimedTaskTrigger", timedTaskTriggerEntity);
	}

	@Override
	public int updateTimedTaskTrigger(TimedTaskTriggerEntity timedTaskTriggerEntity) {
		return this.sqlSessionTemplate.update("timed_task_tri.updateTimedTaskTrigger", timedTaskTriggerEntity);
	}

	@Override
	public int deleteTimedTaskTrigger(TimedTaskTriggerEntity timedTaskTriggerEntity) {
		return this.sqlSessionTemplate.delete("timed_task_tri.deleteTimedTaskTrigger", timedTaskTriggerEntity);
	}

	@Override
	public TimedTaskTriggerEntity queryOneTimedTaskTrigger(
			TimedTaskTriggerEntity timedTaskTriggerEntity) {
		return this.select("timed_task_tri.selectTimedTaskTrigger", timedTaskTriggerEntity);
	}

	@Override
	public List<TimedTaskTriggerEntity> queryTimedTaskTriggers(
			TimedTaskTriggerEntity timedTaskTriggerEntity) {
		return this.selectAll("timed_task_tri.selectTimedTaskTriggers", timedTaskTriggerEntity);
	}

}
