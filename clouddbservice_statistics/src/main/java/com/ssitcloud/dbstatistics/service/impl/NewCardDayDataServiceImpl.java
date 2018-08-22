package com.ssitcloud.dbstatistics.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dbstatistics.dao.NewCardDayDataDao;
import com.ssitcloud.dbstatistics.entity.NewCardDayDataEntity;
import com.ssitcloud.dbstatistics.service.NewCardDayDataService;

@Service
public class NewCardDayDataServiceImpl implements NewCardDayDataService {
	@Resource
	private NewCardDayDataDao newCardDayDataDao;

	@Override
	public int insertNewCardDayData(
			NewCardDayDataEntity newCardDayDataEntity) {
		return newCardDayDataDao.insertNewCardDayData(newCardDayDataEntity);
	}

	@Override
	public int updateNewCardDayData(
			NewCardDayDataEntity newCardDayDataEntity) {
		return newCardDayDataDao.updateNewCardDayData(newCardDayDataEntity);
	}

	@Override
	public int deleteNewCardDayData(
			NewCardDayDataEntity newCardDayDataEntity){
		return newCardDayDataDao.deleteNewCardDayData(newCardDayDataEntity);
	}

	@Override
	public NewCardDayDataEntity queryOneNewCardDayData(
			NewCardDayDataEntity newCardDayDataEntity) {
		return newCardDayDataDao.queryOneNewCardDayData(newCardDayDataEntity);
	}

	@Override
	public List<NewCardDayDataEntity> queryNewCardDayDatas(
			NewCardDayDataEntity newCardDayDataEntity) {
//		ResultEntity model = new ResultEntity();
//		List<NewCardDayDataEntity> list = newCardDayDataDao.queryNewCardDayDatas(newCardDayDataEntity);
//		if (list!=null && list.size()>=0) {
//			model.setState(true);
//			model.setResult(list);
//		}
//		return model;
//		ResultEntity model=new ResultEntity();
//		List<NewCardDayDataEntity> total=newCardDayDataDao.queryNewCardDayDatas(newCardDayDataEntity);
//		if(total!=null&&total.size()>=0){
//			newCardDayDataEntity.setTotal(total.get(0).getTotal());
//		}
//		newCardDayDataEntity.setDoAount(false);
//		List<NewCardDayDataEntity> qlibGuides=newCardDayDataDao.queryNewCardDayDatas(newCardDayDataEntity);
//		newCardDayDataEntity.setRows(qlibGuides);
//		model.setState(true);
//		model.setResult(newCardDayDataEntity);
//		return model;
		return newCardDayDataDao.queryNewCardDayDatas(newCardDayDataEntity);
	}
	@Override
	public List<NewCardDayDataEntity> getAllNewCardDay() {
		return newCardDayDataDao.getAllNewCardDay();
	}

	

}
