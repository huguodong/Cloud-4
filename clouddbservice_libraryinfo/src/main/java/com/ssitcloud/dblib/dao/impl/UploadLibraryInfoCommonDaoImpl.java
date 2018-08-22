package com.ssitcloud.dblib.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dblib.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dblib.dao.LibraryInfoCommonDao;

@Repository
public class UploadLibraryInfoCommonDaoImpl extends CommonDaoImpl implements LibraryInfoCommonDao{

	@Override
	public Integer superManagerInsert(String sql) {
		return sqlSessionTemplate.insert("libraryInfoCommon.superInsert",sql);
	}
	
	@Override
	public Integer superManagerSelectCount(String sql) {
		return sqlSessionTemplate.selectOne("libraryInfoCommon.superSelectCount",sql);
	}
	
	@Override
	public List<LinkedHashMap<String, Object>> superManagerSelect(String sql) {
		return this.sqlSessionTemplate.selectList("libraryInfoCommon.superSelect",sql);
	}
	
	@Override
	public Integer superManagerUpdate(String sql) {
		return this.sqlSessionTemplate.update("libraryInfoCommon.superUpdate", sql);
	}
	
	@Override
	public Integer superManagerDelete(String sql) {
		return this.sqlSessionTemplate.delete("libraryInfoCommon.superDelete",sql);
	}
}
