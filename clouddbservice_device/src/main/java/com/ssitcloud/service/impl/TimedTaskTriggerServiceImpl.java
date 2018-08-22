package com.ssitcloud.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.ssitcloud.dao.TimedTaskTriggerDao;
import com.ssitcloud.entity.TimedTaskTriggerEntity;
import com.ssitcloud.service.TimedTaskTriggerService;


@Service
public class TimedTaskTriggerServiceImpl implements TimedTaskTriggerService {
	@Resource
	private TimedTaskTriggerDao timedTaskTriggerDao;

	@Override
	public int insertTimedTaskTrigger(
			TimedTaskTriggerEntity timedTaskEntity) {
		return timedTaskTriggerDao.insertTimedTaskTrigger(timedTaskEntity);
	}

	@Override
	public int updateTimedTaskTrigger(
			TimedTaskTriggerEntity timedTaskEntity) {
		return timedTaskTriggerDao.updateTimedTaskTrigger(timedTaskEntity);
	}

	@Override
	public int deleteTimedTaskTrigger(
			TimedTaskTriggerEntity timedTaskEntity) {
		return timedTaskTriggerDao.deleteTimedTaskTrigger(timedTaskEntity);
	}

	@Override
	public TimedTaskTriggerEntity queryOneTimedTaskTrigger(
			TimedTaskTriggerEntity timedTaskEntity) {
		return timedTaskTriggerDao.queryOneTimedTaskTrigger(timedTaskEntity);
			
	}

	@Override
	public List<TimedTaskTriggerEntity> queryTimedTaskTriggers(
			TimedTaskTriggerEntity timedTaskEntity) {
		return timedTaskTriggerDao.queryTimedTaskTriggers(timedTaskEntity);
		
	}

	

}
