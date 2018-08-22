package com.ssitcloud.dbstatistics.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dbstatistics.dao.FineWeekDataDao;
import com.ssitcloud.dbstatistics.entity.FineWeekDataEntity;
import com.ssitcloud.dbstatistics.service.FineWeekDataService;

@Service
public class FineWeekDataServiceImpl implements FineWeekDataService {
	@Resource
	private FineWeekDataDao fineWeekDataDao;

	@Override
	public int insertFineWeekData(
			FineWeekDataEntity fineWeekDataEntity) {
		return fineWeekDataDao.insertFineWeekData(fineWeekDataEntity);
	}

	@Override
	public int updateFineWeekData(
			FineWeekDataEntity fineWeekDataEntity) {
		return fineWeekDataDao.updateFineWeekData(fineWeekDataEntity);
	}

	@Override
	public int deleteFineWeekData(
			FineWeekDataEntity fineWeekDataEntity) {
		return fineWeekDataDao.deleteFineWeekData(fineWeekDataEntity);
	}

	@Override
	public FineWeekDataEntity queryOneFineWeekData(
			FineWeekDataEntity fineWeekDataEntity) {
		return fineWeekDataDao.queryOneFineWeekData(fineWeekDataEntity);
	}

	@Override
	public List<FineWeekDataEntity> queryFineWeekDatas(
			FineWeekDataEntity fineWeekDataEntity) {
		/*ResultEntity model = new ResultEntity();
		List<FineWeekDateEntity> list = fineWeekDateDao.queryFineWeekDates(fineWeekDateEntity);
		if (list!=null && list.size()>=0) {
			model.setState(true);
			model.setResult(list);
		}
		return model;*/
//		ResultEntity model=new ResultEntity();
//		List<FineWeekDateEntity> total=fineWeekDateDao.queryFineWeekDates(fineWeekDateEntity);
//		if(total!=null&&total.size()>=0){
//			fineWeekDateEntity.setTotal(total.get(0).getTotal());
//		}
//		fineWeekDateEntity.setDoAount(false);
//		List<FineWeekDateEntity> qlibGuides=fineWeekDateDao.queryFineWeekDates(fineWeekDateEntity);
//		fineWeekDateEntity.setRows(qlibGuides);
//		model.setState(true);
//		model.setResult(fineWeekDateEntity);
//		return model;
		return fineWeekDataDao.queryFineWeekDatas(fineWeekDataEntity);
	}
	
	@Override
	public List<FineWeekDataEntity> getAllFinanceWeek() {
		return fineWeekDataDao.getAllFinanceWeek();
	}

	

}
