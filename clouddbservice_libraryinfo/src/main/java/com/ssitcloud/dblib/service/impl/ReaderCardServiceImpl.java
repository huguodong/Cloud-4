package com.ssitcloud.dblib.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.dao.CollegeInfoDao;
import com.ssitcloud.dblib.dao.ReaderCardDao;
import com.ssitcloud.dblib.entity.DeviceReaderEntity;
import com.ssitcloud.dblib.entity.ReaderCardEntity;
import com.ssitcloud.dblib.service.ReaderCardService;
import com.ssitcloud.readermgmt.entity.CollegeInfoEntity;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Service
public class ReaderCardServiceImpl implements ReaderCardService {
	@Resource
	private ReaderCardDao readerCardDao;
	@Resource
	private CollegeInfoDao collegeInfoDao;

	@Override
	public int insertReaderCard(
			ReaderCardEntity readerCardEntity) {
		return readerCardDao.insertReaderCard(readerCardEntity);
	}

	@Override
	public int updateReaderCard(
			ReaderCardEntity readerCardEntity) {
		return readerCardDao.updateReaderCard(readerCardEntity);
	}

	@Override
	public int deleteReaderCard(
			ReaderCardEntity readerCardEntity) {
		return readerCardDao.deleteReaderCard(readerCardEntity);
	}

	@Override
	public ReaderCardEntity queryOneReaderCard(
			ReaderCardEntity readerCardEntity) {
		return readerCardDao.queryOneReaderCard(readerCardEntity);
			
	}

	@Override
	public List<ReaderCardEntity> queryReaderCards(
			ReaderCardEntity readerCardEntity) {
		return readerCardDao.queryReaderCards(readerCardEntity);
		
	}

	@Override
	public List<ReaderCardEntity> selectReaderCardByParams(ReaderCardEntity cardEntity) {
		return readerCardDao.selectReaderCardByParams(cardEntity);
	}

	@Override
	public ResultEntity uploadReaderCard(CommonsMultipartFile commonsMultipartFile,String req) throws Exception {
		UploadReaderCardUtils cardUtils = new UploadReaderCardUtils(readerCardDao,collegeInfoDao);
		cardUtils.parseUploadFile(commonsMultipartFile,req);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("successNum", cardUtils.getSuccessNum());
		jsonObject.put("failNum", cardUtils.getFailNum());
		jsonObject.put("errorLog", cardUtils.getErrorLogs());
		ResultEntity entity = new ResultEntity();
		entity.setResult(jsonObject);
		entity.setState(true);
		return entity;
	}

	@Override
	public int countReaderByLibIdxAndCardno(ReaderCardEntity readerCardEntity) {
		return readerCardDao.countReaderByLibIdxAndCardno(readerCardEntity);
	}
}
