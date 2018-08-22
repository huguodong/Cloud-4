package com.ssitcloud.dbstatistics.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dbstatistics.dao.CirculationWeekDataDao;
import com.ssitcloud.dbstatistics.entity.CirculationWeekDataEntity;
import com.ssitcloud.dbstatistics.service.CirculationWeekDataService;

@Service
public class CirculationWeekDataServiceImpl implements CirculationWeekDataService{

	@Resource
	private CirculationWeekDataDao circulationWeekDataDao;
	
	@Override
	public int insertCirculationWeekData(CirculationWeekDataEntity circulationWeekDataEntity) {
		return circulationWeekDataDao.insertCirculationWeekData(circulationWeekDataEntity);
	}

	@Override
	public int deleteCirculationWeekData(CirculationWeekDataEntity circulationWeekDataEntity) {
		return circulationWeekDataDao.deleteCirculationWeekData(circulationWeekDataEntity);
	}

	@Override
	public int updateCirculationWeekData(CirculationWeekDataEntity circulationWeekDataEntity) {
		return circulationWeekDataDao.updateCirculationWeekData(circulationWeekDataEntity);
	}

	@Override
	public CirculationWeekDataEntity queryCirculationWeekData(
			CirculationWeekDataEntity circulationWeekDataEntity) {
		return circulationWeekDataDao.queryCirculationWeekData(circulationWeekDataEntity);
	}

	@Override
	public List<CirculationWeekDataEntity> queryCirculationWeekDataList(
			CirculationWeekDataEntity circulationWeekDataEntity) {
		return circulationWeekDataDao.queryCirculationWeekDataList(circulationWeekDataEntity);
	}

	@Override
	public List<CirculationWeekDataEntity> getAllCirculationWeek() {
		return circulationWeekDataDao.getAllCirculationWeek();
	}
	
	
}
