package com.ssitcloud.dbstatistics.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dbstatistics.dao.NewCardMonthDataDao;
import com.ssitcloud.dbstatistics.entity.NewCardMonthDataEntity;
import com.ssitcloud.dbstatistics.service.NewCardMonthDataService;

@Service
public class NewCardMonthDataServiceImpl implements NewCardMonthDataService {
	@Resource
	private NewCardMonthDataDao newCardMonthDataDao;

	@Override
	public int insertNewCardMonthData(
			NewCardMonthDataEntity newCardMonthDataEntity) {
		return newCardMonthDataDao.insertNewCardMonthData(newCardMonthDataEntity);
	}

	@Override
	public int updateNewCardMonthData(
			NewCardMonthDataEntity newCardMonthDataEntity) {
		return newCardMonthDataDao.updateNewCardMonthData(newCardMonthDataEntity);
	}

	@Override
	public int deleteNewCardMonthData(
			NewCardMonthDataEntity newCardMonthDataEntity) {
		return newCardMonthDataDao.deleteNewCardMonthData(newCardMonthDataEntity);
	}

	@Override
	public NewCardMonthDataEntity queryOneNewCardMonthData(
			NewCardMonthDataEntity newCardMonthDataEntity) {
		return newCardMonthDataDao.queryOneNewCardMonthData(newCardMonthDataEntity);
	}

	@Override
	public List<NewCardMonthDataEntity> queryNewCardMonthDatas(
			NewCardMonthDataEntity newCardMonthDataEntity) {
//		ResultEntity model = new ResultEntity();
//		List<NewCardMonthDataEntity> list = newCardMonthDataDao.queryNewCardMonthDatas(newCardMonthDataEntity);
//		if (list!=null && list.size()>=0) {
//			model.setState(true);
//			model.setResult(list);
//		}
//		return model;
//		ResultEntity model=new ResultEntity();
//		List<NewCardMonthDataEntity> total=newCardMonthDataDao.queryNewCardMonthDatas(newCardMonthDataEntity);
//		if(total!=null&&total.size()>=0){
//			newCardMonthDataEntity.setTotal(total.get(0).getTotal());
//		}
//		newCardMonthDataEntity.setDoAount(false);
//		List<NewCardMonthDataEntity> qlibGuides=newCardMonthDataDao.queryNewCardMonthDatas(newCardMonthDataEntity);
//		newCardMonthDataEntity.setRows(qlibGuides);
//		model.setState(true);
//		model.setResult(newCardMonthDataEntity);
//		return model;
		return newCardMonthDataDao.queryNewCardMonthDatas(newCardMonthDataEntity);
	}
	@Override
	public List<NewCardMonthDataEntity> getAllNewCardMonth() {
		return newCardMonthDataDao.getAllNewCardMonth();
	}

	

}
