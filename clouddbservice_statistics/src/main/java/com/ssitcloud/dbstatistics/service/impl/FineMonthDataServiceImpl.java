package com.ssitcloud.dbstatistics.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dbstatistics.dao.FineMonthDataDao;
import com.ssitcloud.dbstatistics.entity.FineMonthDataEntity;
import com.ssitcloud.dbstatistics.service.FineMonthDataService;

@Service
public class FineMonthDataServiceImpl implements FineMonthDataService{
	@Resource
	private FineMonthDataDao fineMonthDateDao;
	
	@Override
	public int insertFineMonthData(FineMonthDataEntity fineMonthDataEntity) {
		return fineMonthDateDao.insertFineMonthData(fineMonthDataEntity);
	}

	@Override
	public int deleteFineMonthData(FineMonthDataEntity fineMonthDataEntity) {
		return fineMonthDateDao.deleteFineMonthData(fineMonthDataEntity);
	}

	@Override
	public int updateFineMonthData(FineMonthDataEntity fineMonthDataEntity) {
		return fineMonthDateDao.updateFineMonthData(fineMonthDataEntity);
	}

	@Override
	public FineMonthDataEntity queryFineMonthData(
			FineMonthDataEntity fineMonthDataEntity) {
		return fineMonthDateDao.queryFineMonthData(fineMonthDataEntity);
	}

	@Override
	public List<FineMonthDataEntity> queryFineMonthDataList(
			FineMonthDataEntity fineMonthDataEntity) {
		return fineMonthDateDao.queryFineMonthDataList(fineMonthDataEntity);
	}
	@Override
	public List<FineMonthDataEntity> getAllFinanceMonth() {
		return fineMonthDateDao.getAllFinanceMonth();
	}
}
