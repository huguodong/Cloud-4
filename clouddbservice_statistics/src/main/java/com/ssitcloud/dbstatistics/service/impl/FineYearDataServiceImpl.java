package com.ssitcloud.dbstatistics.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dbstatistics.dao.FineYearDataDao;
import com.ssitcloud.dbstatistics.entity.FineYearDataEntity;
import com.ssitcloud.dbstatistics.service.FineYearDataService;

@Service
public class FineYearDataServiceImpl implements FineYearDataService {
	@Resource
	private FineYearDataDao fineYearDataDao;

	@Override
	public int insertFineYearData(
			FineYearDataEntity fineYearDataEntity) {
		return fineYearDataDao.insertFineYearData(fineYearDataEntity);
	}

	@Override
	public int updateFineYearData(
			FineYearDataEntity fineYearDataEntity) {
		return fineYearDataDao.updateFineYearData(fineYearDataEntity);
	}

	@Override
	public int deleteFineYearData(
			FineYearDataEntity fineYearDataEntity) {
		return fineYearDataDao.deleteFineYearData(fineYearDataEntity);
	}

	@Override
	public FineYearDataEntity queryOneFineYearData(
			FineYearDataEntity fineYearDataEntity) {
		return fineYearDataDao.queryOneFineYearData(fineYearDataEntity);
	}

	@Override
	public List<FineYearDataEntity> queryFineYearDatas(
			FineYearDataEntity fineYearDataEntity) {
//		ResultEntity model = new ResultEntity();
//		List<FineYearDateEntity> list = fineYearDateDao.queryFineYearDates(fineYearDateEntity);
//		if (list!=null && list.size()>=0) {
//			model.setState(true);
//			model.setResult(list);
//		}
//		return model;
//		ResultEntity model=new ResultEntity();
//		List<FineYearDateEntity> total=fineYearDateDao.queryFineYearDates(fineYearDateEntity);
//		if(total!=null&&total.size()>=0){
//			fineYearDateEntity.setTotal(total.get(0).getTotal());
//		}
//		fineYearDateEntity.setDoAount(false);
//		List<FineYearDateEntity> qlibGuides=fineYearDateDao.queryFineYearDates(fineYearDateEntity);
//		fineYearDateEntity.setRows(qlibGuides);
//		model.setState(true);
//		model.setResult(fineYearDateEntity);
//		return model;
		return fineYearDataDao.queryFineYearDatas(fineYearDataEntity);
	}
	
	@Override
	public List<FineYearDataEntity> getAllFinanceYear() {
		return fineYearDataDao.getAllFinanceYear();
	}

	

}
