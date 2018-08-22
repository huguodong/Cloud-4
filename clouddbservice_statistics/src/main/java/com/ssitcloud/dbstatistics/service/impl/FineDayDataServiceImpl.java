package com.ssitcloud.dbstatistics.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dbstatistics.dao.FineDayDataDao;
import com.ssitcloud.dbstatistics.entity.FineDayDataEntity;
import com.ssitcloud.dbstatistics.service.FineDayDataService;

@Service
public class FineDayDataServiceImpl implements FineDayDataService{
	@Resource
	private FineDayDataDao fineDayDataDao;
	
	@Override
	public int insertFineDayData(FineDayDataEntity fineDayDataEntity) {
		return fineDayDataDao.insertFineDayData(fineDayDataEntity);
	}

	@Override
	public int deleteFineDayData(FineDayDataEntity fineDayDataEntity) {
		return fineDayDataDao.deleteFineDayData(fineDayDataEntity);
	}

	@Override
	public int updateFineDayData(FineDayDataEntity fineDayDataEntity) {
		return fineDayDataDao.updateFineDayData(fineDayDataEntity);
	}

	@Override
	public FineDayDataEntity queryFineDayData(
			FineDayDataEntity fineDayDataEntity) {
		return fineDayDataDao.queryFineDayData(fineDayDataEntity);
	}

	@Override
	public List<FineDayDataEntity> queryFineDayDataList(
			FineDayDataEntity fineDayDataEntity) {
		return fineDayDataDao.queryFineDayDataList(fineDayDataEntity);
	}
	@Override
	public List<FineDayDataEntity> getAllFinanceDay() {
		return fineDayDataDao.getAllFinanceDay();
	}
}
