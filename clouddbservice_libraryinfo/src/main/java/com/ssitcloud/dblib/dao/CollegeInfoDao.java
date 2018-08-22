package com.ssitcloud.dblib.dao;

import java.util.List;

import com.ssitcloud.readermgmt.entity.CollegeInfoEntity;

public interface CollegeInfoDao {
	
	public List<CollegeInfoEntity> queryColleageInfo(CollegeInfoEntity collegeInfoEntity);
	
	public int addCollegeInfo(CollegeInfoEntity collegeInfoEntity);

}
