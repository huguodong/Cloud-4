package com.ssitcloud.dbstatistics.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dbstatistics.dao.CirculationMonthDataDao;
import com.ssitcloud.dbstatistics.entity.CirculationMonthDataEntity;
import com.ssitcloud.dbstatistics.service.CirculationMonthDataService;

@Service
public class CirculationMonthDataServiceImpl implements CirculationMonthDataService{
	@Resource
	private CirculationMonthDataDao circulationMonthDataDao;
	
	@Override
	public int insertCirculationMonthData(CirculationMonthDataEntity circulationMonthDataEntity) {
		return circulationMonthDataDao.insertCirculationMonthData(circulationMonthDataEntity);
	}

	@Override
	public int deleteCirculationMonthData(CirculationMonthDataEntity circulationMonthDataEntity) {
		return circulationMonthDataDao.deleteCirculationMonthData(circulationMonthDataEntity);
	}

	@Override
	public int updateCirculationMonthData(CirculationMonthDataEntity circulationMonthDataEntity) {
		return circulationMonthDataDao.updateCirculationMonthData(circulationMonthDataEntity);
	}

	@Override
	public CirculationMonthDataEntity queryCirculationMonthData(
			CirculationMonthDataEntity circulationMonthDataEntity) {
		return circulationMonthDataDao.queryCirculationMonthData(circulationMonthDataEntity);
	}

	@Override
	public List<CirculationMonthDataEntity> queryCirculationMonthDataList(
			CirculationMonthDataEntity circulationMonthDataEntity) {
		return circulationMonthDataDao.queryCirculationMonthDataList(circulationMonthDataEntity);
	}
	
	@Override
	public List<CirculationMonthDataEntity> getAllCirculationMonth() {
		return circulationMonthDataDao.getAllCirculationMonth();
	}

}
