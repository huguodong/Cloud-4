package com.ssitcloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dao.TimedTaskDao;
import com.ssitcloud.entity.TimedTaskEntity;
import com.ssitcloud.service.TimedTaskService;


@Service
public class TimedTaskServiceImpl implements TimedTaskService {
	@Resource
	private TimedTaskDao timedTaskDao;

	@Override
	public int insertTimedTask(
			TimedTaskEntity timedTaskEntity) {
		return timedTaskDao.insertTimedTask(timedTaskEntity);
	}

	@Override
	public int updateTimedTask(
			TimedTaskEntity timedTaskEntity) {
		return timedTaskDao.updateTimedTask(timedTaskEntity);
	}

	@Override
	public int deleteTimedTask(
			TimedTaskEntity timedTaskEntity) {
		return timedTaskDao.deleteTimedTask(timedTaskEntity);
	}

	@Override
	public TimedTaskEntity queryOneTimedTask(
			TimedTaskEntity timedTaskEntity) {
		return timedTaskDao.queryOneTimedTask(timedTaskEntity);
			
	}

	@Override
	public List<TimedTaskEntity> queryTimedTasks(
			TimedTaskEntity timedTaskEntity) {
		return timedTaskDao.queryTimedTasks(timedTaskEntity);
		
	}

	

}
