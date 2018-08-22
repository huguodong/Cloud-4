package com.ssitcloud.dbstatistics.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dbstatistics.dao.NewCardWeekDataDao;
import com.ssitcloud.dbstatistics.entity.NewCardWeekDataEntity;
import com.ssitcloud.dbstatistics.service.NewCardWeekDataService;

@Service
public class NewCardWeekDataServiceImpl implements NewCardWeekDataService {
	@Resource
	private NewCardWeekDataDao newCardWeekDataDao;

	@Override
	public int insertNewCardWeekData(
			NewCardWeekDataEntity newCardWeekDataEntity) {
		return newCardWeekDataDao.insertNewCardWeekData(newCardWeekDataEntity);
	}

	@Override
	public int updateNewCardWeekData(
			NewCardWeekDataEntity newCardWeekDataEntity) {
		return newCardWeekDataDao.updateNewCardWeekData(newCardWeekDataEntity);
	}

	@Override
	public int deleteNewCardWeekData(
			NewCardWeekDataEntity newCardWeekDataEntity) {
		return newCardWeekDataDao.deleteNewCardWeekData(newCardWeekDataEntity);
	}

	@Override
	public NewCardWeekDataEntity queryOneNewCardWeekData(
			NewCardWeekDataEntity newCardWeekDataEntity) {
		return newCardWeekDataDao.queryOneNewCardWeekData(newCardWeekDataEntity);
	}

	@Override
	public List<NewCardWeekDataEntity> queryNewCardWeekDatas(
			NewCardWeekDataEntity newCardWeekDataEntity) {
		/*ResultEntity model = new ResultEntity();
		List<NewCardWeekDataEntity> list = newCardWeekDataDao.queryNewCardWeekDatas(newCardWeekDataEntity);
		if (list!=null && list.size()>=0) {
			model.setState(true);
			model.setResult(list);
		}
		return model;*/
//		ResultEntity model=new ResultEntity();
//		List<NewCardWeekDataEntity> total=newCardWeekDataDao.queryNewCardWeekDatas(newCardWeekDataEntity);
//		if(total!=null&&total.size()>=0){
//			newCardWeekDataEntity.setTotal(total.get(0).getTotal());
//		}
//		newCardWeekDataEntity.setDoAount(false);
//		List<NewCardWeekDataEntity> qlibGuides=newCardWeekDataDao.queryNewCardWeekDatas(newCardWeekDataEntity);
//		newCardWeekDataEntity.setRows(qlibGuides);
//		model.setState(true);
//		model.setResult(newCardWeekDataEntity);
//		return model;
		return newCardWeekDataDao.queryNewCardWeekDatas(newCardWeekDataEntity);
	}
	@Override
	public List<NewCardWeekDataEntity> getAllNewCardWeek() {
		return newCardWeekDataDao.getAllNewCardWeek();
	}

	

}
