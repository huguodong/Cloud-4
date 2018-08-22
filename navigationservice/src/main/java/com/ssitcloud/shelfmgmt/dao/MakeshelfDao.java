package com.ssitcloud.shelfmgmt.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.shelfmgmt.entity.MakeshelfEntity;
import com.ssitcloud.shelfmgmt.entity.ExportBookshelfEntity;

public interface MakeshelfDao {

	public abstract PageEntity queryAllMakeShelfRecord(Map<String, String> map);
	
	public abstract int deleteMakeShelf(List<MakeshelfEntity> list);
	
	public abstract int addMakeShelfRecord(MakeshelfEntity makeshelfEntity);
	
	public abstract MakeshelfEntity queryMakeShelfByid(MakeshelfEntity makeshelfEntity);
	
}
