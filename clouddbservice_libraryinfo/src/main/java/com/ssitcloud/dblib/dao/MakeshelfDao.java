package com.ssitcloud.dblib.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.dblib.entity.MakeshelfEntity;

public interface MakeshelfDao {

	public abstract PageEntity queryAllMakeShelfRecord(Map<String, String> map);
	
	public abstract int deleteMakeShelf(List<MakeshelfEntity> list);
	
	public abstract int addMakeShelfRecord(MakeshelfEntity makeshelfEntity);
	
	public abstract MakeshelfEntity queryMakeShelfByid(MakeshelfEntity makeshelfEntity);
	
}
