package com.ssitcloud.dbstatistics.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dbstatistics.dao.CirculationYearDataDao;
import com.ssitcloud.dbstatistics.entity.CirculationYearDataEntity;
import com.ssitcloud.dbstatistics.service.CirculationYearDataService;

@Service
public class CirculationYearDataServiceImpl implements CirculationYearDataService{
	@Resource
	private CirculationYearDataDao circulationYearDataDao;
	
	@Override
	public int insertCirculationYearData(CirculationYearDataEntity circulationYearDataEntity) {
		return circulationYearDataDao.insertCirculationYearData(circulationYearDataEntity);
	}

	@Override
	public int deleteCirculationYearData(CirculationYearDataEntity circulationYearDataEntity) {
		return circulationYearDataDao.deleteCirculationYearData(circulationYearDataEntity);
	}

	@Override
	public int updateCirculationYearData(CirculationYearDataEntity circulationYearDataEntity) {
		return circulationYearDataDao.updateCirculationYearData(circulationYearDataEntity);
	}

	@Override
	public CirculationYearDataEntity queryCirculationYearData(
			CirculationYearDataEntity circulationYearDataEntity) {
		return circulationYearDataDao.queryCirculationYearData(circulationYearDataEntity);
	}

	@Override
	public List<CirculationYearDataEntity> queryCirculationYearDataList(
			CirculationYearDataEntity circulationYearDataEntity) {
		return circulationYearDataDao.queryCirculationYearDataList(circulationYearDataEntity);
	}
	@Override
	public List<CirculationYearDataEntity> getAllCirculationYear() {
		return circulationYearDataDao.getAllCirculationYear();
	}
}
