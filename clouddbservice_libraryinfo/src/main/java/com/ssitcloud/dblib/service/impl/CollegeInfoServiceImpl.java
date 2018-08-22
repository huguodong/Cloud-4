package com.ssitcloud.dblib.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.dao.CollegeInfoDao;
import com.ssitcloud.dblib.service.CollegeInfoService;
import com.ssitcloud.readermgmt.entity.CollegeInfoEntity;

@Service
public class CollegeInfoServiceImpl implements CollegeInfoService{
	
	@Autowired
	private CollegeInfoDao collegeInfoDao;

	@Override
	public ResultEntity queryColleageInfo(String req) {
		CollegeInfoEntity collegeInfoEntity = new CollegeInfoEntity();
		ResultEntity resultEntity = new ResultEntity();
		if(req != null && req.length() > 0){
			collegeInfoEntity = JsonUtils.fromJson(req, CollegeInfoEntity.class);
		}
		List<CollegeInfoEntity> collegeInfoEntities = collegeInfoDao.queryColleageInfo(collegeInfoEntity);
		resultEntity.setState(true);
		resultEntity.setResult(collegeInfoEntities);
		return resultEntity;
	}

}
