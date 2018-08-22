package com.ssitcloud.dbstatistics.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dbstatistics.dao.NewCardYearDataDao;
import com.ssitcloud.dbstatistics.entity.NewCardYearDataEntity;
import com.ssitcloud.dbstatistics.service.NewCardYearDataService;

@Service
public class NewCardYearDataServiceImpl implements NewCardYearDataService {
	@Resource
	private NewCardYearDataDao newCardYearDataDao;

	@Override
	public int insertNewCardYearData(NewCardYearDataEntity newCardYearDataEntity){
		return newCardYearDataDao.insertNewCardYearData(newCardYearDataEntity);
	}

	@Override
	public int updateNewCardYearData(
			NewCardYearDataEntity newCardYearDataEntity) {
		return newCardYearDataDao.updateNewCardYearData(newCardYearDataEntity);
	}

	@Override
	public int deleteNewCardYearData(
			NewCardYearDataEntity newCardYearDataEntity) {
		return newCardYearDataDao.deleteNewCardYearData(newCardYearDataEntity);
	}

	@Override
	public NewCardYearDataEntity queryOneNewCardYearData(
			NewCardYearDataEntity newCardYearDataEntity) {
		return newCardYearDataDao.queryOneNewCardYearData(newCardYearDataEntity);
	}

	@Override
	public List<NewCardYearDataEntity> queryNewCardYearDatas(
			NewCardYearDataEntity newCardYearDataEntity) {
//		ResultEntity model = new ResultEntity();
//		List<NewCardYearDataEntity> list = newCardYearDataDao.queryNewCardYearDatas(newCardYearDataEntity);
//		if (list!=null && list.size()>=0) {
//			model.setState(true);
//			model.setResult(list);
//		}
//		return model;
//		ResultEntity model=new ResultEntity();
//		List<NewCardYearDataEntity> total=newCardYearDataDao.queryNewCardYearDatas(newCardYearDataEntity);
//		if(total!=null&&total.size()>=0){
//			newCardYearDataEntity.setTotal(total.get(0).getTotal());
//		}
//		newCardYearDataEntity.setDoAount(false);
//		List<NewCardYearDataEntity> qlibGuides=newCardYearDataDao.queryNewCardYearDatas(newCardYearDataEntity);
//		newCardYearDataEntity.setRows(qlibGuides);
//		model.setState(true);
//		model.setResult(newCardYearDataEntity);
//		return model;
		return newCardYearDataDao.queryNewCardYearDatas(newCardYearDataEntity);
	}
	
	@Override
	public List<NewCardYearDataEntity> getAllNewCardYear() {
		return newCardYearDataDao.getAllNewCardYear();
	}

	

}
