package com.ssitcloud.dblib.service;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.dblib.entity.BookShelfEntity;
import com.ssitcloud.dblib.entity.BookShelfInfoEntity;

public interface BookShelfService {
	public abstract PageEntity queryAllBookshelf(Map<String, String> map);
	
	public abstract BookShelfEntity queryBookshelfById(BookShelfEntity bookshelfEntity);
	
	public abstract int updateBookshelf(BookShelfEntity bookshelfEntity,List<BookShelfInfoEntity> list);
	
	public abstract int deleteBookshelf(BookShelfEntity bookshelfEntity);
	
	public abstract int addBookshelf(BookShelfEntity bookshelfEntity,List<BookShelfInfoEntity> list);

	public abstract int updateShelfimage(BookShelfEntity bookshelfEntity);
	
	public abstract int updateFloorimage(BookShelfEntity bookshelfEntity);
	
	public abstract int uploadPoint(String lib_id,String fileName,int point_type);

}
