package com.ssitcloud.dblib.dao;

import java.util.LinkedHashMap;
import java.util.List;

public interface LibraryInfoCommonDao {

	public abstract Integer superManagerInsert(String sql);
	
	public abstract Integer superManagerSelectCount(String sql);
	
	public abstract List<LinkedHashMap<String, Object>> superManagerSelect(String sql);
	
	public abstract Integer superManagerUpdate(String sql);
	
	public abstract Integer superManagerDelete(String sql);
	
}
