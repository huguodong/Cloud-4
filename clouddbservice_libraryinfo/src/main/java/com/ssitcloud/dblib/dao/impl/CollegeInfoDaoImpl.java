package com.ssitcloud.dblib.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dblib.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dblib.dao.CollegeInfoDao;
import com.ssitcloud.readermgmt.entity.CollegeInfoEntity;

@Repository
public class CollegeInfoDaoImpl extends CommonDaoImpl implements CollegeInfoDao{

	@Override
	public List<CollegeInfoEntity> queryColleageInfo(CollegeInfoEntity collegeInfoEntity) {
		return this.sqlSessionTemplate.selectList("collegeInfo.queryCollegeInfo",collegeInfoEntity);
	}

	@Override
	public int addCollegeInfo(CollegeInfoEntity collegeInfoEntity) {
		return this.sqlSessionTemplate.insert("collegeInfo.addCollegeInfo",collegeInfoEntity);
	}
	
	

}
