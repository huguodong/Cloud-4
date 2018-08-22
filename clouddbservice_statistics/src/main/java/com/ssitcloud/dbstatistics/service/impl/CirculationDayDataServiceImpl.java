package com.ssitcloud.dbstatistics.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dbstatistics.dao.CirculationDayDataDao;
import com.ssitcloud.dbstatistics.entity.CirculationDayDataEntity;
import com.ssitcloud.dbstatistics.service.CirculationDayDataService;

@Service
public class CirculationDayDataServiceImpl implements CirculationDayDataService{

	@Resource
	private CirculationDayDataDao circulationDayDataDao;
	
	@Override
	public int insertCirculationDayData(CirculationDayDataEntity circulationDayDataEntity) {
		return circulationDayDataDao.insertCirculationDayData(circulationDayDataEntity);
	}

	@Override
	public int deleteCirculationDayData(CirculationDayDataEntity circulationDayDataEntity) {
		return circulationDayDataDao.deleteCirculationDayData(circulationDayDataEntity);
	}

	@Override
	public int updateCirculationDayData(CirculationDayDataEntity circulationDayDataEntity) {
		return circulationDayDataDao.updateCirculationDayData(circulationDayDataEntity);
	}

	@Override
	public CirculationDayDataEntity queryCirculationDayData(
			CirculationDayDataEntity circulationDayDataEntity) {
		return circulationDayDataDao.queryCirculationDayData(circulationDayDataEntity);
	}

	@Override
	public List<CirculationDayDataEntity> queryCirculationDayDataList(
			CirculationDayDataEntity circulationDayDataEntity) {
		return circulationDayDataDao.queryCirculationDayDataList(circulationDayDataEntity);
	}
	
	
	@Override
	public List<CirculationDayDataEntity> getAllCirculationDay() {
		return circulationDayDataDao.getAllCirculationDay();
	}

}
